<script setup>

import {useI18n} from "vue-i18n";
import {ref} from "vue";
import TrailerInfo from "@/components/fleet/trailer/TrailerInfo.vue";
import TrailerFiles from "@/components/fleet/trailer/TrailerFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
import TrailerDocuments from "@/components/fleet/trailer/TrailerDocuments.vue";

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
    name: t('trailer'),
    description: '',
    component: TrailerInfo,
    props: {data: props.data}
  },
  {
    key: 'trf',
    name: t('trailer_files'),
    description: '',
    component: TrailerFiles,
    props: {data: props.data}
  },
  {
    key: 'tra',
    name: t('trailer_documents'),
    description: '',
    component: TrailerDocuments,
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