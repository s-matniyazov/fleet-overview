<script setup>
import {computed, onMounted, ref, watch} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {TABLE_STATUS_FILTER_ITEMS} from "@/constants/tableFilters.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, DRIVER_TYPES, filterString, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import { mapSelectItems } from "@/util/selectItems.js";
import { useFormSelectField } from "@/composables/useFormSelectField.js";
import {useFilterStore} from "@/store/FilterStore.js";
import UDateInput from "@/components/base/UDateInput.vue";
import FileMiniCard from "@/components/FileMiniCard.vue";
import UTooltip from "@/components/base/UTooltip.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import {useStateStore} from "@/store/StateStore.js";
import {useDriverReferenceStore} from "@/store/DriverReferenceStore.js";
import UScrollArea from "@/components/base/UScrollArea.vue";
import DriverCard from "@/components/safety/driver/DriverCard.vue";
import {useDriverFileStore} from "@/store/DriverFileStore.js";
import UDropDown from "@/components/base/UDropDown.vue";

const {t} = useI18n();
const filterStore = useFilterStore();
const driverReferenceStore = useDriverReferenceStore();
const driverFileStore = useDriverFileStore();
const stateStore = useStateStore();

const columns = [
  {
    key: 'name',
    name: 'name',
    label: 'name',
    classes: 'min-width: 200px;',
  },
  {
    key: 'contact',
    name: 'contact',
    label: t('contact'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'cdl',
    name: 'cdl',
    label: t('cdl'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'medical_cert',
    name: 'medical_cert',
    label: t('medical_cert'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'mvr',
    name: 'mvr',
    label: t('mvr'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'clearing_house',
    name: 'clearing_house',
    label: t('clearing_house'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'ssn',
    name: 'ssn',
    label: t('ssn'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'ccf',
    name: 'ccf',
    label: t('ccf'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'drug_test_result',
    name: 'drug_test_result',
    label: t('drug_test_result'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'driver_application',
    name: 'driver_application',
    label: t('driver_application'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'pev',
    name: 'pev',
    label: t('pev'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'inspections',
    name: 'inspections',
    label: t('inspections'),
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
    address: null,
    companyId: filterStore.companyId,
    dateOfBirth: null,
    hireDate: null,
    firstName: null,
    zipCode: null,
    lastName: null,
    middleName: null,
    email: null,
    phone: null,
    stateId: null,
    countryId: null,
    city: null,
    status: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.DRIVER;
const pagination = ref({
  rowsPerPage: 10,
  page: 1,
  hasNext: true
});
const filter = ref({
  driverName: null,
  status: 'ACTIVE', // ACTIVE, PASSIVE
});
const dataList = ref([]);
const data = ref(newModel())
const selectedRow = ref();

const showModal = ref(false);

const selectedFileSection = ref({
  dialog: false,
  data: {
    description: '',
    expirationDate: new Date(),
    type: '',
    driverId: ''
  }
});

const driverCountryItems = computed(() =>
  mapSelectItems(stateStore.countries, "name", "id"),
);
const driverStateItems = computed(() =>
  mapSelectItems(stateStore.getStates(data.value.countryId), "name", "id"),
);
const driverTypeItems = computed(() =>
  mapSelectItems(DRIVER_TYPES, "value", "key"),
);
const driverStatusItems = computed(() =>
  mapSelectItems([{ name: "ACTIVE" }, { name: "PASSIVE" }], "name", "name"),
);

const { errorMessages: driverCountryErr } = useFormSelectField(
  "country",
  () => data.value.countryId,
  (v) => (!v && t("required")),
);
const { errorMessages: driverStateErr } = useFormSelectField(
  "state",
  () => data.value.stateId,
  (v) => (!v && t("required")),
);
const { errorMessages: driverTypeErr } = useFormSelectField(
  "type",
  () => data.value.type,
  (v) => (!v && t("required")),
);
const { errorMessages: driverStatusErr } = useFormSelectField(
  "status",
  () => data.value.status,
  (v) => (!v && t("required")),
);

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    ...d,
    countryId: d?.countryId,
    stateId: d?.stateId,
    companyId: filterStore.companyId
  };

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}

const deactivate = (row) => {
  let reason = prompt("Why are you deactivating this driver?");

  onTerminate({
    driverId: row?.id,
    reason: reason !== "Yes" ? reason : ""
  })
}
const activate = (row) => {
  onTerminate({
    driverId: row?.id,
    reason: ""
  })
}

const selectFileSection = (type) => {
  const file = selectedRow.value?.files.find(it => it.type === type) || {}

  selectedFileSection.value = {
    dialog: true,
    data: {
      ...file,
      driverId: selectedRow.value.id,
      type: type
    }
  };
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
const onTerminate = (terminationData) => {
  axiosIns.post(`${apiUrl}/terminate`, terminationData)
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

// HOOKS
onMounted(() => {
  getData();

  driverReferenceStore.init();
  stateStore.init();
})

watch(
    () => selectedFileSection.value.dialog,
    function (newValue) {
      if (!newValue) getData()
    }
)

watch(
    () => showModal.value,
    function (newValue) {
      if (newValue) driverFileStore.init(selectedRow.value.id)
      else driverFileStore.clear()
    }
)

watch(
    () => showModal.value,
    function (newValue) {
      if (newValue) driverFileStore.init(selectedRow.value.id)
      else {
        driverFileStore.clear();
        getData();
      }
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

          <UInput v-model="filter.driverName" style="min-width: 23vw"
                  :placeholder="t('search_by_driver_name')"/>
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </template>
      <template #row_name="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <span class="text-primary badge badge-pill badge-soft-primary" style="font-size: 15px">
                {{ `${row?.firstName} ${row?.lastName} (${row?.id})` }}
              </span>
              <UTooltip>
                <span class="text-primary" style="font-size: 15px">
                  <i class="mdi mdi-comment"/>
                </span>
                <template #content>
                  <div class="row">
                    <div class="col-12 d-flex align-items-center f-700">
                      {{ `First Name: ${row?.firstName}` }}
                    </div>
                    <div class="col-12 d-flex align-items-center f-700">
                      {{ `Last Name: ${row?.lastName}` }}
                    </div>
                  </div>
                </template>
              </UTooltip>
            </div>
            <div class="col-12 d-flex align-items-center mt-1">
              <img src="@/assets/icons/em/truck.svg" alt="Truck icon">
              <span class="text-gray-light f-700">{{ row?.truckName }}</span>
            </div>
            <div class="col-12 d-flex align-items-center mt-1">
              <span class="text-gray-light f-700">{{ row?.type }}</span>
            </div>
          </div>
        </td>
      </template>

      <template #row_contact="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <span>{{ row?.phone }}</span>
            </div>
            <div class="col-12 d-flex align-items-center">
              <span class="text-gray-light f-700">{{ row?.email }}</span>
            </div>
          </div>
        </td>
      </template>

      <template #row_cdl="{row}">
        <td>
          <FileMiniCard name="CDL (Front)" type="CDL"
                        :file="row?.files.find(it => it.type==='CDL')"
                        @click="(e) => {selectedRow = row; selectFileSection('CDL'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_medical_cert="{row}">
        <td>
          <FileMiniCard name="MED/CERT" type="MEDICAL_CERT"
                        :file="row?.files.find(it => it.type==='MEDICAL_CERT')"
                        @click="(e) => {selectedRow = row; selectFileSection('MEDICAL_CERT'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_mvr="{row}">
        <td>
          <FileMiniCard name="MVR" type="MVR"
                        :file="row?.files.find(it => it.type==='MVR')"
                        @click="(e) => {selectedRow = row; selectFileSection('MVR'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_clearing_house="{row}">
        <td>
          <FileMiniCard name="C/HOUSE" type="CLEARING_HOUSE"
                        :file="row?.files.find(it => it.type==='CLEARING_HOUSE')"
                        @click="(e) => {selectedRow = row; selectFileSection('CLEARING_HOUSE'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_ssn="{row}">
        <td>
          <FileMiniCard name="SSN" type="SSN"
                        :file="row?.files.find(it => it.type==='SSN')"
                        @click="(e) => {selectedRow = row; selectFileSection('SSN'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_ccf="{row}">
        <td>
          <FileMiniCard name="CCF" type="CCF"
                        :file="row?.files.find(it => it.type==='CCF')"
                        @click="(e) => {selectedRow = row; selectFileSection('CCF'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_drug_test_result="{row}">
        <td>
          <FileMiniCard name="DRUG_TEST_RESULT" type="DRUG_TEST_RESULT"
                        :file="row?.files.find(it => it.type==='DRUG_TEST_RESULT')"
                        @click="(e) => {selectedRow = row; selectFileSection('DRUG_TEST_RESULT'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_driver_application="{row}">
        <td>
          <FileMiniCard name="DRIVER_APPL" type="DRIVER_APPLICATION"
                        :file="row?.files.find(it => it.type==='DRIVER_APPLICATION')"
                        @click="(e) => {selectedRow = row; selectFileSection('DRIVER_APPLICATION'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_pev="{row}">
        <td>
          <FileMiniCard name="PEV" type="PEV"
                        :file="row?.files.find(it => it.type==='PEV')"
                        @click="(e) => {selectedRow = row; selectFileSection('PEV'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_inspections>
        <td class="driver-inspections-cell">
          <v-sheet
            border
            rounded="lg"
            color="surface"
            class="pa-2 driver-inspections-placeholder"
          >
            <div class="d-flex align-start ga-2">
              <v-icon size="18" color="secondary" class="flex-shrink-0 mt-px">
                mdi-information-outline
              </v-icon>
              <span class="text-caption text-medium-emphasis">
                {{ t('driver_inspections_empty') }}
              </span>
            </div>
          </v-sheet>
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

  <!--  driver modal-->
  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex align-center w-100">
          <span class="text-subtitle-1 font-weight-bold flex-grow-1">
            {{ data.id ? t('edit') : t('add') }} {{ t('driver') }}
            <span v-if="data.id" class="text-caption text-medium-emphasis ms-1">#{{ data.id }}</span>
          </span>
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="onClose">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </div>
      </template>

      <template #body>
        <UForm id="safety-driver-form" @submit="onSave">
          <div class="row">
            <!-- Personal Details -->
            <div class="col-6">
              <div class="text-subtitle-2 font-weight-bold text-primary mb-3">Personal Details</div>
              <div class="row">
                <div class="col-4">
                  <UInput v-model="data.firstName" :label="t('first_name')" :name="t('first_name')"
                          :placeholder="t('enter_first_name')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-4">
                  <UInput v-model="data.middleName" :label="t('middle_name')" :name="t('middle_name')"
                          :placeholder="t('enter_middle_name')" classes="mb-3"/>
                </div>
                <div class="col-4">
                  <UInput v-model="data.lastName" :label="t('last_name')" :name="t('last_name')"
                          :placeholder="t('enter_last_name')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-6">
                  <UInput v-model="data.phone" :label="t('phone')" :name="t('phone')"
                          :placeholder="t('enter_phone_number')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-6">
                  <UInput v-model="data.email" :label="t('email')" :name="t('email')"
                          :placeholder="t('enter_email')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-6">
                  <UDateInput v-model="data.dateOfBirth" classes="mb-3" :label="t('date_of_birth')"
                              name="dataOfBirth"
                              :rules="(val) => (!val && t('required'))"/>
                </div>
                <div class="col-6">
                  <UDateInput v-model="data.hireDate" classes="mb-3" :label="t('hired_date')"
                              name="hiredDate"
                              :rules="(val) => (!val && t('required'))"/>
                </div>
              </div>
            </div>

            <!-- Address & Classification -->
            <div class="col-6">
              <div class="text-subtitle-2 font-weight-bold text-primary mb-3">Current Address</div>
              <div class="row">
                <div class="col-6">
                  <v-select
                    v-model="data.countryId"
                    :items="driverCountryItems"
                    item-title="title"
                    item-value="value"
                    :label="t('country')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="driverCountryErr"
                    class="mb-3"
                  />
                </div>
                <div class="col-6">
                  <v-select
                    v-model="data.stateId"
                    :items="driverStateItems"
                    item-title="title"
                    item-value="value"
                    :label="t('state')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="driverStateErr"
                    class="mb-3"
                  />
                </div>
                <div class="col-4">
                  <UInput v-model="data.address" :label="t('address')" :name="t('address')"
                          :placeholder="t('enter_address')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-4">
                  <UInput v-model="data.city" :label="t('city')" :name="t('city')"
                          :placeholder="t('enter_city')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-4">
                  <UInput v-model="data.zipCode" :label="t('zip_code')" :name="t('zip_code')"
                          :placeholder="t('enter_zip_code')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-6">
                  <v-select
                    v-model="data.type"
                    :items="driverTypeItems"
                    item-title="title"
                    item-value="value"
                    :label="t('type')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="driverTypeErr"
                    class="mb-3"
                  />
                </div>
                <div class="col-6">
                  <v-select
                    v-model="data.status"
                    :items="driverStatusItems"
                    item-title="title"
                    item-value="value"
                    :label="t('status')"
                    variant="outlined"
                    density="compact"
                    hide-details="auto"
                    clearable
                    bg-color="surface"
                    :error-messages="driverStatusErr"
                    class="mb-3"
                  />
                </div>
              </div>
            </div>
          </div>
        </UForm>
      </template>
      <template #actions>
        <v-btn type="submit" form="safety-driver-form" color="info">Save</v-btn>
      </template>
    </modal>
  </Teleport>

  <!--  driver card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(100vw - 500px)">
    <template #header>
      <div class="d-flex w-100">
        <div class="row">
          <div class="col-12 d-flex align-items-center">
            <span class="text-gray-light f-700 font-size-15">
                {{ `${selectedRow?.firstName} ${selectedRow?.lastName}` }}
              </span>
          </div>
          <div class="col-12 d-flex align-items-center">
            <span class="text-gray-light font-size-11">
                {{ `Driver ID: ${selectedRow?.id}` }}
            </span>
          </div>
        </div>
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
          <DriverCard :data="selectedRow"/>
        </div>
      </UScrollArea>
    </template>
  </URightOverlay>

  <!--  file overlay-->
  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <div class="d-flex align-center justify-space-between pa-2 rounded-lg bg-primary mb-2">
        <div class="d-flex flex-column">
          <span class="text-subtitle-2 font-weight-bold text-on-primary">
            {{ DOCUMENT_TYPES[selectedFileSection.data.type] }}
          </span>
          <span class="text-caption text-on-primary opacity-80">
            {{ `${selectedRow?.firstName} ${selectedRow?.lastName}` }}
          </span>
        </div>
        <v-btn icon variant="text" density="comfortable" color="on-primary" aria-label="Close"
          @click="selectedFileSection.dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </div>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.DRIVER}/attach-file`" :data="selectedFileSection.data"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
.justify-content-start {
  justify-content: flex-start !important;
}

.driver-inspections-cell {
  vertical-align: top;
  max-width: 280px;
}

.driver-inspections-placeholder {
  line-height: 1.45;
}

.mt-px {
  margin-top: 1px;
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
