<script setup>
import {onMounted, ref, watch} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, PERMIT_NAMES, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import UCheckbox from "@/components/base/UCheckbox.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
import UDialog from "@/components/base/UDialog.vue";
import UTooltip from "@/components/base/UTooltip.vue";
import FileMiniCard from "@/components/FileMiniCard.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import TruckCard from "@/components/fleet/truck/TruckCard.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import {useTruckFileStore} from "@/store/TruckFileStore.js";
import PermitMiniCard from "@/components/PermitMiniCard.vue";
import {useTruckReferenceStore} from "@/store/TruckReferencesStore.js";
import {useStateStore} from "@/store/StateStore.js";

const {t} = useI18n();
const filterStore = useFilterStore();
const truckFileStore = useTruckFileStore();
const truckReferenceStore = useTruckReferenceStore();
const stateStore = useStateStore();

const columns = [
  {
    key: 'unit_details',
    name: 'unit_details',
    label: t('unit_details'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'operated_by',
    name: 'operated_by',
    label: t('operated_by'),
    styles: 'min-width: 160px;',
    classes: '',
  },
  {
    key: 'ownership',
    name: 'ownership',
    label: t('ownership'),
    styles: 'min-width: 140px;',
    classes: '',
  },
  {
    key: 'registration',
    name: 'registration',
    label: t('registration'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'annual_inspection',
    name: 'annual_inspection',
    label: t('annual_inspection'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'physical_damage_inc',
    name: 'physical_damage_inc',
    label: t('physical_damage_inc'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'lease_agreement',
    name: 'lease_agreement',
    label: t('lease_agreement'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'permits',
    name: 'permits',
    label: t('permits'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'status',
    name: 'status',
    label: t('status'),
    styles: '',
    classes: '',
  },
  // {
  //   key: 'actions',
  //   name: 'actions',
  //   label: t('actions'),
  //   styles: '',
  //   classes: '',
  // },
]

const newModel = () => {
  return {
    id: null,
    unit: null,
    inServiceDate: null,
    licensePlate: null,
    stateId: null,
    modelMakerId: null,
    year: null,
    fuelTypeId: null,
    grossWeight: null,
    axles: null,
    vin: null,
    ownershipTypeId: null,
    includeIFTA: false,
    purchaseTypeId: null,
    driverId: null,
    description: null,
    companyId: filterStore.companyId
  }
}

const addModal = ref(false);
const showModal = ref(false);
const selectedFileSection = ref({
  dialog: false,
  data: {
    description: '',
    expirationDate: new Date(),
    type: '',
    truckId: ''
  }
});

const apiUrl = URIS.TRUCK;
const filter = ref({
  vinOrUnit: null
});
const dataList = ref([]);
const selectedRow = ref();
const data = ref(newModel())

const drivers = ref([]);

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
    countryId: d?.countryId,
    stateId: d?.stateId,
    modelMakerId: d?.modelMakerId,
    year: d.year,
    fuelTypeId: d?.fuelTypeId,
    grossWeight: d.grossWeight,
    axles: d.axles,
    vin: d.vin,
    ownershipTypeId: d?.ownershipTypeId,
    includeIFTA: d.includeIFTA,
    purchaseTypeId: d?.purchaseTypeId,
    driverId: d?.driverId,
    description: d.description,
    companyId: filterStore.companyId
  };

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}
const selectFileSection = (type) => {
  selectedFileSection.value = {
    dialog: true,
    data: {
      ...selectedFileSection.value.data,
      truckId: selectedRow.value.id,
      type: type
    }
  };
}
const getOwnership = (row) => {
  if (row?.ownershipTypeId === 1) {
    return row?.purchaseTypeName
  } else if (row?.ownershipTypeId === 2) {
    return row?.driverName
  } else return "N/A";
}

const reload = () => {
  getData();
  truckReferenceStore.init();
}

// API FUNCTIONS
const onSave = () => {
  if (data.value.id) {
    axiosIns.put(apiUrl, data.value)
        .then(() => {
          getData();
          onClose();
        }).catch(e => {
      showMessage(e)
    });
  } else {
    axiosIns.post(apiUrl, data.value)
        .then(() => {
          getData();
          onClose();
        }).catch(e => {
      showMessage(e)
    });
  }
}

function getData() {
  axiosIns.get(`${apiUrl}${filterString({companyId: filterStore.companyId, ...filter.value})}`)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = null;
      }).catch(e => {
    showMessage(e)
  });
}

function getDrivers() {
  axiosIns.get(`${URIS.DRIVER}?companyId=${filterStore.companyId}`)
      .then(res => {
        drivers.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
  getDrivers();

  truckReferenceStore.init();
  stateStore.init();
})

watch(
    () => data.value.ownershipTypeId,
    function () {
      data.driverId = null;
      data.purchaseTypeId = null;
    }
)

watch(
    () => selectedFileSection.value.dialog,
    function (newValue) {
      if (!newValue) getData()
    }
)

watch(
    () => showModal.value,
    function (newValue) {
      if (newValue) truckFileStore.init(selectedRow.value.id)
      else truckFileStore.clear()
    }
)

</script>

<template>
  <div class="mb-0 p-2">
    <div class="col-12">
      <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
        <button @click="onAdd" class="btn btn-primary btn-sm">
          <span class="mdi mdi-plus"></span> {{ t("add") }}
        </button>
        <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow">
          <span class="mdi mdi-pen"></span> {{ t("edit") }}
        </button>
        <button @click="showModal = true" class="btn btn-primary btn-sm" :disabled="!selectedRow">
          <span class="mdi mdi-eye"></span>
        </button>

        <UInput v-model="filter.vinOrUnit" style="min-width: 23vw"
                :hint="t('vin')" :placeholder="t('search_by_vin_or_unit')"/>
        <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-magnify"></span></button>

        <div class="align-items-center u-end">
          <button @click="reload" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
        </div>
      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 248px)">
      <template #row_unit_details="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <span class="text-primary badge badge-pill badge-soft-primary" style="font-size: 15px">{{
                  row?.unit
                }}</span>
              <span class="badge badge-soft-success mx-1">{{ row?.includeIFTA ? 'IFTA' : '' }}</span>
            </div>
            <div class="col-12 d-flex align-items-center">
              <span class="text-gray-light f-700">{{ row?.vin }}</span>
            </div>
          </div>
        </td>
      </template>

      <template #row_operated_by="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <UTooltip>
                <span class="text-primary" style="font-size: 15px">
                  {{ row?.companyName }}
                </span>
                <template #content>
                  {{ row?.companyName }}
                </template>
              </UTooltip>
            </div>
            <div class="col-12 d-flex align-items-center">
              <UTooltip>
                <span class="text-gray-light f-700">{{ row?.createdByName }}</span>
                <template #content>
                  {{ row?.createdByName }}
                </template>
              </UTooltip>
            </div>
          </div>
        </td>
      </template>

      <template #row_ownership="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <UTooltip>
                <span class="text-primary" style="font-size: 15px">
                  {{ row?.ownershipTypeName }}
                </span>
                <template #content>
                  {{ row?.ownershipTypeName }}
                </template>
              </UTooltip>
            </div>
            <div class="col-12 d-flex align-items-center text-gray-light fw-bold">
              {{ getOwnership(row) }}
            </div>
          </div>
        </td>
      </template>

      <template #row_registration="{row}">
        <td>
          <FileMiniCard name="REG (CAB CARD)" type="REG_CAB_CARD"
                        :file="row?.files.find(it => it.type==='REG_CAB_CARD')"
                        @click="(e) => {selectedRow = row; selectFileSection('REG_CAB_CARD'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_annual_inspection="{row}">
        <td>
          <FileMiniCard name="ANN INS" type="ANN_INS"
                        :file="row?.files.find(it => it.type==='ANN_INS')"
                        @click="(e) => {selectedRow = row; selectFileSection('ANN_INS'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_physical_damage_inc="{row}">
        <td>
          <FileMiniCard name="PHYS DAMAGE" type="PHYS_DAMAGE"
                        :file="row?.files.find(it => it.type==='PHYS_DAMAGE')"
                        @click="(e) => {selectedRow = row; selectFileSection('PHYS_DAMAGE'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_lease_agreement="{row}">
        <td>
          <FileMiniCard name="LEASE AGR" type="LEASE_AGR"
                        :file="row?.files.find(it => it.type==='LEASE_AGR')"
                        @click="(e) => {selectedRow = row; selectFileSection('LEASE_AGR'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_permits="{row}">
        <td>
          <div class="qm-badge qm-badge--dim justify-content-start permit-box">
            <PermitMiniCard v-for="item in PERMIT_NAMES" :type="item.key" :name="item.name"
                            :file="row?.permits.find(it => it.type===item.key && it.status === 'ACTIVE')"/>
          </div>
        </td>
      </template>

      <template #row_status="{row}">
        <td>
          <div class="d-flex gap-2">
            <a class="badge bg-primary-subtle text-primary"
               :class="`bg-${row?.status === 'PASSIVE' ? 'danger' : 'primary'}-subtle`"> {{ row?.status }}</a>
          </div>
        </td>
      </template>
    </UTable>
  </div>

  <!--  truck modal-->
  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
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
          <UScrollArea height="calc(100vh - 300px)">
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
                  <USelect v-model="data.countryId" :label="t('country')"
                           :items="stateStore.countries" name="country"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>

                <!--            state-->
                <div class="col-6">
                  <USelect v-model="data.stateId" :label="t('states')"
                           :items="stateStore.getStates(data.countryId)" name="state"
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
                <div class="col-6">
                  <UInput v-model="data.licensePlate" :label="t('licensePlate')" :hint="t('licensePlate')"
                          :name="t('licensePlate')"
                          :placeholder="t('enter_licensePlate')" classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            modelMaker-->
                <div class="col-6">
                  <USelect v-model="data.modelMakerId" :label="t('modelMakers')"
                           :items="truckReferenceStore.makers" name="modelMaker"
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
                           :items="truckReferenceStore.fuelTypes" name="fuelType"
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
                <div class="col-6">
                  <UInput v-model="data.vin" :label="t('vin')" :hint="t('vin')" :name="t('vin')"
                          :placeholder="t('enter_vin')" classes="mb-2"
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
                           :items="truckReferenceStore.ownershipTypes" name="ownershipType"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>
                <!--            includeIFTA-->
                <div class="col-12">
                  <UCheckbox v-model="data.includeIFTA" :label="t('Include To The IFTA')" :name="t('includeIFTA')"
                             classes="mb-2" type="checkbox"/>
                </div>

                <template v-if="data.ownershipTypeId === 2">
                  <!--            ownerOperator-->
                  <div class="col-12">
                    <USelect v-model="data.driverId" :label="t('ownerOperators')"
                             :items="drivers" name="driver"
                             option_name="firstName"
                             classes="mb-2"
                             :rules="(val) => (!val && $t('required'))"
                    ></USelect>
                  </div>
                </template>

                <template v-if="data.ownershipTypeId === 1">
                  <div class="col-12 text-primary my-3" style="font-weight: 1000; font-size: 16px">
                    Other Details
                  </div>

                  <!--            purchaseType-->
                  <div class="col-12">
                    <USelect v-model="data.purchaseTypeId" :label="t('purchaseTypes')"
                             :items="truckReferenceStore.purchaseTypes" name="purchaseType"
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
          </UScrollArea>

          <div class="modal-footer bg-light">
            <div class="d-flex text-end align-items-end mt-2">
              <button type="submit" class="btn btn-primary">Save</button>
            </div>
          </div>
        </UForm>
      </template>
    </UDialog>
  </Teleport>

  <!--  truck card-->
  <Teleport to="body">
    <UDialog :show="showModal && false" width="calc(100vw - 200px)" class="">
      <template #header>
        <div class="d-flex w-100">
          Truck Card
          <div class="text-end u-end">
            <button class="btn-close" @click="showModal = false"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UScrollArea height="calc(100vh - 200px)">
          <TruckCard :data="selectedRow"/>
        </UScrollArea>
      </template>
    </UDialog>
  </Teleport>

  <!--  truck card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(100vw - 500px)" class="">
    <template #header>
      <div class="d-flex w-100">
        Truck Card
        <div class="text-end u-end">
          <button class="btn-close" @click="showModal = false"></button>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <TruckCard :data="selectedRow"/>
      </UScrollArea>
    </template>
  </URightOverlay>

<!--  file overlay-->
  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <h4 class="fw-bold text-white bg-primary p-2 rounded-2 d-flex">{{
          DOCUMENT_TYPES[selectedFileSection.data.type]
        }}
        <span class="text-end u-end">
          <button class="btn-close" @click="selectedFileSection.dialog = false"></button>
        </span>
      </h4>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.TRUCK}/attach-file`" :data="selectedFileSection.data"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
.qm-badge {
  border: 1px solid transparent;
  border-radius: 3px;
  padding: 5px;
}

.custom-light-shadow {
  box-shadow: 0 0 2px #00000040 !important;
}

.permit-box {
  min-height: 64px;
  max-height: 84px;
  line-height: 140%;
  font-size: 11px;
  padding-bottom: 1px;
  padding-top: 1px;
}

.qm-badge--dim {
  background: #f6f6f6;
  padding: 3px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.justify-content-start {
  justify-content: flex-start !important;
}
</style>
