<template>
    <div>
        <Card>
            <Row>
                <Col span="2">
                    <!-- <Button @click="addContent" type="primary" icon="plus">发布内容</Button> -->
                    <Button type="primary" @click="openMusicModel">添加榜单</Button>
                </Col>
                <Col span="22">
                    <Form :label-width="70" inline style="float:right;">
                        <FormItem label="音乐名称">
                            <Input v-model="queryParam.name" placeholder="请输入音乐名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <FormItem label="歌手名称">
                           <Input v-model="queryParam.albumName" placeholder="请输入歌手名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <FormItem label="专辑名称">
                           <Input v-model="queryParam.artistName" placeholder="请输入专辑名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <FormItem label="来源类型">
                           <Input v-model="queryParam.type" placeholder="请输入专辑名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <Button @click="searchPageList" type="primary" icon="search">搜索</Button>
                    </Form>
                </Col>
            </Row>
            <Row>
                <Table :columns="contentColumns" :data="contentData" :loading="tabLoading" border :stripe="true"></Table>
                <!-- <Page :total="totalPage" :page-size="queryParam.size" :current="contentCurrent" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page> -->
                <Page :total="totalElement" :page-size="queryParam.size" :current="currPage" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
            </Row>
        </Card>
        <Modal :title="musicTitle" v-model="showAddUpdModal"  @on-cancel="closeMusicModel" :mask-closable="false" :width="900" :loading="true">
            <Form  ref="addUpdParamForm" :model="addUpdParam" :rules="ruleAddUpdParam"  :label-width="140">
                <FormItem label="榜单名称" prop="name">
                    <Input v-model="addUpdParam.name" placeholder="请输入音乐名称" clearable style="width: 600px" :maxlength="190" />
                </FormItem>
                <FormItem label="榜单描述" prop="description">
                    <Input type="textarea" v-model="addUpdParam.description" placeholder="请输入内容描述" :autosize="{minRows: 3,maxRows: 3}" style="width: 600px" :maxlength="400" />
                </FormItem>
                <FormItem label="封面链接地址" prop="coverUrl">
                    <Input v-model="addUpdParam.coverUrl" placeholder="请输入链接地址" clearable style="width: 600px" :maxlength="1000" />
                </FormItem>
                <FormItem label="歌单播放量" prop="playNum">
                    <Input v-model="addUpdParam.playNum" placeholder="请设置播放量" clearable style="width: 200px" />
                </FormItem>
                <FormItem label="排序值" prop="orderValue">
                    <Input v-model="addUpdParam.orderValue" placeholder="请设置排序值" clearable style="width: 200px" />
                </FormItem>
                <FormItem label="语种">
                    <RadioGroup v-model="button1" type="button">
                        <Radio label="华语" border></Radio>
                        <Radio label="欧美" border></Radio>
                        <Radio label="日语" border></Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="风格">
                    <RadioGroup v-model="button2" type="button">
                        <Radio label="流行" border></Radio>
                        <Radio label="摇滚" border></Radio>
                        <Radio label="民谣" border></Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="场景">
                    <RadioGroup v-model="button3" type="button">
                        <Radio label="工作" border></Radio>
                        <Radio label="午休" border></Radio>
                        <Radio label="清晨" border></Radio>
                        <Radio label="夜晚" border></Radio>
                        <Radio label="学习" border></Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="情感">
                    <RadioGroup v-model="button4" type="button">
                        <Radio label="怀旧" border></Radio>
                        <Radio label="清新" border></Radio>
                        <Radio label="浪漫" border></Radio>
                    </RadioGroup>
                </FormItem>
            </Form>
            <!-- <MapInfo v-if="addUpdParam.typeCode == 80" ref="subAddMapInfo" :code="addUpdParam.code"></MapInfo> -->
            <div slot="footer">
                <Button @click="closeMusicModel">关闭</Button>
                <!-- <Button :loading="draftLoading" @click="commitDraftContent">存草稿</Button> -->
                <Button type="primary" :loading="addUpdModalLoading" @click="save">{{ isUpdate ? '更新' : '添加' }}</Button>
            </div>
        </Modal>
        <AddMusic ref="addMusicRef"></AddMusic>
        <QueryMusic ref="queryMusicRef"></QueryMusic>
    </div>
</template>
<script>
import AddMusic from './addRankMusic'
import QueryMusic from './queryRankMusic'
export default {
    name: 'RanklistManage',
    components: {
        AddMusic,
        QueryMusic
    },
    data () {
        return {
            totalElement: 0,
            list: [],
            musicTitle :'',
            showAddUpdModal: false,
            addUpdModalLoading: false,
            tabLoading: false,
            isUpdate: false,
            headers:{'Content-Type': 'multipart/form-data'},
            addUpdParam: {
                name: '',
                description: '',
                coverUrl: '',
                playNum: '',
                orderValue:''
            },
            queryParam:{
                name: '',
                description: '',
                coverUrl: '',
                playNum: '',
                page: 0,
                size: 10
            },
            ruleAddUpdParam: {
                name: [
                    {required: true, message: '请输入音乐名称', trigger: 'blur'}
                ],
                coverUrl: [
                    {required: true, message: '请输封面链接地址', trigger: 'blur'}
                ],
            },
            contentColumns: [
                {
                    type: 'expand',
                    width: 50,
                    render: (h, params) => {
                        return h(expandRow, {
                            props: {
                                row: params.row
                            }
                        })
                    }
                },
                {
                    key: 'name',
                    align: 'center',
                    width: 160,
                    title: '榜单名称',
                },
                {
                    key: 'description',
                    align: 'center',
                    width: 160,
                    title: '榜单描述',
                },
                {
                    key: 'tag',
                    align: 'center',
                    width: 200,
                    title: '榜单类别',
                },
                {
                    key: 'playNum',
                    align: 'center',
                    width: 80,
                    title: '播放量',
                },
                {
                    key: 'status',
                    align: 'center',
                    width: 100,
                    title: '是否上架',
                    render: (h, params) => {
                        let status = params.row.status === '101' ? '已上架' : '已下架';
                        return h('span', {}, status);
                    }
                },
                {
                    key: 'recommend',
                    align: 'center',
                    width: 100,
                    title: '是否推荐',
                    render: (h, params) => {
                        let recommend = params.row.recommend === 101 ? '已推荐' : '不推荐';
                        return h('span', {}, recommend);
                    }
                },
                {
                    key: 'orderValue',
                    align: 'center',
                    width: 80,
                    title: '排序值',
                },
                {
                    key: 'coverUrl',
                    align: 'center',
                    width: 200,
                    title: '榜单封面',
                    render: (h, params) => {
                        let coverUrl = params.row.coverUrl;
                        if (coverUrl && coverUrl.length > 50) {
                            coverUrl = coverUrl.substring(0, 50) + '...';
                        }
                        return h('span', {}, coverUrl);
                    }
                },
                {
                  title: '操作',
                  key: 'operation',
                  width: 400,
                  align: 'center',
                  fixed: 'right',
                  render: (h, params) => {
                    return h('div', [
                        h('Button', {
                            props: {
                              type: 'success',
                              size: 'small',
                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                console.log('params',params)
                                this.$refs.addMusicRef.initAddMusic(params)
                                this.$refs.addMusicRef.playListName = params.row.name
                               }
                            }
                        }, '添加歌曲'),
                        h('Button', {
                            props: {
                              type: 'info',
                              size: 'small',
                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                console.log('params',params)
                                this.$refs.queryMusicRef.searchPageList(params)
                                this.$refs.queryMusicRef.playListName = params.row.name
                               }
                            }
                        }, '查看歌曲'),
                        h('Button', {
                            props: {
                              type: 'primary',
                              size: 'small',
                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                console.log('params',params)
                                this.openEditMusicModel(params.row)
                               }
                            }
                        }, '编辑'),
                        h('Button', {
                        props: {
                          type: 'error',
                          size: 'small',
                        },
                        style: {
                              marginRight: '5px'
                        },
                        on: {
                          click: () => {
                            this.$Modal.confirm({
                                title: '提示',
                                content: `<p>确定要删除[${params.row.name}]吗？</p>`,
                                loading: true,
                                onOk: () => {
                                    this.delrankList(params.row)
                                }
                            });
                           }
                        }
                        }, '删除'),
                        h('Button', {
                            props: {
                              type: 'warning',
                              size: 'small',
                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                let msg = params.row.recommend === 101? '取消推荐' :'推荐'
                                this.$Modal.confirm({
                                    title: '提示',
                                    content: `<p>确定要` + msg +`[${params.row.name}]吗？</p>`,
                                    loading: true,
                                    onOk: () => {
                                        this.updateRecommend(params.row)
                                    }
                                });
                               }
                            }
                        }, params.row.recommend === 101? '置为不推荐' :'置为推荐'), 
                        h('Button', {
                            props: {
                              type: 'info',
                              size: 'small',
                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                let msg = params.row.status === '101'? '下架' :'上架'
                                this.$Modal.confirm({
                                    title: '提示',
                                    content: `<p>确定要` + msg +`[${params.row.name}]吗？</p>`,
                                    loading: true,
                                    onOk: () => {
                                        this.updateStatus(params.row)
                                    }
                                });
                               }
                            }
                        }, params.row.status === '101'? '下架' :'上架'),      
                  ]);
                  }
                }
            ],
            contentData: [
            {name:'aaa'}
            ],
            button1: '华语',
            button2:'流行',
            button3:'工作',
            button4: '怀旧'
        }
    },
    created() {
        this.searchPageList()
    },
    computed: {
      currPage () {
          return this.queryParam.page + 1;
      }
    },
    methods: {
        handleSuccess (res, file, filelist) {
            console.log('success', res);
            // console.log(file.name, file.url);
            this.addUpdParam.url = res.msg;
        },
        beforeUpload(file) {
          console.log('beforeUpload', file, file.name, file.url);
        },
        save() {
          this.$refs['addUpdParamForm'].validate(valid => {
              if (valid) {
                console.log('表单校验通过');
                this.addUpdModalLoading = true
                const url = this.isUpdate ? 'music/rank/updateRankList' : 'music/rank/addRankList'
                const msg = this.isUpdate ? '更新成功' : '添加成功'
                this.addUpdParam.tag = this.button1 + ' ' + this.button2 + ' ' + this.button3 + ' ' + this.button4
                this.button1 ='';
                this.button2 ='';
                this.button3 ='';
                this.button4  =''; 
                this.$http.post(url, this.addUpdParam).then(({ resultData }) => {
                  this.addUpdModalLoading = false
                  this.$Message.success(msg);
                  this.showAddUpdModal = false
                  this.$refs.addUpdParamForm.resetFields();
                  this.searchPageList()
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
        openMusicModel() {
          this.showAddUpdModal = true
          this.isUpdate = false
          this.musicTitle = '添加音乐'
        },
        openEditMusicModel(row) {
            this.isUpdate = true
            this.musicTitle = '编辑音乐'
            this.showAddUpdModal = true
            this.addUpdParam = {
                code: row.code,
                name: row.name,
                description: row.description,
                coverUrl:row.coverUrl,
                playNum: row.playNum,
                orderValue: row.orderValue
            }
        },
        closeMusicModel() {
            this.$refs.addUpdParamForm.resetFields();
            this.showAddUpdModal = false,
            this.list = []
        },
        searchPageList(){
            this.tabLoading  = true
           this.$http.post('music/rank/findRankPageList', this.queryParam).then(({ resultData }) => {
              this.tabLoading = false
             console.log('返回的结果', resultData);
             this.contentData = resultData.content
             this.totalElement = resultData.totalElements
             // this.contentData = resultData
           }).catch((error) => {
             console.log('查询日志异常', error);
           }); 
        },
        updateStatus(row) {
          if (row.status && row.status === '101'){
            this.$http.post('music/rank/statusOff/' + row.code).then(({ resultData }) => {
              this.$Modal.remove();
              this.searchPageList()
           }).catch((error) => {
             console.log('查询日志异常', error);
           }); 
         }else {
           this.$http.post('music/rank/statusOn/' + row.code).then(({ resultData }) => {
            this.$Modal.remove();
            this.searchPageList()
          }).catch((error) => {
            console.log('查询日志异常', error);
          }); 
         }
        },
        updateRecommend(row) {
          if (row.recommend && row.recommend === '101'){
            this.$http.post('music/rank/recommendOff/' + row.code).then(({ resultData }) => {
              this.$Modal.remove();
              this.searchPageList()
           }).catch((error) => {
             console.log('查询日志异常', error);
           }); 
         }else {
           this.$http.post('music/rank/recommendOn/' + row.code).then(({ resultData }) => {
            this.$Modal.remove();
            this.searchPageList()
          }).catch((error) => {
            console.log('查询日志异常', error);
          }); 
         }
        },
        delRankList(row) {
           this.$http.post('music/rank/delRankList/' + row.code).then(({ resultData }) => {
             this.$Modal.remove();
             this.searchPageList()
          }).catch((error) => {
            console.log('查询日志异常', error);
          }); 
        },
        pageChange (currPage) {
            this.queryParam.page = currPage - 1;
            this.searchPageList();
        },
        changeSize (pageSize) {
            this.queryParam.size = pageSize;
            this.searchPageList();
        }
    }
}; 
</script>

<style>

</style>