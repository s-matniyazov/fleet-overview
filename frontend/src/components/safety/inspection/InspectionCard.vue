<script setup>

import {useI18n} from "vue-i18n";
import InspectionInfo from "@/components/safety/inspection/InspectionInfo.vue";
import InspectionFiles from "@/components/safety/inspection/InspectionFiles.vue";
import UTabs from "@/components/base/UTabs.vue";
import InspectionDocuments from "@/components/safety/inspection/InspectionDocuments.vue";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const TABS = [
  
];
</script>

<template>
  <div class="row">
    <UTabs class="col-12 d-flex justify-content-center align-items-center" :tabs="TABS" />
  </div>
</template>

<style scoped>

</style>