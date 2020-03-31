import Vue from 'vue';

class PcLogin {
    // 构造
    constructor () {
        this.initParam();
    }

    static setLocal (resultData) {
        let localData = {
            resultData: resultData,
            time: new Date().getTime()
        };
        localStorage.setItem(PcLogin.tokenCode, JSON.stringify(localData));
        localStorage.setItem(PcLogin.appId, this.appId);
    }

    static getFromLocal () {
        let localStr = localStorage.getItem(PcLogin.tokenCode);
        if (!localStr) {
            return false;
        }
        try {
            let localData = JSON.parse(localStr);
            if (new Date().getTime() - localData.time > 2 * 60 * 60 * 1000) {
                localStorage.removeItem(PcLogin.tokenCode);
                return false;
            }
            localData.time = new Date().getTime();
            localStorage.setItem(PcLogin.tokenCode, JSON.stringify(localData));
            return localData.resultData;
        } catch (e) {
            return false;
        }
    }

    static removeLocal () {
        Vue.$store.commit('logout', this);
        Vue.$store.commit('clearOpenedSubmenu');
        Vue.$router.push({
            name: 'login'
        });
        localStorage.removeItem(PcLogin.tokenCode);
    }
}
PcLogin.tokenCode = 'pc-wdt-user';
export default PcLogin;
