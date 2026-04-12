import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useDriverReferenceStore = defineStore("driver-reference", () => {
    const endorsements = ref([]);

    function reloadEndorsements() {
        axiosIns.get(URIS.DRIVER+"/endorsements")
            .then(res => {
                endorsements.value.splice(0, endorsements.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init() {
        reloadEndorsements();
    }

    function clear() {
        endorsements.value = []
    }

    return {
        endorsements,
        init, clear
    };
});
