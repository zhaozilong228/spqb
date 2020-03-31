 <template>
    <div v-if="isRouterAlive" style="width:100%">
        <Row>
           <Row>
            <Form :label-width="160" inline style="float:left;">
                <FormItem label="起止时间" v-if="isRouterAlive">
                    <DatePicker type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择时间范围" style="width: 300px" v-model="evaluateTimeRange">
                    </DatePicker>
                </FormItem> 
                 <FormItem label="时间范围">
                    <RadioGroup v-model="timeOption" type="button" @on-change="timeOptions" style="width: 240px">
                        <Radio label="hour" style="width: 70px;text-align:center">一小时</Radio>
                        <Radio label="sixhour" style="width: 70px;text-align:center">六小时</Radio>
                        <Radio label="oneday" style="width: 70px;text-align:center">一天</Radio>
                    </RadioGroup>
                </FormItem>
                <Button style="margin-bottom:20px;margin-right: 10px; float:right;" @click="loadData" type="primary" icon="search">搜索</Button>
            </Form>
            </Row>
        </Row>
        <!-- <div ref="dom"  style="height: 500px; width:1000px;" ></div> -->
            <Example    v-if="isRouterAlive"  class="lineStyle"
            :time="timeArray"
            :num="numArray" 
            :durationAvg="durationAvgArray"
            :durationMax="durationMaxArray"
            :dataMax="dataMax"
            :durationMaxValue="durationMax"
            statistical="系统运行监控分析">
            </Example>

  </div>
</template>

<script>
//import echarts from 'echarts' 
import Example from './systemline.vue';
 export default {
    components: {
      Example
  } ,
    name: 'stsytemname',
    data () {
        return {
            test:'测试字符串',
            timeOption:'hour',
            isRouterAlive: true,
            calcCount:0,
            myWidth:0,
            tempMax:0,
            dataMax:0,
            durationMax:0,
            durationAvgArray:[],
            durationMaxArray:[],
            numArray:[],
            timeArray:[],
            testMax:[],
            testModel:false,
            xData:[],
            xDataTemp:[],
            numArrayTemp:[],
            durationAvgArrayTemp:[],
            durationMaxArrayTemp:[],
            evaluateTimeRange: [],
             queryParam: {
                 endTime:"",
                 startTime :"",
                 page: 0,
                 size: 10
            }, 
     
      }
  },      
    //加载 
    created () {
        this.initCreateDates();
        this.loadData();
    }, 
    mounted(){
        const that = this
        window.onresize =()  =>{
            return (()=>{
                window.screenWidth = window.innerWidth;
                 
            })()
        }

    },
    computed: {
        isShowOneDay(){
            return this.timeOption==='oneday';
        }
    },
    methods: {
        reload () {
            this.isRouterAlive = false
            this.$nextTick(() => (this.isRouterAlive = true))
          },
        loadData(){
            if (this.evaluateTimeRange[0]) {
                this.queryParam.startTime = this.formatDateToStr(this.evaluateTimeRange[0]);
            } else {
                //this.queryParam.startTime = '';
                this.initCreateDates()
            }
            if (this.evaluateTimeRange[1]) {
                this.queryParam.endTime = this.formatDateEndTime(this.evaluateTimeRange[1]);
            } else {
                //this.queryParam.endTime = '';
            }
            this.xData=[];
            this.numArray=[];
            this.durationMaxArray=[];
            this.durationAvgArray=[];
            this.getMinut(this.queryParam.startTime,this.queryParam.endTime);
            this.timeArray=this.xData;
            this.queryParam.startTime=new Date(this.queryParam.startTime);
            this.queryParam.endTime=new Date(this.queryParam.endTime);
            console.log('打印查询参数',this.queryParam)
            let calcCount=0;//计算比较次数
            this.$http.post('console/log/findComplicateForChart',this.queryParam ).then(({resultData}) => {
                console.log('列表数据', resultData); 
                let start =new Date().getTime();
                let end=0;//结束毫秒数
                let mid=0; //中间毫秒数   
                let flag=0;//标记
                for(var i=0;i<this.xData.length;i++){
                    for(var j=0;j<resultData.length;j++){
                        calcCount++;
                        if(this.xData[i]===resultData[j].complicateTime){
                        this.numArray.push(resultData[j].num);
                        this.durationAvgArray.push(resultData[j].durationAvg);
                        this.durationMaxArray.push(resultData[j].durationMax);
                        console.log('有数据',this.xData[i]);
                        this.xData[i]=this.xData[i].substring(10,18);
                        flag=1;
                        break;
                    }
                }
                if(flag===0){
                    this.xData[i]=this.xData[i].substring(10,18);
                    console.log('没数据',this.xData[i]);
                    this.numArray.push(0);
                    this.durationAvgArray.push(0);
                    this.durationMaxArray.push(0);
                }
                flag=0;
            }
            console.log('最大访问时长',this.durationMaxArray);
            end =new Date().getTime();
            mid=end-start;
           /* this.dataMax=0;
            this.durationMax=0;
            //找出刻度中最大值value
            for(var i=0;i<resultData.length;i++){
                if(this.dataMax<resultData[i].num){
                    this.dataMax=resultData[i].num;
                }
                if(this.durationMax<resultData[i].durationMax){
                    this.durationMax=resultData[i].durationMax;
                }
            }
            this.dataMax=this.dataMax+20;
            this.durationMax=this.durationMax+500;*/
            /*alert('数据测试报告1： 当前数组长度为:'+this.numArray.length+',当前比较次数为'+calcCount+
                '，x轴长度'+this.xData.length+'接口数据条数:'+resultData.length+', 循环算法耗时'+mid+'毫秒数');*/
            console.log('数据测试报告1： 当前数组长度为:'+this.numArray.length+',当前比较次数为'+calcCount+
                '，x轴长度'+this.xData.length+'接口数据条数:'+resultData.length+', 循环算法耗时'+mid+'毫秒数');
           //==========================================================================================================================
           /* calcCount=0;
            start =new Date().getTime();
            var map = {};
            for(let i = 0; i < resultData.length; i++){   // 遍历resultData数据
                var resultItem = resultData[i];              // resultItem代表resultData中的某一项
                map[resultItem.complicateTime] = resultItem;
                calcCount++;
            }
           for(let j=0 ; j < this.xData.length ; j++){  //  遍历X轴的数据
                let oneDay = this.xData[j].trim();                 //  2019是写死的，要对年份进行处理，自动识别年份
                let dataOneDay = map[oneDay];                        //oneDay 应该是和this.xData[j]相对应的
                //  根据所选显示数据赋值（y轴）
                //this.yData.push(dataOneDay?dataOneDay.num:0);
                this.numArray.push(dataOneDay?dataOneDay.num:0);
                this.durationAvgArray.push(dataOneDay?dataOneDay.durationAvg:0);
                this.durationMaxArray.push(dataOneDay?dataOneDay.durationMax:0);
                calcCount++;

            }
            end =new Date().getTime();//s算法结束毫秒数
            mid=end-start;//算法耗用毫秒数
            /*alert('数据测试报告2： 当前数组长度为:'+this.numArray.length+',当前比较次数为'+calcCount+
                '，x轴长度'+this.xData.length+'接口数据条数:'+resultData.length+', 算法耗时'+mid+'毫秒数');*/
            console.log('数据测试报告2： 当前数组长度为:'+this.numArray.length+',当前比较次数为'+calcCount+
                '，x轴长度'+this.xData.length+'接口数据条数:'+resultData.length+', 算法耗时'+mid+'毫秒数');

            let sum=0;
            console.log('num',this.numArray);
           /* if(this.timeOption==='sixhour1'){
                for(let i=0;i<this.xData.length;i++){
                    sum+=this.numArray[i];
                    if((i+1)%30==0){
                        console.log('sum',sum);
                        this.xDataTemp.push(this.xData[i]);
                        this.numArrayTemp.push(sum);;
                        sum=0;
                    }
                }
                this.numArray=this.numArrayTemp;
                console.log('总数num',this.numArray)
                //处理平均数
                sum=0;this.durationAvgArrayTemp=[];
                for(let i=0;i<this.xData.length;i++){
                    sum+=this.durationAvgArray[i];
                    if((i+1)%30==0){
                        console.log('sum',sum);
                        //this.xDataTemp.push(this.xData[i]);
                        this.durationAvgArrayTemp.push(sum);;
                        sum=0;
                    }
                }
                this.durationAvgArray=this.durationAvgArrayTemp;
                console.log('平均数avg',this.durationAvgArray);
                //处理最大数
                let max=0;this.durationMaxArrayTemp=[];
                for(let i=0;i<this.xData.length;i++){
                    if(max<this.durationMaxArray[i]){
                        max=this.durationMaxArray[i];
                    }
                    if((i+1)%30==0){
                        console.log('max',max);
                        //this.xDataTemp.push(this.xData[i]);
                        this.durationMaxArrayTemp.push(max);;
                        max=0;
                    }
                }
                this.durationMaxArray=this.durationMaxArrayTemp
                console.log('处理最大数max',this.durationMaxArray);
                this.timeArray=this.xDataTemp; 
                console.log('总数', this.numArrayTemp);
                console.log('时间轴', this.xDataTemp);
            }*/
           //==========================================================================================================================
            //console.log(this.timeArray,'x轴坐标');
            }).catch((error) => {
                console.log('查询表格数据异常', error);
                this.tabLoading = false;
            });


            
           
        },
        testData(){
            this.testModel=true;
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
        formatDateEndTime(datestamp){
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
            return d.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + '59';
        },
        initCreateDates(){
            
            let d = new Date();
            let startTime=this.formatDateToStr(d);
            //alert('获取默认处理前的时间:'+startTime);
            let month = (d.getMonth() + 1) + '';
            month = month.length === 1 ? '0' + month : month;
            let day = d.getDate() + '';
            day = day.length === 1 ? '0' + day : day;
            let hour = d.getHours() + '';
            hour = hour.length === 1 ? '0' + hour : hour;
            let minute = d.getMinutes() + '';
            minute = minute.length === 1 ? '0' + minute : minute;
            //用于暂时存放的时间
            startTime= d.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + '00';
            let endTime=this.formatDateEndTime(new Date(startTime).getTime()-1000*60);
            //开始时间推前一小时
            startTime=this.formatDateToStr(new Date(startTime).getTime()-1000*60*60);
            this.queryParam.startTime=startTime;
            this.queryParam.endTime=endTime;
            this.evaluateTimeRange[0]=startTime;
            this.evaluateTimeRange[1]=endTime;
            //alert(this.queryParam.startTime);
        },
        MinutString(datestamp){
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
            return d.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute ;
        },
        getMinut(startTime,endTime){
            //获取字符串的时间
            let start = new Date(startTime);
            let  end  = new Date(endTime);
            let  mid  = end-start;
            let count  =  parseInt(mid/(1000*60)+1);
            for(var i=0;i<count;i++){
                this.xData.push(this.MinutString(start.getTime()+i*1000*60));
            }
            console.log('打印x轴时间',this.xData);
        },
        //修改时间选项
        timeOptions(){
            let d = new Date();
            let startTime=this.formatDateToStr(d);
            //alert('获取默认处理前的时间:'+startTime);
            let month = (d.getMonth() + 1) + '';
            month = month.length === 1 ? '0' + month : month;
            let day = d.getDate() + '';
            day = day.length === 1 ? '0' + day : day;
            let hour = d.getHours() + '';
            hour = hour.length === 1 ? '0' + hour : hour;
            let minute = d.getMinutes() + '';
            minute = minute.length === 1 ? '0' + minute : minute;
            //用于暂时存放的时间
            startTime= d.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + '00';
            let endTime=this.formatDateEndTime(new Date(startTime).getTime()-1000*60);

            if(this.timeOption==='hour'){
                 //开始时间推前一小时
                startTime=this.formatDateToStr(new Date(startTime).getTime()-1000*60*60);
            }
            if(this.timeOption==='sixhour'){
                 //开始时间推前六小时
                startTime=this.formatDateToStr(new Date(startTime).getTime()-1000*60*60*6);
            }
            if(this.timeOption==='oneday'){
                 //开始时间推前一天
                startTime=this.formatDateToStr(new Date(startTime).getTime()-1000*60*60*24);
                this.myWidth= parseInt(window.innerWidth*0.7);

            }
            this.queryParam.startTime=startTime;
            this.queryParam.endTime=endTime;
            this.evaluateTimeRange[0]=startTime;
            this.evaluateTimeRange[1]=endTime;;        
            this.loadData();
            this.reload();
        },
       

    }
}
</script>

<style >
*{border: 0px solid red;}
.lineStyle{
            height: 500px; 
            width:100%;

         }
@media screen and (max-width:1242px ){
        .lineStyle{
            height: 500px; 
            width:70%;

         }
    }
</style>
