import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

export const useTruckReferenceStore = defineStore("truck-references", () => {
    const countries = ref([]);
    const makers = ref([]);
    const fuelTypes = ref([]);
    const ownershipTypes = ref([]);
    const purchaseTypes = ref([]);

    function reloadCountries() {
        axiosIns.get(URIS.COUNTRY)
            .then(res => {
                countries.value.splice(0, countries.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function reloadMakers() {
        axiosIns.get(URIS.TRUCK_MODEL_MAKER)
            .then(res => {
                makers.value.splice(0, makers.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function reloadFuelTypes() {
        axiosIns.get(URIS.FUEL_TYPE)
            .then(res => {
                fuelTypes.value.splice(0, fuelTypes.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function reloadOwnershipTypes() {
        axiosIns.get(URIS.OWNERSHIP_TYPE)
            .then(res => {
                ownershipTypes.value.splice(0, ownershipTypes.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function reloadPurchaseType() {
        axiosIns.get(URIS.PURCHASE_TYPE)
            .then(res => {
                purchaseTypes.value.splice(0, purchaseTypes.value.length, ...res.data.data);
            }).catch(e => {
            showMessage(e)
        });
    }

    function init() {
        reloadCountries();
        reloadMakers();
        reloadFuelTypes();
        reloadOwnershipTypes();
        reloadPurchaseType();
    }

    function clear() {
        countries.value = []
        makers.value = []
        fuelTypes.value = []
        ownershipTypes.value = []
        purchaseTypes.value = []
    }


    return {
        countries,
        makers,
        fuelTypes,
        ownershipTypes,
        purchaseTypes,
        init, clear
    };
});
