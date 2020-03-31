import PcLogin from '../pcLogin';
import Cookies from 'js-cookie';

class UserContext {
    // 构造
    constructor () {
        this.token = PcLogin.getFromLocal().token;
    }

    // 根据token的角色获取用户的access级别
    static getAccess () {
        return Cookies.get('access');
    }

    static setAccess (access) {
        return Cookies.set('access', access);
    }
}

export default UserContext;
