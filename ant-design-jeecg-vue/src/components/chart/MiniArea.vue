<template>
  <div class="antv-chart-mini">
    <div class="chart-wrapper" :style="{ height: 46 }">
      <v-chart :force-fit="true" :height="height" :data="data" :scale="scale" :padding="[36, 0, 18, 0]">
        <v-tooltip/>
        <v-smooth-area position="x*y"/>
      </v-chart>
    </div>
  </div>
</template>

<script>
  import moment from 'dayjs'
  import {getAction,} from '@/api/manage'
  const sourceData = []
  export default {
    name: 'MiniArea',
    props: {
      dataSource: {
        type: Array,
        default: () => []
      },
      // x 轴别名
      x: {
        type: String,
        default: 'x'
      },
      // y 轴别名
      y: {
        type: String,
        default: 'y'
      }
    },
    data() {
      return {
        data: [],
        height: 100,
        url: {
          list: "/demo/installation/list",
          delete: "/demo/installation/delete",
          sysanalysisDaily:"/demo/sysanalysis/sysanalysisDaily",
        },
      }
    },
    computed: {
      scale() {
        return [
          { dataKey: 'x', title: this.x, alias: this.x },
          { dataKey: 'y', title: this.y, alias: this.y }
        ]
      }
    },
    created() {
      this.time();
      if (this.dataSource.length === 0) {
        this.data = sourceData
      } else {
        this.data = this.dataSource
      }
    },
    methods: {
      time(){
        getAction(this.url.sysanalysisDaily).then((res) => {
          if (res.success) {
            var a = res.result;
            console.log(a)
            for (let i = 0; i < 7; i++) {
              sourceData.push({
                x: a[i].time,
                y: a[i].count
              })
            }
          }
        })

      }
    }


  }
</script>


<style lang="scss" scoped>
  @import "chart";
</style>