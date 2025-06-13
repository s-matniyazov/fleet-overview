import useToasterStore from "@/store/ToastStore";
import {useLoaderStore} from "@/store/LoaderStore.js";
import useUserStore from "@/store/UserStore.js";

// toast store access
const toasterStore = useToasterStore();

export function showMessage(error) {
    console.log(error)
    const msg = error?.response?.data?.message, status = error?.response?.data?.status;
    if (status === 500) {
        toasterStore.error({text: msg ? msg : error})
    } else if (status === 400) {
        toasterStore.warning({text: msg ? msg : error})
    } else {
        toasterStore.success({text: msg ? msg : error})
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
// yyyy-mm-dd  -- 1
// dd.mm.yyyy  -- 2

// date export functions
// dd.mm.yyyy
// dd.mm.yyyy
export function longToDate(date, format) {
    if (!date) return '';
    let a = new Date(date)
    let res = '';

    if (format === 1) {
        res += a.getFullYear() + '-';
        res += toClockNumber(a.getMonth() + 1) + '-';
        res += toClockNumber(a.getDate());
    } else {
        res += toClockNumber(a.getDate()) + ".";
        res += toClockNumber(a.getMonth() + 1) + '.';
        res += a.getFullYear();
    }

    return res;
}

// dd.mm.yyyy
export function longToDateTime(date) {
    if (!date) return '';
    let a = new Date(date)
    let res = '';
    res += toClockNumber(a.getDate()) + '.';
    res += toClockNumber(a.getMonth() + 1) + '.';
    res += a.getFullYear() + ' ';
    res += toClockNumber(a.getHours()) + ':'
    res += toClockNumber(a.getMinutes()) + ':'
    res += toClockNumber(a.getSeconds())

    return res;
}

export function dateToLong(stringDate) {
    const split = stringDate.split('-');

    return new Date(split[0], split[1] - 1, split[2]);
}

export function toClockNumber(a) {
    return a < 10 ? `0${a}` : a;
}

// user

const userStore = useUserStore();

export function checkUser() {
    return userStore.checkToken();
}

export const DOCUMENT_TYPES = {
    "REG_CAB_CARD": "Registration (Cab Card)",
    "ANN_INS": "Annual Inspection",
    "PHYS_DAMAGE": "Physical Damage Insurance",
    "LEASE_AGR": "Lease Agreement",
    "OR": "Oregon",
    "NM": "New Mexico",
    "KY": "Kentucky",
    "NY": "New York",
    "CN": "Connecticut"
}

export const FILE_TYPE_NAMES = [
    {
        key: "REG_CAB_CARD",
        value: "Registration (Cab Card)"
    },
    {
        key: "ANN_INS",
        value: "Annual Inspection"
    },
    {
        key: "PHYS_DAMAGE",
        value: "Physical Damage Insurance"
    },
    {
        key: "LEASE_AGR",
        value: "Lease Agreement"
    },
];

export
const PERMIT_NAMES = [
    {
        key: "OR",
        value: "Oregon"
    },
    {
        key: "NM",
        value: "New Mexico"
    },
    {
        key: "KY",
        value: "Kentucky"
    },
    {
        key: "NY",
        value: "New York"
    },
    {
        key: "CN",
        value: "Connecticut"
    },
]
