// stores/filter.js
import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'

export const useFilterStore = defineStore('filter', () => {

  const storedCompany = localStorage.getItem('companyId')

  const initialFilter = storedCompany ? {companyId: storedCompany} : { companyId: null }

  const globalFilter = ref(initialFilter)

  const companyId = computed(() => globalFilter.value.companyId)

  function setCompanyId(id) {
    globalFilter.value.companyId = id;
    localStorage.setItem('companyId', id)
  }

  return { globalFilter, companyId, setCompanyId }
})
