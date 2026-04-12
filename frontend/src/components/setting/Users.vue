<script setup>
import {onMounted, ref} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {hideLoader, longToDateTime, showLoader, showMessage} from "@/util/utils.js";
import UDialog from "@/components/base/UDialog.vue";
import USelect from "@/components/base/USelect.vue";
import {useI18n} from "vue-i18n";
import URightOverlay from "@/components/base/URightOverlay.vue";

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
    label: t('NSP'),
    styles: 'width: 200px;',
    classes: '',
  },
  {
    key: 'department',
    name: 'department',
    label: t('department'),
    styles: '',
    classes: '',
  },
  {
    key: 'position',
    name: 'position',
    label: t('position'),
    styles: '',
    classes: '',
  },
  {
    key: 'email',
    name: 'email',
    label: 'Email',
    styles: '',
    classes: '',
  },
  {
    key: 'role',
    name: 'role',
    label: t('role'),
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
    departmentsId: null,
    positionsId: null,
    email: null,
    rolesId: null
  }
}

const addModal = ref(false);

const departments = ref([]);
const positions = ref([]);
const roles = ref([]);

const apiUrl = URIS.USERS;
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
    ...d,
    departmentsId: d?.department.id,
    positionsId: d?.position.id,
    rolesId: d?.role.id,
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

function getData() {
  axiosIns.get(apiUrl)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = null;
      }).catch(e => {
    showMessage(e)
  });
}

function getDepartments() {
  axiosIns.get(URIS.DEPARTMENTS)
      .then(res => {
        departments.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

function getPositions() {
  axiosIns.get(URIS.POSITIONS)
      .then(res => {
        positions.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

function getRoles() {
  axiosIns.get(URIS.ROLES)
      .then(res => {
        roles.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
  getDepartments();
  getPositions();
  getRoles();
})
const isOpen = ref(false);

async function fetchData() {
  showLoader()
  await new Promise(resolve => setTimeout(resolve, 2000)); // Имитация запроса
  hideLoader();
}
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
            <v-btn color="primary" size="small" class="mx-1" icon variant="text" @click="getData">
              <v-icon>mdi-reload</v-icon>
            </v-btn>
            <v-btn color="primary" size="small" prepend-icon="mdi-file-eye" @click="isOpen = true">
              Открыть диалог
            </v-btn>
            <v-btn color="primary" size="small" class="mx-1" prepend-icon="mdi-file-eye" @click="fetchData">
              Загрузить данные
            </v-btn>
          </div>
        </div>
      </template>
      <template #row_status="{row}">
        <td>
          <div class="d-flex gap-2">
            <a class="badge bg-primary-subtle text-primary"
               :class="`bg-${row?.status === 'PASSIVE' ? 'danger' : 'primary'}-subtle`"> {{ row?.status }}</a>
          </div>
        </td>
      </template>

      <template #row_department="{row}">
        <td>
          {{ row?.department.name }}
        </td>
      </template>

      <template #row_position="{row}">
        <td>
          {{ row?.position.name }}
        </td>
      </template>

      <template #row_role="{row}">
        <td>
          {{ row?.role.name }}
        </td>
      </template>

      <template #row_created="{row}">
        <td>{{ longToDateTime(row?.created) }}</td>
      </template>
    </UTable>
  </div>

  <URightOverlay :isOpen="isOpen" @close="isOpen = false">
    <template #body>
      <h2>Привет, Vue!</h2>
      <p>Это правый оверлейный диалог.</p>
    </template>
  </URightOverlay>

  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" @save="onSave">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t('edit') : t('add') }} {{ t('employer') }}
          </div>
          <div class="text-end u-end">
              <v-btn icon variant="text" aria-label="Close" @click="onClose">
                <v-icon>mdi-close</v-icon>
              </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <form class="needs-validation" name="event-form" novalidate="">
          <div class="row">
            <!--            name-->
            <div class="col-12">
              <UInput v-model="data.name" :label="t('NSP')" :hint="t('NSP')" :name="t('NSP')"
                      :placeholder="t('enter_employer_nsp')" classes="mb-3"/>
            </div>

            <!--            department-->
            <div class="col-12">
              <USelect v-model="data.departmentsId" :label="t('department')"
                       :items="departments"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>

            <!--            position-->
            <div class="col-12">
              <USelect v-model="data.positionsId" :label="t('position')"
                       :items="positions"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>

            <!--            email-->
            <div class="col-12">
              <UInput v-model="data.email" :label="t('email')" :hint="t('email')" :name="t('email')"
                      :placeholder="t('enter_email')" classes="mb-3"/>
            </div>

            <!--            role-->
            <div class="col-12">
              <USelect v-model="data.rolesId" :label="t('client')"
                       :items="roles"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>
          </div>
        </form>
      </template>
    </UDialog>
  </Teleport>
</template>

<style scoped>

</style>
