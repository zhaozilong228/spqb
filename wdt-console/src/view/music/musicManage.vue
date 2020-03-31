<template>
  <div>
  <!--     <Upload action="api/pms/image" :on-success="handleSuccess" :before-upload="beforeUpload">
              <i-button type="ghost" icon="ios-cloud-upload-outline">上传文件</i-button>
      </Upload> -->
      <Card>
          <Row>
              <Col span="2">
                  <!-- <Button @click="addContent" type="primary" icon="plus">发布内容</Button> -->
                  <Button type="primary" @click="openMusicModel">添加音乐</Button>
              </Col>
              <Col span="22">
                  <Form :label-width="70" inline style="float:right;">
                      <FormItem label="音乐名称">
                          <Input v-model="queryParam.name" placeholder="请输入音乐名称，支持模糊查询" clearable style="width: 160px" />
                      </FormItem>
                      <FormItem label="专辑名称">
                         <Input v-model="queryParam.albumName" placeholder="请输入歌手名称，支持模糊查询" clearable style="width: 160px" />
                      </FormItem>
                      <FormItem label="歌手名称">
                         <Input v-model="queryParam.artistName" placeholder="请输入专辑名称，支持模糊查询" clearable style="width: 160px" />
                      </FormItem>
                      <FormItem label="来源类型">
                         <Input v-model="queryParam.type" placeholder="请输入专辑名称，支持模糊查询" clearable style="width: 160px" />
                      </FormItem>
                      <Button @click="searchPageList" type="primary" icon="search">搜索</Button>
                  </Form>
              </Col>
              <Col span="24">
                <Form :label-width="70" inline style="float:right;">
                    <FormItem label="排序方式">
                        <Select v-model="queryParam.sortWay" style="width:200px" @on-change="changeSort" >
                          <Option v-for="item in sortList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                         </Select>
                    </FormItem>
                </Form>
              </Col>
          </Row>
          <Row>
              <Table :columns="contentColumns" :data="contentData" :loading="tabLoading" border :stripe="true"></Table>
              <!-- <Page :total="totalElement" :page-size="queryParam.size" :current="currPage" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page> -->
              <Page :total="totalElement" :page-size="queryParam.size" :current="currPage" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
          </Row>
      </Card>
      <Modal :title="musicTitle" v-model="showAddUpdModal"  @on-cancel="closeMusicModel" :mask-closable="false" :width="900" :loading="true">
          <Form  ref="addUpdParamForm" :model="addUpdParam" :rules="ruleAddUpdParam"  :label-width="140">
              <FormItem label="音乐名称" prop="name">
                  <Input v-model="addUpdParam.name" placeholder="请输入音乐名称" clearable style="width: 600px" :maxlength="190" />
              </FormItem>
              <FormItem label="内容描述" prop="description">
                  <Input type="textarea" v-model="addUpdParam.description" placeholder="请输入内容描述" :autosize="{minRows: 3,maxRows: 3}" style="width: 600px" :maxlength="400" />
              </FormItem>
              <FormItem label="封面链接地址" prop="picUrl">
                  <Input v-model="addUpdParam.picUrl" placeholder="请输入链接地址" clearable style="width: 600px" :maxlength="1000" />
              </FormItem>
              <FormItem label="播放量" prop="playNum">
                  <Input v-model="addUpdParam.playNum" placeholder="请设置播放量" clearable style="width: 200px" />
              </FormItem>
              <FormItem label="添加方式">
                <RadioGroup v-model="addWay" >
                  <Radio label="本地上传" :disabled ="isUpdate"></Radio>
                  <Radio label="链接地址" :disabled ="isUpdate"></Radio>
                </RadioGroup>
              </FormItem>
              <FormItem v-if="addWay==='本地上传'" label="音乐文件" prop="url">
                <Upload action="api/pms/image" :on-success="handleSuccess" :before-upload="beforeUpload" :default-file-list="list" >
                  <i-button type="dashed" icon="ios-cloud-upload-outline" :disabled ="isUpdate">上传文件</i-button>
                </Upload>
              </FormItem>
              <FormItem v-else label="链接地址" prop="url">
                 <Input v-model="addUpdParam.url" placeholder="请输入歌手名称" clearable style="width: 600px" :maxlength="190" />
              </FormItem>
              <FormItem label="歌手名称" prop="artistName">
                  <Input v-model="addUpdParam.artistName" placeholder="请输入歌手名称" clearable style="width: 600px" :maxlength="190" />
              </FormItem>
              <FormItem label="专辑名称" prop="albumName">
                  <Input v-model="addUpdParam.albumName" placeholder="请输入专辑名称" clearable style="width: 600px" :maxlength="190" />
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
          </Form>
          <!-- <MapInfo v-if="addUpdParam.typeCode == 80" ref="subAddMapInfo" :code="addUpdParam.code"></MapInfo> -->
          <div slot="footer">
              <Button @click="closeMusicModel">关闭</Button>
              <!-- <Button :loading="draftLoading" @click="commitDraftContent">存草稿</Button> -->
              <Button type="primary" :loading="addUpdModalLoading" @click="save">{{ isUpdate ? '更新' : '添加' }}</Button>
          </div>
      </Modal>
  </div>
</template>

<script>
import expandRow from './musicExpand'
export default {
    name: 'MusicManage',
    data () {
        return {
            sortList:[{
              value: 'time',
              label: '添加时间'
            },
            {
              value: 'playNum',
              label: '播放量'
            }],
            totalElement: 0,
            addWay: '本地上传',
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
                picUrl:'',
                playNum: '',
                url: '',
                albumName: '',
                artistName: ''
            },
            queryParam:{
                name: '',
                description: '',
                coverUrl: '',
                picUrl:'',
                playNum: '',
                url: '',
                albumName: '',
                artistName: '',
                sortWay:'',
                size: 10,
                page: 0
            },
            ruleAddUpdParam: {
                name: [
                    {required: true, message: '请输入音乐名称', trigger: 'blur'}
                ],
                albumName: [
                    {required: true, message: '请输入专辑名称', trigger: 'blur'}
                ],
                artistName: [
                    {required: true, message: '请输入歌手名称', trigger: 'blur'}
                ],
                url: [
                    {required: true, message: '请选择要上传的文件', trigger: 'blur'}
                ],
                picUrl: [
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
                    title: '音乐名称',
                },
                {
                    key: 'artistName',
                    align: 'center',
                    width: 150,
                    title: '歌手名称',
                },
                {
                    key: 'albumName',
                    align: 'center',
                    width: 150,
                    title: '所属专辑',
                },
                // {
                //     key: 'type',
                //     align: 'center',
                //     width: 100,
                //     title: '来源类型',
                // },
                {
                    key: 'tag',
                    align: 'center',
                    width: 120,
                    title: '歌曲类别',
                },
                {
                    key: 'playNum',
                    align: 'center',
                    width: 120,
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
                    key: 'url',
                    align: 'center',
                    width: 200,
                    title: '来源地址',
                    render: (h, params) => {
                        let url = params.row.url.length > 30 ? params.row.url.substring(0,30) : params.row.url
                        return h('span', {}, url);
                    }
                },
                {
                  title: '操作',
                  key: 'operation',
                  width: 350,
                  align: 'center',
                  fixed: 'right',
                  render: (h, params) => {
                    return h('div', [
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
                              type: 'success',
                              size: 'small',
                            },
                            style: {
                              marginRight: '5px'
                            },
                            on: {
                              click: () => {
                                console.log('params',params)
                               }
                            }
                        }, '添加到歌单'),
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
                                    this.delMusic(params.row)
                                }
                            });

                           }
                        }
                        }, '删除'),
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
            contentData: [],
            button1: '华语',
            button2:'流行',
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
                const url = this.isUpdate ? 'music/music/update' : 'music/music/add'
                const msg = this.isUpdate ? '更新成功' : '添加成功'
                this.addUpdParam.type = this.addWay;
                this.addUpdParam.tag = this.button1 + ' ' + this.button2 
                this.button1 ='';
                this.button2 ='';
                this.$http.post(url, this.addUpdParam).then(({ resultData }) => {
                  this.addUpdModalLoading = false
                  this.$Message.success(msg);
                  this.showAddUpdModal = false
                  this.$refs.addUpdParamForm.resetFields();
                  this.searchPageList()
                  console.log('返回的结果', resultData);
                }).catch((error) => {
                  console.log('查询日志异常', error);
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
                picUrl:row.picUrl,
                playNum: row.playNum,
                url: row.url,
                albumName: row.albumName,
                artistName: row.artistName
            }
        },
        closeMusicModel() {
            this.$refs.addUpdParamForm.resetFields();
            this.showAddUpdModal = false,
            this.list = []
        },
        pageChange (currPage) {
            this.queryParam.page = currPage - 1;
            this.searchPageList();
        },
        changeSize (pageSize) {
            this.queryParam.size = pageSize;
            this.searchPageList();
        },
        searchPageList(){
            this.tabLoading  = true
           this.$http.post('music/music/findPageList', this.queryParam).then(({ resultData }) => {
              this.tabLoading = false
             console.log('返回的结果', resultData);
             this.contentData = resultData.content;
             this.totalElement = resultData.totalElements
           }).catch((error) => {
             console.log('查询日志异常', error);
           }); 
        },
        delMusic(row) {
           this.$http.post('music/music/del/' + row.code).then(({ resultData }) => {
             this.$Modal.remove();
             this.searchPageList()
          }).catch((error) => {
            console.log('查询日志异常', error);
          }); 
        },
        updateStatus(row) {
          if (row.status && row.status === '101'){
            this.$http.post('music/music/statusOff/' + row.code).then(({ resultData }) => {
              this.$Modal.remove();
              this.searchPageList()
           }).catch((error) => {
             console.log('查询日志异常', error);
           }); 
         }else {
           this.$http.post('music/music/statusOn/' + row.code).then(({ resultData }) => {
            this.$Modal.remove();
            this.searchPageList()
          }).catch((error) => {
            console.log('查询日志异常', error);
          }); 
         }
        },
        changeSort() {
          this.queryParam.size = 10;
          this.queryParam.page = 0;
          this.searchPageList()
        }
    }
}; 
</script>

<style>

</style>
