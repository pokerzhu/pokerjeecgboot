<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <!--<a-form-item-->
          <!--:labelCol="labelCol"-->
          <!--:wrapperCol="wrapperCol"-->
          <!--label="设备编号">-->
          <!--<a-input placeholder="请输入设备编号" v-decorator="['equipmentId', validatorRules.equipmentId ]" />-->
        <!--</a-form-item>-->
        <!--<a-form-item-->
          <!--:labelCol="labelCol"-->
          <!--:wrapperCol="wrapperCol"-->
          <!--label="商品编号，商品表外键">-->
          <!--<a-input placeholder="请输入商品编号，商品表外键" v-decorator="['commodityId', validatorRules.commodityId ]" />-->
        <!--</a-form-item>-->
        <!--<a-form-item-->
          <!--:labelCol="labelCol"-->
          <!--:wrapperCol="wrapperCol"-->
          <!--label="安装客户编号，客户表外键。">-->
          <!--<a-input placeholder="请输入安装客户编号，客户表外键。" v-decorator="['clientId', validatorRules.clientId ]" />-->
        <!--</a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属代理">
          <a-input placeholder="请输入所属代理" v-decorator="['ids',validatorRules.ids ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="客户名">
          <a-input placeholder="请输入客户名" v-decorator="['ids',validatorRules.ids ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="硬件编号">
          <a-input placeholder="请输入硬件编号" v-decorator="['ids',validatorRules.ids ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否启用，字典">
          <a-input-number v-decorator="[ 'enabled', validatorRules.enabled ]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "EquipmentModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        equipmentId:{rules: [{ required: true, message: '请输入设备编号!' }]},
        commodityId:{rules: [{ required: true, message: '请输入商品编号，商品表外键!' }]},
        clientId:{rules: [{ required: true, message: '请输入安装客户编号，客户表外键。!' }]},
        ids:{rules: [{ required: true, message: '请输入硬件编号!' }]},
        enabled:{rules: [{ required: true, message: '请输入是否启用，字典!' }]},
        },
        url: {
          add: "/demo/equipment/add",
          edit: "/demo/equipment/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'equipmentId','commodityId','clientId','ids','enabled'))
		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>