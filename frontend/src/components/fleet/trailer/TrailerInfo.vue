<script setup>

import {useI18n} from "vue-i18n";

const {t} = useI18n();

const props = defineProps({
  data : {
    type: Object,
    required: true
  }
})
</script>

<template>
  <div class="col-6 row">
    <div class="col-12 text-primary mb-3" style="font-weight: 1000; font-size: 16px">
      Unit details
    </div>
    <!--            unit-->
    <div class="col-12">
      {{ t('unit') }}: {{ data.unit }}
    </div>

    <!--            licensePlate-->
    <div class="col-12">
      {{ t('licensePlate') }}: {{ data.licensePlate }}
    </div>

    <!--            inServiceDate-->
    <div class="col-6">
      {{ t('inServiceDate') }}: {{ data.inServiceDate }}
    </div>

    <!--            modelMaker-->
    <div class="col-12">
      {{ t('modelMakers') }}: {{  data.modelMaker.name }}
    </div>

    <!--            type-->
    <div class="col-12">
      {{ t('types') }}: {{  data.type.name }}
    </div>

    <!--            year-->
    <div class="col-6">
      {{ t('year') }}: {{ data.year }}
    </div>

    <!--            axles-->
    <div class="col-6">
      {{ t('axles') }}: {{ data.axles }}
    </div>

    <!--            length-->
    <div class="col-6">
      {{ t('length') }}: {{ data.length }}
    </div>

    <!--            height-->
    <div class="col-6">
      {{ t('height') }}: {{ data.height }}
    </div>

    <!--            vin-->
    <div class="col-12">
      {{ t('vin') }}: {{ data.vin }}
    </div>
  </div>
</template>

<style scoped>

</style>