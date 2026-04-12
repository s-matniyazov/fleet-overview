<script setup>
import {computed, onMounted, ref, watch} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, showMessage, TIME_ZONES} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import { mapSelectItems } from "@/util/selectItems.js";
import { useFormSelectField } from "@/composables/useFormSelectField.js";
import {useStateStore} from "@/store/StateStore.js";
import UScrollArea from "@/components/base/UScrollArea.vue";
import FileMiniCard from "@/components/FileMiniCard.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import {useCompanyFileStore} from "@/store/CompanyFileStore.js";
import CompanyCard from "@/components/safety/company/CompanyCard.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import UDropDown from "@/components/base/UDropDown.vue";

const {t} = useI18n();
const filterStore = useFilterStore();
const stateStore = useStateStore();
const companyFileStore = useCompanyFileStore();

const columns = [
  {
    key: 'company_name',
    name: 'company_name',
    label: t('company_name'),
    styles: 'min-width: 250px;',
    classes: '',
  },
  {
    key: 'insurance_cert',
    name: 'insurance_cert',
    label: t('insurance_cert'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'ifta_license',
    name: 'ifta_license',
    label: t('ifta_license'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'ucr',
    name: 'ucr',
    label: t('ucr'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'mcs_150',
    name: 'mcs_150',
    label: t('mcs_150'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'ct_permit',
    name: 'ct_permit',
    label: t('ct_permit'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'articles_of_incorporation',
    name: 'articles_of_incorporation',
    label: t('articles_of_incorporation'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'mc_certificate',
    name: 'mc_certificate',
    label: t('mc_certificate'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'owner_operator_agreement',
    name: 'owner_operator_agreement',
    label: t('owner_operator_agreement'),
    styles: 'min-width: 200px;',
    classes: '',
  },
  {
    key: 'driver_agreement',
    name: 'driver_agreement',
    label: t('driver_agreement'),
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

const selectedFileSection = ref({
  dialog: false,
  data: {
    description: '',
    expirationDate: new Date(),
    type: '',
    companyId: ''
  }
});

const apiUrl = URIS.COMPANIES;
const pagination = ref({
  rowsPerPage: 10,
  page: 1,
  hasNext: true
});
const filter = ref({
  companyName: null,
  companyId: filterStore.companyId
});
const dataList = ref([]);
const status = ref([]);
const data = ref(newModel())
const selectedRow = ref();

const showModal = ref(false);

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
const deactivate = (row) => {
  onDeactivate(row);
}
const activate = (row) => {
  onActivate(row);
}

const selectFileSection = (type) => {
  const file = selectedRow.value?.files.find(it => it.type === type) || {}

  selectedFileSection.value = {
    dialog: true,
    data: {
      ...file,
      companyId: selectedRow.value.id,
      type: type
    }
  };
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
  axiosIns.get(`${apiUrl}/with-files${filterString({...filter.value, ...pagination.value})}`)
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
      if (newValue) companyFileStore.init(selectedRow.value.id)
      else companyFileStore.clear()
    }
)

watch(
    () => showModal.value,
    function (newValue) {
      if (newValue) companyFileStore.init(selectedRow.value.id)
      else {
        companyFileStore.clear();
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

          <UInput v-model="filter.companyName" style="min-width: 23vw"
                  :placeholder="t('search_by_company_name')"/>
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </template>
      <template #row_company_name="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
                <span class="text-primary" style="font-size: 15px">
                  {{ row.name }}
                </span>
            </div>
            <div class="col-12 d-flex align-items-center mt-1">
                <span class="text-gray f-500" style="font-size: 12px">
                  MC: {{ row.mc }}
                </span>
            </div>
            <div class="col-12 d-flex align-items-center mt-1">
                <span class="text-gray f-500" style="font-size: 12px">
                  DOT: {{ row.dot }}
                </span>
            </div>
          </div>
        </td>
      </template>

      <template #row_insurance_cert="{row}">
        <td>
          <FileMiniCard name="INS_CERT" type="INS_CERT"
                        :file="row?.files?.find(it => it.type==='INS_CERT')"
                        @click="(e) => {selectedRow = row; selectFileSection('INS_CERT'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_ifta_license="{row}">
        <td>
          <FileMiniCard name="IFTA_LICENSE" type="IFTA_LICENSE"
                        :file="row?.files?.find(it => it.type==='IFTA_LICENSE')"
                        @click="(e) => { selectedRow = row; selectFileSection('IFTA_LICENSE'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_ucr="{row}">
        <td>
          <FileMiniCard name="UCR" type="UCR"
                        :file="row?.files?.find(it => it.type==='UCR')"
                        @click="(e) => { selectedRow = row; selectFileSection('UCR'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_mcs_150="{row}">
        <td>
          <FileMiniCard name="MCS_150" type="MCS_150"
                        :file="row?.files?.find(it => it.type==='MCS_150')"
                        @click="(e) => { selectedRow = row; selectFileSection('MCS_150'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_ct_permit="{row}">
        <td>
          <FileMiniCard name="CT_PERMIT" type="CT_PERMIT"
                        :file="row?.files?.find(it => it.type==='CT_PERMIT')"
                        @click="(e) => { selectedRow = row; selectFileSection('CT_PERMIT'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_articles_of_incorporation="{row}">
        <td>
          <FileMiniCard name="ARTICLES_INCORP" type="ARTICLES_OF_INCORPORATION"
                        :file="row?.files?.find(it => it.type==='ARTICLES_OF_INCORPORATION')"
                        @click="(e) => { selectedRow = row; selectFileSection('ARTICLES_OF_INCORPORATION'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_mc_certificate="{row}">
        <td>
          <FileMiniCard name="MC_CERTIFICATE" type="MC_CERTIFICATE"
                        :file="row?.files?.find(it => it.type==='MC_CERTIFICATE')"
                        @click="(e) => { selectedRow = row; selectFileSection('MC_CERTIFICATE'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_owner_operator_agreement="{row}">
        <td>
          <FileMiniCard name="OWNER_AGREEMENT" type="OWNER_OPERATOR_AGREEMENT"
                        :file="row?.files?.find(it => it.type==='OWNER_OPERATOR_AGREEMENT')"
                        @click="(e) => { selectedRow = row; selectFileSection('OWNER_OPERATOR_AGREEMENT'); e.stopPropagation()}"/>
        </td>
      </template>

      <template #row_driver_agreement="{row}">
        <td>
          <FileMiniCard name="DRIVER_AGREEMENT" type="DRIVER_AGREEMENT"
                        :file="row?.files?.find(it => it.type==='DRIVER_AGREEMENT')"
                        @click="(e) => { selectedRow = row; selectFileSection('DRIVER_AGREEMENT'); e.stopPropagation()}"/>
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

  <!--  company modal-->
  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-white">
            {{ data.id ? t('edit') : t('add') }} Company
          </div>
          <div class="text-end u-end">
              <v-btn icon variant="text" color="white" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <UForm id="company-with-files-form" @submit="onSave">
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
        <v-btn type="submit" form="company-with-files-form" color="info">Save</v-btn>
      </template>
    </modal>
  </Teleport>

  <!--  company card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(50vw)" class="">
    <template #header>
      <div class="d-flex w-100">
        <div class="row">
          <div class="col-12 d-flex align-items-center">
            <span class="text-gray-light f-700 font-size-15">
                {{ `${selectedRow?.name}` }}
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
          <CompanyCard :data="selectedRow"/>
        </div>
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
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="selectedFileSection.dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </span>
      </h4>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.COMPANIES}/attach-file`" :data="selectedFileSection.data"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
.justify-content-start {
  justify-content: flex-start !important;
}

.last-col-sticky {
  position: sticky;
  right: 0;
  z-index: 5;
}
</style>