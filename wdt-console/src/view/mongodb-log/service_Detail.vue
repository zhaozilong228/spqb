<template>
    <div>
        
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">日志编号：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.id"></span>
            </Col>
        </Row>
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">开始毫秒数：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.startMillisecond"></span>
            </Col>
        </Row>
      <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">结束毫秒数：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.endMillisecond"></span>
            </Col>
        </Row>
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">开始时间：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.startTime"></span>
            </Col>
        </Row>
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">结束时间：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.endTime"></span>
            </Col>
        </Row>
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">执行参数结果：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.excuteResult"></span>
            </Col>
        </Row>
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">执行参数格式：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.executeParamsJson"></span>
            </Col>
        </Row>
        
        <Row class="expand-row" style="margin-bottom:10px;">
            <Col span="2">异常名称：</Col>
            <Col span="9">
                <span class="detail-span" v-html="row.excuteException"></span>
            </Col>
        </Row>
      
      
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
        },
        data () {
            return {
                attachList: [],
                hotelFlag: false
            };
        },
        created () {
              this.attachList = [];
         
            if (this.row.id) {
               /* this.$http.post('console/log/findServicExceptionById/' + this.row.id, {})
                    .then((res) => {
                         
                            this.attachList = res.resultData;
                         
                    }).catch(error => {
                        console.log(error);
                    });*/
            }
        }
    };
</script>
<style lang="less" scoped>
    .ivu-row {
        padding-top: 8px;
        padding-bottom: 8px;
    }
    .detail-span {
        color: #999;
    }
</style>