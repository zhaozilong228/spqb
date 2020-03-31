<template>
  <div>
<!--        <Card>
            <Row>
                <Form :label-width="80" inline style="float:left;">
                    
                        
                    <FormItem label="执行类名称">
                        <Input v-model="queryParam.executeClass" style="width:100px;"
                          placeholder="请输入执行类名称"  clearable/>
                    </FormItem>
                    <FormItem label="异常名称">
                        <Input v-model="queryParam.excuteException" style="width:100px;" 
                        placeholder="请输入异常名称"  clearable/>
                    </FormItem>
                     <FormItem label="执行方法">
                        <Input v-model="queryParam.executeFunction" style="width:100px;" 
                        placeholder="请输入执行方法"  clearable/>
                    </FormItem>
                      <FormItem label="日志编号">
                        <Input v-model="queryParam.id" placeholder="请输入日志编号" 
                          style="width:120px;"   
                        clearable/>
                    </FormItem>
                    
                      <FormItem label="起止时间">
                       <DatePicker type="datetimerange" format="yyyy-MM-dd" style="width:180px;" v-model="evaluateTimeRange" placeholder="请选择起止时间"
                        on-focus="timefn" >
                         
                       </DatePicker>
                   </FormItem> 

                   <FormItem label="排序">
                      <i-select v-model="queryParam.sort" style="width:200px" >
                         <i-option v-for="item in cityList" :value="item.value">{{ item.label }}</i-option>
                    </i-select>

                     <DicSelect v-model="queryParam.sourceType" style="width:80px;" dicType="evaluate_source_type"></DicSelect>
                   </FormItem> 
                    
                    <Button style="margin-bottom:20px;margin-right: 10px;" @click="searchPageList" type="primary" icon="search">搜索</Button>
                 
                </Form>
            </Row>
            <Row>
                <Table :columns="evaluateColumns" :data="evaluateDataList" :loading="tabLoading" border>
                </Table>
                <Page :total="totalPage" :page-size="queryParam.size" :current="evaluateCurrent" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
            </Row>
        </Card> -->
        1
        <Upload action="api/pms/image" :on-success="handleSuccess" :before-upload="beforeUpload">
                <i-button type="ghost" icon="ios-cloud-upload-outline">上传文件</i-button>
        </Upload>
  </div>
</template>

<script>
  import evaluateDetail from './service_Detail.vue';
export default {
  name: 'service_log',
  data () {
    return {
      test: 'This is service_log page11!',
      message: 'Runoob' ,
      
       evaluateDataList: [],
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
                 //startTime :"",
                 executeClass:"",
                 endMillisecond:"",
                 excuteException:"",
                 sort:"DESC",
                 page: 0,
                 size: 10
            }, 
             evaluateTimeRange: [],
            tabLoading: false,
            evaluateColumns: [
            
                   {
                    type: 'expand',
                    width: 30,
                    render: (h, params) => {
                        return h(evaluateDetail, {
                            props: {
                                row: params.row
                            }
                        });
                    }
                },
                 {
                    key: 'id',
                    align: 'left',
                    minWidth: 120,
                    title: '异常日志编号',
                  
                }, 
                {
                    key: 'executeClass',
                    align: 'center',
                    minWidth: 200,
                    title: '执行类',
                    
                },
                 
                {
                    key: '_class',
                    align: 'left',
                    minWidth: 140,
                    title: '所报异常类名称',
                  
                },

                {
                    key: 'duration',
                    align: 'left',
                    minWidth: 100,
                    title: '持续时间',
                    render: (h, params) => {
                        let duration = params.row.duration;
                        if (duration.length > 18) {
                            duration = duration.substring(0, 18) + '...';
                        }
                        return h('span', {}, duration);
                    }
                },
                {
                    key: 'startTime',
                    align: 'center',
                    minWidth: 150,
                    title: '开始时间',
                   
                    
                },
                {
                    key: 'endTime',
                    align: 'center',
                    minWidth: 150,
                    title: '结束时间',
                    
                },
                {
                    key: 'executeFunction',
                    align: 'center',
                    minWidth: 150,
                    title: '执行函数',
                    
                },
                {
                    key: 'excuteException',
                    align: 'center',
                    minWidth: 200,
                    title: '异常名称',
                     render: (h, params) => {
                        let excuteException = params.row.excuteException;
                        if (excuteException.length > 18) {
                            excuteException = excuteException.substring(0, 18) + '...';
                        }
                        return h('span', {}, excuteException);
                    }
                    
                }
                 
                
      ] ,
      totalPage: 0,
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
                model1: '',
                headers:{'Content-Type': 'multipart/form-data'}
      
      }
  },      
          //加载 
            created () {
            this.loadPageList();
            this.queryParam.page = 0;
            }, 
          computed: {
            evaluateCurrent () {
            return this.queryParam.page + 1;
            this.tabLoading = true;
            //alert(this.queryParam.page);
            }
            

        },
    methods: {
      
      // 查询
        searchPageList () {
          //alert('查询');
            console.log('查询');
            this.queryParam.page = 0;
            this.loadPageList();
        },
        //加载列表
        loadPageList(){
          this.timeid=true;
            this.logid=true;
            this.tabLoading = true;

              if (this.evaluateTimeRange[0]) {
                this.queryParam.startTime = this.formatDateToStr(this.evaluateTimeRange[0]);
            } else {
               
                //this.queryParam.startTime = '';
            }
            if (this.evaluateTimeRange[1]) {
                this.checkStartEndTime();
                this.queryParam.endTime = this.formatDateToStr(this.evaluateTimeRange[1]);

            } else {
                
                 this.initDate();
                //this.queryParam.endTime = '';
            }
            this.$http.post('console/log/findServicExceptionAlls',this.queryParam ).then(({resultData}) => {
                console.log('列表数据', resultData);

                this.evaluateDataList =resultData.content;
                this.totalPage = resultData.totalElements;
                this.tabLoading = false;
            }).catch((error) => {
                console.log('查询表格数据异常', error);
                this.tabLoading = false;
            });
             
        },
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
        initDate(){
            let d = new Date();
            let month = (d.getMonth() + 1) + '';
            month = month.length === 1 ? '0' + month : month;
            let day = d.getDate() + '';
            day = day.length === 1 ? '0' + day : day;
            let endTime = d.getFullYear() + '-' + month + '-' + day + ' ' + '00' + ':' + '00' + ':' + '00';

            let   startTime = this.formatDateToStr(new Date(endTime).getTime()-3600*1000*24);
            //alert(startTime+','+endTime);
            this.evaluateTimeRange[0] = startTime;
            this.evaluateTimeRange[1] = endTime;
            this.queryParam.startTime = startTime;
            this.queryParam.endTime = endTime;
        },
        checkStartEndTime (){
            let startMillisecond = new Date(this.evaluateTimeRange[0]).getTime();
            let endMillisecond = new Date(this.evaluateTimeRange[1]).getTime(); 
            if (startMillisecond === endMillisecond) {
              //  alert('毫秒数相同,处理前的结束时间'+this.formatDateToStr(endMillisecond));
                this.evaluateTimeRange[1] = new Date(endMillisecond+3600*1000*24)
               // alert('处理后 的结束时间,'+this.formatDateToStr(endMillisecond+3600*1000*24));
            }
        },
        handleSuccess (res, file, filelist) {
            console.log('success', res, file, filelist);
            console.log(file.name, file.url);
        },
        beforeUpload(file) {
          console.log('beforeUpload', file, file.name, file.url);
        } 
    }
}
</script>

<style>

</style>
