<script setup>
import {onMounted, ref} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import USelect from "@/components/base/USelect.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import UInput from "@/components/base/UInput.vue";
import {longToDate, longToDateTime, showMessage} from "@/util/utils.js";
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
    key: 'code',
    name: 'code',
    label: t('code'),
    styles: '',
    classes: '',
  },
  {
    key: 'name',
    name: 'name',
    label: t('name'),
    styles: '',
    classes: '',
  },
  // {
  //   key: 'description',
  //   name: 'description',
  //   label: t('description'),
  //   styles: '',
  //   classes: '',
  // },
  {
    key: 'priority',
    name: 'priority',
    label: t('priority'),
    styles: '',
    classes: '',
  },
  {
    key: 'team',
    name: 'team',
    label: t('team'),
    styles: '',
    classes: '',
  },
  {
    key: 'startDate',
    name: 'startDate',
    label: t('startDate'),
    styles: '',
    classes: '',
  },
  {
    key: 'endDate',
    name: 'endDate',
    label: t('endDate'),
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
    code: null,
    name: null,
    startDate: null,
    endDate: null,
    clientsId: null,
    teamId: null,
    priorityId: null,
    statusId: null,
    description: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.PROJECTS;
const dataList = ref([]);
const data = ref(newModel())
const selectedRow = ref();

const priorities = ref();
const statuses = ref();
const clients = ref();
const teams = ref();

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
    axiosIns.delete(apiUrl, {data: d})
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
      }).catch((e) => {
    showMessage(e)
  });
}

function getPriorities() {
  axiosIns.get(URIS.PROJECT_PRIORITIES)
      .then(res => {
        priorities.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

function getStatuses() {
  axiosIns.get(URIS.PROJECT_STATUSES)
      .then(res => {
        statuses.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

function getClients() {
  axiosIns.get(URIS.CLIENTS)
      .then(res => {
        clients.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

function getTeams() {
  axiosIns.get(URIS.TEAMS)
      .then(res => {
        teams.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
  getPriorities();
  getStatuses();
  getClients();
  getTeams();
})
</script>

<template>
  <div class="mb-0 p-0">
    <div class="row">
      <div class="col-12">
        <div class="col-12">
          <div class="d-flex flex-wrap align-items-center justify-content-start gap-2 mb-3">
            <div>
              <button @click="onAdd" class="btn btn-primary btn-sm mx-1"><span class="mdi mdi-plus"></span> {{
                  t("add")
                }}
              </button>
              <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span
                  class="mdi mdi-pen"></span> {{ t("edit") }}
              </button>
              <button @click="onDelete(selectedRow)" class="btn btn-primary btn-sm mx-1" :disabled="!selectedRow"><span
                  class="mdi mdi-delete"></span> {{ t("delete") }}
              </button>
              <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
            </div>
          </div>
        </div>

        <UTable :items="dataList" :columns="columns" v-model="selectedRow" styles="height: calc(100vh - 388px)">
          <template #row_status="{row}">
            <td>
              <div class="d-flex gap-2">
                <a href="#" class="badge bg-primary-subtle text-primary">{{ row?.status.name }}</a>
              </div>
            </td>
          </template>

          <template #row_priority="{row}">
            <td>
              <div class="d-flex gap-2">
                <a href="#" class="badge bg-primary-subtle text-primary">{{ row?.priority.name }}</a>
              </div>
            </td>
          </template>

          <template #row_team="{row}">
            <td>{{ row?.team.name }}</td>
          </template>

          <template #row_startDate="{row}">
            <td>{{ longToDate(row?.startDate) }}</td>
          </template>

          <template #row_endDate="{row}">
            <td>{{ longToDate(row?.endDate) }}</td>
          </template>

          <template #row_created="{row}">
            <td>{{ longToDateTime(row?.created) }}</td>
          </template>

        </UTable>
      </div>

      <div class="col-12" style="height: 60px; border: 1px dashed black">
        <b>Opysanie: </b> {{selectedRow?.description}}
      </div>
    </div>
  </div>

  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" @save="onSave">
      <template #header>
        <div class="d-flex" style="width: 100%">
          <div class="text-dark">
            {{ data.id ? t("edit") : t("add") }} {{ t("project") }}
          </div>
          <div class="text-end" style="right: 0; margin-left: auto">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <form class="needs-validation" name="event-form" novalidate="">
          <div class="row">
            <!--            code-->
            <div class="col-6">
              <UInput v-model="data.code" :label="t('project_number')" :hint="t('project_number')"
                      :name="t('project_number')" :placeholder="t('enter') + ' ' + t('project_number')" classes="mb-3"/>
            </div>

            <!--            name-->
            <div class="col-12">
              <UInput v-model="data.name" :label="t('project_name')" :hint="t('name')" :name="t('name')"
                      :placeholder="t('enter_project_name') " classes="mb-3"/>
            </div>

            <!--            start date-->
            <div class="col-6">
              <UDateInput v-model="data.startDate" classes="mb-3" :label="t('startDate')"/>
            </div>

            <!--            end date-->
            <div class="col-6">
              <UDateInput v-model="data.endDate" classes="mb-3" :label="t('endDate')"/>
            </div>

            <!--            customer-->
            <div class="col-12">
              <USelect v-model="data.clientsId" :label="t('client')"
                       :items="clients"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>

            <!--            team-->
            <div class="col-12">
              <USelect v-model="data.teamId" :label="t('team')"
                       :items="teams"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>

            <!--            priority-->
            <div class="col-6">
              <USelect v-model="data.priorityId" :label="t('priority')"
                       :items="priorities"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>

            <!--            status-->
            <div class="col-6">
              <USelect v-model="data.statusId" :label="t('status')"
                       :items="statuses"
                       option_name="name"
                       classes="mb-3"
              ></USelect>
            </div>

            <!--            description-->
            <div class="col-12">
              <UTextarea v-model="data.description" :label="t('description')"
                         :placeholder="t('enter_project_description')" classes="mb-3"/>
            </div>
          </div>
        </form>
      </template>
    </UDialog>
  </Teleport>
</template>

<style scoped>

</style>
