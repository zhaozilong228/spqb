<template>
    <div ref="dom" style=" width:550px;height:500px;"></div>
</template>

<script>
import echarts from 'echarts'
import { on, off } from '@/libs/tools'
export default {
  name: 'serviceRequests',
  props:{
         pieData:Array,
         pieNameData:Array,
         pietitle:String,
         subtext:String,
   },
  data () {
    return {
      dom: null
    }
  },
  watch: {
    pieData () {
      this.loadData();
    },
    pieNameData(){
       this.loadData(); 
    },
  },
  methods: {
    resize () {
      this.dom.resize()
    },
    loadData(){
          const option = {
          title : {
              text: this.pietitle,
              subtext: '按订单状态统计',
              x:'center'
          },
          tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
              orient: 'vertical',
              left: 'left',
              data: this.pieData
          },
          series : [
              {
                  name: '',
                  type: 'pie',
                  radius : '55%',
                  center: ['50%', '60%'],
                  data:this.pieData,
                  itemStyle: {
                      emphasis: {
                          shadowBlur: 10,
                          shadowOffsetX: 0,
                          shadowColor: 'rgba(0, 0, 0, 0.5)'
                      }
                  }
              }
          ]
      }
      this.$nextTick(() => {
        this.dom = echarts.init(this.$refs.dom)
        this.dom.setOption(option)
        on(window, 'resize', this.resize())
      })
    }
  },
  mounted () {
        this.loadData();
  },
  beforeDestroy () {
    off(window, 'resize', this.resize())
  }
}
</script>
