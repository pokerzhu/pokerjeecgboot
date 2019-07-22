<template>
  <a-card :bordered="false">
    <!-- 抽屉 -->
    <a-drawer
      title="设备详情"
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
          </a-table>
        </div>
      </div>
    </a-drawer>
    <RelationshipList-model ref="modalForm" @ok="modalFormOk"></RelationshipList-model>
  </a-card>
</template>

<script>
  import {filterObj} from '@/utils/util';
  import {JeecgListMixin } from '@/mixins/JeecgListMixin'
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
            title: '滤芯可用天数',
            align: "center",
            dataIndex: 'validity',
          },
          {
            title: '滤芯最低更换天数',
            align: "center",
            dataIndex: 'replacementdays',
          },
          {
          title: '操作',
          dataIndex: 'action',
          align: "center",
          scopedSlots: {customRender: 'action'},
          },
        ],
        queryParam: {
          typeId: "",
        },
        title: "操作",
        visible: false,
        screenWidth: 800,
        model: {},
        typeId: "",//类型id
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
        this.typeId = id;
        this.edit({});
      },
      edit(record) {
        this.typeId = record.typeId;
        /*console.log(this.commodityId+"1111")*/
        this.queryParam.typeId=record.typeId;
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.typeId = this.typeId;
        console.log(this.model.typeId)
        this.visible = true;
        // 当其它模块调用该模块时,调用此方法加载字典数据
        this.loadData();
      },
      getQueryParams() {
        var param = Object.assign({}, this.queryParam);
        param.typeId = this.typeId;
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      // 添加字典数据
      handleAdd() {
        console.log(this.typeId);
        this.$refs.modalForm.visible=true;
        this.$refs.modalForm.typeId=this.typeId;
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