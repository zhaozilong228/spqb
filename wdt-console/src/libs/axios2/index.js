import axios from 'axios';
import PcLogin from '../pcLogin';
// import { AlertModule } from 'vux';
import Vue from 'vue';
import Util from '@/libs/util2';
// import Base64 from '../util/base64Util';

// require('es6-promise').polyfill();

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    let result = PcLogin.getFromLocal();
    if (result) {
    // result
        console.log('axios token', result.token);
        if (result.token) {
            config.headers.Authorization = result.token;
        }
    }
    // Do something before request is sent
    console.log('before request', config);
    return config;
}, function (error) {
    // Do something with request error
    console.log('before request error', error);
    return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
    // Do something with response data
    console.log('result', response);
    let data = response.data;
    if (data.resultCode !== '200') {
        Vue.$Message.error({
            content: '处理异常' + data.resultCode + ':' + data.message,
            onClose () {
                console.log('Module: I\'m hiding now', data.resultCode);
            },
            duration: 4
        });
        if (data.resultCode === '401') {
            // 清空本地配置，退出登录
            PcLogin.removeLocal();
        }
        return Promise.reject(new Error(data.message));
    }
    return data;
}, function (error) {
    // Do something with response error
    let errorData = error.response.data;
    console.log('errorLog:', errorData);
    Vue.$Message.error({
        content: '请求异常：' + errorData.status + ':' + errorData.message
    });
    return Promise.reject(error);
});

axios.defaults.baseURL = '/api/';

// json类型的参数请求进行base64加密，zhang zhichao,2018-03-12
axios.defaults.transformRequest = [function (data, headers) {
    // console.log('headers', headers);
    // if (process.env.ENCODE_FLAG) {
    //     return Base64.encode(JSON.stringify(data));
    // } else {
    //     return JSON.stringify(data);
    // }
    data = Util.trimAll(data)
    return JSON.stringify(data);
}];

axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';

export default {
    install (Vue) {
        Vue.prototype.$http = axios;
        Vue.http = axios;
    },
    $http: axios
};

export const $http = axios;
