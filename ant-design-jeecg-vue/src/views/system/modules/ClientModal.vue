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
          <a-input  placeholder="请输入密码" v-decorator="['password', validatorRules.password ]" />
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
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import {checkClient } from '@/api/api'

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

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        address:{rules: [{ required: true, message: '请输入客户地址!' }]},
        clientName:{rules: [{ required: true, message: '请输入客户名!' }]},
        password:{rules: [{ required: true, message: '请输入密码!' }]},
        phone:{rules: [{validator: this.validatePhone}]},
        },
        url: {
          add: "/demo/client/add",
          edit: "/demo/client/edit",
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
          this.form.setFieldsValue(pick(this.model,'address','clientId','clientName','open','password','phone'))
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
            if(!this.model.clientId){
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
      validatePhone(rule, value, callback){
        if(!value || new RegExp(/^1[3|4|5|7|8][0-9]\d{8}$/).test(value)){
          var params = {
            clientId:this.model.clientId,
            phone:value
          };
          checkClient(params).then((res)=>{
            if(res.success){
              callback();
            }else{
              callback("该手机号码已被使用，请输入其他手机号码！");
            }
          });
        }else{
          callback("请输入正确格式的手机号码!");
        }
      },
    }
  }
</script>

<style lang="less" scoped>

</style>