import {defineStore} from "pinia";
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

const STORAGE_KEY = "fleet_state_countries_states_v1";

function readCache() {
    try {
        const raw = localStorage.getItem(STORAGE_KEY);
        if (!raw) return null;
        const parsed = JSON.parse(raw);
        if (!parsed || typeof parsed !== "object") return null;
        return parsed;
    } catch {
        return null;
    }
}

function writeCache(countries, states, companies) {
    try {
        localStorage.setItem(
            STORAGE_KEY,
            JSON.stringify({countries, states, companies}),
        );
    } catch {
        /* ignore quota / private mode */
    }
}

function hasToken() {
    return !!localStorage.getItem("token");
}

export const useStateStore = defineStore("state", () => {
    const countries = ref([]);
    const states = ref([]);
    const companies = ref([]);

    function restoreFromLocalStorage() {
        const cached = readCache();
        if (!cached) return;
        if (Array.isArray(cached.countries) && cached.countries.length) {
            countries.value.splice(0, countries.value.length, ...cached.countries);
        }
        if (Array.isArray(cached.states) && cached.states.length) {
            states.value.splice(0, states.value.length, ...cached.states);
        }
        if (Array.isArray(cached.companies)) {
            companies.value.splice(0, companies.value.length, ...cached.companies);
        }
    }

    /**
     * Login muvaffaqiyatidan keyin chaqiring — API dan yuklab localStorage ga yozadi.
     */
    async function loadFromApi() {
        try {
            const [cRes, sRes, coRes] = await Promise.all([
                axiosIns.get(URIS.COUNTRY),
                axiosIns.get(URIS.STATE),
                axiosIns.get(URIS.COMPANIES),
            ]);
            countries.value.splice(0, countries.value.length, ...cRes.data.data);
            states.value.splice(0, states.value.length, ...sRes.data.data);
            companies.value.splice(0, companies.value.length, ...coRes.data.data);
            writeCache(countries.value, states.value, companies.value);
        } catch (e) {
            showMessage(e);
            throw e;
        }
    }

    /**
     * Komponentlar: sessiya davomida kesh bor-yo‘qligini tekshiradi;
     * bo‘sh bo‘lsa va token bo‘lsa — API chaqiradi.
     */
    async function init() {
        if (!hasToken()) return;
        const cached = readCache();
        restoreFromLocalStorage();
        const hasCompaniesInCache = cached != null && Object.prototype.hasOwnProperty.call(cached, "companies");
        if (
            countries.value.length &&
            states.value.length &&
            hasCompaniesInCache
        ) {
            return;
        }
        await loadFromApi();
    }

    function clear() {
        countries.value = [];
        states.value = [];
        companies.value = [];
        localStorage.removeItem(STORAGE_KEY);
    }

    function getStates(countryId) {
        return states.value.filter(it => it?.country?.id === countryId);
    }

    function getStateById(stateId) {
        return states.value.find(it => it?.id === stateId);
    }

    return {
        countries,
        states,
        companies,
        getStates,
        getStateById,
        init,
        loadFromApi,
        restoreFromLocalStorage,
        clear,
    };
});
