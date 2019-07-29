<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="设备数量" :total="Equcount.count |NumberFormat ">
          <div >异常设备数量：<span>{{yccount.count}}</span></div>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="预计下周异常设备数" :total="NextCount.count |NumberFormat">
          <div>
            <mini-area />
          </div>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="设备安装比例" :total="Percentage.count+'%' ">
          <div>
            <mini-progress color="rgb(19, 194, 194)" :target="Percentage.count " :percentage="Percentage.count " :height="8" />
          </div>
          <template slot="footer">
          </template>
        </chart-card>
      </a-col>
      <!--<a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">-->
        <!--<chart-card :loading="loading" title="代理商设备" :total="PercentageUserId.count">-->
          <!--&lt;!&ndash;<div>&ndash;&gt;-->
          <!--&lt;!&ndash;<mini-progress color="rgb(19, 194, 194)" :target="80" :percentage="78" :height="8" />&ndash;&gt;-->
          <!--&lt;!&ndash;</div>&ndash;&gt;-->
          <!--<template slot="footer">-->
          <!--</template>-->
        <!--</chart-card>-->
      <!--</a-col>-->
    </a-row>

    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <!--<div class="extra-wrapper" slot="tabBarExtraContent">-->
            <!--<div class="extra-item">-->
              <!--<a>今日</a>-->
              <!--<a>本周</a>-->
              <!--<a>本月</a>-->
              <!--<a>本年</a>-->
            <!--</div>-->
            <!--<a-range-picker :style="{width: '256px'}" />-->
          <!--</div>-->
          <a-tab-pane loading="true" tab="代理商设备" key="1">
            <a-row>
              <a-col :xl="24" :lg="12" :md="12" :sm="24" :xs="24">
                <bar title="设备数" :dataSource="barData"/>
              </a-col>

            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="商品设备" key="2">
            <a-row>
              <a-col :xl="24" :lg="12" :md="12" :sm="24" :xs="24">
                <bar title="设备数" :dataSource="barDataPlus"/>
              </a-col>

            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import Trend from '@/components/Trend'
  import {getLoginfo} from '@/api/api'
  import {sysCount} from '@/api/api'
  import {sysYcCount} from '@/api/api'
  import {sysNextCount} from '@/api/api'
  import {sysPercentage} from '@/api/api'
  import {sysPercentageUserId} from '@/api/api'
  import {sysPercentageCommodityId} from '@/api/api'
  const rankList = []
  for (let i = 0; i < 7; i++) {
    rankList.push({
      name: '白鹭岛 ' + (i+1) + ' 号店',
      total: 1234.56 - i * 100
    })
  }
  const barData = []
  const barDataPlus=[]
  /*for (let i = 0; i < 12; i += 1) {
    barData.push({
      x: `${i + 1}月`,
      y: Math.floor(Math.random() * 50) + 20
    })
  }*/
  export default {
    name: "Analysis",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend
    },
    data() {
      return {
        loading: true,
        center: null,
        rankList,
        barData,
        barDataPlus,
        loginfo:{},
        Equcount:{},
        yccount:{},
        NextCount:{},
        Percentage:{},
        PercentageUserId:{},
        PercentageCommodityId:{},
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      //执行方法
      this.initLogInfo();
      this.initCount();
      this.YcCount();
      this.NextWeek();
      this.PercentageCount();
      this.PercentageUserCount();
      this.PercentageCommodityCount();
    },
    methods: {
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            this.loginfo = res.result;
          }
        })
      },
      //查询设备总数量
      initCount(){
        sysCount(null).then((res)=>{
          if(res.success){
            this.Equcount = res.result;
            console.log(this.count+" aaaaaa")
          }
        })
      },
      //查询异常设备数量
      YcCount(){
        sysYcCount(null).then((res)=>{
          if(res.success){
            this.yccount = res.result;
            console.log(this.yccount+" aaaaaa")
          }
        })
      },
      //查询预计下周异常设备数量
      NextWeek(){
        sysNextCount(null).then((res)=>{
          if(res.success){
            console.log(res)
            this.NextCount = res.result;
          }
        })
      },
      //设备安装比例
      PercentageCount(){
        sysPercentage(null).then((res)=>{
          if(res.success){
            console.log(res)
            this.Percentage = res.result;
          }
        })
      },
      //查询代理设备数量
      PercentageUserCount(){
        sysPercentageUserId(null).then((res)=>{
          if(res.success){
            var data = res.result;
            var t;
            for (var i = 0; i < data.length-1; i++) {
              for(var j = 0; j < data.length-i-1 ; j++) {
                if (parseInt(data[j].count) > parseInt(data[j+1].count)) {
                  t = data[j];
                  data[j] = data[j+1];
                  data[j+1] = t;
                }
              }
            }
            for (let i = 0; i < data.length; i += 1) {
              barData.push({
                x: data[i].name,
                y: data[i].count
              })
            }
            this.PercentageUserId = res.result;
          }
        })
      },
      //查询商品设备数量
      PercentageCommodityCount(){
        sysPercentageCommodityId(null).then((res)=>{
          if(res.success){
            var data=res.result;
            var t;
            for (var i = 0; i < data.length-1; i++) {
              for(var j = 0; j < data.length-i-1 ; j++) {
                if (parseInt(data[j].count) > parseInt(data[j+1].count)) {
                  t = data[j];
                  data[j] = data[j+1];
                  data[j+1] = t;
                }
              }
            }
            console.log("555555555555555555555555="+res.result)
            for (let i = 0; i < data.length; i += 1) {
              console.log(data[i].count)
              barDataPlus.push({
                x: data[i].name,
                y: data[i].count
              })
            }
            console.log(data.length+"5555555")
            this.PercentageCommodityId = res.result;
          }
        })
      },
    }
  }
</script>

<style lang="scss" scoped>
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>