import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useStateStore = defineStore("state", () => {
    const countries = ref([]);
    const states = ref([])

    function reloadCountries() {
        axiosIns.get(URIS.COUNTRY)
            .then(res => {
                countries.value.splice(0, countries.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function reloadStates() {
        axiosIns.get(URIS.STATE)
            .then(res => {
                states.value.splice(0, states.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init() {
        reloadCountries();
        reloadStates();
    }

    function clear() {
        countries.value = []
        states.value = []
    }

    function getStates(countryId) {
        return states.value.filter(it => it?.country.id === countryId);
    }

    function getStateById(stateId) {
        return states.value.find(it => it?.id === stateId);
    }

    return {
        countries,
        getStates,
        getStateById,
        init, clear
    };
});
