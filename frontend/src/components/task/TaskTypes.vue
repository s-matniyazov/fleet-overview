<script setup>
import {onMounted, ref} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import USelect from "@/components/base/USelect.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import UInput from "@/components/base/UInput.vue";
import {longToDateTime, showMessage} from "@/util/utils.js";
import UDialog from "@/components/base/UDialog.vue";
import {useI18n} from "vue-i18n";

const {t} = useI18n();

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
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'createdBy',
    name: 'createdBy',
    label: t('createdBy'),
    styles: 'width:400px;',
    classes: ''
  },
]
const newModel = () => {
  return {
    id: null,
    name: null,
    description: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.TASK_TYPES;
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

// API FUNCTIONS
const onSave = () => {
  if (data.value.id) {
    axiosIns.put(apiUrl, data.value)
        .then(() => {
          getData();
          onClose();
        }).catch((e) => {
      showMessage(e)
    });
  } else {
    axiosIns.post(apiUrl, data.value)
        .then((res) => {
          getData();
          onClose();
        }).catch((e) => {
      showMessage(e)
    });
  }
}
const onDelete = (d) => {
  if (d.id) {
    axiosIns.delete(apiUrl, { data: d })
        .then(() => {
          getData();
          onClose();
        }).catch((e) => {
      showMessage(e)
    });
  }
}

function getData() {
  axiosIns.get(apiUrl)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = dataList.value[0];
      }).catch((e) => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();

})

</script>

<template>
  <div class="mb-0">
    <div class="row align-items-center">
      <div class="col-12">
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2 mb-3">
          <div>
            <button @click="onAdd" class="btn btn-primary btn-sm mx-1"><span class="mdi mdi-plus"></span> {{ t('add') }}</button>
            <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span class="mdi mdi-pen"></span>{{ t('edit') }} </button>
            <button @click="onDelete(selectedRow)" class="btn btn-primary btn-sm mx-1" :disabled="!selectedRow"><span class="mdi mdi-delete"></span>{{ t('delete') }} </button>
            <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
          </div>
        </div>
      </div>

      <UTable :items="dataList" :columns="columns" v-model="selectedRow">
        <template #row_created="{row}">
          <td>{{longToDateTime(row?.created)}}</td>
        </template>
      </UTable>
    </div>
  </div>

  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" @save="onSave">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t('edit') : t('add') }} {{ t('task_type') }}
          </div>
          <div class="text-end" style="right: 0; margin-left: auto">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <form class="needs-validation" name="event-form" novalidate="">
          <div class="row">
            <!--            name-->
            <div class="col-12">
              <UInput v-model="data.name" :label="t('name')" :hint="t('name')" :name="t('name')" :placeholder="t('enter_type_name')" classes="mb-3" />
            </div>

            <!--            description-->
            <div class="col-12">
              <UTextarea v-model="data.description" :label="t('description')" :placeholder="t('enter_type_description')" classes="mb-3" />
            </div>
          </div>
        </form>
      </template>
    </UDialog>
  </Teleport>
</template>

<style scoped>

</style>
