<script setup>

import UTable from "@/components/base/UTable.vue";
import {ref} from "vue";
import {useI18n} from "vue-i18n";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const columns = [
  {
    key: 'type',
    name: 'type',
    label: t('truck_file_type'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'description',
    name: 'description',
    label: t('truck_file_description'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'expiration_date',
    name: 'expiration_date',
    label: t('truck_file_expiration_date'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'operated_by',
    name: 'operated_by',
    label: t('operated_by'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'actions',
    name: 'actions',
    label: t('actions'),
    styles: '',
    classes: '',
  },
]

const selectedRow = ref();

function downloadDoc(row) {
  axiosIns.get(URIS.RESOURCES + '/view/' + row.id)
      .then(res => {
        const blob = new Blob([res.data], { type: row.contentType })
        const link = document.createElement('a')
        link.href = URL.createObjectURL(blob)
        link.download = row.fileName
        link.click()
        URL.revokeObjectURL(link.href)
      }).catch(e => {
    showMessage(e)
  });
}

</script>

<template>
  <div class="mb-0 p-2">
    <UTable :items="data.files" :columns="columns" v-model="selectedRow" height="calc(100vh - 348px)" hide-pagination>
      <template #row_actions="{row}">
        <td>
          <button @click="downloadDoc(row?.resource)" class="btn btn-primary btn-sm">
            <span class="mdi mdi-download"></span>
          </button>
        </td>
      </template>
    </UTable>
  </div>
</template>

<style scoped>

</style>