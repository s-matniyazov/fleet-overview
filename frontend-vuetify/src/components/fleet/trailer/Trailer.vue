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
import UTextarea from "@/components/base/UTextarea.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
import UDialog from "@/components/base/UDialog.vue";
import UTooltip from "@/components/base/UTooltip.vue";
import FileMiniCard from "@/components/FileMiniCard.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import TrailerCard from "@/components/fleet/trailer/TrailerCard.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import {useTrailerReferenceStore} from "@/store/TrailerReferencesStore.js";
import FileOverlay from "@/components/FileOverlay.vue";
import {useTrailerFileStore} from "@/store/TrailerFileStore.js";
import PermitMiniCard from "@/components/PermitMiniCard.vue";
import UDropDown from "@/components/base/UDropDown.vue";

const {t} = useI18n();
const filterStore = useFilterStore();
const trailerFileStore = useTrailerFileStore();
const trailerReferenceStore = useTrailerReferenceStore();

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
    licensePlate: null,
    inServiceDate: null,
    modelMakerId: null,
    typeId: null,
    year: null,
    axles: null,
    length: null,
    vin: null,
    ownershipTypeId: null,
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
    trailerId: ''
  }
});

const apiUrl = URIS.TRAILER;
const pagination = ref({
  rowsPerPage: 5,
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

const trailerModelMakerItems = computed(() =>
  mapSelectItems(trailerReferenceStore.makers, "name", "id"),
);
const trailerTypeItems = computed(() =>
  mapSelectItems(trailerReferenceStore.types, "name", "id"),
);
const trailerOwnershipItems = computed(() =>
  mapSelectItems(trailerReferenceStore.ownershipTypes, "name", "id"),
);
const trailerDriverItems = computed(() =>
  mapSelectItems(
    drivers.value,
    (d) => [d.firstName, d.lastName].filter(Boolean).join(" "),
    "id",
  ),
);
const trailerPurchaseTypeItems = computed(() =>
  mapSelectItems(trailerReferenceStore.purchaseTypes, "name", "id"),
);

const { errorMessages: trailerModelMakerErr } = useFormSelectField(
  "modelMaker",
  () => data.value.modelMakerId,
  (v) => (!v && t("required")),
);
const { errorMessages: trailerTypeErr } = useFormSelectField(
  "type",
  () => data.value.typeId,
  (v) => (!v && t("required")),
);
const { errorMessages: trailerOwnershipErr } = useFormSelectField(
  "ownershipType",
  () => data.value.ownershipTypeId,
  (v) => (!v && t("required")),
);
const { errorMessages: trailerDriverErr } = useFormSelectField(
  "driver",
  () => data.value.driverId,
  (v) => [2].includes(data.value.ownershipTypeId) && !v && t("required"),
);
const { errorMessages: trailerPurchaseErr } = useFormSelectField(
  "purchaseType",
  () => data.value.purchaseTypeId,
  (v) => [1, 3].includes(data.value.ownershipTypeId) && !v && t("required"),
);

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    id: d.id,
    unit: d.unit,
    licensePlate: d.licensePlate,
    inServiceDate: d.inServiceDate,
    modelMakerId: parseOptionalId(d?.modelMakerId),
    typeId: parseOptionalId(d?.typeId),
    year: d.year,
    axles: d.axles,
    length: d.length,
    height: d.height,
    vin: d.vin,
    ownershipTypeId: parseOptionalId(d?.ownershipTypeId),
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
      trailerId: selectedRow.value.id,
      type: type
    }
  };
}
const getOwnership = (row) => {
  if (row?.ownershipTypeId === 1) {
    return row?.purchaseTypeName
  } else if (row?.ownershipTypeId === 2) {
    return row?.driver?.firstName
  } else return "N/A";
}

const reload = () => {
  getData();
  trailerReferenceStore.init();
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
const deactivate = (row) => {
  onDeactivate(row);
}
const activate = (row) => {
  onActivate(row);
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
  trailerReferenceStore.init();
  getDrivers();
})

watch(
    () => data.value.ownershipTypeId,
    function (newValue) {
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
      if (newValue) trailerFileStore.init(selectedRow.value.id)
      else trailerFileStore.clear()
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

  <!--  trailer modal-->
  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex w-100">
          <div class="text-white" style="font-weight: 1000; font-size: 16px">
            {{ data.id ? t('edit') : t('add') }} {{ t('trailer') }}
          </div>
          <div class="text-end u-end">
              <v-btn icon variant="text" color="white" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <UForm id="fleet-trailer-form" @submit="onSave">
            <div class="row">
              <div class="col-6 row">
                <div class="col-12 text-white mb-3" style="font-weight: 1000; font-size: 16px">
                  Unit details
                </div>
                <!--            unit-->
                <div class="col-12">
                  <UInput v-model="data.unit" :label="t('unit')" :hint="t('unit')" :name="t('unit')"
                          :placeholder="t('enter_unit')" classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            licensePlate-->
                <div class="col-6">
                  <UInput v-model="data.licensePlate" :label="t('licensePlate')" :hint="t('licensePlate')"
                          :name="t('licensePlate')"
                          :placeholder="t('enter_licensePlate')" classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            inServiceDate-->
                <div class="col-6">
                  <UDateInput v-model="data.inServiceDate" classes="mb-2" :label="t('inServiceDate')"
                              name="inServiceDate"
                              :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            modelMaker-->
                <div class="col-6">
                  <v-select
                    v-model="data.modelMakerId"
                    :items="trailerModelMakerItems"
                    item-title="title"
                    item-value="value"
                    :label="t('modelMakers')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="trailerModelMakerErr"
                    class="mb-2"
                  />
                </div>

                <!--            type-->
                <div class="col-6">
                  <v-select
                    v-model="data.typeId"
                    :items="trailerTypeItems"
                    item-title="title"
                    item-value="value"
                    :label="t('types')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="trailerTypeErr"
                    class="mb-2"
                  />
                </div>

                <!--            year-->
                <div class="col-6">
                  <UInput v-model="data.year" :label="t('year')" :hint="t('year')" :name="t('year')"
                          :placeholder="t('enter_year')" classes="mb-2" type="number"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            axles-->
                <div class="col-6">
                  <UInput v-model="data.axles" :label="t('axles')" :hint="t('axles')" :name="t('axles')"
                          :placeholder="t('enter_axles')" classes="mb-2" type="number"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            length-->
                <div class="col-6">
                  <UInput v-model="data.length" :label="t('length')" :hint="t('length')"
                          :name="t('length')"
                          :placeholder="t('enter_length')" classes="mb-2" type="number"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <!--            height-->
                <div class="col-6">
                  <UInput v-model="data.height" :label="t('height')" :hint="t('height')"
                          :name="t('height')"
                          :placeholder="t('enter_height')" classes="mb-2" type="number"
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
                <div class="col-12 text-white mb-3" style="font-weight: 1000; font-size: 16px">
                  Ownership details
                </div>

                <!--            ownershipType-->
                <div class="col-12">
                  <v-select
                    v-model="data.ownershipTypeId"
                    :items="trailerOwnershipItems"
                    item-title="title"
                    item-value="value"
                    :label="t('ownershipTypes')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="trailerOwnershipErr"
                    class="mb-2"
                  />
                </div>

                <template v-if="[2].includes(data.ownershipTypeId)">
                  <!--            ownerOperator-->
                  <div class="col-12">
                    <v-select
                      v-model="data.driverId"
                      :items="trailerDriverItems"
                      item-title="title"
                      item-value="value"
                      :label="t('ownerOperators')"
                      variant="outlined"
                      density="compact"
                      hide-details="auto"
                      clearable
                      bg-color="surface"
                      :error-messages="trailerDriverErr"
                      class="mb-2"
                    />
                  </div>
                </template>

                <template v-if="[1, 3].includes(data.ownershipTypeId)">
                  <div class="col-12 text-white my-3" style="font-weight: 1000; font-size: 16px">
                    Other Details
                  </div>

                  <!--            purchaseType-->
                  <div class="col-12">
                    <v-select
                      v-model="data.purchaseTypeId"
                      :items="trailerPurchaseTypeItems"
                      item-title="title"
                      item-value="value"
                      :label="t('purchaseTypes')"
                      variant="outlined"
                      density="compact"
                      hide-details="auto"
                      clearable
                      bg-color="surface"
                      :error-messages="trailerPurchaseErr"
                      class="mb-2"
                    />
                  </div>
                </template>

                <!--            description — section title outside the field (same as Other Details) -->
                <div class="col-12 text-white my-3" style="font-weight: 1000; font-size: 16px">
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
        <v-btn type="submit" form="fleet-trailer-form" color="info">Save</v-btn>
      </template>
    </UDialog>
  </Teleport>

  <!--  trailer card-->
  <Teleport to="body">
    <UDialog :show="showModal && false" width="calc(100vw - 200px)" class="">
      <template #header>
        <div class="d-flex w-100">
          Trailer Card
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
            <TrailerCard :data="selectedRow"/>
          </div>
        </UScrollArea>
      </template>
    </UDialog>
  </Teleport>

  <!--  trailer card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(100vw - 500px)" class="">
    <template #header>
      <div class="d-flex w-100">
        Trailer Card
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
          <TrailerCard :data="selectedRow"/>
        </div>
      </UScrollArea>
    </template>
  </URightOverlay>

  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <h4 class="fw-bold text-white bg-primary p-2 rounded-2 d-flex">{{
          DOCUMENT_TYPES[selectedFileSection.data.type]
        }}
        <span class="text-end u-end">
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="selectedFileSection.dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </span>
      </h4>
    </template>
    <template #body>
      <FileOverlay :data="selectedFileSection.data" :url="`${URIS.TRAILER}/attach-file`"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
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
