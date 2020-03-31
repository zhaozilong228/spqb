<template>    
  <div>
    <Card>
      <Row :gutter="16">
        <Col span="24">
          <Form :label-width="95" inline >
            <FormItem label="日志编号">
              <Input search v-model="queryParam.id" placeholder="请输入id，不支持模糊查询" clearable style="width: 280px"/>
            </FormItem>
            <FormItem label="日志路径">
              <Input search v-model="queryParam.url" placeholder="请输入url，支持模糊查询" clearable  style="width: 280px"/>
            </FormItem>
            <FormItem label="执行方法">
              <Input search v-model="queryParam.executeFunction" placeholder="请输入executeFunction，支持模糊查询" clearable  style="width: 280px"/>
            </FormItem>
            <!-- <FormItem label="起止时间">  
              <DatePicker v-model="queryParam.date" style="width: 280px" type="datetimerange" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择起止时间，精确到秒"></DatePicker>
            </FormItem> -->
          </Form>
        </Col>
      </Row>
      <Row :gutter="16">
        <Col span="24">
          <Form :label-width="95" inline >
            <FormItem label="选择日期" > 
                <DatePicker v-model="queryParam.day" type="date" format="yyyy-MM-dd" placeholder="请选择，默认显示昨天的数据" style="width: 216px"></DatePicker>
                <div style="width:280px"></div>
            </FormItem>
            <FormItem label="有无异常">  
               <Select v-model="queryParam.execption" style="width:120px;text-align:center">
                  <Option v-for="item in execptionList" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>
              <div style="width:200px;margin-right: 80px"></div>
            </FormItem>
            <FormItem label="显示顺序">  
              <RadioGroup v-model="queryParam.sort" type="button" @on-change="searchList" style="width: 180px">
                <Radio label="desc" style="width: 70px;text-align:center">倒序</Radio>
                <Radio label="asc" style="width: 70px;text-align:center">正序</Radio>
              </RadioGroup>
              <Button @click="searchList" type="primary"  icon="ios-search" style="width: 80px;margin-left: 20px">搜索</Button>
            </FormItem>
          </Form>
        </Col>
      </Row>
      <Row>
        <Table :columns="Columns" :data="contentData" :loading="tabLoading" border></Table>
        <Page :total="totalPage" :page-size="queryParam.size" :current="current" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
      </Row>
    </Card>
  </div>
</template>

<script>
import controllerLogDetail from './controllerLogDetail.vue';
export default {
  name: 'controller_log',
  data () {
    return {
      tabLoading: false,
      totalPage: 0,
      sort: 'desc',
      contentData: [],
      queryParam: {
        id: '',
        url: '',
        executeFunction: '',
        execption: '',
        page: 0,
        size: 10,
        day: this.getDay(-1),
        dayFm: '',
        sort: 'desc'
      },
      execptionList: [
        // {
        //   value: 'all',
        //   label: '全部数据'
        // },
        {
          value: 'notExist',
          label: '无异常'
        },
        {
          value: 'exist',
          label: '有异常'
        }
      ],
      // 这里是字段名
      Columns: [
        // 扩展
        {
          type: 'expand',
          width: 30,
          render: (h, params) => {
            return h(controllerLogDetail, {
              props: {
                row: params.row
              }
            })
          }
        },
        {
          key: 'id',
          align: 'center',
          minWidth: 80,
          title: '日志编号'
        },
        {
          key: 'url',
          align: 'center',
          minWidth: 30,
          title: '日志路径'
        },
        {
          key: 'executeFunction',
          align: 'center',
          minWidth: 80,
          title: '执行方法'
        },
        {
          key: 'duration',
          align: 'center',
          minWidth: 20,
          title: '持续时长/(ms)'
        },
        {
          key: 'startTime',
          align: 'center',
          minWidth: 80,
          title: '开始时间'
        },
        {
          key: 'endTime',
          align: 'center',
          minWidth: 80,
          title: '结束时间'
        }
      ]
    };
  },
  created () {
    this.loadPageList();
    this.queryParam.page = 0;
  },
  computed: {
    current () {
      return this.queryParam.page + 1;
    }
  },
  methods: {
    // 时间格式化函数，此处仅针对yyyy-MM-dd  的格式进行格式化
    dateFormat:function(time) {
      var date=new Date(time);
      var year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month= date.getMonth()+1<10 ? "0" + (date.getMonth()+1) : date.getMonth()+1;
      var day=date.getDate()<10 ? "0" + date.getDate() : date.getDate();
      // var hours=date.getHours()<10 ? "0" + date.getHours() : date.getHours();
      // var minutes=date.getMinutes()<10 ? "0" + date.getMinutes() : date.getMinutes();
      // var seconds=date.getSeconds()<10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return year + "-" + month + "-" + day + " ";
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
    // 查询
    searchList () {
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
      // if (this.queryParam.date[0]) {
      //   this.queryParam.beginTime = this.dateFormat(this.queryParam.date[0]);
      // } else {
      //   this.queryParam.beginTime = '';
      //   }
      console.log('dayFm的格式', this.queryParam.dayFm);
      // 传送请求
      this.$http.post('console/log/findAllLog', this.queryParam).then(({ resultData }) => {
        console.log('查询controller的日志', resultData);
        this.contentData = resultData.content;
        this.totalPage = resultData.totalElements;
        this.tabLoading = false;
      }).catch((error) => {
        console.log('查询日志异常', error);
      });
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
<style>

</style>
