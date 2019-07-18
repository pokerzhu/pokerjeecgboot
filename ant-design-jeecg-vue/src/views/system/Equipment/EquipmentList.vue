<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="设备编号">
              <a-input placeholder="请输入设备编号" v-model="queryParam.equipmentId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="硬件编号">
              <a-input placeholder="请输入硬件编号" v-model="queryParam.ids"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="安装状态">
              <a-select  placeholder="请选择安装状态" v-model="queryParam.enabled">
                <a-select-option :value="0">  未激活 </a-select-option>
                <a-select-option :value="1">  已激活 </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="likeEquipment" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('设备表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-if="record.clientName==null&&record.leasestate==0">编辑</a>

          <a-divider type="vertical" />
          <a @click="handleDetailDemo(record.equipmentId)">详情</a>

          <a-divider type="vertical" />
          <a-dropdown v-if="record.leasestate=='0'">
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-if="record.clientName==null">
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.equipmentId)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
               <a-menu-item v-if="record.clientName==null">
                <a-popconfirm  title="确定安装吗?" @confirm="() =>handleAddequipment(record.equipmentId,record.commodityId)">
                  <a>安装</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item v-if="record.clientName!=null&&record.leasestate=='0'">
                <a-popconfirm title="确定回收吗?" @confirm="() =>handEquipment(record.equipmentId)">
                  <a>回收</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>

          </a-dropdown>
        </span>
        <span slot="leasestate" slot-scope="text, record">
                {{record.leasestate==0?record.commodityRent:record.commodityPrices}}
        </span>
        <span slot="filtere" slot-scope="text, record">
               <a v-if="record.filterelementType==0" @click="handleDetailDemo(record.equipmentId)">异常</a>
                <span v-if="record.filterelementType==1" >正常</span>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <equipment-modal ref="modalForm" @ok="modalFormOk"></equipment-modal>
    <equipment-client-x-z ref="equipmentclientxz" @ok="modalFormOk"></equipment-client-x-z>
    <relationship-list-model ref="relation" @ok="modalFormOk"></relationship-list-model>
  </a-card>
</template>

<script>
  import EquipmentModal from '../modules/EquipmentModal'
  import {JeecgListMixin } from '@/mixins/JeecgListMixin'
  import EquipmentClientXZ from '../modules/EquipmentClientXZ'
  import {httpAction,putAction } from '@/api/manage'
  import RelationshipListModel from '../modules/RelationshipListModel'
  import {getAction} from '@/api/manage'

  export default {
    name: "EquipmentList",
    mixins:[JeecgListMixin],
    components: {//EquipmentXqModal,
      EquipmentModal,EquipmentClientXZ,RelationshipListModel
    },
    data () {
      return {
        description: '设备表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
          {
            title: '设备编号',
            align:"center",
            dataIndex: 'equipmentId'
          },
          // {
          //   title: '商品编号',
          //   align:"center",
          //   dataIndex: 'commodityId'
          // },
          {
            title: '商品名称',
            align:"center",
            dataIndex: 'commodityName'
          },
          {
            title: '所属代理',
            align:"center",
            dataIndex: 'departname'
          },
          {
            title: '客户名',
            align:"center",
            dataIndex: 'clientName'
          },
          {
            title: '硬件编号',
            align:"center",
            dataIndex: 'ids'
          },
          {
            title: '启用状态',
            align:"center",
            dataIndex: 'enabled_dictText'
          },
          {
            title: '租赁状态',
            align:"center",
            dataIndex: 'leasestate_dictText'
          },
          {
            title: '价格',
            dataIndex: 'leasestate',
            align:"center",
            scopedSlots: { customRender: 'leasestate' },
          },
          {
            title: '主板状态',
            align:"center",
            dataIndex: 'mainboard_dictText'
          },
          // {
          //   title: '滤芯状态',
          //   align:"center",
          //   dataIndex: 'filterelementType_dictText'
          // },
          {
            title: '滤芯状态',
            dataIndex: 'filtere',
            align:"center",
            scopedSlots: { customRender: 'filtere' },
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        equipmentId:'',
        ids:'',
        enabled:'',
        queryParam:{},
        url: {
          list:   "/demo/equipment/list",
          delete: "/demo/equipment/delete",
          update: "/demo/equipment/editA",
          deleteBatch: "/demo/equipment/deleteBatch",
          exportXlsUrl: "demo/equipment/exportXls",
          importExcelUrl: "demo/equipment/importExcel",
        },

      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      likeEquipment(){
          var A =this.queryParam
          getAction("/demo/equipment/likeSelect",A).then((res) => {
            console.log(A+"55555555555");
            if (res.success) {
              this.dataSource = res.result.records;
              console.log(this.dataSource);
            }
          });
      },
      handleDetailDemo(equipmentId){
        this.$refs.relation.visible=true;
        this.$refs.relation.relation(equipmentId);
      },
      //取消选择
      cancelDict() {
        this.dict = "";
        this.visible = false;
        this.loadData();
      },
      // 添加字典数据
      handleAddequipment(equipmentId,commodityId) {
        this.$refs.equipmentclientxz.add(equipmentId,commodityId);
        this.$refs.equipmentclientxz.title="请选择客户";
      },
      handEquipment(equipmentId){
        this.confirmLoading = true;
            var record = {"equipmentId":equipmentId,"clientId":null,"lossratio":0};
            putAction(this.url.update,record).then((res) => {//撤回设备，删除设备客户关联记录
              if (res.success) {
                console.log(this.dataSource);
                this.$message.success(res.message);
                that.$emit('ok');
              }else{
                this.$message.warning(res.message);
              }
            }).finally(() => {
              this.confirmLoading = false;
              this.loadData();
            })
      },
      hx(){
        this.loadData();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>