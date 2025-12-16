<script setup>
import {onMounted} from "vue";

import {useI18n} from "vue-i18n";
import Truck from "@/components/fleet/truck/Truck.vue";
import Tabs from "@/components/base/UTabs.vue";
import Trailer from "@/components/fleet/trailer/Trailer.vue";

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

// HOOKS
onMounted(() => {

})
</script>

<template>
  <div class="h-100 col-12 p-2">
    <tabs :tabs="TABS" />
  </div>
</template>

<style scoped>

</style>
