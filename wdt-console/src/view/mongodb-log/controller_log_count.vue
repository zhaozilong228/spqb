<template> 
  <div>   
    <Card>
      <Row :gutter="16">
        <Col span="24">
          <Form :label-width="95" inline >
            <FormItem label="选择日期" > 
                <DatePicker v-model="queryParam.day" type="date" format="yyyy-MM-dd" placeholder="请选择日期" style="width: 120px"></DatePicker>
            </FormItem>
            <FormItem label="排序规则">  
              <RadioGroup v-model="queryParam.rule" type="button" @on-change="countList" style="width: 290px">
                <Radio label="num">访问次数</Radio>
                <Radio label="durationAvg">平均访问时长</Radio>
                <Radio label="durationMax">最大访问时长</Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="执行方法">
              <Input search v-model="queryParam.executeFunction" placeholder="请输入executeFunction，支持模糊查询" clearable  style="width: 250px"/>
            </FormItem>
            <FormItem > 
              <Button @click="countList" type="primary"  icon="ios-search" style="width: 80px">搜索</Button>
            </FormItem>
          </Form>
        </Col>
      </Row>
      <Row>
        <Table :columns="Columns" :data="contentData" :loading="tabLoading" border></Table>
        <Page :total="totalPage" :page-size="queryParam.size" :current="current" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
      </Row>
    </Card>
    <!-- 以下是弹窗内容 -->
    <Modal
      v-model="chartFlag"
      @on-ok="ok"
      @on-cancel="cancel"
      width="1000"
      draggable>  
      <p slot="header" style="color:#f60;text-align:center">
        <span>controller日志数据统计折线图</span>
      </p>
      <div style="text-align:center">
        <!-- 以下是chart表 -->
        <Row>
          <Card >
            <Row :gutter="16">
              <Col span="24">
                <Form :label-width="60" inline >
                  <FormItem label="起止日期" > 
                    <DatePicker v-model="queryParam.days" type="daterange" format="yyyy/MM/dd" placeholder="请选择日期范围，默认显示过去7天的数据" style="width: 270px"></DatePicker>
                  </FormItem>
                  <FormItem > 
                    <Button @click="countChart" type="primary"  icon="ios-search" style="width: 80px" >搜索</Button>
                  </FormItem>
                </Form>
              </Col>
            </Row>
            <Row>
              <ControllerLogCountChart style="height: 400px;" :ychildData = "yData" :xChildData = "xData" ref = "toChart" />
              <Col span="24">
                <Form :label-width="80" inline>
                  <FormItem label="显示内容">
                    <RadioGroup v-model="queryParam.rule" type="button" @on-change="countChart" style="width: 350px">
                      <Radio label="num">访问次数</Radio>
                      <Radio label="durationAvg">平均访问时长</Radio>
                      <Radio label="durationMax">最大访问时长</Radio>
                    </RadioGroup>
                  </FormItem>
                </Form>
              </Col>
            </Row>
          </Card>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
import ControllerLogCountChart from '@/view/mongodb-log/controllerLogCountChart.vue'

export default {
  name: 'controller_log_count',
  components: {
    ControllerLogCountChart
  },
  data () {
    return {
      chartFlag: false,
      tabLoading: false,
      totalPage: 0,
      execption: 'notExist',
      rule: 'num',
      ruleToChart: [],
      xData: [],
      yData: [],
      countData: [],
      contentData: [],
      queryParam: {
        id: '',
        url: '',
        executeClass: '',
        executeFunction: '',
        page: 0,
        size: 10,
        days: [],
        dayFm: '',
        date: '',
        day: this.getDay(-1),
        execption: 'notExist',
        rule: 'num',
        executeFunctionToChart: '',
        executeClassToChart: '',
        beginDate: '',
        endDate: ''
      },
      // 这里是字段名
      Columns: [
        {
          key: 'executeClass',
          align: 'center',
          minWidth: 80,
          title: '执行类'
        },
        {
          key: 'url',
          align: 'center',
          minWidth: 80,
          title: '路径'
        },
        {
          key: 'executeFunction',
          align: 'center',
          minWidth: 50,
          title: '执行方法'
        },
        {
          key: 'num',
          align: 'center',
          minWidth: 30,
          title: '访问次数'
        },
        {
          key: 'durationAvg',
          align: 'center',
          minWidth: 30,
          title: '平均访问时长(ms)',
          render:(h,params)=>{// long类型转换为int
            let durationAvg = params.row.durationAvg;
            durationAvg = parseInt(durationAvg);
            return h('span' , {} , durationAvg)
          }
        },
        {
          key: 'durationMax',
          align: 'center',
          minWidth: 30,
          title: '最大访问时长(ms)'
        },
        //  操作按钮
        {
          key: 'operation',
          align: 'center',
          title: '操作',
          minWidth: 40,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  size: 'small',
                  loading: false,
                  type: 'success'
                },
                class: {
                  btn: true
                },
                on: {
                  click: () => {
                    this.show(params.index);
                    this.queryParam.executeClassToChart = this.contentData[params.index].executeClass;
                    this.queryParam.executeFunctionToChart = this.contentData[params.index].executeFunction;
                  }
                }
              }, '统计图')
            ]);
          }
        }
      ],
    };
  },
  created () {  //  生命周期钩子函数，在VUE实例生成后被调用，会调用ajax获取页面初始化所需的数据
    this.loadPageList();
    this.queryParam.page = 0;
    this.xData = this.getDateRange(this.dateFormat(this.getDay(-7)), this.dateFormat(this.getDay(-1)));
  },
  computed: {   //  在HTML DOM加载后马上执行的，如赋值
    current () {
      return this.queryParam.page + 1;
    }
  },
  methods: {    //  必须要有一定的触发条件才能执行，如点击事件
    ok () {
    },
    cancel () {
    },
    // 时间格式化函数，此处仅针对yyyy-MM-dd的格式进行格式化
    dateFormat (time, type) {
      var date=new Date(time);
      var year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05  */
      var month= date.getMonth()+1<10 ? "0" + (date.getMonth()+1) : date.getMonth()+1;
      var day=date.getDate()<10 ? "0" + date.getDate() : date.getDate();
      // 拼接
      if (type === 'm'){
        return month + '-' + day;
      } else {
        return year + '-' + month + '-' + day + ' ';
      }
    },
    // 求所选时间区间之间的日期，得出想到的日期格式（输入的日期格式是yy-MM-dd）
    getDateRange(begin, end) {
      var dateAllArr = new Array();          // 数组：盛放X轴的日期
      var beginSplit = begin.split("-");     // 开始日期，剪切后的数组[2019,01,08]
      var endSplit = end.split("-");        // 结束日期，剪切后的数组[2019,01,15]
      var beginUTC = new Date();            // 新日期db
      beginUTC.setUTCFullYear(beginSplit[0], beginSplit[1] - 1, beginSplit[2]);    // Mon Jan 08 2019 00:00:00 GMT+0800
      var endUTC = new Date();
      endUTC.setUTCFullYear(endSplit[0], endSplit[1] - 1, endSplit[2]);     //
      var beginL = beginUTC.getTime();      // 转换为毫秒数日期
      var endL = endUTC.getTime();
      for (var l = beginL; l <= endL;) {
          dateAllArr.push(this.dateFormat(new Date(parseInt(l))));
          l = l + 24 * 60 * 60 * 1000;
      }
      return dateAllArr;
    },
    //获取昨天的日期
    getDay (num) {
      var today = new Date();
      var nowTime = today.getTime();
      var ms = 24*3600*1000*num;
      today.setTime(parseInt(nowTime + ms));
      var oYear = today.getFullYear();
      var oMoth = (today.getMonth() + 1).toString();
      if (oMoth.length <= 1) oMoth = '0' + oMoth;
      var oDay = today.getDate().toString();
      if (oDay.length <= 1) oDay = '0' + oDay;
      return oYear + "-" + oMoth + "-" + oDay;
    },
    // 弹窗的搜索按钮，统计工作
    countChart () {
      console.log('现在是统计图表的操作');
      this.loadCountChart();
    },
    // 加载统计图表
    loadCountChart () {
      // 判断传入的时间参数是否为空
      if (this.queryParam.days[0]) {
        this.queryParam.beginDate = this.dateFormat(this.queryParam.days[0]);
      } else {//  参数为空，默认是倒数第七天
        this.queryParam.beginDate = this.dateFormat(this.getDay(-7));
        }
      if (this.queryParam.days[1]) {
        this.queryParam.endDate = this.dateFormat(this.queryParam.days[1]);
      } else {
        this.queryParam.endDate = this.dateFormat(this.getDay(-1));
      }
      // 得到所选时间段内的所有日期(x轴)
      this.xData = this.getDateRange(this.queryParam.beginDate , this.queryParam.endDate);
      console.log('this.xData============',this.xData);
      // 传送请求
      this.$http.post('console/log/countForChart', this.queryParam).then(({ resultData }) => {
        console.log('controller日志的图表统计', resultData);
        var map = {};                                  // 临时记录根据某字段分组的临时键值对变量
        for(let i = 0; i < resultData.length; i++){    // 遍历resultData数据
          var resultItem = resultData[i];              // resultItem代表resultData中的某一项
          map[resultItem.startDate] = resultItem;
        }
        this.yData = [];                              //**********初始化Ydata，否则每次搜索push的数据都会保留在里面 */
        for(let j=0 ; j < this.xData.length ; j++){  //  遍历X轴的数据
          let oneDay = this.xData[j].trim();                 //  2019是写死的，要对年份进行处理，自动识别年份
          let dataOneDay = map[oneDay];                        //oneDay 应该是和this.xData[j]相对应的
           //  根据所选显示数据赋值（y轴）
          if (this.queryParam.rule === 'num'){
            this.yData.push(dataOneDay?dataOneDay.num:0);
          }
          if (this.queryParam.rule === 'durationAvg') {
            this.yData.push(dataOneDay?dataOneDay.durationAvg:0);// 切换第二个的时候，第一次push的内容还在yData里***************
          }
          if (this.queryParam.rule === 'durationMax') {
            this.yData.push(dataOneDay?dataOneDay.durationMax:0);
          }
        }
        this.$refs.toChart.reloadChart(this.xData, this.yData);
        

      }).catch((error) => {
        console.log('查询数据异常', error);
      });
    },
    // 查询列表
    countList () {
      console.log('查询');
      this.queryParam.page = 0;
      this.loadPageList();
    },
    // 加载列表
    loadPageList () {
      this.tabLoading = true;
      // 判断传入的时间参数是否为空
      if (this.queryParam.day) { //  参数不为空
        this.queryParam.dayFm = this.dateFormat(this.queryParam.day);
      } else {//  参数为空，默认显示昨天
        // debugger;
        this.queryParam.dayFm = this.dateFormat(this.getDay(-1));
      }
      // 传送请求
      this.$http.post('console/log/pageList', this.queryParam).then(({ resultData }) => {
        console.log('controller日志的每日访问量统计', resultData);
        this.contentData = resultData.content;
        this.totalPage = resultData.totalElements;
        this.tabLoading = false;
      }).catch((error) => {
        console.log('查询日志异常', error);
      });
    },
    //  弹出窗口的事件
    show (dbData) {
      this.chartFlag = true;
      this.loadCountChart();
    },
    pageChange (currentPage) {
      this.queryParam.page = currentPage - 1;
      this.loadPageList();
    },
    changeSize (pageSize) {
      this.queryParam.size = pageSize;
      this.loadPageList();
    }
  }
};
</script>
<style lang="less">
.vertical-center-modal{
        display: flex;
        align-items: center;
        justify-content: center;
        .ivu-modal{
            top: 0;
        }
    }
</style>
