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
        <a-spin :spinning="confirmLoading">
          <!--<a-form :form="form">
            <a-form-item label="所属代理" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select  placeholder="请选择所属代理"  v-decorator="[ 'userId',{}]" >
                <a-select-option v-for="(selpurpose) in SysDepartList"
                                 :value="selpurpose.id">
                  {{ selpurpose.departName}}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-form>-->

          <a-form :form="form">
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select  placeholder="请选择商品名称"  v-decorator="[ 'commodityId',{}]" >
                <a-select-option v-for="(selpurpose) in CommodityList"
                                 :value="selpurpose.commodityId">
                  {{ selpurpose.commodityName }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-form>

          <!--<a-form :form="form">-->
          <!--<a-form-item label="状态是否启用" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--<a-select  placeholder="状态是否启用"  v-decorator="[ 'enabled',{initialValue:'001'}]" >-->
          <!--<a-select-option v-for="(selpurpose) in "-->
          <!--:value="selpurpose.filterelementId">-->
          <!--{{ selpurpose }}-->
          <!--</a-select-option>-->
          <!--</a-select>-->
          <!--</a-form-item>-->
          <!--</a-form>-->
        </a-spin>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="硬件编号">
          <a-input placeholder="请输入硬件编号" v-decorator="['ids',validatorRules.ids ]" />
        </a-form-item>

        <a-form :form="form">
          <a-form-item label="是否启用" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select  placeholder="请选择是否激活"  v-decorator="[ 'enabled',{}]" >
              <a-select-option :value="0">  未激活 </a-select-option>
              <a-select-option :value="1">  已激活 </a-select-option>
            </a-select>
          </a-form-item>
        </a-form>

        <a-form :form="form">
          <a-form-item label="租赁状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select  placeholder="请选择租赁状态"  v-decorator="[ 'leasestate',{}]" >
              <a-select-option :value="0">  租 </a-select-option>
              <a-select-option :value="1">  卖 </a-select-option>
            </a-select>
          </a-form-item>
        </a-form>
      </a-form>
    </a-spin>

  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import { getAction,postAction} from '@/api/manage'
  import pick from 'lodash.pick'
  // import moment from "moment"

  export default {
    name: "EquipmentModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        equipmentId:"",
        leasestate:"",
        ClientList:[],
        SysDepartList:[],
        CommodityList:[],
        status: 1,
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
          userId:{rules: [{ required: true, message: '请输入所属代理!' }]},
          clientId:{rules: [{ required: true, message: '请输入客户名。!' }]},
          ids:{rules: [{ required: true, message: '请输入硬件编号!' }]},
          enabled:{rules: [{ required: true, message: '请输入是否启用!' }]},
          leasestate:{rules: [{ required: true, message: '请输入租赁状态!' }]},
        },
        url: {
          add: "/demo/equipment/add",
          edit: "/demo/equipment/edit",
        },
      }
    },
    created () {
      this.selectDepart();
      this.sleClient();
      this.selCommodity();
    },
    methods: {
      selectDepart(){
        getAction("/sysdepart/sysDepart/selDepart",null).then((res) => {
          this.SysDepartList = res;
        })
      },
      sleClient(){
        getAction("/demo/client/selClient",null).then((res) => {
          this.ClientList = res;
        })
      },
      selCommodity(){
        getAction("/commodity/commodity/selCommodity",null).then((res) => {
          this.CommodityList = res;
        })
      },
      add () {
        this.edit({});
      },
      edit (record) {
        console.log(record+"************************************")
        this.form.resetFields();
        this.model = Object.assign({},record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'userId','commodityId','ids','enabled','leasestate'))
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
            if(!this.model.equipmentId){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
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