<script setup>
import {onMounted, ref} from "vue";

import modal from '../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import {useI18n} from "vue-i18n";
import {longToDateTime, showMessage} from "@/util/utils.js";

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
    styles: '',
    classes: '',
  },
  {
    key: 'createdBy',
    name: 'createdBy',
    label: t('createdBy'),
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
  }
}

const addModal = ref(false);

const apiUrl = URIS.ACTIONS;
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
    alert("ERROR");
  });
}

// HOOKS
onMounted(() => {
  getData();
})

</script>

<template>
  <div class="mb-0 p-2">
    <UTable :items="dataList" :columns="columns" v-model="selectedRow">
      <template #top>
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
          <div class="d-flex flex-wrap ga-2">
            <v-btn color="primary" size="small" class="mx-1" prepend-icon="mdi-plus" @click="onAdd">
              {{ t('add') }}
            </v-btn>
            <v-btn color="primary" size="small" prepend-icon="mdi-pencil" :disabled="!selectedRow" @click="onEdit(selectedRow)">
              {{ t('edit') }}
            </v-btn>
            <v-btn color="primary" size="small" class="mx-1" prepend-icon="mdi-delete" :disabled="!selectedRow" @click="onDelete(selectedRow)">
              {{ t('delete') }}
            </v-btn>
            <v-btn color="primary" size="small" icon variant="text" @click="getData">
              <v-icon>mdi-reload</v-icon>
            </v-btn>
          </div>
        </div>
      </template>
      <template #row_created="{row}">
        <td>{{ longToDateTime(row?.created) }}</td>
      </template>
    </UTable>
  </div>

  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false" @save="onSave">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t('edit') : t('add') }} {{ t('project_priority') }}
          </div>
          <div class="text-end u-end">
              <v-btn icon variant="text" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <form class="needs-validation" novalidate="">
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
        </form>
      </template>
    </modal>
  </Teleport>
</template>

<style scoped>

</style>
