<script setup>

import UTable from "@/components/base/UTable.vue";
import {ref} from "vue";
import {useI18n} from "vue-i18n";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {longToDate, showMessage} from "@/util/utils.js";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const columns = [
  {
    key: 'id',
    name: 'id',
    label: t('id'),
    styles: 'width: 50px;',
    classes: '',
  },
  {
    key: 'type',
    name: 'type',
    label: t('type'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'description',
    name: 'description',
    label: t('description'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'expiration_date',
    name: 'expiration_date',
    label: t('expiration_date'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'file_name',
    name: 'file_name',
    label: t('file_name'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'file_size',
    name: 'file_size',
    label: t('file_size'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'status',
    name: 'status',
    label: t('status'),
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
  axiosIns.get(URIS.RESOURCES + '/view/' + row.id, {
    responseType: 'blob',
  })
      .then(res => {
        console.log(row);
        const blob = new Blob([res.data], {type: row.contentType})
        const link = document.createElement('a')
        link.href = URL.createObjectURL(blob)
        link.download = row.fileName
        link.click()
        URL.revokeObjectURL(link.href)
      }).catch(e => {
    showMessage(e)
  });
}

const formatSize = (size) => {
  return (size / 1024).toFixed(2) + ' KB';
};

</script>

<template>
  <div class="mb-0 p-2">
    <UTable :items="data.files.sort((it, bit) => it.id <= bit.id ? 1 : -1)" :columns="columns" v-model="selectedRow"
            height="calc(100vh - 258px)" hide-pagination>

      <template #row_file_name="{row}">
        <td>
          {{ row.resource.fileName }}
        </td>
      </template>

      <template #row_file_size="{row}">
        <td>
          {{ formatSize(row.resource.size) }}
        </td>
      </template>

      <template #row_expiration_date="{row}">
        <td>
          {{ longToDate(row.expirationDate) }}
        </td>
      </template>

      <template #row_status="{row}">
        <td>
          <div class="col-12 d-flex align-items-center">
              <span class="badge badge-pill" :class="row?.status === 'ACTIVE' ? 'text-primary  badge-soft-primary' : 'text-danger badge-soft-danger'">
                {{ row?.status }}
              </span>
          </div>
        </td>
      </template>

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