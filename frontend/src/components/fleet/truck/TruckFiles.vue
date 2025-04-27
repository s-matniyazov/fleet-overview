<script setup>

import UTable from "@/components/base/UTable.vue";
import {ref} from "vue";

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const columns = [
  {
    key: 'type',
    name: 'truck_file_type',
    label: t('truck_file_type'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'description',
    name: 'truck_file_description',
    label: t('truck_file_description'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'expiration_date',
    name: 'truck_file_expiration_date',
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

</script>

<template>
  hello
  {{data}}
  <div class="mb-0 p-2">
    <UTable :items="data.files" :columns="columns" v-model="selectedRow" height="calc(100vh - 348px)">
    </UTable>
  </div>
</template>

<style scoped>

</style>