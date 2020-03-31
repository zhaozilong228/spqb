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
                <Table :columns="contentColumns" :data="musicList" :loading="tabLoading" border :stripe="true"></Table>
                <!-- <Page :total="totalPage" :page-size="queryParam.size" :current="contentCurrent" @on-change="pageChange" @on-page-size-change="changeSize" size="small" show-total show-sizer></Page> -->
            </Row>
        </Card>
          <div slot="footer">
              <Button @click="closeMusicModel">关闭</Button>
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
            list: [],
            musicList: [],
            playListName :'',
            addMusicParam: {},
            showAddUpdModal: false,
            addUpdModalLoading: false,
            tabLoading: false,
            isUpdate: false,
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
                artistName: ''
            },
            contentColumns: [
                {
                    key: 'playlistName',
                    align: 'center',
                    Width: 160,
                    title: '歌单名称',
                },
                {
                    key: 'musicName',
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
                  title: '操作',
                  key: 'operation',
                  width: 350,
                  align: 'center',
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
                               }
                            }
                        }, '取消关联')     
                  ]);
                  }
                }
            ],
            contentData: [
            {name:'aaa'}
            ],
        }
    },
    created() {
    },
    methods: {
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
        },
        searchPageList(param){
            this.tabLoading  = true
            this.showAddUpdModal = true;
            this.$http.post('music/play/findMusicByCode/' + param.row.code).then(({ resultData }) => {
               this.tabLoading = false
              console.log('返回的结果', resultData);
              resultData.forEach( (item) =>{
                 item._disabled = true
              })
              this.musicList = resultData
            }).catch((error) => {
              console.log('查询日志异常', error);
            }); 
        }
    }
}; 
</script>

<style>

</style>
