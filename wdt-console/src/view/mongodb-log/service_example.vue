<template>
    <div ref="dom"  style=" width:500px;height:800px;" ></div>
</template>

<script>
import echarts from 'echarts'
 
import { on, off } from '@/libs/tools'

export default {
    
   // props: ['mychars'],
   props:{
         mychars:Array ,
         countArray:Array,
         durationArray:Array,
        durationMaxArray:Array,
        statistical:String,
   }, 
  
  name: 'serviceRequests',
  data () {
    return {
      dom: null,
       //mychars:['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      //  mychars:['周一', '周二', '周三', '周四', '周五', '周六', '周日']
       
         

    }
  },
  
 created () {
   this.serch();
  },
  watch: {
    mychars () {
      this.loadData();
    },
    countArray(){
       this.loadData(); 
    },
    durationArray(){
      this.loadData();
    },
    durationMaxArray(){
      this.loadData();
    },
    statistical(){
      this.loadData();
    }
  },
  methods: {
    resize () {
      this.dom.resize()
      //this.echartsInstance.resize();
       
    },
    serch(){
    },
    loadData () {
      const option = {
        title: {
        text: this.statistical+'方法统计',//图标标题
        x: 'center' ,//水平居中
        top:0,
        textStyle:{
                fontSize:14,
                fontWeight: 'normal',
                },
        
        },
        legend: {
            bottom: 0,
            padding: 5,
            left: 'center',
            data: [  '访问次数', '平均访问时间', '最大访问时间']
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          },
          borderWidth: 1,
          borderColor: '#ccc',
          padding: 10,
        },
        grid: {
          top: '10%',
          left: '1.2%',
          right: '2%',
          bottom: '15%',
          containLabel: true,
       
        },
       xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.mychars,
            
          }
        ],
        yAxis: [
          {
            type: 'value'
          },
          {
            type : 'value',
            //splitNumber: 5, // 数值轴用，分割段数，默认为5
           /* boundaryGap:[0,0],
            axisLine:{show: true},
            splitLine:{lineStyle:{color:'#505a73'}},
            axisLabel: { show: true, textStyle: { color: '#fff' },formatter: '{value} '},
            show:'true',*/
            position: 'right',//控制新y轴的位置
            }
        ],
        series: [
          {
            name: '访问次数',
            type: 'line',
            //stack: '总量',
            animationDurationUpdate: function (idx) {
                  return idx * 100;
              },
             
            data: this.countArray,
            itemStyle : {
                normal :{
                    color:'#FF0000', 
                    lineStyle:{
                        color:'#FF0000'
                    }
                }  
            },
          },
          {
            name: '平均访问时间',
            type: 'line',
            //stack: '总量',
            yAxisIndex:'1',//使用第二条y轴
            data: this.durationArray,
            animationDurationUpdate: function (idx) {
                  return idx * 100;
            },
            itemStyle : {
                normal :{
                    color:'#00FF00', 
                    lineStyle:{
                        color:'#00FF00'
                    }
                }  
            },
          },
          {
            name: '最大访问时间',
            type: 'line',
            //stack: '总量',
            yAxisIndex:'1',//使用第二条y轴
            data: this.durationMaxArray, 
              //数据更新动画的时长
              animationDurationUpdate: function (idx) {
                  return idx * 1000;
              },
              itemStyle : {
                normal :{
                    color:'#0000FF', 
                    lineStyle:{
                        color:'#0000FF'
                    }
                }  
            },
          },
            
         
        ]
      }

      this.$nextTick(() => {
        this.dom = echarts.init(this.$refs.dom)
        this.dom.setOption(option)
        on(window, 'resize', this.resize())
      })
    },
  /*  loadData1 () {
      alert('loadData1');
      const option1 = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        grid: {
          top: '3%',
          left: '1.2%',
          right: '1%',
          bottom: '3%',
          containLabel: true
        },
       xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.mychars
          }
        ],
        yAxis: [
          {
            type: 'value'
          } 
        ],
        series: [
          {
            name: '出现次数',
            type: 'line',
            stack: '总量',
             
            data: this.countArray,
          },
          {
            name: '平均持续时间',
            type: 'line',
            stack: '总量',
            
            data: this.durationArray
          },
          {
            name: '最大持续时间',
            type: 'line',
            stack: '总量',
           
            data: this.durationMaxArray
          },
            
         
        ]
      }

      this.$nextTick(() => {
        this.dom = echarts.init(this.$refs.dom)
        this.dom.setOption(option1);
        on(window, 'resize', this.resize())
      })
    }*/

  },

  mounted () {
    this.loadData();
    // const option = {
    //   tooltip: {
    //     trigger: 'axis',
    //     axisPointer: {
    //       type: 'cross',
    //       label: {
    //         backgroundColor: '#6a7985'
    //       }
    //     }
    //   },
    //   grid: {
    //     top: '3%',
    //     left: '1.2%',
    //     right: '1%',
    //     bottom: '3%',
    //     containLabel: true
    //   },
    //  xAxis: [
    //     {
    //       type: 'category',
    //       boundaryGap: false,
    //       data: this.mychars
    //     }
    //   ],
    //   yAxis: [
    //     {
    //       type: 'value'
    //     }
    //   ],
    //   series: [
    //     {
    //       name: '运营商/网络服务',
    //       type: 'line',
    //       stack: '总量',
           
    //       data: [320, 320, 320, 320, 320, 320, 320, ],
    //     },
    //     {
    //       name: '银行/证券',
    //       type: 'line',
    //       stack: '总量',
           
    //       data: [257, 358, 278, 234, 290, 330, 310]
    //     },
    //     {
    //       name: '游戏/视频',
    //       type: 'line',
    //       stack: '总量',
           
    //       data: [379, 268, 354, 269, 310, 478, 358]
    //     },
    //     {
    //       name: '餐饮/外卖',
    //       type: 'line',
    //       stack: '总量',
           
    //       data: [320, 332, 301, 334, 390, 330, 320]
    //     },
    //     {
    //       name: '快递/电商',
    //       type: 'line',
    //       stack: '总量',
    //       label: {
    //         normal: {
    //           show: true,
    //           position: 'top'
    //         }
    //       },
           
    //       data: [820, 645, 546, 745, 872, 624, 258]
    //     }
    //   ]
    // }
    // this.$nextTick(() => {
    //   this.dom = echarts.init(this.$refs.dom)
    //   this.dom.setOption(option)
    //   on(window, 'resize', this.resize())
    // })
  },
  beforeDestroy () {
    off(window, 'resize', this.resize());
    //
  }
}
</script>
