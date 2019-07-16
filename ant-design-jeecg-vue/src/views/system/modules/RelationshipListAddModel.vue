<template>
  <div>
    <a-modal
      centered
      :title="title"
      :width="1000"
      :visible="visible"
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭">

      <!--&lt;!&ndash; 查询区域 &ndash;&gt;
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="24">

            <a-col :span="10">
              <a-form-item label="滤芯">
                <a-input placeholder="请输入滤芯名" v-model="queryParam.filterelementName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="10">
              <a-form-item label="滤芯编号">
                <a-input placeholder="请输入滤芯编号" v-model="queryParam.filterelementId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
                    <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                      <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                      <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                    </span>
            </a-col>

          </a-row>
        </a-form>
      </div>-->
      <!-- table区域-begin -->
      <div>
        <a-table
          size="small"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource1"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{selectedRowKeys: selectedRowKeys,onSelectAll:onSelectAll,onSelect:onSelect,onChange: onSelectChange}"
          @change="handleTableChange">

        </a-table>
      </div>
      <!-- table区域-end -->
    </a-modal>
  </div>
</template>

<script>
  import {filterObj} from '@/utils/util'
  import {getAction,postAction} from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "RelationshipListModel",
    data() {
      return {
        title: "添加已有滤芯",
        names: [],
        visible: false,
        placement: 'right',
        description: '',
        typeId:'',
        count:'',
        // 查询条件
        queryParam: {
          typeId:'',
        },
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
          },{
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
        ],
        //数据集
        dataSource1: [],
        dataSource2: [],
        // 分页参数
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter: {
          column: 'createTime',
          order: 'desc',
        },
        loading: false,
        selectedRowKeys: [],
        selectedRows: [],
        url: {
          selectbytypeId: "/demo/filterelement/selectbytypeId",
          add:"/dome/relationship/add",
        }
      }
    },
    created() {
    },
    methods: {
      searchQuery() {
        this.loadData(1);
      },
      searchReset() {
        this.queryParam = {};
        this.loadData(1);
      },
      handleCancel() {
        this.onClearSelected();
        this.visible = false;
      },
      handleOk() {
        console.log("data:" + this.dataSource2);
        /*this.selectOK(this.dataSource2);*/
        /*this.visible = false;*/
        let params = {};
        params.typeId = this.typeId;
        params.filterelementIds = '';
        for (var a = 0; a < this.dataSource2.length; a++) {
          params.filterelementIds += this.dataSource2[a].filterelementId + ','
        }
        /*for (var a = 0; a < this.dataSource2.length; a++) {
          params.userIdList.push(this.dataSource2[a].filterelementId);
        }*/
        console.log(params);
        postAction(this.url.add, params).then((res) => {
          if (res.success) {
            this.$message.success(res.message);
            this.onClearSelected();
            this.$emit('ok');
          } else {
            this.$message.warning(res.message);
            this.onClearSelected();
          }
        })
        this.visible = false;
        this.onClearSelected();
      },
      add() {
        this.visible = true;
      },
      selectbytypeId() {
        var params = {"typeId":this.typeId};
        getAction(this.url.selectbytypeId, params).then((res) => {
          if (res.success) {
            this.dataSource1 = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },
      getQueryParams() {
        var param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        //TODO 字段权限控制
      },
      onSelectAll(selected, selectedRows, changeRows) {//判断点击全选事件发生后是否被选中
        if (selected === true) {
          for (var a = 0; a < changeRows.length; a++) {
            this.dataSource2.push(changeRows[a]);
            console.log(this.dataSource2)
          }
        } else {
          for (var b = 0; b < changeRows.length; b++) {
            this.dataSource2.splice(this.dataSource2.indexOf(changeRows[b]), 1);
            console.log(this.dataSource2)
          }
        }
        // console.log(selected, selectedRows, changeRows);
      },
      onSelect(record, selected) {
        if (selected === true) {//判断点击事件发生后是否被选中
          this.dataSource2.push(record);
          console.log(this.dataSource2)//如果当前行被选中则加入到集合中
        } else {
          var index = this.dataSource2.indexOf(record);//得到该行在数据源中的索引
          console.log(index);
          console.log(this.dataSource2)
          //console.log();
          if (index >= 0) {
            this.dataSource2.splice(this.dataSource2.indexOf(record), 1);//如果当前行被取消选中，则在集合中剪切掉该行
            console.log(this.dataSource2)
          }

        }
      },
      onSelectChange(selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectedRows;
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.dataSource2=[];
      },
      handleDelete: function (record) {
        this.dataSource2.splice(this.dataSource2.indexOf(record), 1);
      },
      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        console.log(sorter);
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" == sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
        this.loadData();
      }
    }
  }
</script>
<style lang="less" scoped>
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>