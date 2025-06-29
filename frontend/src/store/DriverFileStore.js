import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useDriverFileStore = defineStore("driver-docs", () => {
    const files = ref([]);

    function reloadFiles(driverId) {
        axiosIns.get(`${URIS.DRIVER_FILES}?driverId=${driverId}`)
            .then(res => {
                files.value.splice(0, files.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init(driverId) {
        reloadFiles(driverId);
    }

    function clear() {
        files.value = []
    }

    function allDocuments() {
        return [...files.value]
    }

    return {files, init, clear, allDocuments};
});
