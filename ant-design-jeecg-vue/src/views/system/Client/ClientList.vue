<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <!--<a-col :md="6" :sm="8">
            <a-form-item label="地址">
              <a-input placeholder="请输入地址" v-model="queryParam.address"></a-input>
            </a-form-item>
          </a-col>-->
          <!--<a-col :md="6" :sm="8">
            <a-form-item label="编号">
              <a-input placeholder="请输入编号" v-model="queryParam.clientId"></a-input>
            </a-form-item>
          </a-col>-->
       <!-- <template v-if="toggleSearchStatus">-->
        <a-col :md="6" :sm="8">
            <a-form-item label="用户名">
              <a-input placeholder="请输入用户名" v-model="queryParam.clientName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="手机号码">
              <a-input placeholder="请输入手机号码" v-model="queryParam.phone"></a-input>
            </a-form-item>
          </a-col>
        <!--</template>-->
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('客户表')">导出</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>-->
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
          <a @click="handleEdit(record)">
            <a-icon type="edit"/>
            编辑
          </a>
              <!--<a @click="ClientFF(record)">-->
            <!--<a-icon type="edit"/>-->
            <!--详情-->
          <!--</a>-->
          <a-divider type="vertical"/>
          <a @click="ClientEdit(record)"><a-icon type="setting"/>可用设备</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() =>handleDelete(record.clientId)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <client-modal ref="modalForm" @ok="modalFormOk"></client-modal>
    <client-item-list ref="clientitemlist"></client-item-list>
    <ClientXQ ref="clientxq"></ClientXQ>
  </a-card>
</template>

<script>
  import { filterObj } from '@/utils/util';
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ClientModal from '../modules/ClientModal'
  import ClientItemList from './ClientItemList'
  import ClientXQ from "../modules/ClientXQ";
  export default {
    name: "ClientList",
    mixins:[JeecgListMixin],
    components: {
      ClientModal,
      ClientItemList,
      ClientXQ,
    },
    data () {
      return {
        description: '客户表管理页面',
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
		   // {
       //   title: '编号',
       //   align:"center",
       //   dataIndex: 'clientId'
       //     },
		   {
         title: '用户名',
         align:"center",
         dataIndex: 'clientName'
           },
		   {
         title: '密码',
         align:"center",
         dataIndex: 'password'
           },
		   {
         title: '手机号',
         align:"center",
         dataIndex: 'phone'
           },
		   {
         title: '地址',
         align:"center",
         dataIndex: 'address'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/demo/client/list",
          delete: "/demo/client/delete",
          deleteBatch: "/demo/client/deleteBatch",
          exportXlsUrl: "demo/client/exportXls",
          importExcelUrl: "demo/client/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      getQueryParams() {
        var param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      //取消选择
      cancelDict() {
        this.dict = "";
        this.visible = false;
        this.loadData();
      },
      //编辑字典数据
      ClientEdit(record) {
        console.log(record.clientId)
        this.$refs.clientitemlist.edit(record);
      },
      //编辑字典数据
      ClientFF(record) {
        this.$refs.clientxq.edit(record);
      },
      // // 重置字典类型搜索框的内容
      // searchReset() {
      //   var that = this;
      //   that.queryParam.dictName = "";
      //   that.queryParam.dictCode = "";
      //   that.loadData(this.ipagination.current);
      // },
    },
    watch: {
      openKeys(val) {
        console.log('openKeys', val)
      },
    },
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>