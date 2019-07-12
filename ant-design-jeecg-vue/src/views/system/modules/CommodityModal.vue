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

        <!-- <a-form-item
           :labelCol="labelCol"
           :wrapperCol="wrapperCol"
           label="商品编号">
           <a-input placeholder="请输入商品编号" v-decorator="['commodityId', validatorRules.commodityId ]" />
         </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品名称">
          <a-input placeholder="请输入商品名称" v-decorator="['commodityName', validatorRules.commodityName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品价格">
          <a-input placeholder="请输入商品价格" v-decorator="['commodityPrices', validatorRules.commodityPrices ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品租价">
          <a-input placeholder="请输入商品租价" v-decorator="['commodityRent', validatorRules.commodityRent ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品描述">
          <a-input placeholder="请输入商品描述" v-decorator="['description', validatorRules.description ]" />
        </a-form-item>
        <a-form-item label="商品缩略图" :labelCol="labelCol" :wrapperCol="wrapperCol"><!--v-decorator="[ 'thumbnail', validatorRules.thumbnail]"-->
          <a-upload
            listType="picture-card"
            class="thumbnail-uploader"
            :showUploadList="false"
            :action="uploadAction"
            :data="{'isup':1}"
            :headers="headers"
            :beforeUpload="beforeUpload"
            @change="handleChange"
          >
            <img  v-if="picUrl" :src="getThumbnailView()"  alt="缩略图" style="height:104px;max-width:300px"/>
            <div v-else>
              <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">上传</div>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品类型">
          <a-input-number v-decorator="[ 'type', validatorRules.type ]" />
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
    name: "CommodityModal",
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
        headers:{},
        uploadLoading:false,
        confirmLoading: false,
        picUrl: "",
        form: this.$form.createForm(this),
        validatorRules:{
          commodityId:{rules: [{ required: true, message: '请输入商品编号!' }]},
          commodityName:{rules: [{ required: true, message: '请输入商品名称!' }]},
          commodityPrices:{rules: [{ required: true, message: '请输入商品价格!' }]},
          commodityRent:{rules: [{ required: true, message: '请输入商品租价!' }]},
          description:{rules: [{ required: true, message: '请输入商品描述!' }]},
          images:{rules: [{ required: true, message: '请输入商品图片!' }]},
          type:{rules: [{ required: true, message: '请输入商品类型!' }]},
        },
        url: {
          add: "/commodity/commodity/add",
          edit: "/commodity/commodity/edit",
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
        },
      }
    },
    created () {
      this.getThumbnailView();
    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.picUrl = "Has no pic url yet";
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'commodityId','commodityName','commodityPrices','commodityRent','description','images','type'))
          //时间格式化
        });
      },
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      handleChange (info) {
        this.picUrl = "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          console.log(response);
          if(response.success){
            this.model.images = response.message;
            this.picUrl = "Has no pic url yet";
          }else{
            this.$message.warning(response.message);
          }
        }
      },
      getThumbnailView(){
        console.log(this.url.imgerver +"/"+ this.model.images)
        return this.url.imgerver +"/"+ this.model.images;
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
            if(!this.model.commodityId){
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