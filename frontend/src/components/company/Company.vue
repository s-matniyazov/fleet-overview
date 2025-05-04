<script setup>
<<<<<<< HEAD
import {onMounted,ref} from "vue";

import {useI18n} from "vue-i18n";

const {t} = useI18n();

const  columns = [
  {
    key:'id',
    name:'id',
    label:'',
    style:'width:50px',
    classes:'',
  },
  {
    key:'name',
    name:'name',
    label:('name'),
    style:'width:400px',
    classes:'',
  },
  {
    key:'address',
    name:'address',
    label:('address'),
    style:'width:600px',
    classes:'',
  },
  {
    key:'phone',
    name:'phone',
    label:('phone'),
    style:'width:200px',
    classes:'',
  },
  {
    key:'status',
    name:'status',
    label:('status'),
    style:'width:200px',
    classes:'',
  },
]

const newModal = () =>{
  return {
    id:null,
    name:null,
    description:null,
  }
}

onMounted(() => {

=======
import {onMounted, ref} from "vue";

import modal from '../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import {useI18n} from "vue-i18n";
import {longToDateTime, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import router from "@/router/index.js";

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
    key: 'name',
    name: 'name',
    label: t('name'),
    styles: 'width: 400px;',
    classes: '',
  },
  {
    key: 'description',
    name: 'description',
    label: t('description'),
    styles: '',
    classes: '',
  },
  {
    key: 'created',
    name: 'created',
    label: t('created'),
    styles: '',
    classes: '',
  },
  {
    key: 'createdBy',
    name: 'createdBy',
    label: t('createdBy'),
    styles: '',
    classes: '',
  }
]

const newModel = () => {
  return {
    id: null,
    name: null,
    description: null
  }
}

const addModal = ref(false);

const apiUrl = URIS.COMPANIES;
const dataList = ref([]);
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
  setTimeout(() => {
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
>>>>>>> 55023a1902470c26e5dd14d3e4e6ebd007716a49
})

</script>

<template>
<<<<<<< HEAD
  <div class="h-100 col-12 p-2">
    <div class="container-fluid mb-2 px-2 d-flex align-items-center"
         style="border: 1px dashed #eae1e1; border-radius: 5px; height: 50px; font-weight: bold; font-size: 16px;">

    </div>


  </div>
=======
  <div class="mb-0 p-2 bg-light-subtle">
    <div class="col-12">
      <div class="d-flex flex-wrap align-items-center justify-content-start gap-2 mb-3">
        <div class="d-flex">
          <button @click="onAdd" class="btn btn-primary btn-sm mx-1"><span class="mdi mdi-plus"></span> {{ t('add') }}
          </button>
          <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span
              class="mdi mdi-pen"></span> {{ t('edit') }}
          </button>
          <button @click="onDelete(selectedRow)" class="btn btn-primary btn-sm mx-1" :disabled="!selectedRow"><span
              class="mdi mdi-delete"></span> {{ t('delete') }}
          </button>
          <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
        </div>
      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" height="calc(100vh - 190px)" hide-pagination
            @row-dblclick="handleDoubleClick">
      <template #row_created="{row}">
        <td>{{ longToDateTime(row?.created) }}</td>
      </template>
    </UTable>
  </div>

  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t('edit') : t('add') }} {{ t('project_priority') }}
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
            <div class="col-12">
              <UInput v-model="data.name" :label="t('name')" :hint="t('name')" :name="t('name')"
                      :placeholder="t('enter_priority_name')" classes="mb-3"/>
            </div>

            <!--            description-->
            <div class="col-12">
              <UTextarea v-model="data.description" :label="t('description')"
                         :placeholder="t('enter_priority_description')" classes="mb-3"/>
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
>>>>>>> 55023a1902470c26e5dd14d3e4e6ebd007716a49
</template>

<style scoped>

<<<<<<< HEAD
</style>
=======
</style>
>>>>>>> 55023a1902470c26e5dd14d3e4e6ebd007716a49
