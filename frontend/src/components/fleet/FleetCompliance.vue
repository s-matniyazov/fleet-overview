<script setup>
import {onMounted, ref} from "vue";

import {useI18n} from "vue-i18n";
import Truck from "@/components/fleet/truck/Truck.vue";
import TabPanels from "@/components/base/UTabPanels.vue";
import Tabs from "@/components/base/UTabs.vue";
import Trailer from "@/components/fleet/Trailer.vue";

const {t} = useI18n();

const TABS = [
  {
    key: 'tr',
    name: t('trucks'),
    description: '',
    component: Truck
  },
  {
    key: 'tra',
    name: t('trailers'),
    description: '',
    component: Trailer
  },
];
const activeTab = ref(TABS[0].key);

// HOOKS
onMounted(() => {

})
</script>

<template>
  <div class="h-100 col-12 p-2">
    <div class="container-fluid mb-2 px-2 d-flex align-items-center"
         style="border: 1px dashed #eae1e1; border-radius: 5px; height: 50px; font-weight: bold; font-size: 16px;">
      <tabs :tabs="TABS" v-model="activeTab"/>
    </div>

    <tab-panels :tabs="TABS" v-model="activeTab"/>
  </div>
</template>

<style scoped>

</style>
