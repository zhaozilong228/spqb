<template>
  <div>
    <Collapse v-model="value1">
        <Panel name="1">
            <span class="panal-title">系统各节点监控</span>
            <div slot="content">
              <Row>
                <Row :gutter="20">
                  <i-col span="6" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 120px;">
                    <infor-card :color="infor.color" :icon="infor.icon" :icon-size="36">
                      <count-to :end="infor.count" count-class="count-style" :style="{color: infor.color}" :decimals="infor.decimals">
                        <span v-if="infor.text" class="slot-text" slot="right">{{infor.text}}</span>
                      </count-to>
                      <p>{{ infor.title }}</p>
                    </infor-card>
                  </i-col>
                </Row>
                <Row style="margin-bottom: 20px;margin-top: 20px;">
                  <Col span="7" style="padding-left: 30px;">
                      <RadioGroup v-model="findType" type="button" @on-change="changeFindType">
                          <Radio label="hour">最近1小时</Radio>
                          <Radio label="day">最近1天</Radio>
                          <Radio label="week">最近3天</Radio>
                      </RadioGroup>
                  </Col>
                  <Col span="8">
                      <DatePicker type="datetimerange" placement="bottom-end" placeholder="请选择查询时间" 
                        v-model="findDate" format="yyyy-MM-dd HH:mm:ss" @on-change="changeFindDate"
                        style="width:90%;"></DatePicker>
                  </Col>
                  <Col span="5">
                      <RadioGroup v-model="lineType" type="button" @on-change="changeLineType">
                          <Radio label="jvm">JVM内存</Radio>
                          <Radio label="top">物理内存</Radio>
                          <!-- <Radio label="currJvm">JVM当前内存</Radio> -->
                      </RadioGroup>
                  </Col>
                  <Col span="2" style="text-align:left;">
                      <Button type="primary" icon="md-refresh" @click="refreshLine">刷新</Button>
                  </Col>
                </Row>
                <div ref="engineRef" style="width:100%;height:400px;"></div>
              </Row>
            </div>
        </Panel>
        <Panel name="2">
            <span class="panal-title">服务器磁盘使用监控</span>
            <div slot="content">
              <Row :gutter="20">
                <Col span="8">
                  <div ref="machinePieRef" style="width:100%;height:450px;margin-top:30px;"></div>
                </Col>
                <Col span="16">
                  <Row>
                    <Row style="margin-bottom: 20px;margin-top: 20px;">
                      <Col span="7" style="padding-left: 30px;">
                          <RadioGroup v-model="findTypeMachine" type="button" @on-change="changeFindTypeMachine">
                              <Radio label="week">最近7天</Radio>
                              <Radio label="month">最近30天</Radio>
                          </RadioGroup>
                      </Col>
                      <Col span="8">
                          <DatePicker type="datetimerange" placement="bottom-end" placeholder="请选择查询时间" 
                            v-model="findDateMachine" format="yyyy-MM-dd" @on-change="changeFindDateMachine"
                            style="width: 90%;"></DatePicker>
                      </Col>
                      <Col span="2" style="text-align:left;">
                          <Button type="primary" icon="md-refresh" @click="refreshMachineLine">刷新</Button>
                      </Col>
                    </Row>
                    <div ref="machineRef" style="width:100%;height:450px;"></div>
                  </Row>
                </Col>
              </Row>
            </div>
        </Panel>
    </Collapse>
    <!-- <Card>
        <p slot="title">系统各节点监控</p>
        
    </Card>
    <Card style="margin-top: 20px;">
        <p slot="title">服务器磁盘使用监控</p>
        
    </Card> -->
  </div>
</template>

<script>
import echarts from 'echarts'
import InforCard from '_c/info-card'
import CountTo from '_c/count-to'
import { on, off } from '@/libs/tools'
export default {
  name: 'home-stat',
  components: {
    InforCard,
    CountTo
  },
  data () {
    return {
      value1: ['1', '2'],
      findType: 'hour',
      findDate: [],
      lineType: 'jvm',
      engineChart: null,
      option: {
        title: {
          text: '系统运行情况趋势图',
          left: 'center'
        },
        legend: {
          top: '30'
        },
        grid: {
          left: '5%',
          right: '5%'
        },
        dataZoom: [{
          type: 'inside'
        }],
        tooltip: {
          trigger: 'axis',
          formatter: function (param) {
            let fmtResult = [];
            for (let i = 0; i < param.length; i++) {
              let oneItem = param[i];
              let oneTip = [
                i > 0 ? '---------------------------</br>' : '',
                '节点名称：' + oneItem.seriesName + '</br>',
                '时间：' + oneItem.data[0] + '</br>',
                'JVM内存使用：' + oneItem.data[4] + ' %</br>',
                '物理内存占用：' + oneItem.data[1] + ' %</br>',
                'JVM当前内存：' + oneItem.data[2] + ' MB</br>',
                'JVM最大内存：' + oneItem.data[3] + ' MB</br>',
                '节点状态：' + oneItem.data[5] + '</br>'
              ];
              fmtResult.push(...oneTip);
            }
            return fmtResult.join(' ');
          },
          position: function (pos, params, el, elRect, size) {
            var obj = {top: 10};
            obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
            return obj;
          }
        },
        toolbox: {
          show: true,
          right: '5%',
          feature: {
              dataZoom: {
                  yAxisIndex: 'none'
              },
              dataView: {readOnly: false},
              magicType: {type: ['line', 'bar']},
              restore: {},
              saveAsImage: {}
          }
        },
        xAxis: {
          name: '时间',
          type: 'time'
        },
        yAxis: {
          name: '物理内存占用(%)',
          min: function(value) {
              return Math.floor(value.min - 1);
          },
          max: function(value) {
              return Math.floor(value.max + 1);
          }
        },
        series: []
      },
      commonTooltip: {
        formatter: function (param) {
          return [
            '节点名称：' + param.seriesName + '</br>',
            '时间：' + param.data[0] + '</br>',
            'JVM内存使用：' + param.data[4] + ' %</br>',
            '物理内存占用：' + param.data[1] + ' %</br>',
            'JVM当前内存：' + param.data[2] + ' MB</br>',
            'JVM最大内存：' + param.data[3] + ' MB</br>',
            '节点状态：' + param.data[5] + '</br>'
          ].join(' ');
        }
      },
      statusMap: {
        'D': '不可中断的睡眠状态',
        'R': '运行',
        'S': '睡眠',
        'T': '跟踪/停止',
        'Z': '僵尸进程'
      },
      // 信息：#2d8cf0， 正常 #19be6b，警告：#ff9900，报警：#ed3f14
      inforCardData: [
        { title: '节点数', icon: 'md-locate', count: 0, color: '#2d8cf0', decimals: 0 },
        { title: '最大物理内存占比', icon: 'ios-pie', count: 0, color: '#19be6b', decimals: 2, text: '%' },
        { title: '最大JVM内存占比', icon: 'ios-podium', count: 0, color: '#19be6b', decimals: 2, text: '%' },
        { title: '最大JVM内存数', icon: 'md-pulse', count: 0, color: '#2d8cf0', decimals: 0, text: 'MB' }
      ],
      machineLine: null,
      machinePie: null,
      findTypeMachine: 'week',
      findDateMachine: [],
      machinePieOption: {
        title: {
          text: '服务器磁盘使用占比',
          left: 'center'
        },
        legend: {
          orient: 'vertical',
          left: 2,
          top: 35
        },
        series: [{
          type: 'pie',
          radius: '85%',
          label: {
            position: 'inside',
            // fontWeight: 'bold',
            fontSize: 14,
            formatter: '{b}\n{c}G（{d}%）'
          },
          data: []
        }]
      },
      machineLineOption: {
        title: {
          text: '服务器磁盘使用情况趋势图',
          left: 'center'
        },
        tooltip: {},
        grid: {
          left: '5%',
          right: '7%'
        },
        xAxis: {
          name: '时间',
          type: 'time'
        },
        yAxis: {
          name: '已用空间(G)',
          min: function(value) {
              return Math.floor(value.min - 3);
          },
          max: function(value) {
              return Math.floor(value.max + 3);
          }
        },
        series: [{
          type: 'line',
          tooltip: {
            formatter: function (param) {
              let fmtResult = [];
              let oneItem = param.data;
              if (oneItem) {
                let oneTip = [
                  '时间：' + oneItem[0] + '</br>',
                  '磁盘已用空间：' + oneItem[1] + ' G</br>',
                  '磁盘可用空间：' + oneItem[2] + ' G</br>',
                  '使用占比：' + oneItem[3] + ' %'
                ];
                fmtResult.push(...oneTip);
              }
              return fmtResult.join(' ');
            }
          },
          data: []
        }]
      },
      machineCardData: [
        { title: '当前磁盘使用百分比', icon: 'ios-pie', count: 0, color: '#19be6b', text: '%' },
        { title: '当前磁盘可用空间', icon: 'ios-analytics', count: 0, color: '#2d8cf0', text: 'G' }
      ]
    }
  },
  mounted () {
    // 初始化系统运行折线图
    this.engineChart = echarts.init(this.$refs.engineRef);
    this.changeFindType('hour');
    // 初始化磁盘占用饼图
    this.initMachinePie();
    // 初始化磁盘占用折线图
    this.$nextTick(() => {
      this.machineLine = echarts.init(this.$refs.machineRef);
      this.changeFindTypeMachine('week');
    });
  },
  beforeDestroy () {
    off(window, 'resize', this.resize())
  },
  methods: {
    resize () {
      if (this.engineChart) {
        this.engineChart.resize();
      }
    },
    // 查询数据
    findEngineLog (startTime, endTime) {
      this.engineChart.showLoading();
      this.$http.post('console/enginelog/find', {
        startTime,
        endTime
      }).then(({ resultData }) => {
        this.engineChart.hideLoading();
        this.formatLineData(resultData);
      }).catch((error) => {
        this.engineChart.hideLoading();
        console.log('查询异常', error);
      });
    },
    // 处理数据
    formatLineData (resultData) {
      let engineNum = 0;
      let maxMem = 0;
      let maxJvmMem = 0;
      let maxCurrJvm = 0;
      if (resultData && resultData.length) {
        let seriesDataObj = {};
        resultData.forEach(item => {
          if (item.memoryPercentBD > maxMem) {
            maxMem = item.memoryPercentBD;
          }
          if (item.jvmPercentBD > maxJvmMem) {
            maxJvmMem = item.jvmPercentBD;
          }
          if (item.jvmCurrMemory > maxCurrJvm) {
            maxCurrJvm = item.jvmCurrMemory;
          }
          let engineObj = seriesDataObj[item.engineShortName];
          if (!engineObj) {
            engineObj = {
              name: item.engineShortName,
              type: 'line',
              // tooltip: this.commonTooltip,
              symbol: 'circle',
              showSymbol: false,
              // symbolSize: 2,
              encode: {
                y: this.lineType==='top'?1:4
              },
              data: []
            }
            seriesDataObj[item.engineShortName] = engineObj;
          }
          engineObj.data.push([
            item.time,
            item.memoryPercentBD,
            item.jvmCurrMemory,
            item.jvmMaxMemory,
            item.jvmPercentBD,
            item.status + '(' + this.statusMap[item.status] + ')']);
        });
        let seriesDataArr = [];
        for (let oneAttr in seriesDataObj) {
          seriesDataArr.push(seriesDataObj[oneAttr]);
        }
        this.option.series = seriesDataArr;
      } else {
        this.option.series = [];
        this.engineChart.clear();
      }
      this.option.yAxis.name = this.lineType==='top'?'物理内存占用(%)':'JVM内存使用(%)';
      // 汇总信息
      engineNum = this.option.series.length;
      this.formatAnalysisData(engineNum, maxMem, maxJvmMem, maxCurrJvm);
      this.$nextTick(() => {
        this.engineChart.setOption(this.option);
        on(window, 'resize', this.resize());
      });
    },
    formatAnalysisData (engineNum, maxMem, maxJvmMem, maxCurrJvm) {
      // 节点数
      this.inforCardData[0].count = engineNum;
      if (engineNum == 0) {
        this.inforCardData[0].color = "#ed3f14";
      } else {
        this.inforCardData[0].color = "#2d8cf0";
      }
      // 最大物理内存占比
      this.inforCardData[1].count = maxMem;
      if ( maxMem > 50 && maxMem <= 80) {
        this.inforCardData[1].color = "#ff9900";
      } else if ( maxMem > 80) {
        this.inforCardData[1].color = "#ed3f14";
      } else {
        this.inforCardData[1].color = "#19be6b";
      }
      // 最大JVM内存占比
      this.inforCardData[2].count = maxJvmMem;
      if ( maxJvmMem > 50 && maxJvmMem <= 80) {
        this.inforCardData[2].color = "#ff9900";
      } else if ( maxJvmMem > 80) {
        this.inforCardData[2].color = "#ed3f14";
      } else {
        this.inforCardData[2].color = "#19be6b";
      }
      // 最大JVM内存数
      this.inforCardData[3].count = maxCurrJvm;
    },
    changeFindType (type) {
      if (type) {
        let sTime = '';
        let eTime = '';
        let currDate = new Date();
        eTime = this.getTimeStr();
        if (type === 'hour') {
          sTime = this.getTimeStr(currDate, 1/24);
        } else if (type === 'day') {
          sTime = this.getTimeStr(currDate, 1);
        } else if (type === 'week') {
          sTime = this.getTimeStr(currDate, 7);
        }
        this.findDate = [sTime, eTime];
        this.findEngineLog(sTime, eTime);
      }
    },
    getTimeStr (d=new Date(), beforeDay=0, shortFlag=false) {
      if (beforeDay != 0) {
        d = new Date(d.getTime() - beforeDay*24*60*60*1000);
      }
      let year = d.getFullYear();
      let month = (d.getMonth() + 1) + '';
      let day = d.getDate() + '';
      let hour = d.getHours() + '';
      let minute = d.getMinutes() + '';
      let second = d.getSeconds() + '';
      if (shortFlag) {
        return year+'-'+(month.length==1?'0'+month:month)+'-'+(day.length==1?'0'+day:day);
      } else {
        return year+'-'+(month.length==1?'0'+month:month)+'-'+(day.length==1?'0'+day:day)
        +' '+(hour.length==1?'0'+hour:hour)+':'+(minute.length==1?'0'+minute:minute)
        +':'+(second.length==1?'0'+second:second);
      }
    },
    changeFindDate (selDate) {
      if (selDate && selDate.length) {
        let sTime = new Date(selDate[0]);
        let eTime = new Date(selDate[1]);
        // 最大跨度3天
        if ((eTime.getTime()-sTime.getTime()) > 3*24*60*60*1000) {
          this.$Message.error('查询时间跨度不允许超过3天！');
        } else {
          this.findType = '';
          this.findEngineLog(selDate[0], selDate[1]);
        } 
      } else {
          this.$Message.error('请选择查询时间！');
      }
    },
    changeLineType (type) {
      let yIndex = 1;
      if (type === 'top') {
        this.option.yAxis.name = '物理内存占用(%)';
        yIndex = 1;
      } else if (type === 'jvm') {
        this.option.yAxis.name = 'JVM内存使用(%)';
        yIndex = 4;
      } else if (type === 'currJvm') {
        this.option.yAxis.name = 'JVM当前内存数(MB)';
        yIndex = 2;
      }
      if (this.option.series && this.option.series.length) {
        this.option.series.forEach(oneSeries => {
          oneSeries.encode.y = yIndex;
        });
      }
      this.$nextTick(() => {
        this.engineChart.setOption(this.option);
        on(window, 'resize', this.resize());
      });
    },
    refreshLine () {
      if (this.findType) {
        this.changeFindType(this.findType);
      } else {
        let dateArr = [];
        if (this.findDate && this.findDate.length) {
          dateArr = [this.getTimeStr(this.findDate[0]), this.getTimeStr(this.findDate[1])];
        }
        this.changeFindDate(dateArr);
      }
    },
    // 查询数据
    findMachineLog (startTime, endTime) {
      this.machineLineOption.series[0].data = [];
      this.machineLine.showLoading();
      this.$http.post('console/enginelog/findMachineInfo', {
        startTime: startTime + ' 00:00:00',
        endTime: endTime + ' 23:59:59'
      }).then(({ resultData }) => {
        this.machineLine.hideLoading();
        let machineLineData = [];
        if (resultData && resultData.length) {
          resultData.forEach(item => {
            machineLineData.push([
              item.time,
              item.used,
              item.avail,
              item.usedPercent
            ]);
          });
        }
        console.log('machineLineData=', machineLineData);
        this.$nextTick(() => {
          this.machineLineOption.series[0].data = machineLineData;
          this.machineLine.setOption(this.machineLineOption);
          on(window, 'resize', this.resize());
        });
      }).catch((error) => {
        this.machineLine.hideLoading();
        console.log('查询异常', error);
      });
    },
    changeFindTypeMachine (type) {
      if (type) {
        let sTime = '';
        let eTime = '';
        let currDate = new Date();
        eTime = this.getTimeStr(currDate, 0, true);
        if (type === 'week') {
          sTime = this.getTimeStr(currDate, 7, true);
        } else if (type === 'month') {
          sTime = this.getTimeStr(currDate, 30, true);
        }
        this.findDateMachine = [sTime, eTime];
        this.findMachineLog(sTime, eTime);
      }
    },
    changeFindDateMachine (selDate) {
      if (selDate && selDate.length) {
        let sTime = new Date(selDate[0]);
        let eTime = new Date(selDate[1]);
        // 最大跨度90天
        if ((eTime.getTime()-sTime.getTime()) > 90*24*60*60*1000) {
          this.$Message.error('查询时间跨度不允许超过90天！');
        } else {
          this.findTypeMachine = '';
          this.findMachineLog(selDate[0], selDate[1]);
        } 
      } else {
          this.$Message.error('请选择查询时间！');
      }
    },
    refreshMachineLine () {
      if (this.findTypeMachine) {
        this.changeFindTypeMachine(this.findTypeMachine);
      } else {
        let dateArr = [];
        if (this.findDateMachine && this.findDateMachine.length) {
          dateArr = [this.getTimeStr(this.findDateMachine[0], 0, true), this.getTimeStr(this.findDateMachine[1], 0, true)];
        }
        this.changeFindDateMachine(dateArr);
      }
    },
    // 初始化饼图
    initMachinePie () {
      this.$nextTick(() => {
        let nowDate = this.getTimeStr().substring(0,10);
        this.machinePieOption.series[0].data = [];
        this.machinePie = echarts.init(this.$refs.machinePieRef);
        this.machinePie.showLoading();
        this.$http.post('console/enginelog/findMachineInfo', {
          startTime: nowDate + ' 00:00:00',
          endTime:  nowDate + ' 23:59:59'
        }).then(({ resultData }) => {
          this.machinePie.hideLoading();
          if (resultData && resultData.length) {
            let todayResult = resultData[0];
            this.machinePieOption.series[0].data.push(...[{
              name: '已用空间',
              value: todayResult.used,
              itemStyle:{
                color: '#c23531'
              }
            },{
              name: '可用空间',
              value: todayResult.avail,
              itemStyle:{
                color: '#91c7ae'
              }
            }]);
            this.machinePie.setOption(this.machinePieOption);
            on(window, 'resize', this.resize());
          }
        }).catch((error) => {
          this.machinePie.hideLoading();
          console.log('查询异常', error);
        });
      });
    }
  }
}
</script>

<style lang="less">
.count-style{
  font-size: 30px;
  font-weight: bold;
}
.panal-title{
  font-size: 15px;
  color: #17233d;
  font-weight: bold;
}
</style>
