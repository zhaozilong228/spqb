<template>
  <div>
      <Modal :title="playListName" v-model="showAddUpdModal"  @on-cancel="closeMusicModel" :mask-closable="false" :width="1200" :loading="true">
        <Card>
            <Row>
                <Col span="2">
                    <!-- <Button @click="addContent" type="primary" icon="plus">发布内容</Button> -->
                    <!-- <Button type="primary" @click="openMusicModel">添加音乐</Button> -->
                </Col>
                <Col span="22">
                    <Form :label-width="70" inline style="float:right;">
                        <FormItem label="音乐名称">
                            <Input v-model="queryParam.name" placeholder="请输入音乐名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <FormItem label="歌手名称">
                           <Input v-model="queryParam.artistName" placeholder="请输入歌手名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <FormItem label="专辑名称">
                           <Input v-model="queryParam.albumName" placeholder="请输入专辑名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <FormItem label="来源类型">
                           <Input v-model="queryParam.type" placeholder="请输入专辑名称，支持模糊查询" clearable style="width: 160px" />
                        </FormItem>
                        <Button @click="searchPageList" type="primary" icon="search">搜索</Button>
                    </Form>
                </Col>
            </Row>
            <Row>
                <Alert show-icon>点击复选框添加音乐，复选禁用时表示歌曲已存在当前歌单中，然后点击右下方添加</Alert>
                <Table :columns="contentColumns" :data="contentData" :loading="tabLoading" border :stripe="true" @on-selection-change="selectionChange"></Table>
                <Page :total="totalElement" :page-size="queryParam.size" :current="currPage" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page>
            </Row>
        </Card>
          <div slot="footer">
              <Button @click="closeMusicModel">取消</Button>
              <Button type="primary" :loading="addUpdModalLoading" :disabled="disabledSave" @click="save">添加</Button>
          </div>
      </Modal>
  </div>
</template>

<script>
import expandRow from './musicExpand'
export default {
    name: 'AddMusic',
    data () {
        return {
            totalElement: 0,
            isAddMap: {},
            disabledSave: true,
            list: [],
            musicList: [],
            selectionMusicList: [],
            playListName :'',
            addMusicParam: {},
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
                size:10,
                page:0
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
                // {
                //     type: 'expand',
                //     width: 50,
                //     render: (h, params) => {
                //         return h(expandRow, {
                //             props: {
                //                 row: params.row
                //             }
                //         })
                //     }
                // },
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    key: '_disabled',
                    align: 'center',
                    Width: 30,
                    title: '是否已关联到本歌单',
                    render: (h, params) => {
                        let _disabled = params.row._disabled;
                        let title =  _disabled ? '已关联' : '未关联'
                        return h('span', {}, title);
                    }
                },
                {
                    key: 'name',
                    align: 'center',
                    Width: 160,
                    title: '音乐名称',
                },
                {
                    key: 'artistName',
                    align: 'center',
                    Width: 60,
                    title: '歌手名称',
                },
                {
                    key: 'albumName',
                    align: 'center',
                    Width: 60,
                    title: '专辑名称',
                },
                {
                    key: 'type',
                    align: 'center',
                    Width: 60,
                    title: '来源类型',
                },
                {
                    key: 'playNum',
                    align: 'center',
                    Width: 30,
                    title: '播放量',
                },
            ],
            contentData: [
            {name:'aaa'}
            ],
        }
    },
    created() {
        this.searchPageList()
    },
    watch: {
        selectionMusicList() {
            if(this.selectionMusicList && this.selectionMusicList.length > 0) {
                this.disabledSave = false;
            } else {
                this.disabledSave = true;
            }
        }
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
            const musicList = []
            this.addUpdModalLoading = true
            if (this.selectionMusicList && this.selectionMusicList.length >0) {
                this.selectionMusicList.forEach( (item) =>{
                    let isExist = this.musicList.some((it)=>{
                        return it.musicCode === item.code
                    });
                    if (!isExist) {
                        this.musicList.push({
                            playlistName: this.addMusicParam.name,
                            playlistCode : this.addMusicParam.code,
                            musicName: item.name,
                            musicCode: item.code,
                            albumName: item.albumName,
                            artistName: item.artistName
                        });
                    }
                })
            }
            this.$http.post('music/play/addPlayListAttach', this.musicList).then(({ resultData }) => {
                this.addUpdModalLoading = false;
                this.showAddUpdModal = false;
                this.$Message.success('添加成功');
            }).catch((error) => {
                console.log('查询日志异常', error);
                this.addUpdModalLoading = false
            }); 

        },
        openMusicModel() {
          this.showAddUpdModal = true
          this.isUpdate = false
          this.playListName = '添加音乐'
        },
        openEditMusicModel(row) {
            this.isUpdate = true
            this.playListName = '编辑音乐'
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
            this.showAddUpdModal = false
            this.queryParam.size = 10;
            this.queryParam.page = 0;
        },
        searchPageList(){
            this.tabLoading  = true
           this.$http.post('music/music/findPageList', this.queryParam).then(({ resultData }) => {
              this.tabLoading = false
             console.log('返回的结果', resultData);
             resultData.content.forEach( (item) =>{
                item._disabled = this.isAddMap[item.code] ? true : false
             })
             this.contentData = resultData.content
             this.totalElement = resultData.totalElements
           }).catch((error) => {
             console.log('查询日志异常', error);
           }); 
        },
        initAddMusic(param) {
            this.disabledSave = true;
            this.addMusicParam = param.row;
            this.showAddUpdModal = true;
            this.$http.post('music/play/findMusicByCode/' + param.row.code).then(({ resultData }) => {
               this.tabLoading = false
              console.log('返回的结果', resultData);
              this.isAddMap = {};
              resultData.forEach( (item) =>{
                 this.isAddMap[item.musicCode] = item
              })
              this.musicList = resultData
              this.searchPageList()
            }).catch((error) => {
              console.log('查询日志异常', error);
            }); 
        },
        selectionChange(selection) {
            console.log(selection)
            this.selectionMusicList = selection
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
