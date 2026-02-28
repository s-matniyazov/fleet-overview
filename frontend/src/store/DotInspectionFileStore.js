import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useDotInspectionStore = defineStore("dot-inspection-docs", () => {
    const files = ref([]);

    function reloadFiles(driverId) {
        
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
