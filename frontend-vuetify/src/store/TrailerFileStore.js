import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useTrailerFileStore = defineStore("trailer-docs", () => {
    const files = ref([]);

    function reloadFiles(trailerId) {
        axiosIns.get(`${URIS.TRAILER_FILES}?trailerId=${trailerId}`)
            .then(res => {
                files.value.splice(0, files.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init(trailerId) {
        reloadFiles(trailerId);
    }

    function clear() {
        files.value = []
    }

    return {files, init, clear};
});
