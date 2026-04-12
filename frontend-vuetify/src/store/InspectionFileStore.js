import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useInspectionStore = defineStore("inspection-docs", () => {
    const files = ref([]);

    function reloadFiles(inspectionId) {
        axiosIns.get(`${URIS.INSPECTION_FILES}?inspectionId=${inspectionId}`)
            .then(res => {
                files.value.splice(0, files.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init(inspectionId) {
        reloadFiles(inspectionId);
    }

    function clear() {
        files.value = []
    }

    function allDocuments() {
        return [...files.value]
    }

    
    return {files, init, clear, allDocuments};
});
