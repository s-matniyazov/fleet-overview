<script setup>
import {onMounted, ref, watch} from "vue";

import modal from '../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import UCheckbox from "@/components/base/UCheckbox.vue";
import UTextarea from "@/components/base/UTextarea.vue";

const {t} = useI18n();

const columns = [
  {
    key: 'unit_details',
    name: 'unit_details',
    label: t('unit_details'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'operated_by',
    name: 'operated_by',
    label: t('operated_by'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'ownership',
    name: 'ownership',
    label: t('ownership'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'registration',
    name: 'registration',
    label: t('registration'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'annual_inspection',
    name: 'annual_inspection',
    label: t('annual_inspection'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'physical_damage_inc',
    name: 'physical_damage_inc',
    label: t('physical_damage_inc'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'lease_agreement',
    name: 'lease_agreement',
    label: t('lease_agreement'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'permits',
    name: 'permits',
    label: t('permits'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'status',
    name: 'status',
    label: t('status'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'actions',
    name: 'actions',
    label: t('actions'),
    styles: 'width: 200px;',
    classes: '',
  },
]

const newModel = () => {
  return {
    id: null,
    unit: null,
    inServiceDate: null,
    licensePlate: null,
    cityId: null,
    modelMakerId: null,
    year: null,
    fuelTypeId: null,
    grossWeight: null,
    axles: null,
    vin: null,
    ownershipTypeId: null,
    includeIFTA: null,
    purchaseTypeId: null,
    ownerOperatorId: null,
    description: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.TRUCK;
const dataList = ref([]);
const data = ref(newModel())
const selectedRow = ref();

const countries = ref([]);
const cities = ref([]);
const makers = ref([]);
const fuelTypes = ref([]);
const ownershipTypes = ref([]);
const purchaseTypes = ref([]);
const ownerOperators = ref([]);

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    id: d.id,
    unit: d.unit,
    inServiceDate: d.inServiceDate,
    licensePlate: d.licensePlate,
    countryId: d?.city?.country.id,
    cityId: d?.city?.id,
    modelMakerId: d?.modelMaker?.id,
    year: d.year,
    fuelTypeId: d?.fuelType.id,
    grossWeight: d.grossWeight,
    axles: d.axles,
    vin: d.vin,
    ownershipTypeId: d?.ownershipType?.id,
    includeIFTA: d.includeIFTA,
    purchaseTypeId: d?.purchaseType?.id,
    ownerOperatorId: d.ownerOperator.id,
    description: d.description,
  };

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}

// API FUNCTIONS
const onSave = () => {
  if (data.value.id) {
    axiosIns.put(apiUrl, data.value)
        .then(res => {
          getData();
          onClose();
        }).catch(e => {
      showMessage(e)
    });
  } else {
    axiosIns.post(apiUrl, data.value)
        .then(res => {
          getData();
          onClose();
        }).catch(e => {
      showMessage(e)
    });
  }
}

function getData() {
  axiosIns.get(apiUrl)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = null;
      }).catch(e => {
    showMessage(e)
  });
}

function getCountry() {
  axiosIns.get(URIS.COUNTRY)
      .then(res => {
        countries.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getCity(countryId) {
  axiosIns.get(`${URIS.CITY}?countryId=${countryId}`)
      .then(res => {
        cities.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getMaker() {
  axiosIns.get(URIS.MODEL_MAKER)
      .then(res => {
        makers.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getFuelType() {
  axiosIns.get(URIS.FUEL_TYPE)
      .then(res => {
        fuelTypes.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getOwnershipType() {
  axiosIns.get(URIS.OWNERSHIP_TYPE)
      .then(res => {
        ownershipTypes.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getPurchaseType() {
  axiosIns.get(URIS.PURCHASE_TYPE)
      .then(res => {
        purchaseTypes.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getOwnerOperator() {
  axiosIns.get(URIS.PURCHASE_TYPE)
      .then(res => {
        ownerOperators.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
  getCountry();
  getMaker();
  getFuelType();
  getOwnershipType();
  getPurchaseType();
  getOwnerOperator();
})

watch(
    () => data.value.countryId,
    function (newValue, oldValue) {
      data.cityId = null;
      cities.value = [];
      getCity(newValue)
    }
)

</script>

<template>
  <div class="mb-0 p-2">
    <div class="col-12">
      <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
        <button @click="onAdd" class="btn btn-primary btn-sm"><span class="mdi mdi-plus"></span> {{ t("add") }}</button>
        <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow">
          <span class="mdi mdi-pen"></span> {{ t("edit") }}
        </button>

        <div class="align-items-center u-end">
          <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
        </div>
      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" styles="height: calc(100vh - 296px)">
      <template #row_unit_details="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_operated_by="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_ownership="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_registration="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_annual_inspection="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_physical_damage_inc="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_lease_agreement="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_permits="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_status="{row}">
        <td>{{ row?.unit }}</td>
      </template>

      <template #row_actions="{row}">
        <td>{{ row?.unit }}</td>
      </template>
    </UTable>
  </div>

  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" width="calc(100vw - 50%)">
      <template #header>
        <div class="d-flex w-100">
          <div class="text-primary" style="font-weight: 1000; font-size: 16px">
            {{ data.id ? t('edit') : t('add') }} {{ t('truck') }}
          </div>
          <div class="text-end u-end">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row">
            <div class="col-6 row">
              <div class="col-12 text-primary mb-3" style="font-weight: 1000; font-size: 16px">
                Unit details
              </div>
              <!--            unit-->
              <div class="col-12">
                <UInput v-model="data.unit" :label="t('unit')" :hint="t('unit')" :name="t('unit')"
                        :placeholder="t('enter_unit')" classes="mb-2"
                        :rules="(val) => (!val && $t('required'))"/>
              </div>

              <!--            country-->
              <div class="col-6">
                <USelect v-model="data.countryId" :label="t('countries')"
                         :items="countries" name="country"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>

              <!--            city-->
              <div class="col-6">
                <USelect v-model="data.cityId" :label="t('cities')"
                         :items="cities" name="city"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>

              <!--            inServiceDate-->
              <div class="col-6">
                <UDateInput v-model="data.inServiceDate" classes="mb-2" :label="t('inServiceDate')"
                            name="inServiceDate"
                            :rules="(val) => (!val && $t('required'))"/>
              </div>

              <!--            licensePlate-->
              <div class="col-12">
                <UInput v-model="data.licensePlate" :label="t('licensePlate')" :hint="t('licensePlate')"
                        :name="t('licensePlate')"
                        :placeholder="t('enter_licensePlate')" classes="mb-2"
                        :rules="(val) => (!val && $t('required'))"/>
              </div>

              <!--            modelMaker-->
              <div class="col-12">
                <USelect v-model="data.modelMakerId" :label="t('modelMakers')"
                         :items="makers" name="modelMaker"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>

              <!--            year-->
              <div class="col-6">
                <UInput v-model="data.year" :label="t('year')" :hint="t('year')" :name="t('year')"
                        :placeholder="t('enter_year')" classes="mb-2" type="number"
                        :rules="(val) => (!val && $t('required'))"/>
              </div>

              <!--            fuelType-->
              <div class="col-6">
                <USelect v-model="data.fuelTypeId" :label="t('fuelTypes')"
                         :items="fuelTypes" name="fuelType"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>

              <!--            grossWeight-->
              <div class="col-6">
                <UInput v-model="data.grossWeight" :label="t('grossWeight')" :hint="t('grossWeight')"
                        :name="t('grossWeight')"
                        :placeholder="t('enter_grossWeight')" classes="mb-2" type="number"
                        :rules="(val) => (!val && $t('required'))"/>
              </div>

              <!--            axles-->
              <div class="col-6">
                <UInput v-model="data.axles" :label="t('axles')" :hint="t('axles')" :name="t('axles')"
                        :placeholder="t('enter_axles')" classes="mb-2" type="number"
                        :rules="(val) => (!val && $t('required'))"/>
              </div>

              <!--            vin-->
              <div class="col-12">
                <UInput v-model="data.vin" :label="t('vin')" :hint="t('vin')" :name="t('vin')"
                        :placeholder="t('enter_vin')" classes="mb-2" type="number"
                        :rules="(val) => (!val && $t('required'))"/>
              </div>
            </div>

            <div class="col-6">
              <div class="col-12 text-primary mb-3" style="font-weight: 1000; font-size: 16px">
                Ownership details
              </div>

              <!--            ownershipType-->
              <div class="col-12">
                <USelect v-model="data.ownershipTypeId" :label="t('ownershipTypes')"
                         :items="ownershipTypes" name="ownershipType"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>

              <template v-if="data.ownershipTypeId === 2">
                <!--            ownerOperator-->
                <div class="col-12">
                  <USelect v-model="data.ownerOperatorId" :label="t('ownerOperators')"
                           :items="ownerOperators" name="ownerOperator"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>
              </template>

              <!--            includeIFTA-->
              <div class="col-12">
                <UCheckbox v-model="data.includeIFTA" :label="t('Include To The IFTA')" :name="t('includeIFTA')"
                           classes="mb-2" type="checkbox"
                           :rules="(val) => (!val && $t('required'))"/>
              </div>

              <template v-if="data.ownershipTypeId === 1">
                <div class="col-12 text-primary my-3" style="font-weight: 1000; font-size: 16px">
                  Other Details
                </div>

                <!--            purchaseType-->
                <div class="col-12">
                  <USelect v-model="data.purchaseTypeId" :label="t('purchaseTypes')"
                           :items="purchaseTypes" name="purchaseType"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>
              </template>


              <div class="col-12 text-primary my-3" style="font-weight: 1000; font-size: 16px">
                Additional Notes
              </div>
              <!--            description-->
              <div class="col-12">
                <UTextarea v-model="data.description" :label="t('description')"
                           :placeholder="t('enter_description')" rows="10"
                           classes="mb-2"/>
              </div>
            </div>

          </div>

          <div class="modal-footer">
            <div class="d-flex text-end align-items-end mt-2">
              <button type="submit" class="btn btn-primary">Сахранить</button>
            </div>
          </div>
        </UForm>
      </template>
    </modal>
  </Teleport>
</template>

<style scoped>

</style>
