<template>
    <div ref="dom" style="width:950px;height:800px;"></div>
</template>

<script>
import echarts from 'echarts'
import { on, off } from '@/libs/tools'

export default {
  name: 'serviceRequests',
  props:{
    ychildData:Array,
    xChildData:Array
  },
  data () {
    return {
      dom: null,
      option: { // 图表基础内容
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
        },
        grid: { // 栅格(图表显示不全)
          top: '7%',
          left: '3%',
          right: '4.5%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.xChildData
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '',
            type: 'line',
            stack: '',
            label: {
              normal: {
                show: true,
                position: 'top'
              }
            },
            data: this.ychildData
          }
        ]
      }
    }
  },
  methods: {
    resize () {
      this.dom.resize()
    },
    reloadChart (xData, yData) {  //  ChildMethods
      this.$nextTick(() => {
        this.dom = echarts.init(this.$refs.dom)
        // if (type === 'x') {
          this.option.xAxis[0].data = xData;
        // }
        // if (type === 'y'){
          this.option.series[0].data = yData;    //  马虎之处：y的数据应该在series里面，不在yAxis
        // }
        this.dom.setOption(this.option)
        on(window, 'resize', this.resize())
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.dom = echarts.init(this.$refs.dom)
      this.dom.setOption(this.option)
      on(window, 'resize', this.resize())
    })
  },
  beforeDestroy () {
    off(window, 'resize', this.resize())
  }
}
</script>
