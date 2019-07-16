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
        <div class="table-page-search-wrapper">
          <a-form layout="inline" :form="form">
            <a-row :gutter="10">
              <a-col :md="9" :sm="24">
                <a-form-item label="设备编号">
                  {{RelationShip.equipmentId}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="所属代理">
                  {{RelationShip.departname}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="客户名">
                  {{RelationShip.clientName}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="硬件编号">
                  {{RelationShip.ids}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="启用状态">
                  {{RelationShip.enabled_dictText}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="租赁状态">
                  {{RelationShip.leasestate_dictText}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="商品名称">
                  {{RelationShip.commodityName}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="主板状态">
                  {{RelationShip.mainboard_dictText}}
                </a-form-item>
              </a-col>
              <a-col :md="9" :sm="24">
                <a-form-item label="滤芯状态">
                  {{RelationShip.filterelementType_dictText}}
                </a-form-item>
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
            :dataSource="dataSource">
            <span slot="action" slot-scope="text, record">
          <a @click="filtereleReplace(record.recordId)">更换</a>
            </span>
          </a-table>
        </div>
      </div>

    </a-drawer>
  </a-card>

</template>

<script>

  import {filterObj} from '@/utils/util';
  import  RelationshipListModel from '../modules/RelationshipListModel'
  import { getAction,putAction} from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "RelationshipListModal",
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
            title: '滤芯剩余使用天数',
            align: "center",
            dataIndex: 'remaining',
          },
          {
            title: '滤芯已使用天数',
            align: "center",
            dataIndex: 'used',
          },
          {
            title: '是否提醒',
            align: "center",
            dataIndex: 'remind_dictText',
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
        RelationShip:{},
        recordId:"",
        equipmentId:"",
        dataSource:[],
        Query:{},
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
          // list: "/dome/relationship/list",
          // delete: "/dome/relationship/delete",
          equipmentlist: "/demo/equipment/equipmentlist",
        },
      }
    },
    created() {
      // 当页面初始化时,根据屏幕大小来给抽屉设置宽度
      this.resetScreenSize();
    },
    methods: {
      filtereleReplace(recordId){
        var C ={"recordId":recordId};
        this.loading = true;
        putAction("/dome/filterelementReplace/relation",C ).then((res) => {
          if (res.success) {
            this.$message.success(res.message);
          }else{
            this.$message.warning(res.message);
          }
        }).finally(() => {
          this.relation(this.equipmentId);
          this.loading = false;
        })
      },
      relation(equipmentId){
        this.equipmentId = equipmentId;
        this.Quary(equipmentId);
        var  A ={"equipmentId":equipmentId};
        getAction(this.url.equipmentlist,A ).then((res) => {
          var RelationShip = res.result.records;
          this.RelationShip = RelationShip[0];
          console.log(this.RelationShip);
        })
      },
      Quary(equipmentId){
        var F ={"equipmentId":equipmentId};
        getAction("/demo/filterelement/query",F).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            console.log(this.dataSource);
          }
        });
      },
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
        console.log(this.model.typeId);
        this.visible = true;
        // 当其它模块调用该模块时,调用此方法加载字典数据
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
        this.visible = false;
        this.form.resetFields();
        this.dataSource = [];
      },
      // 抽屉的宽度随着屏幕大小来改变
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 600) {
          this.screenWidth = screenWidth;
        } else {
          this.screenWidth = 900;
        }
      },
    }
  }
</script>
<style scoped>
</style>