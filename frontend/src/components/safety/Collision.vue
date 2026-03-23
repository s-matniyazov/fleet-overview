<script setup>
import {onMounted, ref, watch} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {filterString, showMessage} from "@/util/utils.js";
import {useFilterStore} from "@/store/FilterStore.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import UDialog from "@/components/base/UDialog.vue";
import {useStateStore} from "@/store/StateStore.js";

const {t} = useI18n();
const filterStore = useFilterStore();
const stateStore = useStateStore();

const columns = [
  {
    key: 'reportNumber',
    name: 'reportNumber',
    label: t('claim_number'),
    styles: 'min-width: 150px;',
    classes: '',
  },
  {
    key: 'collisionDate',
    name: 'collisionDate',
    label: t('collision_date'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'city_state',
    name: 'city_state',
    label: t('city') + ' ' + t('state'),
    styles: 'min-width: 180px;',
    classes: '',
  },
  {
    key: 'driverName',
    name: 'driverName',
    label: t('driver'),
    styles: 'min-width: 180px;',
    classes: '',
  },
  {
    key: 'truckUnit',
    name: 'truckUnit',
    label: t('truck'),
    styles: 'min-width: 120px;',
    classes: '',
  },
]

const newModel = () => {
  return {
    id: null,
    reportNumber: null,
    collisionDate: null,
    collisionTime: null,
    timeZone: 'EST',
    city: null,
    stateId: null,
    zipCode: null,
    typeOfCargo: null,
    companyId: filterStore.companyId,
    truckId: null,
    driverId: null
  }
}

const addModal = ref(false);
const apiUrl = URIS.COLLISIONS;
const pagination = ref({
  rowsPerPage: 10,
  page: 1,
  hasNext: true
});
const filter = ref({
  reportNumber: null,
});
const dataList = ref([]);
const selectedRow = ref();
const data = ref(newModel())

const drivers = ref([]);
const trucks = ref([]);
const timeZones = [
  {id: 'EST', name: 'EST'},
  {id: 'CST', name: 'CST'},
  {id: 'MST', name: 'MST'},
  {id: 'PST', name: 'PST'},
  {id: 'AST', name: 'AST'},
  {id: 'HST', name: 'HST'},
];

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
  data.value = {
    id: d.id,
    reportNumber: d.reportNumber,
    collisionDate: d.collisionDate,
    collisionTime: d.collisionTime,
    timeZone: d.timeZone,
    city: d.city,
    stateId: d.stateId,
    zipCode: d.zipCode,
    typeOfCargo: d.typeOfCargo,
    companyId: filterStore.companyId,
    truckId: d.truckId,
    driverId: d.driverId
  };
  addModal.value = true;
}

const onClose = () => {
  addModal.value = false;
}

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

function getTrucks() {
  axiosIns.get(`${URIS.TRUCK}?companyId=${filterStore.companyId}`)
      .then(res => {
        trucks.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
  getDrivers();
  getTrucks();
  stateStore.init();
})

watch(
    () => pagination.value.page,
    function () {
      getData();
    }
)
watch(
    () => pagination.value.rowsPerPage,
    function () {
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

        <UInput v-model="filter.reportNumber" style="min-width: 23vw"
                :placeholder="t('claim_number')"/>
        <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-magnify"></span></button>

        <div class="align-items-center u-end">
          <ul class="pagination pagination-sm ul-style">
            <select v-model="pagination.rowsPerPage" class="form-select form-select-sm mb-0 my-n1">
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
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 200px)">
      <template #row_city_state="{row}">
        <td>
          {{ row.city }}, {{ row.stateName }}
        </td>
      </template>
    </UTable>

    <UDialog v-model="addModal" :title="t('collision')">
      <template #body>
        <UForm @submit="onSave" :submit-text="t('save')" @close="onClose">
          <div class="row">
            <div class="col-md-6 mb-3">
              <UInput v-model="data.reportNumber" :label="t('claim_number')" required/>
            </div>
            <div class="col-md-6 mb-3">
              <UDateInput v-model="data.collisionDate" :label="t('collision_date')" required/>
            </div>
            <div class="col-md-6 mb-3">
              <UInput v-model="data.collisionTime" :label="t('time')" type="time"/>
            </div>
            <div class="col-md-6 mb-3">
              <USelect v-model="data.timeZone" :items="timeZones" :label="t('timeZone')" option_value="id" option_name="name"/>
            </div>
            <div class="col-md-6 mb-3">
              <UInput v-model="data.city" :label="t('city')"/>
            </div>
            <div class="col-md-6 mb-3">
              <USelect v-model="data.stateId" :items="stateStore.states" :label="t('state')" option_value="id" option_name="name"/>
            </div>
            <div class="col-md-6 mb-3">
              <UInput v-model="data.zipCode" :label="t('zip_code')"/>
            </div>
            <div class="col-md-6 mb-3">
              <UInput v-model="data.typeOfCargo" :label="t('type')"/>
            </div>
            <div class="col-md-6 mb-3">
              <USelect v-model="data.truckId" :items="trucks" :label="t('truck')" option_value="id" option_name="unit"/>
            </div>
            <div class="col-md-6 mb-3">
              <USelect v-model="data.driverId" :items="drivers" :label="t('driver')" option_value="id" :option_name="it => it.firstName + ' ' + it.lastName"/>
            </div>
          </div>
        </UForm>
      </template>
    </UDialog>
  </div>
</template>

<style scoped>
</style>
