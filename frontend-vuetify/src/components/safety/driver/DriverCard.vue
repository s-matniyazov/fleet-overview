<script setup>

import {useI18n} from "vue-i18n";
import DriverInfo from "@/components/safety/driver/DriverInfo.vue";
import DriverFiles from "@/components/safety/driver/DriverFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import DriverDocuments from "@/components/safety/driver/DriverDocuments.vue";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const TABS = [
  {
    key: 'tr',
    name: t('driver'),
    description: '',
    component: DriverInfo,
    props: {data: props.data}
  },
  {
    key: 'trf',
    name: t('driver_files'),
    description: '',
    component: DriverFiles,
    props: {data: props.data}
  },
  {
    key: 'tra',
    name: t('driver_documents'),
    description: '',
    component: DriverDocuments,
    props: {data: props.data}
  },
];
</script>

<template>
  <div class="row g-0 mx-0 flex-grow-1 min-h-0 h-100">
    <UTabs class="col-12 d-flex flex-column min-h-0 h-100" :tabs="TABS" />
  </div>
</template>

<style scoped>

</style>