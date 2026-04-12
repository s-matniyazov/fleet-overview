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
    key: 'level',
    name: 'level',
    label: t('level'),
    styles: 'width: 400px;',
    classes: '',
  },
  {
    key: 'description',
    name: 'description',
    label: t('description'),
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
    level: null,
    description: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.INSPECTION_LEVELS;
const dataList = ref([]);
const data = ref(newModel())
const selectedRow = ref();

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    id: d.id,
    level: d.level ,
    description: d.description,
  };

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}

// API FUNCTIONS
const onSave = () => {

    axiosIns.post(apiUrl, data.value)
        .then(res => {
          getData();
          onClose();
        }).catch(e => {
      showMessage(e)
    });
}
const onDelete = (d) => {
  if (d.id) {
    axiosIns.delete(apiUrl, {params: { id: d.id }})
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
        <v-btn color="primary" size="small" prepend-icon="mdi-plus" @click="onAdd">
          {{ t("add") }}
        </v-btn>
        <v-btn color="primary" size="small" prepend-icon="mdi-pencil" :disabled="!selectedRow" @click="onEdit(selectedRow)">
          {{ t("edit") }}
        </v-btn>
        <v-btn color="primary" size="small" prepend-icon="mdi-delete" :disabled="!selectedRow" @click="onDelete(selectedRow)">
          {{ t("delete") }}
        </v-btn>

        <div class="align-items-center" style="right: 2px; margin-left: auto">
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-reload</v-icon>
          </v-btn>
        </div>
      </div>
    </div>

    <UTable :items="dataList" :columns="columns" v-model="selectedRow">
      <template #row_created="{row}">
        <td>{{ longToDateTime(row?.created) }}</td>
      </template>
    </UTable>
  </div>

  <Teleport to="body">
    <modal :show="addModal" @close="addModal = false">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-white">
            {{ data.id ? t('edit') : t('add') }} {{ t('purchase_type') }}
          </div>
          <div class="text-end u-end">
              <v-btn icon variant="text" color="white" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row">
            <div class="col-12">
              <UInput v-model="data.level" :label="t('level')" :hint="t('level')" :name="t('level')"
                      :placeholder="t('level')" classes="mb-3"
                      :rules="(val) => (!val && $t('required'))" type="number"
                      />

              <UInput v-model="data.description" :label="t('description')" :hint="t('description')" :name="t('description')"
                      :placeholder="t('description')" classes="mb-3"
                      :rules="(val) => (!val && $t('required'))"/>
            </div>
          </div>

          <!-- footer -->
          <div class="modal-footer d-flex justify-content-between align-items-center border-top border-primary pt-3 mt-3">
              <v-btn variant="tonal" color="secondary" @click="onClose">Cancel</v-btn>
              <v-btn type="submit" color="info">Save</v-btn>
          </div>
        </UForm>
      </template>
    </modal>
  </Teleport>
</template>

<style scoped>

</style>
