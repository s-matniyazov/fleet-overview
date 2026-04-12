import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useCompanyFileStore = defineStore("company-docs", () => {
    const files = ref([]);

    function reloadFiles(companyId) {
        axiosIns.get(`${URIS.COMPANY_FILES}?companyId=${companyId}`)
            .then(res => {
                files.value.splice(0, files.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init(companyId) {
        reloadFiles(companyId);
    }

    function clear() {
        files.value = []
    }

    function allDocuments() {
        return [...files.value]
    }

    return {files, init, clear, allDocuments};
});
