<script setup>
import {onMounted, ref, watch} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, showMessage, TIME_ZONES} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
import {useStateStore} from "@/store/StateStore.js";
import UScrollArea from "@/components/base/UScrollArea.vue";
import FileMiniCard from "@/components/FileMiniCard.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import {useCompanyFileStore} from "@/store/CompanyFileStore.js";
import CompanyCard from "@/components/safety/company/CompanyCard.vue";

const {t} = useI18n();
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
  rowsPerPage: 5,
  page: 1,
  hasNext: true
});
const filter = ref({
  companyName: null
});
const dataList = ref([]);
const status = ref([]);
const data = ref(newModel())
const selectedRow = ref();

const showModal = ref(false);

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

  data.value = {...d};

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

        <UInput v-model="filter.companyName" style="min-width: 23vw"
                :hint="t('vin')" :placeholder="t('search_by_company_name')"/>
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
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <UScrollArea height="calc(100vh - 300px)">
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
                <USelect v-model="data.countryId" :label="t('country')"
                         :items="stateStore.countries" name="country"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></Uselect>
              </div>
              <div class="col-3">
                <USelect v-model="data.stateId" :label="t('state')"
                         :items="stateStore.getStates(data.countryId)" name="state"
                         option_name="name"
                         classes="mb-2"
                         :rules="(val) => (!val && $t('required'))"
                ></Uselect>
              </div>
              <div class="col-3">
                <UInput v-model="data.city" :label="t('city')" :hint="t('city')" :name="t('city')"
                        :placeholder="t('enter_city')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.zipcode" type="number" :label="t('zipcode')" :hint="t('zipcode')"
                        :name="t('zipcode')"
                        :placeholder="t('enter_zipcode')" classes=""/>
              </div>
              <div class="col-3">
                <UInput v-model="data.address1" :label="t('address1')" :hint="t('address1')" :name="t('address1')"
                        :placeholder="t('enter_address1')" classes=""/>
              </div>
              <div class="col-3">
                <USelect v-model="data.timeZone" :label="t('timeZone')"
                         :items="TIME_ZONES" name="timeZone"
                         option_name="key"
                         option_value="value"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
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
                <USelect v-model="data.status" :label="t('status')"
                         :items="[{name:'ACTIVE'},{name:'INACTIVE'}]" name="status"
                         option_name="name"
                         option_value="name"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>
              <div class="col-3">
                <USelect v-model="data.entrantStatus" :label="t('entrantStatus')"
                         :items="[{name:'PASSED'},{name:'NOT_PASSED'}]" name="entrantStatus"
                         option_name="name"
                         option_value="name"
                         :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>
            </div>

            <div class="modal-footer">
              <div class="d-flex text-end align-items-end mt-2">
                <button type="submit" style="background-color:#0891B2;" class="btn text-white">Save</button>
              </div>
            </div>
          </UScrollArea>
        </UForm>
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
          <button class="btn-close" @click="showModal = false"></button>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <CompanyCard :data="selectedRow"/>
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
          <button class="btn-close" @click="selectedFileSection.dialog = false"></button>
        </span>
      </h4>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.COMPANIES}/attach-file`" :data="selectedFileSection.data"/>
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