<script setup>

import {useI18n} from "vue-i18n";
import {ref} from "vue";
import DriverInfo from "@/components/safety/driver/DriverInfo.vue";
import DriverFiles from "@/components/safety/driver/DriverFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
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
const activeTab = ref(TABS[0].key);
</script>

<template>
  <div class="row">
    <UTabs class="col-12 d-flex justify-content-center align-items-center" :tabs="TABS" v-model="activeTab"/>

    <tab-panels :tabs="TABS" v-model="activeTab"/>
  </div>
</template>

<style scoped>

</style>