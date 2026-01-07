<script setup>
import {onMounted, ref, watch} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
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
  vinOrUnit: null
});
const dataList = ref([]);
const selectedRow = ref();
const data = ref(newModel())

const drivers = ref([]);

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
    id: d.id,
    unit: d.unit,
    licensePlate: d.licensePlate,
    inServiceDate: d.inServiceDate,
    modelMakerId: d?.modelMakerId,
    typeId: d?.typeId,
    year: d.year,
    axles: d.axles,
    length: d.length,
    height: d.height,
    vin: d.vin,
    ownershipTypeId: d?.ownershipTypeId,
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

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 242px)">
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

  <!--  trailer modal-->
  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex w-100">
          <div class="text-white" style="font-weight: 1000; font-size: 16px">
            {{ data.id ? t('edit') : t('add') }} {{ t('trailer') }}
          </div>
          <div class="text-end u-end">
              <button class="btn-close btn-close-white" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <UScrollArea height="calc(100vh - 300px)">
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
                  <USelect v-model="data.modelMakerId" :label="t('modelMakers')"
                           :items="trailerReferenceStore.makers" name="modelMaker"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>

                <!--            type-->
                <div class="col-6">
                  <USelect v-model="data.typeId" :label="t('types')"
                           :items="trailerReferenceStore.types" name="type"
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
                  <USelect v-model="data.ownershipTypeId" :label="t('ownershipTypes')"
                           :items="trailerReferenceStore.ownershipTypes" name="ownershipType"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></USelect>
                </div>

                <template v-if="[2].includes(data.ownershipTypeId)">
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

                <template v-if="[1, 3].includes(data.ownershipTypeId)">
                  <div class="col-12 text-white my-3" style="font-weight: 1000; font-size: 16px">
                    Other Details
                  </div>

                  <!--            purchaseType-->
                  <div class="col-12">
                    <USelect v-model="data.purchaseTypeId" :label="t('purchaseTypes')"
                             :items="trailerReferenceStore.purchaseTypes" name="purchaseType"
                             option_name="name"
                             classes="mb-2"
                             :rules="(val) => (!val && $t('required'))"
                    ></USelect>
                  </div>
                </template>

                <div class="col-12 text-white my-3" style="font-weight: 1000; font-size: 16px">
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

          <div class="modal-footer">
            <div class="d-flex text-end align-items-end mt-2">
              <button type="submit" style="background-color:#0891B2;" class="btn text-white">Save</button>
            </div>
          </div>
        </UForm>
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
            <button class="btn-close" @click="showModal = false"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UScrollArea height="calc(100vh - 200px)">
          <TrailerCard :data="selectedRow"/>
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
          <button class="btn-close" @click="showModal = false"></button>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <TrailerCard :data="selectedRow"/>
      </UScrollArea>
    </template>
  </URightOverlay>

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
      <FileOverlay :data="selectedFileSection.data" :url="`${URIS.TRAILER}/attach-file`"/>
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
  max-height: 68px;
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
