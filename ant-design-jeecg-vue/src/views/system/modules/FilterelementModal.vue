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
          label="编号">
          <a-input placeholder="请输入编号" v-decorator="['filterelementId', validatorRules.filterelementId ]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="滤芯名称">
          <a-input placeholder="请输入滤芯名称" v-decorator="['filterelementName', validatorRules.filterelementName ]" />
        </a-form-item>
        <a-form-item label="滤芯展示图" :labelCol="labelCol" :wrapperCol="wrapperCol"><!--v-decorator="[ 'thumbnail', validatorRules.thumbnail]"-->
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
        <!--<a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="滤芯图">
          <a-input placeholder="请输入滤芯图" v-decorator="['images', validatorRules.images ]" />
        </a-form-item>-->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="有效时长">
          <a-input-number style="width: 150px" placeholder="请输入有效时长" :min="1" v-decorator="['validity', validatorRules.validity ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="滤芯最低更换天数">
          <a-input-number style="width: 150px" placeholder="请输入滤芯最低更换天数" :min="1" v-decorator="['replacementdays', validatorRules.replacementdays ]" />
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
    name: "FilterelementModal",
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
        filterelementId:{rules: [{ required: true, message: '请输入编号!' }]},
        filterelementName:{rules: [{ required: true, message: '请输入滤芯名称!' }]},
        images:{rules: [{ required: true, message: '请输入滤芯图!' }]},
        validity:{rules: [{ required: true, message: '请输入有效时长!' }]},
        replacementdays:{rules: [{ required: true, message: '请输入滤芯最低更换时间！' }]}
        },
        url: {
          add: "/demo/filterelement/add",
          edit: "/demo/filterelement/edit",
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['domianURL']+"/sys/common/view",
        },
      }
    },
    created () {
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
        if(record.filterelementName){
          this.picUrl = "Has no pic url yet";
        }
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'filterelementId','filterelementName','images','validity','replacementdays'))
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
            if(!this.model.filterelementId){
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
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>

</style>