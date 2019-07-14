<template>
  <a-card :bordered="false">
    <!-- 抽屉 -->
    <a-drawer
      title="设备列表"
      :width="screenWidth"
      @close="onClose"
      :visible="visible"
    >
      <!-- 抽屉内容的border -->
      <div
        :style="{
          padding:'10px',
          border: '1px solid #e9e9e9',
          background: '#fff',
        }">

        <!--<div class="table-page-search-wrapper">-->
          <!--<a-form layout="inline" :form="form">-->
            <!--<a-row>-->
              <!--<a-col :md="2" :sm="24">-->
                <!--<a-button style="margin-bottom: 10px" type="primary" @click="handleAdd">新增</a-button>-->
              <!--</a-col>-->
            <!--</a-row>-->
          <!--</a-form>-->
        <!--</div>-->
        <div>
          <a-table
            ref="table"
            rowKey="id"
            size="middle"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :loading="loading"
            @change="handleTableChange"
          >
          <span slot="action" slot-scope="text, record">
            <a @click="handleEdit(record)">
              <span v-if="record.open==0">授权</span>
              <span v-if="record.open==1">取消授权</span>
            </a>
           <!-- <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.openId)">
              <a>删除</a>
            </a-popconfirm>-->
          </span>
          </a-table>
        </div>
      </div>
    </a-drawer>
    <ClientItem-modal ref="modalForm" @ok="modalFormOk"></ClientItem-modal>
  </a-card>
</template>

<script>
  import pick from 'lodash.pick'
  import {filterObj} from '@/utils/util';
  import ClientItemModal from '../modules/ClientItemModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {putAction,} from '@/api/manage'

  export default {
    name: "ClientItemList",
    mixins: [JeecgListMixin],
    components: {ClientItemModal},
    data() {
      return {
        columns: [
          /*{
            title: 'ID',
            align: "center",
            dataIndex: 'openId',
          },*/
          {
            title: '商品名称',
            align: "center",
            dataIndex: 'commodityName',
          },
          {
            title: '设备编号',
            align: "center",
            dataIndex: 'equipmentId',
          },
          {
            title: '设备当前状态',
            align: "center",
            dataIndex: 'open_dictText',
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'},
          }
        ],
        queryParam: {
          clientId:""
        },
        title: "操作",
        visible: false,
        screenWidth: 800,
        model: {},
        openId: "",
        status: 1,
        labelCol: {
          xs: {span: 5},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 12},
          sm: {span: 12},
        },
        form: this.$form.createForm(this),
        validatorRules: {
          itemText: {rules: [{required: true, message: '请输入名称!'}]},
          itemValue: {rules: [{required: true, message: '请输入数据值!'}]},
        },
        url: {
          list: "/demo/installation/list",
          delete: "/demo/installation/delete",
          updateopen:"/demo/installation/updateopen",
        },
      }
    },
    created() {
      // 当页面初始化时,根据屏幕大小来给抽屉设置宽度
      this.resetScreenSize();
    },
    methods: {
      // add(dictId) {
      //   this.dictId = dictId;
      //   this.edit({});
      // },
      edit(record) {
        console.log(record.clientId)
        this.form.resetFields();
        this.model = Object.assign({},record);
        this.queryParam.clientId= record.clientId;
        this.model.clientId = this.clientId;//将客户编号存进model
        this.visible = true;
       /* this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'equipmentId'))
        });*/
        // 当其它模块调用该模块时,调用此方法加载字典数据
        this.loadData();
      },
      handleEdit(record){
        this.loading = true;
        /*let obj;上下两种皆可，
        obj=putAction(this.url.updateopen, record);
        obj.then((res) => {
          console.log(res);
          if (res.success) {
            this.$message.success(res.message);
            that.$emit('ok');
          } else {
            this.$message.warning(res.message);
          }
        }).finally(() => {
          this.loadData();
        })*/
        putAction(this.url.updateopen, record).then((res) => {
          if (res.success) {
            console.log(this.dataSource);
            this.$message.success(res.message);
          }
          this.loading = false;
          this.loadData();
        })
      },
      getQueryParams() {
        var param = Object.assign({}, this.queryParam);
        param.dictId = this.dictId;
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },

      // 添加字典数据
      handleAdd() {
        this.$refs.modalForm.visible=true;
        this.$refs.modalForm.title = "新增";
      },
      showDrawer() {
        this.visible = true
      },
      onClose() {
        this.visible = false
        this.form.resetFields();
        this.dataSource = [];
      },
      // 抽屉的宽度随着屏幕大小来改变
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 600) {
          this.screenWidth = screenWidth;
        } else {
          this.screenWidth = 600;
        }
      },
    }
  }
</script>
<style scoped>
</style>