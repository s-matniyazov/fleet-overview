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

    <!--            country-->
    <div class="col-6">
      {{ t('countries') }}: {{ data.city.country.name }}
    </div>

    <!--            city-->
    <div class="col-6">
      {{ t('city') }}: {{ data.city.name }}
    </div>

    <!--            inServiceDate-->
    <div class="col-6">
      {{ t('inServiceDate') }}: {{ data.inServiceDate }}
    </div>

    <!--            licensePlate-->
    <div class="col-12">
      {{ t('licensePlate') }}: {{ data.licensePlate }}
    </div>

    <!--            modelMaker-->
    <div class="col-12">
      {{ t('modelMakers') }}: {{  data.modelMaker.name }}
    </div>

    <!--            year-->
    <div class="col-6">
      {{ t('year') }}: {{ data.year }}
    </div>

    <!--            fuelType-->
    <div class="col-6">
      {{ t('fuelTypes') }}: {{ data.fuelType.name }}
    </div>

    <!--            grossWeight-->
    <div class="col-6">
      {{ t('grossWeight') }}: {{ data.grossWeight }}
    </div>

    <!--            axles-->
    <div class="col-6">
      {{ t('axles') }}: {{ data.axles }}
    </div>

    <!--            vin-->
    <div class="col-12">
      {{ t('vin') }}: {{ data.vin }}
    </div>
  </div>
</template>

<style scoped>

</style>