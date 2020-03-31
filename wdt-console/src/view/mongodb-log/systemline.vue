<template>
    <div ref="dom"   ></div>
</template>

<script>
import echarts from 'echarts' 
import { on, off } from '@/libs/tools'
export default {
    props:{
        num:Array ,
        durationAvg:Array,
        durationMax:Array,
        time:Array,
        statistical:String,
        dataMax:Number,
        durationMaxValue:Number,
   }, 
  
  name: 'serviceRequests',
  data () {
    return {
      dom: null,
      count:0,
    }
  },
    created () {
        this.serch();
    },
    mounted(){
        const that = this
        window.onresize =()  =>{
            return (()=>{
                window.screenWidth = window.innerWidth;
                //alert(window.innerWidth+'px');
            })()
        }

    },
  watch: {
    time () {
      
      this.loadData();
      console.log('time修改数据',++this.count);
    },
     durationAvg(){
       this.loadData(); 
       console.log('durationAvg修改数据',++this.count);
    },
    durationMax(){
      this.loadData();
      console.log('durationMax修改数据',++this.count);
    },
    num(){
      this.loadData();
      console.log('num修改数据',++this.count);
    },
    statistical(){
      this.loadData();
      console.log('statistical修改数据',++this.count);
    },
    dataMax(){
     this.loadData();
     console.log('dataMax修改数据',++this.count);
    },
    durationMaxValue(){
        this.loadData();
        console.log('durationMaxValue修改数据',++this.count);
    },
  },
  methods: {
    resize () {
      this.dom.resize()
       
    },
    serch(){
    },
    loadData () {
      const option = {
        title: {
        text: this.statistical,//图标标题
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
            data: [  '访问次数', '平均访问时长', '最大访问时长']
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line',
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
        dataZoom: [
                {
                    id: 'dataZoomX',
                    type: 'inside',
                    xAxisIndex: [0],
                    filterMode: 'filter', // 设定为 'filter' 从而 X 的窗口变化会影响 Y 的范围。
                    // start: 30,
                    // end: 70
                }
        ],
        xAxis: [
        {
            type: 'category',
            boundaryGap: false,
            data: this.time,
            /*axisLabel:{
                formatter:function(params) {
                    var newParamsName = "";
                    var paramsNameNumber = params.length;
                    var provideNumber = 10;  //一行显示几个字
                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                    if (paramsNameNumber > provideNumber) {
                        for (var p = 0; p < rowNumber; p++) {
                            var tempStr = "";
                            var start = p * provideNumber;
                            var end = start + provideNumber;
                            if (p == rowNumber - 1) {
                                tempStr = params.substring(start, paramsNameNumber);
                            } else {
                                tempStr = params.substring(start, end) + "\n";
                            }
                            newParamsName += tempStr;
                        }

                    } else {
                        newParamsName = params;
                    }
                    return newParamsName
                },
            },*/
        }
        ],
        yAxis: [
        {
            name:'访问次数',
            type: 'value',
            position: 'left',
            splitLine:{
                show:false
            },
            //max:this.dataMax,
        },
        {
            name :'访问时长(ms)',
            type : 'value',
            position: 'right',//控制新y轴的位置
            splitLine:{
                show:false
            },

        }
        ],
        series: [
          {
            name: '访问次数',
            type: 'line',
            yAxis: 1,
            symbol:'none', //去掉折线图中的节点
            smooth: true,  //true 为平滑曲线，false为直线
            itemStyle : {
                normal :{
                    color:'#FF0000', 
                    lineStyle:{
                        color:'#FF0000'
                    }
                }  
            },
             
            data: this.num,
          },
          {
            name: '平均访问时长',
            type: 'line',
            symbol:'none', //去掉折线图中的节点
            smooth: true,  //true 为平滑曲线，false为直线
            yAxisIndex:'1',//使用第二条y轴
            data: this.durationAvg,
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
            name: '最大访问时长',
            type: 'line',
            //stack: '总量',
            symbol:'none', //去掉折线图中的节点
            smooth: true,  //true 为平滑曲线，false为直线
            yAxisIndex:'1',//使用第二条y轴
            data: this.durationMax,
            itemStyle : {
                normal : {
                    color:'#0000FF',
                    lineStyle:{
                        color:'#0000FF'
                    }
                }
            }, 
              //数据更新动画的时长
              /*animationDurationUpdate: function (idx) {
                  return idx * 1000;
              }*/
          },
            
         
        ]
      }

      this.$nextTick(() => {

        this.dom = echarts.init(this.$refs.dom)
       
        this.dom.showLoading();
        this.dom.clear();
        this.dom.setOption(option);
        on(window, 'resize', this.resize());
        this.dom.hideLoading();
      })

    },

  },

  mounted () {
    this.loadData();
  },
  beforeDestroy () {
    off(window, 'resize', this.resize());
    //
  }
}
</script>
