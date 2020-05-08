<template>
  <div>
    <Row>
        <Col span="11">
            <Card>
                <p slot="title">每日推荐</p>
                <Table :columns="contentColumns" :data="contentData" :loading="tabLoading" border :stripe="true" @on-row-click="findRecommendByDate"></Table>
            </Card>
            <Card>
                <p slot="title">查看推荐历史</p>
                <Row>
                    <Col span="12">
                        <Form :label-width="70" inline style="float:right;">
                            <FormItem label="查询日期">
                                <DatePicker type="daterange" placeholder="选择日期" style="width: 200px; margin-right: -200px;" clearable @on-change="cahngeDate"></DatePicker>
                            </FormItem>
                        </Form>
                    </Col>
                </Row>
                <Table :columns="historyColumns" :data="historyData" :loading="historLoading" border :stripe="true" @on-row-click="findRecommendByDate"></Table>
            </Card>
        </Col>
        <Col span="12" offset="1">
            <Card dis-hover v-if="first"> 
                <p slot="title">{{ corrent }} 推荐歌曲列表, 当前歌曲数为：{{ }}</p>
                <Button type="primary" @click="addRecommendModel" style="margin-bottom: 20px;" :disabled="historyFlag">选择音乐</Button>
                <Table :columns="musicColumns" :data="musicList" :loading="recommendLoading" border :stripe="true"></Table>
            </Card>
        </Col>
        </Row>
        <AddRecommendMusic ref="addRecommendMusicRef" @closeModel="closeAddRecommendMusic"></AddRecommendMusic>
        <Modal :title="recommendTitle" v-model="showAddUpdModal"  @on-cancel="closeEditRecommenModel" :mask-closable="false" :width="900" :loading="true">
            <Form  ref="addUpdParamForm" :model="addUpdParam" :label-width="140">
                <FormItem label="主题" prop="theme">
                    <Input v-model="addUpdParam.theme" placeholder="请输入主题" clearable style="width: 600px" :maxlength="190" />
                </FormItem>
                <FormItem label="封面链接地址" prop="coverUrl">
                    <Input v-model="addUpdParam.coverUrl" placeholder="请输入封面地址" clearable style="width: 600px" :maxlength="1000" />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button @click="closeMusicModel">关闭</Button>
                <Button type="primary" :loading="addUpdModalLoading" @click="save">{{ '更新' }}</Button>
            </div>
        </Modal>
  </div>
</template>

<script>
import AddRecommendMusic from './addRecommendMusic'
export default {
    name: 'Recommend',
    components: {
        AddRecommendMusic
    },
    data () {
        return {
            historyFlag: false,
            recommendTitle: '',
            showAddUpdModal: false,
            recommendLoading: false,
            historLoading: false,
            addUpdModalLoading:  false,
            tabLoading: false,
            addUpdParam: {
                date:'',
                id:'',
                theme:'',
                coverUrl:''
            },
            queryParam: {
                date:'',
                id:'',
                theme:'',
                coverUrl:'',
                size: 10,
                page:0 
            },
            correntInfo: {},
            first: false,
            corrent: '',
            contentData:[{
                remark:'今天',
                date:'2020-04-05'
            },{
                remark:'明天',
                date:'2020-04-06'
            },{
                remark:'后天',
                date:'2020-04-07'
            }],
            musicList: [],
            musicColumns: [{
                key: 'musicName',
                align: 'center',
                width: 160,
                title: '歌曲名',
            },
            {
                key: 'albumName',
                align: 'center',
                width: 160,
                title: '专辑'
            },
            {
                key: 'artistName',
                align: 'center',
                width: 160,
                title: '歌手'
            },
            {
              title: '操作',
              key: 'operation',
              width: 150,
              align: 'center',
              fixed: 'right',
              render: (h, params) => {
                return h('div', [
                    h('Button', {
                        props: {
                          type: 'primary',
                          size: 'small',
                         disabled: this.historyFlag
                        },
                        style: {
                          marginRight: '5px'
                        },
                        on: {
                          click: () => {
                            console.log('params',params)
                            this.delRecommendById(params)
                           }
                        }
                    }, '取消推荐')     
              ]);
              }
            }
            ],
            historyData: [],
            historyColumns: [
                {
                    key: 'date',
                    align: 'center',
                    width: 150,
                    title: '维护日期',
                },
                {
                    key: 'theme',
                    align: 'center',
                    title: '主题',
                }
            ],
            contentColumns: [
                {
                    key: 'remark',
                    align: 'center',
                    width: 160,
                    title: '说明',
                },
                {
                    key: 'date',
                    align: 'center',
                    width: 150,
                    title: '维护日期',
                },
                // {
                //     key: 'count',
                //     align: 'center',
                //     title: '歌曲数量',
                // },
                {
                    key: 'theme',
                    align: 'center',
                    title: '主题',
                },
                {
                  title: '操作',
                  key: 'operation',
                  width: 80,
                  align: 'center',
                  fixed: 'right',
                  render: (h, params) => {
                    return h('div', [
                        h('Button', {
                            props: {
                              type: 'primary',
                              size: 'small'

                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                console.log('params',params)
                                this.opneEditRecommenModel(params.row);
                               }
                            }
                        }, '编辑')     
                  ]);
                  }
                }
            ],
        }
    },
    created() {
        this.first = false
        this.initDate()
        this.loadHistoryData()
    },
    watch: {
    },
    methods: {
        findRecommendByDate(row, index) {
            this.first = true
            this.historyFlag = row.historyFlag
            console.log('findRecommendByDate', row, index);
            this.correntInfo = row
            this.corrent = row.date
            this.loadDate()
        },
        loadDate() {
            this.$http.post('music/recommend/findRecommendAttachList', {
                recommendDate: this.corrent
            } ).then(({ resultData }) => {
               this.tabLoading = false
              console.log('返回的结果', resultData);
              resultData.forEach( (item) =>{
                 item._disabled = true
              })
              this.musicList = resultData
            }).catch((error) => {
              console.log('查询日志异常', error);
            });
        },
        addRecommendModel() {
            this.$refs.addRecommendMusicRef.initAddMusic(this.correntInfo)
            this.$refs.addRecommendMusicRef.recommendDate = this.corrent
        },
        closeAddRecommendMusic () {
            this.loadDate()
        },
        delRecommendById(params){
            this.recommendLoading = true
            this.$http.post('music/recommend/delRecommendById', {
                id: params.row.id
            } ).then(({ resultData }) => {
               this.recommendLoading = false
              console.log('返回的结果', resultData);
              this.loadDate()
            }).catch((error) => {
              console.log('查询日志异常', error);
              this.recommendLoading = false
            });  
        },
        initDate() {
            // findRecommendByDate
            let dates = [];
            let date = new  Date()
            for (let i = 0; i<3; i++){
                 let nowDate = this.formatDateToStr(date.getTime() + 1000 * 3600 * 24 * i );
                 console.log(nowDate)
                 dates.push(nowDate)
            }
            
            this.$http.post('music/recommend/findRecommendByDate', dates).then(({ resultData }) => {
               this.tabLoading = false
              console.log('返回的结果', resultData);
              resultData[0].remark = '今天'
              resultData[1].remark = '明天'
              resultData[2].remark = '后天'
              this.contentData = resultData
            }).catch((error) => {
              console.log('查询日志异常', error);
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
            return d.getFullYear() + '-' + month + '-' + day ;
        },
        closeEditRecommenModel() {
            this.showAddUpdModal = false
            this.$refs.addUpdParamForm.resetFields();
        },
        opneEditRecommenModel(row) {
            this.recommendTitle = row.date + '的单日推荐'
            this.showAddUpdModal = true
            this.addUpdParam = {
                date: row.date,
                id:row.id,
                theme:row.theme,
                coverUrl: row.coverUrl
            }
        },
        save() {
          this.$refs['addUpdParamForm'].validate(valid => {
              if (valid) {
                console.log('表单校验通过');
                this.addUpdModalLoading = true
                this.$http.post('music/recommend/updateRecommend', this.addUpdParam).then(({ resultData }) => {
                  this.addUpdModalLoading = false
                  this.$Message.success('更新成功');
                  this.showAddUpdModal = false
                  this.$refs.addUpdParamForm.resetFields();
                  this.initDate()
                  console.log('返回的结果', resultData);
                }).catch((error) => {
                  console.log('查询日志异常', error);
                  this.addUpdModalLoading = false
                });
              } else {
                  console.log('表单校验不通过');
                  this.$Message.error('表单填写不完整，请确认');
              }
          });
        },
        loadHistoryData() {
            this.$http.post('music/recommend/findRecommendList', this.queryParam).then(({ resultData }) => {
                this.historyData = resultData.content
                if(resultData.content && resultData.content.length > 0) {
                    console.log('dddd')
                    resultData.content.forEach((item)=>{
                        console.log('dd',item)
                        item.historyFlag = true
                    })
                    this.historyData = resultData.content
                }
            }).catch((error) => {
              console.log('查询日志异常', error);
              this.addUpdModalLoading = false
            });
        },
        closeMusicModel(){},
        cahngeDate(date){
            console.log(date)
            this.queryParam.startTime = date[0]
            this.queryParam.endTime = date[1]
            this.$http.post('music/recommend/findRecommendList', this.queryParam).then(({ resultData }) => {
                this.historyData = resultData.content
                if(resultData.content && resultData.content.length > 0) {
                    console.log('dddd')
                    resultData.content.forEach((item)=>{
                        console.log('dd',item)
                        item.historyFlag = true
                    })
                    this.historyData = resultData.content
                }
              console.log('返回的结果', resultData);
            }).catch((error) => {
              console.log('查询日志异常', error);
              this.addUpdModalLoading = false
            });
        }
    }
}; 
</script>

<style>

</style>
