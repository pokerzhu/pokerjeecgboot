<template>
  <a-card :bordered="false">
    <!--商品列表页-->
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="商品编号">
              <a-input placeholder="请输入商品编号" v-model="queryParam.commodityId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="商品名称">
              <a-input placeholder="请输入商品名称" v-model="queryParam.commodityName"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="商品价格">
              <a-input placeholder="请输入商品价格" v-model="queryParam.commodityPrices"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="商品租价">
              <a-input placeholder="请输入商品租价" v-model="queryParam.commodityRent"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="商品描述">
              <a-input placeholder="请输入商品描述" v-model="queryParam.description"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('商品表')">导出</a-button>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.images)" icon="user"/>
          </div>
        </template>
        <span slot="action" slot-scope="text, record">

          <a @click="handleEdit(record)">编辑</a>
           <a-divider type="vertical" />
          <a-dropdown>
          <a @click="editDictItem(record)"><a-icon type="setting"/>商品使用滤芯</a>
          </a-dropdown>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.commodityId)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <commodity-modal ref="modalForm" @ok="modalFormOk"></commodity-modal>
    <RelationshipList ref="relationshipList" ></RelationshipList>
  </a-card>
</template>

<script>
  import { filterObj } from '@/utils/util';
  import CommodityModal from '../modules/CommodityModal'
  import RelationshipList from '../Commodity/RelationshipList'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "CommodityList",
    mixins:[JeecgListMixin],
    components: {
      CommodityModal,
      RelationshipList
    },
    data () {
      return {
        description: '商品表管理页面',
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
           },/*,
		   {
            title: '商品编号',
            align:"center",
            dataIndex: 'commodityId'
           }*/
		   {
            title: '商品名称',
            align:"center",
            dataIndex: 'commodityName'
           },
		   {
            title: '商品价格',
            align:"center",
            dataIndex: 'commodityPrices'
           },
		   {
            title: '商品租价',
            align:"center",
            dataIndex: 'commodityRent'
           },
		   {
            title: '商品描述',
            align:"center",
            dataIndex: 'description'
           },
          {
            title: '商品缩略图',
            align: "center",
            dataIndex: 'images',
            scopedSlots: {customRender: "avatarslot"}
          },
		   {
            title: '商品类型',
            align:"center",
            dataIndex: 'typeName'
           },
          /*{
            title: '创建人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title: '创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '更新人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title: '更新时间',
            align:"center",
            dataIndex: 'updateTime'
          },*/
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/commodity/commodity/list",
          delete: "/commodity/commodity/delete",
          deleteBatch: "/commodity/commodity/deleteBatch",
          exportXlsUrl: "commodity/commodity/exportXls",
          importExcelUrl: "commodity/commodity/importExcel",
          imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      editDictItem(record) {
        this.$refs.relationshipList.edit(record);
      },
      getAvatarView: function (avatar) {
        return this.url.imgerver + "/" + avatar;
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>