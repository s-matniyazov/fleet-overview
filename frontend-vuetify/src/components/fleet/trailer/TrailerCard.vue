<script setup>

import {useI18n} from "vue-i18n";
import TrailerInfo from "@/components/fleet/trailer/TrailerInfo.vue";
import TrailerFiles from "@/components/fleet/trailer/TrailerFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
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
</script>

<template>
  <div class="row g-0 mx-0 flex-grow-1 min-h-0 h-100">
    <UTabs class="col-12 d-flex flex-column min-h-0 h-100" :tabs="TABS" />
  </div>
</template>

<style scoped>

</style>