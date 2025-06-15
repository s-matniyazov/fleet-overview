<script setup>
import {useI18n} from "vue-i18n";
import UInput from "@/components/base/UInput.vue";
import UDateInput from "@/components/base/UDateInput.vue";
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
      <div class="col-12">
        <UInput v-model="data.unit" :label="t('unit')"
                :name="t('unit')" classes="mb-2" readonly/>
      </div>

      <!--            licensePlate-->
      <div class="col-6">
        <UInput v-model="data.licensePlate" :label="t('licensePlate')"
                :name="t('licensePlate')"
                classes="mb-2" readonly/>
      </div>

      <!--            inServiceDate-->
      <div class="col-6">
        <UDateInput v-model="data.inServiceDate" classes="mb-2" :label="t('inServiceDate')"
                    name="inServiceDate" readonly/>
      </div>

      <!--            modelMaker-->
      <div class="col-6">
        <UInput v-model="data.modelMaker.name" :label="t('modelMakers')"
                classes="mb-2" readonly
        ></UInput>
      </div>

      <!--            type-->
      <div class="col-6">
        <UInput v-model="data.type.name" :label="t('types')"
                name="type" classes="mb-2" readonly
        ></UInput>
      </div>

      <!--            year-->
      <div class="col-6">
        <UInput v-model="data.year" :label="t('year')" :name="t('year')"
                classes="mb-2" type="number" readonly/>
      </div>

      <!--            axles-->
      <div class="col-6">
        <UInput v-model="data.axles" :label="t('axles')" :name="t('axles')"
                classes="mb-2" readonly/>
      </div>

      <!--            axles-->
      <div class="col-6">
        <UInput v-model="data.length" :label="t('length')" :name="t('length')"
                classes="mb-2" readonly/>
      </div>

      <!--            axles-->
      <div class="col-6">
        <UInput v-model="data.height" :label="t('height')" :name="t('height')"
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

      <template v-if="[2].includes(data.ownershipTypeId)">
        <!--            ownerOperator-->
        <div class="col-12">
          <UInput v-model="data.driver.name" :label="t('ownerOperators')"
                  name="driver" classes="mb-2" readonly
          ></UInput>
        </div>
      </template>

      <template v-if="[1, 3].includes(data.ownershipTypeId)">
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
fieldset {
  min-width: 0;
  padding: 0;
  margin: 0;
  border: 0;
}

fieldset {
  display: block;
  min-inline-size: min-content;
  margin-inline: 2px;
  border-width: 2px;
  border-style: groove;
  border-color: threedface;
  border-image: initial;
  padding-block: 0.35em 0.625em;
  padding-inline: 0.75em;
}

.bd-card-info-text {
  border-width: 1px !important;
  border-style: solid !important;
  border-color: #E5E5E7 #E5E5E7 !important;
  padding: 2px 15px 6px 15px !important;
  margin: 0;
  border-radius: 8px;
}

fieldset {
  margin-bottom: 15px !important;
}
</style>
