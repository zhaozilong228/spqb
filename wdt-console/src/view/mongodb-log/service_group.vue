 <template>
  <div>
    <Card>
      <Row> <!-- <Tag>  方法：{{this.queryParam.executeFunction}}，类名：{{this.queryParam.executeClass}}</Tag> -->
            <Form :label-width="160" inline style="float:left;">    
                <FormItem label="按某个方法名统计">
                    <Input v-model="queryParam.executeFunction" style="width:150px;" placeholder="请输入方法名" clearable/>
                </FormItem>
                <FormItem label="按某个类名统计">
                    <Input v-model="queryParam.executeClass" style="width:150px;" placeholder="请输入方法名" clearable/>
                </FormItem>
                <FormItem label="选择日期" style="float:left" >
                  <DatePicker v-model="dayTime" type="date" format="yyyy-MM-dd " placeholder="请选择日期" 
                  style="width: 200px" on-change="checkStartTime">
                  </DatePicker>
                </FormItem> 
                <Button style="margin-bottom:20px;margin-right: 10px; float:right;" @click="searchPageList" type="primary" icon="search">搜索</Button>
            </Form>
        </Row>
        <Row> <Tag> 所选时间内所有方法出现次数{{this.totalFunction}}</Tag>
            <Table stripe :columns="evaluateColumns" :data="evaluateDataList" :loading="tabLoading" border 
            @on-sort-change='changeSort'>
            </Table>
            <Page :total="totalPage" :page-size="queryParam.size" :current="evaluateCurrent" @on-change="pageChange" 
            @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
        </Row>
    </Card>

    <Modal :title="titleFunction"  v-model="statModel" :styles="{top: '20px'}"   width="1250"  @on-ok="changeModel" 
       @on-cancel="changeModel" >
        <Row style="margin-top: 0px;">
        <Row>
          <DatePicker v-if="isRouterAlive" type="daterange" placement="bottom-end" placeholder="请选择一周内" style="width:450；"
           v-model="evaluateTimeRange">
          </DatePicker>
            <Button style="margin-bottom:20px;margin-right: 10px; float:right;" @click="searchWeeksList" type="primary" icon="search">搜索</Button>
        </Row>
        <Card shadow>
            <Example   style="height: 310px; width:100%;"    
              :mychars="mycarss" 
              :test="test"
              :countArray="countArray"
              :durationArray="durationArray"
              :durationMaxArray="durationMaxArray" 
              :statistical="statParam.executeFunction"
               >
            </Example>
        </Card>
      </Row>
   </Modal>
  </div>
</template>

<script>
import Example from './service_example.vue';
import InforCard from '_c/info-card'
import CountTo from '_c/count-to'
import { ChartPie, ChartBar } from '_c/charts'
export default {
   components: {
      Example
   } ,
  name: 'service_group',
  data () {
    return {
        test: 'This is service_log page11!',
        statModel: false,
        chartwidth:'40',
        evaluateDataList: [],
        mycarss:[],
        countArray:[],
        durationArray:[],
        durationMaxArray:[],
        test:'',
        titleFunction:'',
        dayTime: '',
        isRouterAlive: '',
        queryParam: {
            id:"",
            _class:"",
            duration: "",
            excuteResult:"",
            startTime:"",
            endTime:"",
            executeParamsJson:"",
            startMillisecond :"",
            executeFunction :"",
            executeClass:"",
            endMillisecond:"",
            excuteException:"",
            sort:"",
            durationSort:'',
            countSort:'',
            durationMaxSort:'',
            startTime1: '',
            day:7,
            page: 0,
            size: 10
            }, 
        statParam: {
            startTime: '',
            endTime: '',
            day:7,
            executeFunction: '',
            executeClass: '',
        },
        evaluateTimeRange: [],
        tabLoading: false,
        evaluateColumns: [
          {
              key: 'ExecuteClass',
              align: 'left',
              minWidth: 150,
              title: '类名',
            
          },
           {
              key: 'ExecuteFunction',
              align: 'left',
              minWidth: 120,
              title: '方法名',
            
          },
          {
              key: 'count',
              align: 'center',
              Width: 60,
              title: '出现次数',
              sortable: true,
              
              
          },
          {
              key: 'duration',
              align: 'center',
              Width: 60,
              title: '平均时长(毫秒)',
              sortable:true,
              sortMethod:function(){},
              render: (h, params) => {
                  let duration = params.row.duration;
                  duration = parseInt(duration);
                  return h('span', {}, duration);
              }
              
          }, 
          {
              key: 'durationMax（毫秒）',
              align: 'center',
              Width: 60,
              title: '最大时长',
              sortable:true,
              sortMethod:function(){},
              render: (h, params) => {
                  let duration = params.row.durationMax;
                  duration = parseInt(duration);
                  return h('span', {}, duration);
              }
              
          },
          {
            title: '行为',
            key: 'action',
            width: 150,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small',
                    long:true,
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      console.log('当前统计的方法和类信息'+
                      this.evaluateDataList[ params.index].ExecuteFunction+", "+
                      this.evaluateDataList[ params.index].ExecuteClass);
                    this.show(params.index)
                    //设置默认时间
                    //this.defaultData()
                    //重新绘画折线图
                    this.drawLine()
                     }
                  }
              }, '统计'),
                     
            ]);
            }
          }                         
      ] ,
      totalPage: 0,
      totalFunction:0,
      cityList: [
        {
            value: 'DESC',
            label: '降序'
        },
        {
            value: 'ASC',
            label: '升序'
        },   
      ],
      model1: ''
      
      }
  },      
  //加载 
    created () {
       
      this.loadPageList();
      this.queryParam.page = 0;

      this.$http.post('console/log/ckeckTask',{} ).then(({resultData}) => {
          console.log('**检查定时任务状态**', resultData);
        
        }).catch((error) => {
          console.log('查询表格数据异常', error);
          this.tabLoading = false;
      });
    
    }, 
    computed: {
      evaluateCurrent () {
      return this.queryParam.page + 1;
      this.tabLoading = true;
      //alert(this.queryParam.page);
      }
      

  },
    methods: {
      reload () {
            this.isRouterAlive = false
            this.$nextTick(() => (this.isRouterAlive = true))
        },
      // 查询
        searchPageList () {
            console.log('查询');
            //把搜索指定日期保留下来
            this.tempDate=this.queryParam.startTime;
            this.queryParam.page = 0;
            this.loadPageList();
            //设置为搜索是的排序为降序 DESC，以为DESC会反转排序规则
            this.queryParam.countSort = 'DESC'
        },
        //加载列表
        loadPageList(){
            
             this.tabLoading = true;
              if (this.dayTime) {
                this.queryParam.startTime = this.formatDateToStr(this.dayTime);
            } else {
                //this.queryParam.startTime = '';
                this.queryParam.startTime=this.formatDayToStr(new Date());
                this.dayTime = this.queryParam.startTime;
            }
            this.tabLoading = false;
            this.$http.post('console/log/groupByDateNewTable',this.queryParam ).then(({resultData}) => {
                console.log('列表数据', resultData);

               this.evaluateDataList =resultData.content;
                this.totalPage = resultData.totalElements;
                console.log("evaluateDetail数组的长度",this.evaluateDataList.length);
               for(var i=0;i< this.evaluateDataList.length;i++)
               {
                  console.log(this.evaluateDataList[i]._id.executeFunction);
                  //this.evaluateDataList[i].executeClass=this.evaluateDataList[i]._id.ExecuteClass;
                  //this.evaluateDataList[i].executeFunction=this.evaluateDataList[i]._id.ExecuteFunction;
                  //console.log("cntent");
               }
                console.log(this.totalPage);
                this.tabLoading = false;
                this.isSort();
            }).catch((error) => {
                console.log('查询表格数据异常', error);
                this.tabLoading = false;
            });

            //====================================================================
             this.$http.post('console/log/getCountFunction',this.queryParam ).then(({resultData}) => {
                console.log('列表数据', resultData);
                this.totalFunction=resultData;

            }).catch((error) => {
                console.log('查询表格数据异常', error);
                this.tabLoading = false;
            }); 

        }
        ,
        // 格式化时间
        formatDateToStr (datestamp) {
            if (!datestamp) {
                return '-';
            }
            let d = new Date(datestamp);
            let month = (d.getMonth() + 1) + '';
            month = month.length === 1 ? '0' + month : month;
            let day = d.getDate() + '';
            day = day.length === 1 ? '0' + day : day;
            let hour = d.getHours() + '';
            hour = hour.length === 1 ? '0' + hour : hour;
            let minute = d.getMinutes() + '';
            minute = minute.length === 1 ? '0' + minute : minute;
            let second = d.getSeconds() + '';
            second = second.length === 1 ? '0' + second : second;
            return d.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        },     // 格式化时间
        formatDateWeekToStr (datestamp) {
            if (!datestamp) {
                return '-';
            }
            let d = new Date(datestamp);
            let month = (d.getMonth() + 1) + '';
            month = month.length === 1 ? '0' + month : month;
            let day = d.getDate() + '';
            day = day.length === 1 ? '0' + day : day;
             
            return d.getFullYear() + '-' + month + '-' + day;
        },
        pageChange (currentPage) {
            if (this.queryParam.page !== currentPage - 1) {
                this.queryParam.page = currentPage - 1;
                this.loadPageList();
            }
        },
        changeSize (pageSize) {
            
            if (this.queryParam.size !== pageSize) {
                this.queryParam.size = pageSize;
                 
                this.loadPageList();
            }
        },
        //设置排序方式   
      changeSort(){
        console.log(' changeSort',arguments ); 
        //console.log(' changeSort',arguments[0].order); 
         //获取排序字段
         let key=arguments[0].column.key;
         //获取排序规则
         let order=arguments[0].order;
         //alert(key+","+order);
           console.log('排序列和规则：',arguments[0].order+', '+arguments[0].key); 
        if(key==='count'){
            this.queryParam.countSort=order;
            this.queryParam.durationMaxSort = '';
            this.queryParam.durationSort = '';
        }else if(key==='durationMax'){
             this.queryParam.durationMaxSort = order;
             this.queryParam.countSort='';
             this.queryParam.durationSort = '';
        }else if(key==='duration'){
            this.queryParam.durationSort = order;
             this.queryParam.durationMaxSort = '';
             this.queryParam.countSort='';
        }

            this.loadPageList();
        },
            //判断当前排序类型
            isSort(){
                
                if(this.queryParam.countSort === 'DESC'){
                  this.queryParam.countSort = 'ASC'
                    }
                else{
                      this.queryParam.countSort = 'DESC'
                    }
                },
                 
                show (index) {
                    this.queryParam.executeFunction=this.evaluateDataList[index].ExecuteFunction;
                    this.queryParam.executeClass=this.evaluateDataList[index].ExecuteClass;
                    
                    this.statParam.executeFunction=this.evaluateDataList[index].ExecuteFunction;
                    this.statParam.executeClass=this.evaluateDataList[index].ExecuteClass;
                    this.titleFunction =this.evaluateDataList[index].ExecuteFunction;
                    this.statModel=true;
            },
            //重新绘画折线图
            drawLine () {
                if (this.evaluateTimeRange[0]) {
                    //this.statParam.startTime = this.formatDateToStr(this.evaluateTimeRange[0]);
                    this.queryParam.startTime = this.formatDateToStr(this.evaluateTimeRange[0]);
                } 
                if (this.evaluateTimeRange[1]) {
                    //this.statParam.endTime = this.formatDateToStr(this.evaluateTimeRange[1]);
                    this.queryParam.endTime = this.formatDateToStr(this.evaluateTimeRange[1]);
                }else{
                    this.defaultData();
                }
                      //计算起止时间为多少天
                    this.getDay(this.queryParam.startTime,this.queryParam.endTime);
                    //alert(this.queryParam.startTime+"开始时间格式 ")
                  //=============================================================================>
                     console.log(this.queryParam,"打印查询参数");
                     console.log(this.statParam,"打印统计图查询参数");
                  this.$http.post('console/log/groupByDateNewTableLine',this.queryParam ).then(({resultData}) => {
                       console.log('*****列表数据*****', resultData);
                      this.countArray=resultData[0];
                      this.durationArray=resultData[1]
                      this.durationMaxArray=resultData[2]
                      this.mycarss=resultData[3];
                        
                  }).catch((error) => {
                      console.log('groupByDateNewTableLine查询表格数据异常', error);
                      this.tabLoading = false;
                  }); 


                    },
          // 按指定周查询 
        searchWeeksList () {
           console.log("此时时间选择器里的值"+this.evaluateTimeRange[0]);
            this.getDateByMs(this.evaluateTimeRange[0])
            console.log('查询');
            this.drawLine();
        },
    //展开视图默认上一周的周一至周日
    defaultData () {
         let d = new Date();
         let month = (d.getMonth() + 1) + '';
         month = month.length === 1 ? '0' + month : month;
         let day = d.getDate() + '';
         day = day.length === 1 ? '0' + day : day;
         let hour = d.getHours() + '';
         hour = hour.length === 1 ? '0' + hour : hour;
         let minute = d.getMinutes() + '';
         minute = minute.length === 1 ? '0' + minute : minute;
         let second = d.getSeconds() + '';
         second = second.length === 1 ? '0' + second : second;
        let endTime = d.getFullYear() + '-' + month + '-' + day + ' ' + '00' + ':' + '00' + ':' + '00';
        let startTime =this.formatDateToStr(new Date(endTime).getTime()-3600*1000*24*7); 
        //alert(startTime+","+endTime);
        this.queryParam.startTime =startTime;
        this.queryParam.endTime = endTime; 
        //this.queryParam.startTime1=startTime; 

        this.statParam.startTime = startTime;
        this.statParam.endTime = endTime; 
        this.evaluateTimeRange[0] = startTime;
        this.evaluateTimeRange[1] = endTime;
        this.reload(); 

    },
     getDay(startTime,endTime){
        let start = new Date(startTime);
        let  end  = new Date(endTime);
        let  mid  = end-start;
        let  day  =  mid/(1000*60*60*24)+1;
        console.log("当前选取的时间天数为：",day);
        this.queryParam.day=day;    
          
     },
     getDateByMs(datestamp){
        let d = new Date(datestamp);
        let month = (d.getMonth() + 1) + '';
        month = month.length === 1 ? '0' + month : month;
        let day = d.getDate() + '';
        day = day.length === 1 ? '0' + day : day;
             
         //alert('选择器的时间'+d.getFullYear() + '-' + month + '-' + day) ;
     },
     changeModel(){
     
        this.queryParam.executeFunction='';
        this.queryParam.executeClass='';
        //this.queryParam.startTime=this.tempDate;

     },
     checkStartTime(){
     },
    formatDayToStr (datestamp) {
        if (!datestamp) {
            return '-';
        }
        let d = new Date(datestamp);
        let month = (d.getMonth() + 1) + '';
        month = month.length === 1 ? '0' + month : month;
        let day = d.getDate() + '';
        day = day.length === 1 ? '0' + day : day;
        let hour = d.getHours() + '';
        hour = hour.length === 1 ? '0' + hour : hour;
        let minute = d.getMinutes() + '';
        minute = minute.length === 1 ? '0' + minute : minute;
        let second = d.getSeconds() + '';
        second = second.length === 1 ? '0' + second : second;
         let startTime= d.getFullYear() + '-' + month + '-' + day + ' ' + '00' + ':' + '00' + ':' + '00';
         return this.formatDateToStr(new Date(new Date(startTime).getTime()-3600*1000*24));
    },     
}
}
</script>

<style>

</style>
