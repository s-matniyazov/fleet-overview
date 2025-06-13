<script setup>
import {useI18n} from "vue-i18n";
import UInput from "@/components/base/UInput.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import UCheckbox from "@/components/base/UCheckbox.vue";
import UTextarea from "@/components/base/UTextarea.vue";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
});
</script>
<template>
  <div class="row">
    <div class="col-6 row">
      <!--            unit-->
      <div class="col-12">
        <UInput v-model="data.unit" :label="t('unit')"
                :name="t('unit')" classes="mb-2" readonly/>
      </div>

      <!--            country-->
      <div class="col-6">
        <UInput v-model="data.state.country.name" :label="t('country')"
                classes="mb-2" readonly
        ></UInput>
      </div>

      <!--            state-->
      <div class="col-6">
        <UInput v-model="data.state.name" :label="t('states')"
                name="state" classes="mb-2" readonly
        ></UInput>
      </div>

      <!--            inServiceDate-->
      <div class="col-6">
        <UDateInput v-model="data.inServiceDate" classes="mb-2" :label="t('inServiceDate')"
                    name="inServiceDate" readonly/>
      </div>

      <!--            licensePlate-->
      <div class="col-6">
        <UInput v-model="data.licensePlate" :label="t('licensePlate')"
                :name="t('licensePlate')"
                classes="mb-2" readonly/>
      </div>

      <!--            modelMaker-->
      <div class="col-6">
        <UInput v-model="data.modelMaker.name" :label="t('modelMakers')"
                classes="mb-2" readonly
        ></UInput>
      </div>

      <!--            year-->
      <div class="col-6">
        <UInput v-model="data.year" :label="t('year')" :name="t('year')"
                classes="mb-2" type="number" readonly/>
      </div>

      <!--            fuelType-->
      <div class="col-6">
        <UInput v-model="data.fuelType.name" :label="t('fuelTypes')"
                name="fuelType" classes="mb-2" readonly
        ></UInput>
      </div>

      <!--            grossWeight-->
      <div class="col-6">
        <UInput v-model="data.grossWeight" :label="t('grossWeight')"
                :name="t('grossWeight')" classes="mb-2" readonly/>
      </div>

      <!--            axles-->
      <div class="col-6">
        <UInput v-model="data.axles" :label="t('axles')" :name="t('axles')"
                classes="mb-2" readonly/>
      </div>

      <div class="col-6">
        <UInput v-model="data.vin" :label="t('vin')" :name="t('vin')"
                classes="mb-2" readonly/>
      </div>
    </div>

    <div class="col-6">

      <!--            ownershipType-->
      <div class="col-12">
        <UInput v-model="data.ownershipType.name" :label="t('ownershipTypes')"
                name="ownershipType" classes="mb-2" readonly
        ></UInput>
      </div>
      <!--            includeIFTA-->
<!--      <div class="col-12">-->
<!--        <UCheckbox v-model="data.includeIFTA" :label="t('Include To The IFTA')" :name="t('includeIFTA')"-->
<!--                   classes="mb-2" type="checkbox" readonly/>-->
<!--      </div>-->

      <template v-if="data.ownershipType.id === 2">
        <!--            ownerOperator-->
        <div class="col-12">
          <UInput v-model="data.driver.name" :label="t('ownerOperators')"
                  name="driver" classes="mb-2" readonly
          ></UInput>
        </div>
      </template>

      <template v-if="data.ownershipType.id === 1">
        <!--            purchaseType-->
        <div class="col-12">
          <UInput v-model="data.purchaseType.name" :label="t('purchaseTypes')"
                  name="purchaseType" classes="mb-2" readonly
          ></UInput>
        </div>
      </template>

      <!--                    description-->
      <div class="col-12">
        <UTextarea v-model="data.description" :label="t('description')"
                   rows="10" classes="mb-2" readonly/>
      </div>
    </div>

  </div>
</template>

<style scoped>
</style>
