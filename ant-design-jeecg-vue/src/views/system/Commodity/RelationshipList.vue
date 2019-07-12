<template>
  <a-card :bordered="false">
    <!-- 抽屉 -->
    <a-drawer
      title="规格列表"
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

        <div class="table-page-search-wrapper">
          <a-form layout="inline" :form="form">
            <a-row>
              <a-col :md="2" :sm="24">
                <a-button style="margin-bottom: 10px" type="primary" @click="handleAdd">新增滤芯</a-button>
              </a-col>
            </a-row>
          </a-form>
        </div>
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
            <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.relationshipId)">
              <a>删除</a>
            </a-popconfirm>
          </span>
          </a-table>
        </div>
      </div>
    </a-drawer>
    <RelationshipList-model ref="modalForm" @ok="modalFormOk"></RelationshipList-model>
  </a-card>
</template>

<script>
  import {filterObj} from '@/utils/util';
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import  RelationshipListModel from '../modules/RelationshipListModel'
  export default {
    name: "AttributeList",
    mixins: [JeecgListMixin],
    components: {RelationshipListModel},
    data() {
      return {
        columns: [
          {
            title: '使用滤芯',
            align: "center",
            dataIndex: 'filterelementName',
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'},
          }
        ],
        queryParam: {
          commodityId: "",
        },
        title: "操作",
        visible: false,
        screenWidth: 800,
        model: {},
        commodityId: "",
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
          value: {rules: [{required: true, message: '请输入规格值!'}]},
        },
        url: {
          list: "/dome/relationship/list",
          delete: "/dome/relationship/delete",
        },
      }
    },
    created() {
      // 当页面初始化时,根据屏幕大小来给抽屉设置宽度
      this.resetScreenSize();
    },
    methods: {
      add(id) {
        this.commodityId = id;
        this.edit({});
      },
      edit(record) {
        this.commodityId = record.commodityId;
        /*console.log(this.commodityId+"1111")*/
        this.queryParam.commodityId=record.commodityId;
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.commodityId = this.commodityId;
        console.log(this.model.commodityId)
        this.visible = true;
        // 当其它模块调用该模块时,调用此方法加载字典数据
        this.loadData();
      },
      getQueryParams() {
        var param = Object.assign({}, this.queryParam);
        param.commodityId = this.commodityId;
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      // 添加字典数据
      handleAdd() {
        console.log(this.commodityId);
        this.$refs.modalForm.visible=true;
        this.$refs.modalForm.commodityId=this.commodityId;
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