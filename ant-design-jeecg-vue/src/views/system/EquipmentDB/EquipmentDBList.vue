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
              <a-button type="primary" @click="Eqlikeselect" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay" style="width: 300px">
          <j-select-depart v-model="bumens" :multi="true" @ok="EquipmentDB"/>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onSelectAll:onSelectAll,onSelect:onSelect,onChange: onSelectChange}"
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
    <!-- 表单区域 -->
    <equipment-modal ref="modalForm" @ok="modalFormOk"></equipment-modal>
    <!--<equipment-client-add ref="clientmodal"></equipment-client-add>-->
    <equipment-client-x-z ref="equipmentclientxz" @ok="modalFormOk"></equipment-client-x-z>
  </a-card>
</template>

<script>
  import EquipmentModal from '../modules/EquipmentModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import EquipmentClientXZ from '../modules/EquipmentClientXZ'
  import { httpAction,putAction ,getAction} from '@/api/manage'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'

  export default {
    name: "EquipmentList",
    mixins:[JeecgListMixin],
    components: {
      EquipmentModal,EquipmentClientXZ,JSelectDepart
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
            title: '所属代理部门',
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
          }
        ],
        form: this.$form.createForm(this),
        bumen:"",
        bumens:"",//保存选中的部门id
        dataSource2:[],//保存选中的设备id
        queryParam:{},//保存查询信息
        url: {
          list:   "/demo/quipmentDb/list",
          eqlikeselect:"/demo/quipmentDb/eqlikeselect",
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      Eqlikeselect(){
        var a = this.queryParam;
        console.log(a)
        this.loading = true;
        getAction(this.url.eqlikeselect, a).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
            console.log(this.dataSource);
          }
          this.loading = false;
        })
      },
      onSelectAll(selected, selectedRows, changeRows) {//判断点击全选事件发生后是否被选中
        if (selected === true) {
          for (var a = 0; a < changeRows.length; a++) {
            this.dataSource2.push(changeRows[a].equipmentId);//遍历数据源得到指定下标添加
          }
        } else {
          for (var b = 0; b < changeRows.length; b++) {
            this.dataSource2.splice(this.dataSource2.indexOf(changeRows[b].equipmentId), 1);//遍历数据源得到指定下标删除
          }
        }
      },
      onSelect(record, selected) {
        if (selected === true) {//判断点击事件发生后是否被选中
          this.dataSource2.push(record.equipmentId);//如果当前行被选中则加入到集合中
        } else {
          var index = this.dataSource2.indexOf(record.equipmentId);//得到该行在数据源中的索引
          if (index >= 0) {
            this.dataSource2.splice(this.dataSource2.indexOf(record.equipmentId), 1);//如果当前行被取消选中，则在集合中剪切掉该行
          }
        }
      },
      onSelectChange(selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectedRows;
      },
      EquipmentDB(departIds){
        this.bumens =departIds;//保存选中要调拨得部门
        console.log(this.dataSource2);
        var equipmentIds = '';
        for (var a = 0; a < this.dataSource2.length; a++) {
          equipmentIds += this.dataSource2[a] + ','
        }
        var record = {"departIds":this.bumens,"equipmentIds":equipmentIds}
        this.confirmLoading = true;
        putAction("/demo/quipmentDb/updateuserid",record ).then((res) =>{//将得到得设备id以及调拨得部门传回后台进行调拨
          if (res.success) {
            this.$message.success(res.message);
          }else{
            this.$message.warning(res.message);
          }
        }).finally(() => {
          this.confirmLoading = false;
          this.loadData();
        });
      },
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