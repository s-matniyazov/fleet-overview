<script setup>
import {onMounted, ref, watch} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {longToDateTime, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import USelect from "@/components/base/USelect.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import UDateInput from "@/components/base/UDateInput.vue";

const {t} = useI18n();
const filterStore = useFilterStore();

const columns = [
  {
    key: 'id',
    name: 'id',
    label: '',
    styles: 'width: 50px;',
    classes: '',
  },
  {
    key: 'firstName',
    name: 'firstName',
    label: t('first_name'),
    styles: '',
    classes: '',
  },
  {
    key: 'middleName',
    name: 'middleName',
    label: t('middle_name'),
    styles: '',
    classes: '',
  },
  {
    key: 'lastName',
    name: 'lastName',
    label: t('last_name'),
    styles: '',
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
    key: 'city',
    name: 'city',
    label: t('city'),
    styles: '',
    classes: '',
  },
  {
    key: 'address',
    name: 'address',
    label: t('address'),
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

const apiUrl = URIS.DRIVERS;
const dataList = ref([]);
const countries = ref([]);
const states = ref([]);
const data = ref(newModel())
const selectedRow = ref();

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    ...d,
    countryId: d?.state?.country.id,
    stateId: d?.state?.id,
    companyId: filterStore.companyId
  };

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
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
  axiosIns.get(`${apiUrl}?companyId=${filterStore.companyId}`)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = null;
      }).catch(e => {
    showMessage(e)
  });
}


function getCountries() {
  axiosIns.get(`${URIS.COUNTRY}`)
      .then(res => {
        countries.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

function getState(countryId) {
  if (countryId) {
    axiosIns.get(`${URIS.STATE}?countryId=${countryId}`)
        .then(res => {
          states.value = res.data.data;
        }).catch(e => {
      showMessage(e)
    });
  }
}

// HOOKS
onMounted(() => {
  getData();
  getCountries();
})

watch(
    () => data.value.countryId,
    function (newValue) {
      data.stateId = null;
      states.value = [];
      getState(newValue)
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
        <button @click="onDelete(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span
            class="mdi mdi-delete"></span> {{ t("delete") }}
        </button>

        <div class="align-items-center" style="right: 2px; margin-left: auto">
          <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
        </div>
      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 248px)">
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

  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" width="calc(100vw - 400px)">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t('edit') : t('add') }} New {{ t('driver') }}
          </div>
          <div class="text-end u-end">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row p-4 border border-1">
            <!--            company-->
            <div class="col-6 pe-5">
              <div class="col-12 text-primary mb-3" style="font-weight: 1000; font-size: 16px">
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
              <div class="col-12 text-primary mb-3" style="font-weight: 1000; font-size: 16px">
                Current Address
              </div>
              <div class="col-12 row">
                <div class="col-6">
                  <USelect v-model="data.countryId" :label="t('country')"
                           :items="countries" name="country"
                           option_name="name"
                           classes="mb-2"
                           :rules="(val) => (!val && $t('required'))"
                  ></Uselect>
                </div>
                <div class="col-6">
                  <USelect v-model="data.stateId" :label="t('state')"
                           :items="states" name="state"
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
              <button type="submit" class="btn btn-primary">Save</button>
            </div>
          </div>
        </UForm>
      </template>
    </modal>
  </Teleport>
</template>

<style scoped>

</style>
