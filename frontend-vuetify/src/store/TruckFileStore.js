import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useTruckFileStore = defineStore("truck-docs", () => {
    const files = ref([]);
    const permits = ref([])

    function reloadFiles(truckId) {
        axiosIns.get(`${URIS.TRUCK_FILES}?truckId=${truckId}`)
            .then(res => {
                files.value.splice(0, files.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function reloadPermits(truckId) {
        axiosIns.get(`${URIS.TRUCK_PERMITS}?truckId=${truckId}`)
            .then(res => {
                permits.value.splice(0, permits.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init(truckId) {
        reloadFiles(truckId);
        reloadPermits(truckId);
    }

    function clear() {
        files.value = []
        permits.value = []
    }

    function allDocuments() {
        return [...files.value, ...permits.value]
    }


    return {files, permits, init, clear, allDocuments};
});
