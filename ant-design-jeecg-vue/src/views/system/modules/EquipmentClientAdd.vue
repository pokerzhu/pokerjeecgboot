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
          label="地址">
          <a-input placeholder="请输入地址" v-decorator="['address', validatorRules.address ]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="编号">
          <a-input placeholder="请输入编号" v-decorator="['clientId', validatorRules.clientId ]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户名">
          <a-input placeholder="请输入用户名" v-decorator="['clientName', validatorRules.clientName ]" />
        </a-form-item>
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开关设备编号">
          <a-input placeholder="请输入开关设备编号" v-decorator="['open', {}]" />
        </a-form-item>-->
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

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction,putAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "ClientModal",
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
        equipmentId:"",
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          address:{rules: [{ required: true, message: '请输入address!' }]},
          clientId:{rules: [{ required: true, message: '请输入clientId!' }]},
          clientName:{rules: [{ required: true, message: '请输入clientName!' }]},
          password:{rules: [{ required: true, message: '请输入password!' }]},
          phone:{rules: [{ required: true, message: '请输入phone!' }]},
        },
        url: {
          add: "/demo/client/add",
          edit: "/demo/client/edit",
          updateequipment:"/demo/equipment/updateequipment",
        },
      }
    },
    created () {
    },
    methods: {
      add (equipmentId) {
        this.equipmentId=equipmentId;
        console.log(equipmentId);
        this.visible=true;
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
                var record = {"equipmentId":this.equipmentId,"clientId":clientId};
                putAction(this.url.updateequipment, record).then((res) => {//更新设备信息，绑定客户id
                  if (res.success) {
                    console.log(this.dataSource);
                    this.$message.success(res.message);
                    that.$emit('ok');
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
              this.loadData();
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