<script setup>

import UTable from "@/components/base/UTable.vue";
import {ref} from "vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, downloadResource, longToDate} from "@/util/utils.js";
import {useDriverFileStore} from "@/store/DriverFileStore.js";

const driverFileStore = useDriverFileStore();
const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const columns = [
  
]

const selectedRow = ref();

const formatSize = (size) => {
  return (size / 1024).toFixed(2) + ' KB';
};

</script>

<template>
  <div class="mb-0 p-2">
    <UTable :items="driverFileStore.allDocuments()" :columns="columns" v-model="selectedRow"
            height="calc(100vh - 178px)" hide-pagination>
      <template #row_type="{row}">
        <td>
          {{ DOCUMENT_TYPES[row?.type] }}
        </td>
      </template>

      <template #row_file_name="{row}">
        <td>
          {{ row?.resource?.fileName }}
        </td>
      </template>

      <template #row_file_size="{row}">
        <td>
          {{ formatSize(row?.resource?.size) }}
        </td>
      </template>

      <template #row_expiration_date="{row}">
        <td>
          {{ longToDate(row?.expirationDate) }}
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

      <template #row_operated_by="{row}">
        <td>
          {{row?.createdBy?.name}}
        </td>
      </template>

      <template #row_actions="{row}">
        <td>
          <button @click="downloadResource(row?.resource)" :disabled="!row?.resource" class="btn btn-primary btn-sm">
            <span class="mdi mdi-download"></span>
          </button>
        </td>
      </template>
    </UTable>
  </div>
</template>

<style scoped>

</style>