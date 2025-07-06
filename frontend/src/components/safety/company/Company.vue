<script setup>
import {onMounted, ref} from "vue";

import modal from '../../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {longToDateTime, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import router from "@/router/index.js";
import USelect from "@/components/base/USelect.vue";

const {t} = useI18n();
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
    key: 'usdot',
    name: 'usdot',
    label: t('usdot'),
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
    key: 'address',
    name: 'address',
    label: t('address'),
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
    description: null,
    phone: null,
    USDOT: null,
    status: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.COMPANIES;
const dataList = ref([]);
const status = ref([]);
const data = ref(newModel())
const selectedRow = ref();

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
})

</script>

<template>
  <div :class="`mb-0 p-2 ${layout && 'bg-light rounded-4 shadow-dark'}`">
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

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 248px)"
            @row-dblclick="handleDoubleClick">
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
    <modal :show="addModal" @close="addModal = false" width="calc(100vw-400px)">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t('edit') : t('add') }}
          </div>
          <div class="text-end u-end">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row">
            <!--            name-->
            <div class="col-6">
              <UInput v-model="data.name" :label="t('company_name')" :hint="t('company_name')" :name="t('company_name')"
                      :placeholder="t('enter_company_name')" classes="mb-3"/>
            </div>
            <div class="col-6">
              <UInput v-model="data.usdot" :label="t('usdot')" :hint="t('usdot')" :name="t('usdot')"
                      :placeholder="t('enter_usdot')" classes="mb-3"/>
            </div>
            <div class="col-6">
              <UInput v-model="data.email" :label="t('email')" :hint="t('email')" :name="t('email')"
                      :placeholder="t('enter_email')" classes="mb-3"/>
            </div>
            <div class="col-6">
              <UInput v-model="data.phone" :label="t('phone')" :hint="t('phone')" :name="t('phone')"
                      :placeholder="t('enter_phone_number')" classes="mb-3"/>
            </div>
            <div class="col-6">
              <UInput v-model="data.address" :label="t('company_address')" :hint="t('company_address')"
                      :name="t('company_address')"
                      :placeholder="t('enter_company_address')" classes="mb-3"/>
            </div>
            <div class="col-6">
              <USelect v-model="data.status" :label="t('status')"
                       :items="[{name:'Active'},{name:'Inactive'}]" name="status"
                       option_name="name"
                       option_value="name"
                       classes="mb-2"
                       :rules="(val) => (!val && $t('required'))"
              ></USelect>
            </div>
          </div>

          <div class="modal-footer bg-light">
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
