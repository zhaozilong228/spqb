<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
</style>
<template>
    <div>
        <Row class="expand-row">
            <Col span="24">
                <span class="expand-key">音乐链接: </span>
                <span class="expand-value">{{ row.url }}</span>
            </Col>
        </Row>
        <Row class="expand-row">
            <Col span="24">
                <span class="expand-key">描述: </span>
                <span class="expand-value">{{ row.description }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <span class="expand-key">封面图链接: </span>
                <span class="expand-value">{{ row.picUrl }}</span>
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
        }
    };
</script>