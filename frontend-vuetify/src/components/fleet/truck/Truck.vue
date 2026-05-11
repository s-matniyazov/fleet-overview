<script setup>
import {computed, onMounted, ref, watch} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {TABLE_STATUS_FILTER_ITEMS} from "@/constants/tableFilters.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, longToDateTime, PERMIT_NAMES, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import { mapSelectItems, parseOptionalId } from "@/util/selectItems.js";
import { useFormSelectField } from "@/composables/useFormSelectField.js";
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
import UDropDown from "@/components/base/UDropDown.vue";

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
    styles: 'min-width: 200px;',
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
    key: 'non_trucking_liability',
    name: 'non_trucking_liability',
    label: t('non_trucking_liability'),
    styles: 'min-width: 250px;',
    classes: '',
  },
  {
    key: 'clean_truck_check_permit',
    name: 'clean_truck_check_permit',
    label: t('clean_truck_check_permit'),
    styles: 'min-width: 250px;',
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
    styles: 'min-width: 160px;',
    classes: '',
  },
  {
    key: 'actions',
    name: 'actions',
    label: t('actions'),
    styles: '',
    classes: 'last-col-sticky',
  },
]

const newModel = () => {
  return {
    id: null,
    unit: null,
    carrierResponsibleForSafety: null,
    inServiceDate: null,
    licensePlate: null,
    countryId: null,
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
const pagination = ref({
  rowsPerPage: 10,
  page: 1,
  hasNext: true
});
const filter = ref({
  vinOrUnit: null,
  status: 'ACTIVE'
});
const dataList = ref([]);
const selectedRow = ref();
const data = ref(newModel())

const drivers = ref([]);

const truckCountryItems = computed(() =>
  mapSelectItems(stateStore.countries, "name", "id"),
);
const truckStateItems = computed(() =>
  mapSelectItems(stateStore.getStates(data.value.countryId), "name", "id"),
);
const truckModelMakerItems = computed(() =>
  mapSelectItems(truckReferenceStore.makers, "name", "id"),
);
const truckFuelTypeItems = computed(() =>
  mapSelectItems(truckReferenceStore.fuelTypes, "name", "id"),
);
const truckOwnershipItems = computed(() =>
  mapSelectItems(truckReferenceStore.ownershipTypes, "name", "id"),
);
const truckDriverItems = computed(() =>
  mapSelectItems(
    drivers.value,
    (d) => [d.firstName, d.lastName].filter(Boolean).join(" "),
    "id",
  ),
);
const truckPurchaseTypeItems = computed(() =>
  mapSelectItems(truckReferenceStore.purchaseTypes, "name", "id"),
);

const { errorMessages: truckCountryErr } = useFormSelectField(
  "country",
  () => data.value.countryId,
  (v) => (!v && t("required")),
);
const { errorMessages: truckStateErr } = useFormSelectField(
  "state",
  () => data.value.stateId,
  (v) => (!v && t("required")),
);
const { errorMessages: truckModelMakerErr } = useFormSelectField(
  "modelMaker",
  () => data.value.modelMakerId,
  (v) => (!v && t("required")),
);
const { errorMessages: truckFuelTypeErr } = useFormSelectField(
  "fuelType",
  () => data.value.fuelTypeId,
  (v) => (!v && t("required")),
);
useFormSelectField("ownershipType", () => data.value.ownershipTypeId, null);
useFormSelectField("driver", () => data.value.driverId, null);
useFormSelectField("purchaseType", () => data.value.purchaseTypeId, null);

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    id: d.id,
    unit: d.unit,
    carrierResponsibleForSafety: d.carrierResponsibleForSafety,
    inServiceDate: d.inServiceDate,
    licensePlate: d.licensePlate,
    countryId: parseOptionalId(d?.countryId),
    stateId: parseOptionalId(d?.stateId),
    modelMakerId: parseOptionalId(d?.modelMakerId),
    year: d.year,
    fuelTypeId: parseOptionalId(d?.fuelTypeId),
    grossWeight: d.grossWeight,
    axles: d.axles,
    vin: d.vin,
    ownershipTypeId: parseOptionalId(d?.ownershipTypeId),
    includeIFTA: d.includeIFTA,
    purchaseTypeId: parseOptionalId(d?.purchaseTypeId),
    driverId: parseOptionalId(d?.driverId),
    description: d.description,
    companyId: filterStore.companyId
  };

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}
const selectFileSection = (type) => {
  const file = selectedRow.value?.files.find(it => it.type === type) || {}

  selectedFileSection.value = {
    dialog: true,
    data: {
      ...file,
      truckId: selectedRow.value.id,
      type: type,
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
const deactivate = (row) => {
  onDeactivate(row);
}
const activate = (row) => {
  onActivate(row);
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
const onDeactivate = (row) => {
  axiosIns.post(`${apiUrl}/${row.id}/deactivate`)
      .then(() => {
        getData();
      }).catch(e => {
    showMessage(e)
  });
}
const onActivate = (row) => {
  axiosIns.post(`${apiUrl}/${row.id}/activate`)
      .then(() => {
        getData();
      }).catch(e => {
    showMessage(e)
  });
}

function getData() {
  axiosIns.get(`${apiUrl}${filterString({companyId: filterStore.companyId, ...filter.value, ...pagination.value})}`)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = null;
        pagination.value.hasNext = dataList.value.length >= pagination.value.rowsPerPage;
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

watch(
    () => pagination.value.page,
    function (newValue) {
      getData();
    }
)
watch(
    () => pagination.value.rowsPerPage,
    function (newValue) {
      getData();
    }
)

</script>

<template>
  <div class="mb-0 p-2">
    <UTable
      :items="dataList"
      :columns="columns"
      v-model="selectedRow"
      v-model:pagination="pagination"
    >
      <template #top>
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
          <v-btn color="primary" size="small" prepend-icon="mdi-plus" @click="onAdd">
            {{ t("add") }}
          </v-btn>
          <v-btn color="primary" size="small" prepend-icon="mdi-pencil" :disabled="!selectedRow" @click="onEdit(selectedRow)">
            {{ t("edit") }}
          </v-btn>
          <v-btn color="primary" size="small" icon variant="text" :disabled="!selectedRow" @click="showModal = true">
            <v-icon>mdi-eye</v-icon>
          </v-btn>

          <v-select
            v-model="filter.status"
            :items="TABLE_STATUS_FILTER_ITEMS"
            item-title="title"
            item-value="value"
            :label="t('status')"
            density="compact"
            variant="outlined"
            hide-details
            bg-color="surface"
            class="table-top-status-select"
            @update:model-value="getData"
          />

          <UInput v-model="filter.vinOrUnit" style="min-width: 23vw"
                  :placeholder="t('search_by_vin_or_unit')"/>
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </template>
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

      <template #row_non_trucking_liability="{row}">
        <td>
          <FileMiniCard name="NON TRUCKING LIABILITY" type="NON_TRUCKING_LIABILITY"
                        :file="row?.files.find(it => it.type==='NON_TRUCKING_LIABILITY')"
                        @click="(e) => {selectedRow = row; selectFileSection('NON_TRUCKING_LIABILITY'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_clean_truck_check_permit="{row}">
        <td>
          <FileMiniCard name="CLEAN TRUCK CHECK PERMIT" type="CLEAN_TRUCK_CHECK_PERMIT"
                        :file="row?.files.find(it => it.type==='CLEAN_TRUCK_CHECK_PERMIT')"
                        @click="(e) => {selectedRow = row; selectFileSection('CLEAN_TRUCK_CHECK_PERMIT'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_permits="{row}">
        <td class="permits-cell">
          <div class="permit-stack">
            <PermitMiniCard
              v-for="item in PERMIT_NAMES"
              :key="item.key"
              :type="item.key"
              :name="item.value"
              :file="row?.permits?.find(it => it.type === item.key && it.status === 'ACTIVE')"
            />
          </div>
        </td>
      </template>

      <template #row_status="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <a class="badge bg-primary-subtle text-primary"
                 :class="`bg-${row?.status === 'PASSIVE' ? 'danger' : 'primary'}-subtle`"> {{ row?.status }}</a>
            </div>
            <div class="col-12 d-flex align-items-center mt-1">
              {{ longToDateTime(row.statusDate) }}
            </div>
          </div>
        </td>
      </template>

      <template #row_actions="{row}">
        <td class="last-col-sticky">
          <div class="items-center">
            <UDropDown position="left" class="btn w-auto text-start p-0">
              <template #header>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="feather feather-more-vertical">
                  <circle cx="12" cy="12" r="1"></circle>
                  <circle cx="12" cy="5" r="1"></circle>
                  <circle cx="12" cy="19" r="1"></circle>
                </svg>
              </template>
              <template #body>
                <div class="bg-body rounded-1 font-size-15 p-2">
                  <v-btn v-if="row.status === 'ACTIVE'" size="small" variant="outlined" color="error" @click="deactivate(row)">
                    {{ t("deactivate") }}
                  </v-btn>
                  <v-btn v-else size="small" variant="outlined" color="success" @click="activate(row)">
                    {{ t("activate") }}
                  </v-btn>
                </div>
              </template>
            </UDropDown>
          </div>
        </td>
      </template>
    </UTable>
  </div>

  <!--  truck modal-->
  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex align-center w-100">
          <span class="text-subtitle-1 font-weight-bold flex-grow-1">
            {{ data.id ? t('edit') : t('add') }} {{ t('truck') }}
          </span>
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="onClose">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </div>
      </template>

      <template #body>
        <UForm id="fleet-truck-form" @submit="onSave">
            <div class="row">
              <div class="col-6 row">
                <div class="col-12 text-subtitle-2 font-weight-bold text-primary mb-3">
                  Unit details
                </div>
                <!--            unit-->
                <div class="col-6">
                  <UInput v-model="data.unit" :label="t('unit')" :hint="t('unit')" :name="t('unit')"
                          :placeholder="t('enter_unit')" classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <!--            unit-->
                <div class="col-6">
                  <UInput v-model="data.carrierResponsibleForSafety" :label="t('carrier_responsible_for_safety')"
                          :hint="t('carrier_responsible_for_safety')" :name="t('carrier_responsible_for_safety')"
                          :placeholder="t('enter_carrier_responsible_for_safety')" classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            country-->
                <div class="col-6">
                  <v-select
                    v-model="data.countryId"
                    :items="truckCountryItems"
                    item-title="title"
                    item-value="value"
                    :label="t('country')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="truckCountryErr"
                    class="mb-2"
                  />
                </div>

                <!--            state-->
                <div class="col-6">
                  <v-select
                    v-model="data.stateId"
                    :items="truckStateItems"
                    item-title="title"
                    item-value="value"
                    :label="t('states')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="truckStateErr"
                    class="mb-2"
                  />
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
                  <v-select
                    v-model="data.modelMakerId"
                    :items="truckModelMakerItems"
                    item-title="title"
                    item-value="value"
                    :label="t('modelMakers')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="truckModelMakerErr"
                    class="mb-2"
                  />
                </div>

                <!--            year-->
                <div class="col-6">
                  <UInput v-model="data.year" :label="t('year')" :hint="t('year')" :name="t('year')"
                          :placeholder="t('enter_year')" classes="mb-2" type="number"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            fuelType-->
                <div class="col-6">
                  <v-select
                    v-model="data.fuelTypeId"
                    :items="truckFuelTypeItems"
                    item-title="title"
                    item-value="value"
                    :label="t('fuelTypes')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="truckFuelTypeErr"
                    class="mb-2"
                  />
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
                <div class="col-12 text-subtitle-2 font-weight-bold text-primary mb-3">
                  Ownership details
                </div>

                <!--            ownershipType-->
                <div class="col-12">
                  <v-select
                    v-model="data.ownershipTypeId"
                    :items="truckOwnershipItems"
                    item-title="title"
                    item-value="value"
                    :label="t('ownershipTypes')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    class="mb-2"
                  />
                </div>
                <!--            includeIFTA — same surface / radius as outlined fields -->
                <div class="col-12">
                  <v-sheet
                    border
                    rounded="lg"
                    color="surface"
                    class="pa-2 mb-2"
                  >
                    <UCheckbox
                      v-model="data.includeIFTA"
                      :label="t('Include To The IFTA')"
                      :name="t('includeIFTA')"
                    />
                  </v-sheet>
                </div>

                <template v-if="data.ownershipTypeId === 2">
                  <!--            ownerOperator-->
                  <div class="col-12">
                    <v-select
                      v-model="data.driverId"
                      :items="truckDriverItems"
                      item-title="title"
                      item-value="value"
                      :label="t('ownerOperators')"
                      variant="outlined"
                      density="compact"
                      hide-details="auto"
                      clearable
                      bg-color="surface"
                      class="mb-2"
                    />
                  </div>
                </template>

                <template v-if="data.ownershipTypeId === 1">
                  <div class="col-12 text-subtitle-2 font-weight-bold text-primary my-3">
                    Other Details
                  </div>

                  <!--            purchaseType-->
                  <div class="col-12">
                    <v-select
                      v-model="data.purchaseTypeId"
                      :items="truckPurchaseTypeItems"
                      item-title="title"
                      item-value="value"
                      :label="t('purchaseTypes')"
                      variant="outlined"
                      density="compact"
                      hide-details="auto"
                      clearable
                      bg-color="surface"
                      class="mb-2"
                    />
                  </div>
                </template>

                <!--            description — section title outside the field (same as Other Details) -->
                <div class="col-12 text-subtitle-2 font-weight-bold text-primary my-3">
                  {{ t('additional_notes') }}
                </div>
                <div class="col-12">
                  <UTextarea
                    v-model="data.description"
                    no-label
                    :placeholder="t('enter_description')"
                    rows="10"
                    classes="mb-2"
                  />
                </div>
              </div>

            </div>
        </UForm>
      </template>
      <template #actions>
        <v-btn type="submit" form="fleet-truck-form" color="info">Save</v-btn>
      </template>
    </UDialog>
  </Teleport>

  <!--  truck card-->
  <Teleport to="body">
    <UDialog :show="showModal && false" width="calc(100vw - 200px)" class="">
      <template #header>
        <div class="d-flex w-100 f-700">
          TR: {{ selectedRow.unit }}
          <div class="text-end u-end">
            <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="showModal = false">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <UScrollArea height="calc(100vh - 200px)">
          <div class="u-scroll-slot-fill">
            <TruckCard :data="selectedRow"/>
          </div>
        </UScrollArea>
      </template>
    </UDialog>
  </Teleport>

  <!--  truck card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(100vw - 500px)" class="">
    <template #header>
      <div class="d-flex w-100 f-700">
        TR: {{ selectedRow.unit }}
        <div class="text-end u-end">
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="showModal = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <div class="u-scroll-slot-fill">
          <TruckCard :data="selectedRow"/>
        </div>
      </UScrollArea>
    </template>
  </URightOverlay>

  <!--  file overlay-->
  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <div class="d-flex align-center justify-space-between pa-2 rounded-lg bg-primary">
        <span class="text-subtitle-2 font-weight-bold text-on-primary">
          {{ DOCUMENT_TYPES[selectedFileSection.data.type] }}
        </span>
        <v-btn icon variant="text" density="comfortable" color="on-primary" aria-label="Close"
          @click="selectedFileSection.dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </div>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.TRUCK}/attach-file`" :data="selectedFileSection.data"
                   :file="selectedFileSection.file"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
/* Permits: ixcham chip’lar, scroll yo‘q — barchasi bir vaqtda (wrap) */
.permits-cell {
  vertical-align: middle;
  min-width: 140px;
  max-width: 320px;
}

.permit-stack {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 4px;
  padding: 4px 5px;
  border-radius: 8px;
  background: rgb(var(--v-theme-surface));
  border: 1px solid rgba(var(--v-theme-on-surface), 0.1);
}

.justify-content-start {
  justify-content: flex-start !important;
}

.tab-panels-container {
  background-color: var(--bs-card-bg);
  border: 2px solid var(--bs-border-color, #e0e0e0);
  /* border-top: 2px solid var(--bs-border-color, #e0e0e0); */
  border-radius: 0 0 0.5rem 0.5rem;
  /* padding: 1rem; */
  margin-top: -2px;
  color: var(--bs-body-color);
  min-height: 400px;
  width: 100%;
  display: block;
  background-color: var(--bs-card-bg);
}

:deep(.tab-pane) {
  width: 100%;
  display: block;
}

:deep(.tab-pane.active) {
  animation: slideDownFade 0.6s ease-out;
}

@keyframes slideDownFade {
  from {
    opacity: 0;
    transform: translateY(-15px) scale(0.98);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.last-col-sticky {
  position: sticky;
  right: 0;
  z-index: 5;
}
</style>
