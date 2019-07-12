<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleequipment" type="primary" icon="plus">新增客户信息</a-button>
    </div>

    <a-spin :spinning="confirmLoading">
    <a-form :form="form">
      <a-form-item label="客户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-select  placeholder="请选择客户名"  v-decorator="[ 'clientId',{}]" >
          <a-select-option v-for="(selpurpose) in ClientList" :value="selpurpose.clientId">
            {{ selpurpose.clientName }}
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
    </a-spin>
    <equipment-client-add ref="clientmodal" ></equipment-client-add>
  </a-modal>
</template>

<script>
  import { httpAction,putAction } from '@/api/manage'
  import { getAction,postAction} from '@/api/manage'
  import EquipmentClientAdd from '../modules/EquipmentClientAdd'
  import pick from 'lodash.pick'
  export default {
    name: "EquipmentClientXZ",
    components: {
      EquipmentClientAdd
    },
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
        ClientList:[],
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
          clientName:{rules: [{ required: true, message: '请输入clientName!' }]},
        },
        url: {
          add: "/demo/equipment/editA",
          // edit: "/demo/equipment/editA",
        },
      }
    },
    created () {
      this.sleClient();
    },
    methods: {
      add (equipmentId) {
        this.visible=true;
        this.equipmentId=equipmentId;
        console.log(equipmentId+"111111111111111");
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.loadData();
      },
      sleClient(){
        getAction("/demo/client/selClient",null).then((res) => {
          this.ClientList = res;
        })
      },
      // 添加客户信息
      handleequipment() {
        console.log(this.equipmentId);
        this.$refs.clientmodal.add(this.equipmentId);
        this.$refs.clientmodal.title="请填写安装客户信息";
        this.close();
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err,values) => {
          if (!err) {
            values.equipmentId=this.equipmentId;
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            httpurl+=this.url.add;
            method = 'put';
            let formData = Object.assign(this.model,values);
            putAction(this.url.add, formData).then((res) => {//更新设备信息，绑定客户id
              if (res.success) {
                this.$message.success(res.message);
                that.$emit('ok');
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