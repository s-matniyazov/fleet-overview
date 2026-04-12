<script setup>
import {computed, onMounted, ref, watch} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {filterString, showMessage} from "@/util/utils.js";
import {useFilterStore} from "@/store/FilterStore.js";
import UForm from "@/components/base/UForm.vue";
import { mapSelectItems } from "@/util/selectItems.js";
import { useFormSelectField } from "@/composables/useFormSelectField.js";
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
    countryId: null,
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
  {id: 'HST', name: 'HST'},
];

const collisionTimeZoneItems = computed(() =>
  mapSelectItems(timeZones, "name", "id"),
);
const collisionCountryItems = computed(() =>
  mapSelectItems(stateStore.countries, "name", "id"),
);
const collisionStateItems = computed(() =>
  mapSelectItems(stateStore.getStates(data.value.countryId), "name", "id"),
);
const collisionTruckItems = computed(() =>
  mapSelectItems(trucks.value, "unit", "id"),
);
const collisionDriverItems = computed(() =>
  mapSelectItems(
    drivers.value,
    (it) => `${it.firstName} ${it.lastName}`,
    "id",
  ),
);

const { errorMessages: collisionCountryErr } = useFormSelectField(
  "country",
  () => data.value.countryId,
  (v) => (!v && t("required")),
);
const { errorMessages: collisionStateErr } = useFormSelectField(
  "state",
  () => data.value.stateId,
  (v) => (!v && t("required")),
);

// FUNCTIONS

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
    countryId: d.countryId,
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

          <UInput v-model="filter.reportNumber" style="min-width: 23vw"
                  :placeholder="t('claim_number')"/>
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </template>
      <template #row_city_state="{row}">
        <td>
          {{ row.city }}, {{ row.stateName }}
        </td>
      </template>
    </UTable>

    <!--  collision modal-->
    <Teleport to="body">
      <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 700px)">
        <template #header>
          <div style="width: 100%">
            <div class="d-flex justify-content-between align-items-center">
              <div class="text-white">
                {{ data.id ? data.inspection : t('add') }} {{ t('inspection') }}
              </div>

              <v-btn icon variant="text" color="white" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </div>
            <hr class="my-3 border-primary opacity-75">
          </div>
        </template>

        <template #body>
          <UForm id="safety-collision-form" @submit="onSave" :submit-text="t('save')" @close="onClose">
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
                <v-select
                  v-model="data.timeZone"
                  :items="collisionTimeZoneItems"
                  item-title="title"
                  item-value="value"
                  :label="t('timeZone')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                />
              </div>
              <div class="col-md-6 mb-3">
                <UInput v-model="data.city" :label="t('city')"/>
              </div>
              <div class="col-6">
                <v-select
                  v-model="data.countryId"
                  :items="collisionCountryItems"
                  item-title="title"
                  item-value="value"
                  :label="t('country')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="collisionCountryErr"
                  class="mb-2"
                />
              </div>
              <div class="col-6">
                <v-select
                  v-model="data.stateId"
                  :items="collisionStateItems"
                  item-title="title"
                  item-value="value"
                  :label="t('state')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="collisionStateErr"
                  class="mb-2"
                />
              </div>
              <div class="col-md-6 mb-3">
                <UInput v-model="data.zipCode" :label="t('zip_code')"/>
              </div>
              <div class="col-md-6 mb-3">
                <UInput v-model="data.typeOfCargo" :label="t('type')"/>
              </div>
              <div class="col-md-6 mb-3">
                <v-select
                  v-model="data.truckId"
                  :items="collisionTruckItems"
                  item-title="title"
                  item-value="value"
                  :label="t('truck')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                />
              </div>
              <div class="col-md-6 mb-3">
                <v-select
                  v-model="data.driverId"
                  :items="collisionDriverItems"
                  item-title="title"
                  item-value="value"
                  :label="t('driver')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                />
              </div>
            </div>
          </UForm>
        </template>
        <template #actions>
          <div class="d-flex w-100 justify-space-between align-center flex-wrap ga-2">
            <v-btn variant="tonal" color="secondary" @click="onClose">Cancel</v-btn>
            <v-btn type="submit" form="safety-collision-form" color="info">Save</v-btn>
          </div>
        </template>
      </UDialog>
    </Teleport>
  </div>
</template>

<style scoped>
</style>
