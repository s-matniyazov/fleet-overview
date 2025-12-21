<script setup>
import {onMounted, ref} from "vue";

import modal from '../base/UDialog.vue'
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {longToDateTime, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";

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
    key: 'created',
    name: 'created',
    label: t('created'),
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

const apiUrl = URIS.FUEL_TYPE;
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
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
})

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

    <UTable :items="dataList" :columns="columns" v-model="selectedRow" styles="height: calc(100vh - 141px)">
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
            {{ data.id ? t('edit') : t('add') }} {{ t('fuel_type') }}
          </div>
          <div class="text-end u-end">
              <button class="btn-close btn-close-white" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row">
            <!--            name-->
            <div class="col-12">
              <UInput v-model="data.name" :label="t('name')" :hint="t('name')" :name="t('name')"
                      :placeholder="t('enter_status_name')" classes="mb-3"
                      :rules="(val) => (!val && $t('required'))"/>
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
