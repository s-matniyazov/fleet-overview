<script setup>

import UTable from "@/components/base/UTable.vue";
import {ref} from "vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, downloadResource, longToDate} from "@/util/utils.js";
import {useInspectionStore} from "@/store/InspectionFileStore.js";

const inspectionFileStore = useInspectionStore();
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
    styles: '',
    classes: '',
  },
  {
    key: 'type',
    name: 'type',
    label: t('type'),
    styles: '',
    classes: '',
  },
  {
    key: 'fileName',
    name: 'fileName',
    label: t('name'),
    styles: '',
    classes: '',
  },
  {
    key: 'size',
    name: 'size',
    label: t('size'),
    styles: '',
    classes: '',
  },
  {
    key: 'status',
    name: 'status',
    label: t('status'),
    styles: '',
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

const formatSize = (size) => {
  return (size / 1024).toFixed(2) + ' KB';
};

</script>

<template>
  <div class="mb-0 p-2 d-flex flex-column flex-grow-1 min-h-0">
    <UTable :items="inspectionFileStore.allDocuments()" :columns="columns" v-model="selectedRow"
            hide-pagination>
      <template #row_type="{row}">
        <td>
          {{ DOCUMENT_TYPES[row?.type] }}
        </td>
      </template>

      <template #row_file_name="{row}">
        <td>
          {{ row?.fileName }}
        </td>
      </template>

      <template #row_size="{row}">
        <td>
          {{ formatSize(row?.size) }}
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
          <v-btn color="primary" size="small" icon variant="text" :disabled="!row?.resource" @click="downloadResource(row?.resource)">
            <v-icon>mdi-download</v-icon>
          </v-btn>
        </td>
      </template>
    </UTable>
  </div>
</template>

<style scoped>

</style>