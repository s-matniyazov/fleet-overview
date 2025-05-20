<script setup>

import {useI18n} from "vue-i18n";
import {ref} from "vue";
import TruckInfo from "@/components/fleet/truck/TruckInfo.vue";
import TruckFiles from "@/components/fleet/truck/TruckFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
import TruckDocuments from "@/components/fleet/truck/TruckDocuments.vue";

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
    name: t('truck'),
    description: '',
    component: TruckInfo,
    props: {data: props.data}
  },
  {
    key: 'trf',
    name: t('truck_files'),
    description: '',
    component: TruckFiles,
    props: {data: props.data}
  },
  {
    key: 'tra',
    name: t('truck_documents'),
    description: '',
    component: TruckDocuments,
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