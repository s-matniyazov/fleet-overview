<script setup>
import UTable from "@/components/base/UTable.vue";
import {onMounted, ref} from "vue";
import {URIS} from "@/constants/UriConstants.js";
import UInput from "@/components/base/UInput.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import axiosIns from "@/plugins/axios.js";
import UDialog from "@/components/base/UDialog.vue";
import USelect from "@/components/base/USelect.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import {longToDate, longToDateTime, showMessage} from "@/util/utils.js";
import {useI18n} from "vue-i18n";

const {t} = useI18n();
const columns = [
  {
    key: 'id',
    name: 'id',
    label: '',
    styles: 'width:50px;',
    classes: ''
  },
  {
    key: 'name',
    name: 'name',
    label: t('name'),
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'project',
    name: 'project',
    label: t('project'),
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'type',
    name: 'type',
    label: t('type'),
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'priority',
    name: 'priority',
    label: t('priority'),
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'description',
    name: 'description',
    label: t('description'),
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'startDate',
    name: 'startDate',
    label: t('startDate'),
    styles: 'width:400px;',
    classes: ''
  },
  {
    key: 'endDate',
    name: 'endDate',
    label: t('endDate'),
    styles: 'width:400px;',
    classes: ''
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

  {
    key: 'status',
    name: 'status',
    label: t('status'),
    styles: 'width:400px;',
    classes: ''
  },
]
const newModel = () => {
  return {
    id: null,
    name: null,
    typeId: null,
    priorityId: null,
    statusId: null,
    projectId:null,
    startDate: null,
    endDate: null,
    description: null,
  }
}

const addModal = ref(false);

const apiUrl = URIS.TASKS;
const dataList = ref([]);
const data = ref(newModel());
const selectedRow = ref();

const priorities = ref();
const statuses = ref();
const projects = ref();
const types = ref();

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

//API FUNCTIONS
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
function getPriorities() {
  axiosIns.get(URIS.TASK_PRIORITIES)
      .then(res => {
        priorities.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}

function getStatuses() {
  axiosIns.get(URIS.TASK_STATUSES)
      .then(res => {
        statuses.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });
}
function getProjects() {
  axiosIns.get(URIS.PROJECTS)
      .then(res => {
        projects.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });

}
function getTypes() {
  axiosIns.get(URIS.TASK_TYPES)
      .then(res => {
        types.value = res.data.data;
      }).catch((e) => {
    showMessage(e)
  });

}

onMounted(() => {
  getData();
  getStatuses();
  getPriorities();
  getProjects();
  getTypes();
})
</script>

<template>
  <div class="mb-0">
    <div class="row align-items-center">
      <div class="col-12">
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2 mb-3">
          <div>
            <button @click="onAdd" class="btn btn-primary btn-sm mx-1"><span class="mdi mdi-plus"></span> {{ t('add') }}</button>
            <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span class="mdi mdi-pen"></span>
              {{ t('edit') }} </button>
            <button @click="onDelete(selectedRow)" class="btn btn-primary btn-sm mx-1" :disabled="!selectedRow"><span class="mdi mdi-delete"></span>
              {{ t('delete') }} </button>
            <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
          </div>
        </div>
      </div>

      <UTable :items="dataList" :columns="columns" v-model="selectedRow">


        <template #row_name="{row}">
          <td>
            <div class="d-flex gap-2">
              <a class="badge bg-primary-subtle text-primary">{{ row?.name }}</a>
            </div>
          </td>
        </template>

        <template #row_created="{row}">
          <td>{{longToDateTime(row?.created)}}</td>
        </template>

        <template #row_type="{row}">
          <td>
            {{ row?.type.name }}
          </td>
        </template>
        <template #row_priority="{row}">
          <td>
            {{ row?.priority.name }}
          </td>
        </template>
        <template #row_status="{row}">
          <td>
            {{ row?.status.name }}
          </td>
        </template>

      </UTable>

      <Teleport to="body">
        <UDialog :show="addModal" @close="addModal = false" @save="onSave">
          <template #header>
            <div class="d-flex" style="width: 100%">
              <div class="text-dark">
                {{ data.id ? t('edit') : t('add')}} {{t('task')}}
              </div>
              <div class="text-end" style="right: 0; margin-left: auto">
                <button class="btn-close" @click="onClose"></button>
              </div>
            </div>
          </template>

          <template #body >
            <form class="needs-validation" name="event-form" novalidate="">
              <div class="row">
                <!--            name-->
                <div class="col-12">
                  <UInput v-model="data.name" :label="t('name')" :hint="t('name')" name="t('name')" :placeholder="t('enter_task_name')" classes="mb-3" />
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
                  <USelect v-model="data.projectId" :label="t('project')"
                           :items="projects"
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

                <!--            type-->
                <div class="col-6">
                  <USelect v-model="data.typeId" :label="t('type')"
                           :items="types"
                           option_name="name"
                           classes="mb-3"
                  ></USelect>
                </div>

                <!--            description-->
                <div class="col-12">
                  <UTextarea v-model="data.description" :label="t('description')" :placeholder="t('enter_task_description')" classes="mb-3" />
                </div>
              </div>
            </form>
          </template>
        </UDialog>
      </Teleport>

    </div>
  </div>


</template>

<style scoped>

</style>
