import useToasterStore from "@/store/ToastStore";
import {useLoaderStore} from "@/store/LoaderStore.js";
import useUserStore from "@/store/UserStore.js";

// toast store access
const toasterStore = useToasterStore();
export function showMessage(message) {
    if (message?.actionCode > 0 || !message?.actionCode) {
        toasterStore.error({ text: message?.actionMessage ? message.actionMessage : message })
    } else if (message?.actionCode >= 0) {
        toasterStore.warning({ text: message?.actionMessage ? message.actionMessage : message })
    } else {
        toasterStore.success({ text: message?.actionMessage ? message.actionMessage : message })
    }
}
// loader store access
const loader = useLoaderStore();
export function showLoader() {
    loader.show();
}
export function hideLoader() {
    loader.hide();
}

// date export functions
export function longToDate(date) {
    if (!date) return '';
    let a = new Date(date)
    let res = '';
    res += a.getFullYear() + '-';
    res += toClockNumber(a.getMonth() + 1) + '-';
    res += toClockNumber(a.getDate());

    return res;
}

export function longToDateTime(date) {
    if (!date) return '';
    let a = new Date(date)
    let res = '';
    res += a.getFullYear() + '-';
    res += toClockNumber(a.getMonth() + 1) + '-';
    res += toClockNumber(a.getDate()) + ' ';
    res += toClockNumber(a.getHours()) + ':'
    res += toClockNumber(a.getMinutes())

    return res;
}

export function dateToLong(stringDate) {
    const split = stringDate.split('-');

    return new Date(split[0], split[1]-1, split[2]);
}

export function toClockNumber(a) {
    return a < 10 ? `0${a}` : a;
}

// user

const userStore = useUserStore();
export function checkUser() {
    return userStore.checkToken();
}
