<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="滤芯" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select  placeholder="请选择滤芯"  v-decorator="[ 'filterelementId',{initialValue:'001'}]" >
            <a-select-option v-for="(purpose) in purposeList" :value="purpose.filterelementId">
              {{ purpose.filterelementName }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import pick from 'lodash.pick'
  import { getAction,postAction} from '@/api/manage'

  export default {
    name: "AttributeListModal",
    data() {
      return {
        title: "操作",
        visible: false,
        visibleCheck: true,
        model: {},
        commodityId:"",
        purposeList:[],//滤芯
        status: 1,
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
          filterelementId : {rules: [{required: true, message: '请选择滤芯!'}]},
        },
      }
    },
    created() {
      this.selPurpose();
    },
    methods: {
      selPurpose(){
        getAction("/demo/filterelement/selpurpose",null).then((res) => {
          console.log(res);
          this.purposeList = res;
          console.log(this.commodityId);
          console.log(this.purposeList);
        })
      },
      // 确定
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            console.log(values.value);
            values.value = (values.value || '').trim()
            let formData = Object.assign(this.model, values);
            formData.commodityId=this.commodityId;
            console.log(formData);
            let obj;
            obj=postAction("/dome/relationship/add",formData);
            obj.then((res) => {
              console.log(res);
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
            /*obj = addAttributeValue(formData);
            obj.then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })*/
          }
        })
      },
      // 关闭
      handleCancel() {
        this.close();
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
    }
  }
</script>