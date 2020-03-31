<template>
    <div>
        <Row class="expand-row">
            <Col span="12">
                <span class="expand-key">类名: </span>
                <span class="expand-value">{{ row._class }}</span>
            </Col>
            <Col span="12">
                <span class="expand-key">IP: </span>
                <span class="expand-value">{{ row.ip }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="12">
                <span class="expand-key">请求方法: </span>
                <span class="expand-value">{{ row.httpMethod }}</span>
            </Col>
            <Col span="12">
                <span class="expand-key">执行类: </span>
                <span class="expand-value">{{ row.executeClass }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="12">
                <span class="expand-key">当前登陆用户: </span>
                <span class="expand-value">{{ row.currentUser }}</span>
            </Col>
            <Col span="12">
                <span class="expand-key">参数: </span>
                <span class="expand-value">{{ row.parameter }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="12">
                <span class="expand-key">执行参数Json: </span>
                <span class="expand-value">{{ row.executeParamsJson }}</span>
            </Col>
            <Col span="12">
                <span class="expand-key">异常信息Exception: </span>
                <span class="expand-value">{{ row.exception }}</span>
            </Col>
            
        </Row>
        <Row>
            <Col span="24">
                <span class="expand-key">客户端: </span>
                <span class="expand-value">{{ row.userAgent }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <span class="expand-key">执行结果: </span>
                <span class="expand-value">{{ row.excuteResult }}</span>
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
        return {};
}
};
</script>
<style scoped>
    .ivu-row {
        padding-top: 8px;
        padding-bottom: 8px;
    }
    .detail-span {
        color: #999;
    }
</style>
