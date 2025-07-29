<script setup>
import {onMounted, ref, watch} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, DRIVER_TYPES, filterString, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
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
  rowsPerPage: 5,
  page: 1,
  hasNext: true
});
const filter = ref({
  driverName: null
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

// FUNCTIONS
const paging = (a) => {
  if (a === 'p' && pagination.value.page > 1) pagination.value.page --;
  if (a === 'n' && pagination.value.hasNext) pagination.value.page ++;
}
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
const terminate = (row) => {
  let reason = prompt("Are you sure to deactivate this truck?");

  if (reason)
    onTerminate({
      driverId: row?.id,
      reason: reason
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
        selectedRow.value.terminationDate = new Date();
        selectedRow.value.terminationReason = terminationData.reason;

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
    <div class="col-12">
      <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
        <button @click="onAdd" class="btn btn-primary btn-sm"><span class="mdi mdi-plus"></span> {{
            t("add")
          }}
        </button>
        <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span
            class="mdi mdi-pen"></span> {{ t("edit") }}
        </button>
        <button @click="showModal = true" class="btn btn-primary btn-sm" :disabled="!selectedRow">
          <span class="mdi mdi-eye"></span>
        </button>

        <UInput v-model="filter.driverName" style="min-width: 23vw"
                :hint="t('vin')" :placeholder="t('search_by_driver_name')"/>
        <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-magnify"></span></button>

        <div class="align-items-center u-end">
          <ul class="pagination pagination-sm ul-style">
            <select v-model="pagination.rowsPerPage" class="form-select form-select-sm mb-0 my-n1">
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="20">20</option>
            </select>

            <li class="page-item cursor-pointer"><a class="page-link" @click="paging('p')" :disabled="pagination.page <= 1">&laquo;</a></li>
            <li class="page-item active cursor-not-allowed"><a class="page-link">{{ pagination.page }}</a></li>
            <li class="page-item cursor-pointer"><a class="page-link" @click="paging('n')" :disabled="!pagination.hasNext">&raquo;</a></li>
            <!--            <li class="page-item cursor-pointer">-->
            <!--              <button @click="reload" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>-->
            <!--            </li>-->
          </ul>
        </div>
      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 248px)">
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

      <template #row_inspections="{row}">
        <td>
          <div class="qm-badge qm-badge--dim justify-content-start width-135px min-height-64">
            <div class="m-auto text-center font-size-12 text text-dark-gray p-2">
              No Inspections in the past 3 years
            </div>
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

  <!--  driver modal-->
  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-white">
            {{ data.id ? t('edit') : t('add') }} {{ t('driver') }} : ({{ data.id }})
          </div>
          <div class="text-end u-end">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row p-4">
            <!--            company-->
            <div class="col-6 pe-5">
              <div class="col-12 text-white mb-3" style="font-weight: 1000; font-size: 16px">
                Personal Details
              </div>
              <!--            name-->
              <div class="col-12 row">
                <div class="col-4">
                  <UInput v-model="data.firstName" :label="t('first_name')" :hint="t('first_name')"
                          :name="t('first_name')"
                          :placeholder="t('enter_first_name')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <!--           middle name-->
                <div class="col-4">
                  <UInput v-model="data.middleName" :label="t('middle_name')" :hint="t('middle_name')"
                          :name="t('middle_name')"
                          :placeholder="t('enter_middle_name')" classes="mb-3"/>
                </div>
                <!--           last name-->
                <div class="col-4">
                  <UInput v-model="data.lastName" :label="t('last_name')" :hint="t('last_name')" :name="t('last_name')"
                          :placeholder="t('enter_last_name')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <!--           phone number-->
                <div class="col-6">
                  <UInput v-model="data.phone" :label="t('phone')" :hint="t('phone')" :name="t('phone')"
                          :placeholder="t('enter_phone_number')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-6">
                  <UInput v-model="data.email" :label="t('email')" :hint="t('email')" :name="t('email')"
                          :placeholder="t('enter_email')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <div class="col-3">
                  <UDateInput v-model="data.dateOfBirth" classes="mb-2" :label="t('date_of_birth')"
                              name="dataOfBirth"
                              :rules="(val) => (!val && t('required'))"/>
                </div>
                <div class="col-3">
                  <UDateInput v-model="data.hireDate" classes="mb-2" :label="t('hired_date')"
                              name="hiredDate"
                              :rules="(val) => (!val && t('required'))"/>
                </div>
              </div>
            </div>
            <div class="col-6">
              <div class="col-12 text-white mb-3" style="font-weight: 1000; font-size: 16px">
                Current Address
              </div>
              <div class="col-12 row">
                <div class="col-6">
                  <USelect v-model="data.countryId" :label="t('country')"
                           :items="stateStore.countries" name="country"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></Uselect>
                </div>
                <div class="col-6">
                  <USelect v-model="data.stateId" :label="t('state')"
                           :items="stateStore.getStates(data.countryId)" name="state"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></Uselect>
                </div>
              </div>

              <!--            address-->
              <div class="col-12 row">
                <div class="col-4">
                  <UInput v-model="data.address" :label="t('address')" :hint="t('address')" :name="t('address')"
                          :placeholder="t('enter_address')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <!--           city name-->
                <div class="col-4">
                  <UInput v-model="data.city" :label="t('city')" :hint="t('city')" :name="t('city')"
                          :placeholder="t('enter_city')" classes="mb-3"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>
                <!--           zipcode-->
                <div class="col-4">
                  <UInput v-model="data.zipCode" :label="t('zip_code')" :hint="t('zip_code')" :name="t('zip_code')"
                          :placeholder="t('enter_zip_code')" classes="mb-3" type="number"
                          :rules="(val) => (!val && $t('required'))"/>
                </div>

                <div class="col-3">
                  <USelect v-model="data.type" :label="t('type')"
                           :items="DRIVER_TYPES" name="type"
                           option_name="value"
                           option_value="key"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>

                <div class="col-3">
                  <USelect v-model="data.status" :label="t('status')"
                           :items="[{name:'ACTIVE'},{name:'PASSIVE'}]" name="status"
                           option_name="name"
                           option_value="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>

              </div>
            </div>
          </div>

          <div class="modal-footer">
            <div class="d-flex text-end align-items-end mt-2">
              <button type="submit" style="background-color:#0891B2;" class="btn text-white">Save</button>
            </div>
          </div>
        </UForm>
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
          <button v-if="!selectedRow.terminationDate" @click="terminate(selectedRow)"
                  class="btn btn-outline-danger btn-sm mx-3">
            {{ t("terminate") }}
          </button>
          <button class="btn-close" @click="showModal = false"></button>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <DriverCard :data="selectedRow"/>
      </UScrollArea>
    </template>
  </URightOverlay>

  <!--  file overlay-->
  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <div class="fw-bold text-white bg-primary p-2 rounded-2 d-flex p-2 mb-2">
        <div class="row">
          <div class="col-12 d-flex align-items-center font-size-14">
            {{ DOCUMENT_TYPES[selectedFileSection.data.type] }}
          </div>
          <div class="col-12 d-flex align-items-center font-size-11">
            {{ `${selectedRow?.firstName} ${selectedRow?.lastName}` }}
          </div>
        </div>
        <span class="text-end u-end">
          <button class="btn-close" @click="selectedFileSection.dialog = false"></button>
        </span>
      </div>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.DRIVER}/attach-file`" :data="selectedFileSection.data"/>
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

.width-135px {
  width: 135px;
}

.min-height-64 {
  min-height: 64%;
}
</style>
