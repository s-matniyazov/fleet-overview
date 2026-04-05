<script setup>
import {onMounted, ref, watch} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import FileMiniCard from "@/components/FileMiniCard.vue";

import URightOverlay from "@/components/base/URightOverlay.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import InspectionCard from "@/components/safety/inspection/InspectionCard.vue";

import {useStateStore} from "@/store/StateStore.js";
import {useInspectionStore} from "@/store/InspectionFileStore.js";
import UDropDown from "@/components/base/UDropDown.vue";
import USelect from "@/components/base/USelect.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import UDialog from "@/components/base/UDialog.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
import {stringToDateFormatter} from "@/util/utils.js";


const {t} = useI18n();
const filterStore = useFilterStore();
const inspectionStore = useInspectionStore();
const stateStore = useStateStore();

const columns = [
  {
    key: 'inspection',
    name: 'inspection',
    label: t('inspection'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'driver',
    name: 'driver',
    label: t('driver'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'correction',
    name: 'correction',
    label: t('correction'),
    styles: 'min-width: 140px;',
    classes: '',
  },
  {
    key: 'certification',
    name: 'certification',
    label: t('certification'),
    styles: 'min-width: 140px;',
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
    label:t('actions'),
    styles: '',
    classes: 'last-col-sticky',
  },
]
const violations = ref([{ type: '', sectionViolCode: '', description: ''}]);

const newModel = () => {
  return {
    id: null,
    inspectionNumber:null,
    location:null,
    violationDiscovered:false,
    companyId: filterStore.companyId,
    inspectionDate: null,
    driverId:null,
    coDriverId:null,
    stateId: null,
    countryId: null,
    city: null,
    description: null,
    inspectionLevel:null,
    // totalFineAmount:null,
    violationTypes:[]
  }
}

const addModal = ref(false);
const showViolation = ref(false);
const trucks = ref([]);
const drivers = ref([]);
const inspectionLevels = ref([]);

const apiUrl = URIS.INSPECTION;
const pagination = ref({
  size: 10,
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
    inspectionDate: new Date(),
    type: '',
    inspectionId: ''
  }
});

// FUNCTIONS
const paging = (a) => {
  if (a === 'p' && pagination.value.page > 1) pagination.value.page--;
  if (a === 'n' && pagination.value.hasNext) pagination.value.page++;
}
const onAdd = () => {
  data.value = newModel();
  addModal.value = true;
}
const onEdit = (d) => {

  axiosIns.get(`${apiUrl+'/'+URIS.VIOLATION_TYPES}?inspectionId=${d.id}`)
      .then(res => {
        data.value.violationTypes = res.data.data;

        violations.value = res.data.data.map(v => ({
          id: v.id,
          type: v.type,
          sectionViolCode: v.sectionViolCode,
          description: v.description
        }));

      }).catch(e => {showMessage(e)});
  data.value = {
    ...d,
    companyId: filterStore.companyId,
    violationTypes: data.value.violationTypes

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
      inspectionId: selectedRow.value.id,
      type: type
    }
  };
}
function addNewViolation() {
  violations.value.push({ type: null, sectionViolCode: null, description: null});
}


// API FUNCTIONS
const onSave = () => {

  // prepare violationTypes payload
  data.value.violationTypes = violations.value
    .filter(v => v.type || v.sectionViolCode || v.description)
    .map(v => ({
      type: v.type,
      sectionViolCode: v.sectionViolCode,
      description: v.description
    }));

  axiosIns.post(apiUrl, data.value)
      .then(() => {
        getData();
        onClose();
      }).catch(e => {
    showMessage(e)
  });
  
}

function getData() {
  axiosIns.get(`${apiUrl}${filterString({companyId: filterStore.companyId, ...filter.value, ...pagination.value})}`)
      .then(res => {
        dataList.value = res.data.content;
        selectedRow.value = null;
        pagination.value.hasNext = dataList.value.length >= pagination.value.size;
      }).catch(e => {
    showMessage(e)
  });
}
function getTrucks (){
  axiosIns.get(`${URIS.TRUCK}?companyId=${filterStore.companyId}`)
      .then(res => {
          trucks.value = res.data.data;
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
function getInspectionLevels(){
  axiosIns.get(URIS.INSPECTION_LEVELS)
      .then(res => {
          inspectionLevels.value = res.data.data;
      }).catch(e => {
      showMessage(e)
  });
}


const updateStatus = (inspectionId) => {
  axiosIns.post(`${apiUrl}/update-status?inspectionId=${inspectionId}`)
      .then(() => {
        getData();
        showModal.value = false;
      }).catch(e => {
    showMessage(e)
  });
}


// HOOKS
onMounted(() => {
  getData();
  stateStore.init();
  getTrucks();
  getDrivers();
  getInspectionLevels();
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
      if (newValue) inspectionStore.init(selectedRow.value.id)
      else inspectionStore.clear()
    }
)



watch(
    () => pagination.value.page,
    function (newValue) {
      getData();
    }
)
watch(
    () => pagination.value.size,
    function (newValue) {
      getData();
    }
)
watch(() => data.value.violationDiscovered, (val) => {
  showViolation.value = val === true;
  if (!showViolation.value){
    violations.value = [{}];
  }
});

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
          <span class="mdi mdi-eye"> View</span>
        </button>

        <UInput v-model="filter.driverName" style="min-width: 23vw"
                :hint="t('driver')" :placeholder="t('search_by_driver_name')"/>
        <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-magnify"></span></button>

        <div class="align-items-center u-end">
          <ul class="pagination pagination-sm ul-style">
            <select v-model="pagination.size" class="form-select form-select-sm mb-0 my-n1">
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="20">20</option>
            </select>

            <li class="page-item cursor-pointer"><a class="page-link" @click="paging('p')"
                                                    :disabled="pagination.page <= 1">&laquo;</a></li>
            <li class="page-item active cursor-not-allowed"><a class="page-link">{{ pagination.page }}</a></li>
            <li class="page-item cursor-pointer"><a class="page-link" @click="paging('n')"
                                                    :disabled="!pagination.hasNext">&raquo;</a></li>
          </ul>
        </div>
      </div>
      <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">

        <button @click="() => {filter.status = filter.status !== 'ACTIVE' ? 'ACTIVE' : null; getData()}"
                :class="['tab-button', { 'tab-active': filter.status === 'ACTIVE' }]">
          Active
        </button>
        <button @click="() => {filter.status = filter.status !== 'PASSIVE' ? 'PASSIVE' : null; getData()}"
                :class="['tab-button', { 'tab-active': filter.status === 'PASSIVE' }]">
          Passive
        </button>

      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 290px)">
      <template #row_inspection="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex flex-column align-items-left">
              <div class="d-flex align-items-left">
                <span class="text-primary badge badge-pill badge-soft-primary" style="font-size: 15px; color: hsl(198.99deg 86.03% 47.64%);">
                  {{ `${row?.inspectionNumber}` }}
                </span>
            </div>
            <div>  
              <span class="text-secondary badge badge-pill" style="font-size: 15px">
                {{ `Date: ${stringToDateFormatter(row?.inspectionDate)}` }}
              </span>
            </div>
              
            </div>
          </div>
        </td>
      </template>

      <template #row_driver="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <span class="text-primary badge badge-pill" style="font-size: 15px">
                {{ `${row?.driverFirstName || ''} ${row?.driverLastName || ''}` }}
              </span>
            </div>
            <div class="col-12 d-flex align-items-center mt-1">
              <img src="@/assets/icons/em/truck.svg" alt="Truck icon">
              <span class="text-gray-light f-700 ms-1">
                {{ `${row?.driverFirstName || ''} ${row?.driverLastName || ''}` }}
                {{ `Unit: ${row?.unitNumber || ''}` }}
              </span>
            </div>
          </div>
        </td>
      </template>

      <template #row_correction="{row}">
        <td>
          <FileMiniCard name="CORRECTION" type="CORRECTION"
                        :file="row?.files?.find(it => it.type==='CORRECTION')"
                        @click="(e) => {selectedRow = row; selectFileSection('CORRECTION'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_certification="{row}">
        <td>
          <FileMiniCard name="CERTIFICATION" type="CERTIFICATION"
                        :file="row?.files?.find(it => it.type==='CERTIFICATION')"
                        @click="(e) => {selectedRow = row; selectFileSection('CERTIFICATION'); e.stopPropagation()}"/>
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
        <td class="last-col-sticky text-end" style="width: 30px; white-space: nowrap;">
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
                  <button v-if="row.status === 'ACTIVE'" @click="updateStatus(row.id)"
                          class="btn btn-outline-danger btn-sm">
                    {{ t("deactivate") }}
                  </button>
                  <button v-else @click="updateStatus(row.id)" class="btn btn-outline-success btn-sm">
                    {{ t("activate") }}
                  </button>
                </div>
              </template>
            </UDropDown>
          </div>
        </td>
      </template>
    </UTable>
  </div>


  <!--  inspection modal-->
  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 700px)">
      <template #header>
        <div style="width: 100%">
          <div class="d-flex justify-content-between align-items-center">
            <div class="text-white">
              {{ data.id ? data.inspection : t('add') }} {{ t('inspection') }}
            </div>

            <button class="btn-close btn-close-white" @click="onClose"></button>
          </div>
          <hr class="my-3 border-primary opacity-75">
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row g-2"> <!-- g-3 adds uniform gutter spacing between all columns -->
            <!-- --- Inspection Section --- -->
            <div class="col-12">
              <div class="row g-4">
                <div class="col-6">
                  <UInput
                    v-model="data.inspectionNumber"
                    icon="mdi-number"
                    :label="t('inspectionNumber')"
                    :hint="t('enter_inspectionNumber')"
                    :name="t('inspectionNumber')"
                    :placeholder="t('inspectionNumber')"
                    :rules="(val) => (!val && $t('required'))"
                  />
                </div>
                <div class="col-6">
                  <UInput
                    v-model="data.inspectionDate"
                    :label="t('inspectionDate')"
                    :hint="t('inspectionDate')"
                    :name="t('inspectionDate')"
                    type="date"
                    :rules="(val) => (!val && $t('required'))"
                  />
                </div>
              </div>
              <hr class="my-4 border-primary opacity-75"> <!-- adds clear separation -->
            </div>

            <!-- --- Driver Section --- -->
            <div class="col-12">
              <div class="row g-4">
                <div class="col-6">
                  <USelect
                    v-model="data.driverId" :label="t('driver')" icon="mdi-account"
                    :items="drivers"
                    name="driver"
                    :option_name="(d) => d.firstName + ' ' + d.lastName"
                  />
                </div>
                <div class="col-6">
                  <USelect
                    v-model="data.coDriverId" :label="t('co_driver')" icon="mdi-account-multiple"
                    :items="drivers"
                    name="co_driver"
                    :option_name="(d) => d.firstName + ' ' + d.lastName"
                  />
                </div>
              </div>
              <hr class="my-4 border-primary opacity-75"> <!-- adds clear separation -->
            </div>
            
            <!-- --- violation discovered --- -->
            <div class="col-12">
              <div class="row g-4">
                <div class="col-6">
                  <div class="row">
                    <div class="col-6 mb-2">
                      <USelect
                        v-model="data.truckId" :label="t('truck')" icon="mdi-truck-check"
                        :items="trucks"
                        name="truck"
                        option_name="unit"
                      />
                    </div>
                    <div class="col mb-2">
                      <USelect
                        v-model="data.inspectionLevel" :label="t('inspection_level')"
                        :items="inspectionLevels"
                        name="level"
                        option_name="level"
                        :rules="(val) => (!val && $t('required'))"
                      />
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-6">
                      <UInput
                        v-model="data.city"
                        icon="mdi-map-marker-radius"
                        :label="t('city')"
                        :hint="t('city')"
                        :name="t('city')"
                        :placeholder="t('city')"
                        :rules="(val) => (!val && $t('required'))"
                      />
                    </div>
                    <div class="col-6">
                      <USelect
                        v-model="data.stateId" :label="t('states')" icon="mdi-flag-variant"
                        :items="stateStore.getStates(1)" name="state"
                        option_name="name"
                        :rules="(val) => (!val && $t('required'))"
                        ></USelect>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col">
                      <UInput
                        v-model="data.violationDiscovered"
                        type="radio"
                        :label="t('violation_discovered')"
                        :value="true"
                        id="violation_yes"
                        name="violation_discovered"
                      />
                    </div>

                    <div class="col">
                      <UInput
                        v-model="data.violationDiscovered"
                        type="radio"
                        :label="t('no_violation_discovered')"
                        :value="false"
                        id="violation_no"
                        name="violation_discovered"
                      />
                    </div>  
                  </div>
                </div>

                <div class="col-6" v-if="showViolation">
                  <div v-for="(item, index) in violations" :key="index" class="pb-3">
                    <div class="row border border-primary pb-2" >
                        <div class="col">
                            <USelect
                              v-model="item.type" :label="t('violation_type')"
                              :items="[{'name':'violation'},{'name':'citation'}]"
                              name="type"
                              option_name="name"
                              option_value="name"
                              :rules="(val) => (!val && $t('required'))"
                            />
                          </div>
                        <div class="col">
                            <UInput
                              v-model="item.sectionViolCode"
                              :label="t('section_viol_code')"
                              :hint="t('section_viol_code')"
                              name="sectionViolCode"
                              :placeholder="t('enter_viol_code')"
                              :rules="(val) => (!val && $t('required'))"
                            />
                        </div>
                        <div class="col-12">
                          <UTextarea v-model="item.description" :label="t('description')" 
                            :placeholder="t('enter_description')"
                            rows="1" 
                          />
                        </div>
                    </div>
                  </div>
                  <div class="row form-check form-switch">
                    <div class="col-12 mt-3">
                      <input class="form-check-input" type="checkbox" v-model="data.outOfService" id="outOfService">
                      <label class="form-check-label" for="outOfService">{{ t('out_of_service') }}</label>
                    </div> 
                  </div>
                  <div class="row">
                    <div class="col-12 mt-3">
                      <button @click="addNewViolation" class="btn btn-outline-info fw-bold mdi-add">New Type</button>
                    </div>
                  </div>
                  
                </div>
              </div>
            </div>
          </div>

          <!-- footer -->
          <div class="modal-footer d-flex justify-content-between align-items-center border-top border-primary pt-3 mt-3">
              <button @click="onClose" class="btn text-white btn-secondary">Cancel</button>
              <button type="submit" class="btn btn-info fw-bold">Save</button>
          </div>
        </UForm>
      </template>
    </UDialog>
  </Teleport>

  <!--  inspection card-->
  <Teleport to="body">
    <UDialog :show="showModal && false" width="calc(100vw - 200px)" class="">
      <template #header>
        <div class="d-flex w-100 f-700">
          Inspection: {{ selectedRow.inspectionNumber }}
          <div class="text-end u-end">
            <button class="btn-close" @click="showModal = false"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UScrollArea height="calc(100vh - 200px)">
          <InspectionCard :data="selectedRow"/>
        </UScrollArea>
      </template>
    </UDialog>
  </Teleport>
  

  <!--  inspection card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(100vw - 500px)" class="">
    <template #header>
      <div class="d-flex w-100 f-700 fs-4">
        Inspection: {{ selectedRow.inspectionNumber }}
        <div class="text-end u-end">
          <button v-if="selectedRow.status === 'ACTIVE'" @click="updateStatus(selectedRow.id)"
                  class="btn btn-outline-danger btn-sm mx-3">
            {{ t("deactivate") }}
          </button>
          <button v-else @click="updateStatus(selectedRow.id)" class="btn btn-outline-success btn-sm mx-3">
            {{ t("activate") }}
          </button>
          <button class="btn-close" @click="showModal = false"></button>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <InspectionCard :data="selectedRow"/>
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
            {{ `${selectedRow?.inspectionNumber} ${selectedRow?.driverFirstName} ${selectedRow?.driverLastName}` }}
          </div>
        </div>
        <span class="text-end u-end">
          <button class="btn-close" @click="selectedFileSection.dialog = false"></button>
        </span>
      </div>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.INSPECTION}/attach-file`" :data="selectedFileSection.data"/>
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
.tab-button {
  padding: 0.6rem 1rem calc(0.6rem + 2px);
  cursor: pointer;
  font-size: 0.875rem;
  font-weight: 400;
  color: var(--bs-secondary-color);
  background: transparent;
  border: none;
  border-bottom: 2px solid transparent;
  border-radius: 1;
  white-space: nowrap;
  outline: none;
}

.tab-button:hover {
  color: var(--bs-body-color);
  background: rgba(56, 90, 138, 0.05);
}

.tab-button.tab-active {
  color: #385a8a;
  font-weight: 500;
  background: rgba(56, 90, 138, 0.06);
  border-bottom: 2px solid #385a8a;
  padding-bottom: 0.6rem;
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

@media (max-width: 576px) {
  .tab-button {
    padding: 0.75rem 1rem;
    font-size: 0.9rem;
  }

  .tab-label {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 100px;
  }
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
