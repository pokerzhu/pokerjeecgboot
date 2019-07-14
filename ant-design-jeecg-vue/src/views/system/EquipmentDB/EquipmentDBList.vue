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
            <a-form-item label="商品编号，商品表外键">
              <a-input placeholder="请输入商品编号，商品表外键" v-model="queryParam.commodityId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="安装客户编号，客户表外键。">
                <a-input placeholder="请输入安装客户编号，客户表外键。" v-model="queryParam.clientId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="硬件编号">
                <a-input placeholder="请输入硬件编号" v-model="queryParam.ids"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="安装状态">
                <a-input placeholder="请输入是否安装" v-model="queryParam.enabled"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button type="primary" icon="download" @click="handleExportXls('设备表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>调拨</a-menu-item>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">调拨</a>

          <a-divider type="vertical" />
        </span>
        <span slot="leasestate" slot-scope="text, record">
                {{record.leasestate==0?record.commodityRent:record.commodityPrices}}
        </span>

        <span slot="filtere" slot-scope="text, record">
               <a v-if="record.filterelementType==0" @click="handleDetailDemo(record)">异常</a>
                <span v-if="record.filterelementType==1" >正常</span>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <equipment-modal ref="modalForm" @ok="modalFormOk"></equipment-modal>
    <!--<equipment-client-add ref="clientmodal"></equipment-client-add>-->
    <equipment-client-x-z ref="equipmentclientxz" @ok="modalFormOk"></equipment-client-x-z>
    <equipment-xq-modal ref="equipmentXq"></equipment-xq-modal>
  </a-card>
</template>

<script>
  import EquipmentModal from '../modules/EquipmentModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import EquipmentXqModal from '../modules/EquipmentXqModal'
  import EquipmentClientXZ from '../modules/EquipmentClientXZ'
  import { httpAction,putAction } from '@/api/manage'

  export default {
    name: "EquipmentList",
    mixins:[JeecgListMixin],
    components: {
      EquipmentModal,EquipmentClientXZ,EquipmentXqModal,
    },
    data () {
      return {
        description: '设备调拨页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          //  },
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
            dataIndex: 'realname'
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
        url: {
          list:   "/demo/quipmentDb/list",
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleDetailDemo(record){
        this.$refs.equipmentXq.visible=true;
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
        var record = {"equipmentId":equipmentId,"clientId":null};
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