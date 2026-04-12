<script setup>
import {computed, onMounted, ref} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {longToDateTime, showMessage, TIME_ZONES} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import router from "@/router/index.js";
import { mapSelectItems } from "@/util/selectItems.js";
import { useFormSelectField } from "@/composables/useFormSelectField.js";
import {useStateStore} from "@/store/StateStore.js";

const {t} = useI18n();
const stateStore = useStateStore();
const filterStore = useFilterStore();

const props = defineProps({
  layout: {
    type: Boolean,
    required: false,
    default: false
  },
})

const columns = [
  {
    key: 'id',
    name: 'id',
    label: '',
    styles: 'width: 50px;',
    classes: '',
  },
  {
    key: 'dot',
    name: 'dot',
    label: t('dot'),
    styles: '',
    classes: '',
  }, {
    key: 'name',
    name: 'name',
    label: t('name'),
    styles: 'width: 400px;',
    classes: '',
  },
  {
    key: 'email',
    name: 'email',
    label: t('email'),
    styles: '',
    classes: '',
  },
  {
    key: 'phone',
    name: 'phone',
    label: t('phone'),
    styles: '',
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
    name: null,
    ownerName: null,
    dot: null,
    pinNumber: null,
    mc: null,
    dba: null,
    fein: null,
    stateId: null,
    address1: null,
    address2: null,
    city: null,
    zipcode: null,
    email: null,
    phone: null,
    timeZone: null,
    status: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.COMPANIES;
const dataList = ref([]);
const status = ref([]);
const data = ref(newModel())
const selectedRow = ref();

const companyCountryItems = computed(() =>
  mapSelectItems(stateStore.countries, "name", "id"),
);
const companyStateItems = computed(() =>
  mapSelectItems(stateStore.getStates(data.value.countryId), "name", "id"),
);
const companyTimeZoneItems = computed(() =>
  mapSelectItems(TIME_ZONES, "key", "value"),
);
const companyStatusItems = computed(() =>
  mapSelectItems([{ name: "ACTIVE" }, { name: "INACTIVE" }], "name", "name"),
);
const companyEntrantItems = computed(() =>
  mapSelectItems([{ name: "PASSED" }, { name: "NOT_PASSED" }], "name", "name"),
);

const { errorMessages: companyCountryErr } = useFormSelectField(
  "country",
  () => data.value.countryId,
  (v) => (!v && t("required")),
);
const { errorMessages: companyStateErr } = useFormSelectField(
  "state",
  () => data.value.stateId,
  (v) => (!v && t("required")),
);
const { errorMessages: companyTimeZoneErr } = useFormSelectField(
  "timeZone",
  () => data.value.timeZone,
  (v) => (!v && t("required")),
);
const { errorMessages: companyStatusErr } = useFormSelectField(
  "status",
  () => data.value.status,
  (v) => (!v && t("required")),
);
const { errorMessages: companyEntrantErr } = useFormSelectField(
  "entrantStatus",
  () => data.value.entrantStatus,
  (v) => (!v && t("required")),
);

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}

const onEdit = (d) => {

  data.value = {...d};

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}

const handleDoubleClick = (row) => {
  if (props.layout) setTimeout(() => {
    filterStore.setCompanyId(row.id)

    router.push("/");
  }, 300)
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
const onDelete = (d) => {
  if (d.id) {
    axiosIns.delete(apiUrl, {data: d})
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

// HOOKS
onMounted(() => {
  getData();

  stateStore.init();
})

function onExcel () {
axiosIns.post(apiUrl+"/excel", data.value)
      .then(res => {})
      .catch(e => {
    showMessage(e)
  });
}

</script>

<template>
  <div class="mb-0 p-2">
    <UTable
      :items="dataList"
      :columns="columns"
      v-model="selectedRow"
      @row-dblclick="handleDoubleClick"
    >
      <template #top>
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
          <v-btn color="primary" size="small" prepend-icon="mdi-plus" @click="onAdd">
            {{ t("add") }}
          </v-btn>
        </div>
      </template>
      <template #row_created="{row}">
        <td>{{ longToDateTime(row?.created) }}</td>
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

  <!--  company modal-->
  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-primary">
            {{ data.id ? t('edit') : t('add') }} Company
          </div>
          <div class="text-end u-end">
              <v-btn icon variant="text" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <UForm id="safety-company-form" @submit="onSave">
            <div class="row">
              <!--            name-->
              <div class="col-3">
                <UInput v-model="data.name" :label="t('company_name')" :hint="t('company_name')"
                        :name="t('company_name')"
                        :placeholder="t('enter_company_name')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.ownerName" :label="t('company_ownerName')" :hint="t('company_ownerName')"
                        :name="t('company_ownerName')"
                        :placeholder="t('enter_company_ownerName')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.dot" :label="t('dot')" :hint="t('dot')" :name="t('dot')"
                        :placeholder="t('enter_dot')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.pinNumber" :label="t('pinNumber')" :hint="t('pinNumber')" :name="t('pinNumber')"
                        :placeholder="t('enter_pinNumber')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.mc" :label="t('mc')" :hint="t('mc')" :name="t('mc')"
                        :placeholder="t('enter_mc')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.dba" :label="t('dba')" :hint="t('dba')" :name="t('dba')"
                        :placeholder="t('enter_dba')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.fein" :label="t('fein')" :hint="t('fein')" :name="t('fein')"
                        :placeholder="t('enter_fein')" classes=""/>
              </div>

              <div class="col-3"/>

              <div class="col-3">
                <v-select
                  v-model="data.countryId"
                  :items="companyCountryItems"
                  item-title="title"
                  item-value="value"
                  :label="t('country')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="companyCountryErr"
                  class="mb-2"
                />
              </div>
              <div class="col-3">
                <v-select
                  v-model="data.stateId"
                  :items="companyStateItems"
                  item-title="title"
                  item-value="value"
                  :label="t('state')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="companyStateErr"
                  class="mb-2"
                />
              </div>
              <div class="col-3">
                <UInput v-model="data.city" :label="t('city')" :hint="t('city')" :name="t('city')"
                        :placeholder="t('enter_city')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.zipcode" :label="t('zipcode')" :hint="t('zipcode')"
                        :name="t('zipcode')"
                        :placeholder="t('enter_zipcode')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.address1" :label="t('address1')" :hint="t('address1')" :name="t('address1')"
                        :placeholder="t('enter_address1')" classes=""/>
              </div>
              <div class="col-3">
                <v-select
                  v-model="data.timeZone"
                  :items="companyTimeZoneItems"
                  item-title="title"
                  item-value="value"
                  :label="t('timeZone')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="companyTimeZoneErr"
                />
              </div>
              <div class="col-4">
                <UInput v-model="data.address2" :label="t('mailingAddress')" :hint="t('mailingAddress')" :name="t('mailingAddress')"
                        :placeholder="t('enter_mailingAddress')" type="email"/>
              </div>
              <div class="col-4">
                <UInput v-model="data.email" type="email" :label="t('email')" :hint="t('email')" :name="t('email')"
                        :placeholder="t('enter_email')" classes=""/>
              </div>
              <div class="col-4">
                <UInput v-model="data.phone" :label="t('phone')" :hint="t('phone')" :name="t('phone')"
                        :placeholder="t('enter_phone_number')" classes=""/>
              </div>
              <div class="col-3">
                <v-select
                  v-model="data.status"
                  :items="companyStatusItems"
                  item-title="title"
                  item-value="value"
                  :label="t('status')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="companyStatusErr"
                />
              </div>
              <div class="col-3">
                <v-select
                  v-model="data.entrantStatus"
                  :items="companyEntrantItems"
                  item-title="title"
                  item-value="value"
                  :label="t('entrantStatus')"
                  variant="outlined"
                  density="compact"
                  hide-details="auto"
                  clearable
                  bg-color="surface"
                  :error-messages="companyEntrantErr"
                />
              </div>
            </div>
        </UForm>
      </template>
      <template #actions>
        <v-btn type="submit" form="safety-company-form" color="primary">Save</v-btn>
      </template>
    </modal>
  </Teleport>
</template>

<style scoped>

</style>
