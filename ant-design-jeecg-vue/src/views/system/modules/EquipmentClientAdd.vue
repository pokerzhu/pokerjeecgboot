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
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户名">
          <a-input placeholder="请输入用户名" v-decorator="['clientName', validatorRules.clientName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地址">
          <a-input placeholder="请输入地址" v-decorator="['address', validatorRules.address ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="密码">
          <a-input placeholder="请输入密码" v-decorator="['password', validatorRules.password ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机号">
          <a-input placeholder="请输入手机号" v-decorator="['phone', validatorRules.phone ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="特殊地区设备损耗比例">
          <a-input placeholder="请输入损耗比例" v-model="lossratio" v-decorator="['lossratio', validatorRules.lossratio ]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="编号">
          <a-input placeholder="请输入编号" v-decorator="['clientId', validatorRules.clientId ]" />
        </a-form-item>-->
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开关设备编号">
          <a-input placeholder="请输入开关设备编号" v-decorator="['open', {}]" />
        </a-form-item>-->
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,putAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "EquipmentClientAdd",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        lossratio:'',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        commodityId:"",
        equipmentId:"",
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          lossratio:{rules: [{ required: true, message: '请输入损耗比例!' }]},
          address:{rules: [{ required: true, message: '请输入安装地址!' }]},
          clientName:{rules: [{ required: true, message: '请输入用户名!' }]},
          password:{rules: [{ required: true, message: '请输入密码!' }]},
          phone:{rules: [{ required: true, message: '请输入手机号码!' }]},
        },
        url: {
          add: "/demo/client/add",
          edit: "/demo/client/edit",
          updateequipment:"/demo/equipment/editA",
        },
      }
    },
    created () {
    },
    methods: {
      add (equipmentId,commodityId) {
        this.commodityId = commodityId;
        this.equipmentId=equipmentId;
        console.log(equipmentId);
        this.visible=true;
      },
      close () {
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
            if(!this.model.clientId){
              httpurl+=this.url.add;
              method = 'post';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            console.log(formData)

            httpAction(httpurl,formData,method).then((res)=>{
              console.log(res);
              if(res.success){//有success就表示添加客户信息成功，进行下一步操作更新设备客户信息
                var clientId = res.result.clientId;
                console.log(clientId);
                var record = {
                  "equipmentId":this.equipmentId,
                  "clientId":clientId,
                  "commodityId":this.commodityId,
                  "lossratio":this.lossratio,
                };
                console.log(record);
                putAction(this.url.updateequipment, record).then((res) => {//更新设备信息，绑定客户id
                  if (res.success) {
                    console.log(this.dataSource);
                    this.$message.success(res.message);
                    this.$emit('ok');
                  }else{
                    this.$message.warning(res.message);
                  }
                })
                /*that.$message.success(res.message);
                that.$emit('ok');*/
              }else{
                this.$message.warning(res.message);
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