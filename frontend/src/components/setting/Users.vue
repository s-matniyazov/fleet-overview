<script setup>
import {onMounted, ref, watch} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {TABLE_STATUS_FILTER_ITEMS} from "@/constants/tableFilters.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import {useI18n} from "vue-i18n";
import {DOCUMENT_TYPES, filterString, showMessage} from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import {useFilterStore} from "@/store/FilterStore.js";
import FileMiniCard from "@/components/FileMiniCard.vue";

import URightOverlay from "@/components/base/URightOverlay.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import InspectionCard from "@/components/safety/inspection/InspectionCard.vue";

import {useStateStore} from "@/store/StateStore.js";
import {useInspectionStore} from "@/store/InspectionFileStore.js";
import UDropDown from "@/components/base/UDropDown.vue";
import USelect from "@/components/base/USelect.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import UDialog from "@/components/base/UDialog.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
import {stringToDateFormatter} from "@/util/utils.js";


const {t} = useI18n();
const filterStore = useFilterStore();
const inspectionStore = useInspectionStore();
const stateStore = useStateStore();

const columns = [
  {
    key: 'id',
    name: 'id',
    label: t('id'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'name',
    name: 'name',
    label: t('name'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'email',
    name: 'email',
    label: t('email'),
    styles: 'min-width: 120px;',
    classes: '',
  },
   {
    key: 'role',
    name: 'role',
    label: t('role'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'status',
    name: 'status',
    label: t('status'),
    styles: 'min-width: 120px;',
    classes: '',
  },
  {
    key: 'actions',
    name: 'actions',
    label:t('actions'),
    styles: '',
    classes: 'last-col-sticky',
  },
]

const newModel = () => {
  return {
    id: null,
    email:null,
    username:null,
    password:null,
    role:null,
    phone_number:null,
    status:null
  }
}

const addModal = ref(false);
const showViolation = ref(false);

const apiUrl = URIS.USERS;
const pagination = ref({
  size: 10,
  page: 1,
  hasNext: true
});
const filter = ref({
  username: null,
  status: 'ACTIVE', // ACTIVE, PASSIVE
});
const dataList = ref([]);
const data = ref(newModel())
const selectedRow = ref();
const showModal = ref(false);

const selectedFileSection = ref({
  dialog: false,
  data: {
    id: null,
    email:null,
    username:null,
    password:null,
    role:null,
    phone_number:null,
    status:null
  }
});

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();
  addModal.value = true;
}
const onEdit = (d) => {
  data.value = {
    ...d
  };
  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
}


// API FUNCTIONS
const onSave = () => {

  axiosIns.post(apiUrl, data.value)
      .then(() => {
        getData();
        onClose();
      }).catch(e => {
    showMessage(e)
  });
  
}

function getData() {
  axiosIns.get(`${apiUrl}`)
      .then(res => {
        dataList.value = res.data.data;
        selectedRow.value = null;
        pagination.value.hasNext = dataList.value.length >= pagination.value.size;
      }).catch(e => {
    showMessage(e)
  });
}


// HOOKS
onMounted(() => {
  getData();
  
})





watch(
    () => pagination.value.page,
    function (newValue) {
      getData();
    }
)
watch(
    () => pagination.value.size,
    function (newValue) {
      getData();
    }
)

</script>

<template>
  <div class="mb-0 p-2">
    <UTable
      :items="dataList"
      :columns="columns"
      v-model="selectedRow"
      v-model:pagination="pagination"
      pagination-rows-key="size"
    >
      <template #top>
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2">
          <v-btn color="primary" size="small" prepend-icon="mdi-plus" @click="onAdd">
            {{ t("add") }}
          </v-btn>
          <v-btn color="primary" size="small" prepend-icon="mdi-pencil" :disabled="!selectedRow" @click="onEdit(selectedRow)">
            {{ t("edit") }}
          </v-btn>
          <v-btn color="primary" size="small" prepend-icon="mdi-eye" :disabled="!selectedRow" @click="showModal = true">
            View
          </v-btn>

          <v-select
            v-model="filter.status"
            :items="TABLE_STATUS_FILTER_ITEMS"
            item-title="title"
            item-value="value"
            :label="t('status')"
            density="compact"
            variant="outlined"
            hide-details
            bg-color="surface"
            class="table-top-status-select"
            @update:model-value="getData"
          />

          <UInput v-model="filter.name" style="min-width: 23vw"
                  :placeholder="t('search_by_name')"/>
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </template>
      <template #row_id="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex flex-column align-items-left">
              
              <div>  
                <span class="text-secondary badge badge-pill" style="font-size: 15px">
                  {{ `${row?.id}` }}
                </span>
              </div>
              
            </div>
          </div>
        </td>
      </template>

      <template #row_email="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <span class="text-primary badge badge-pill" style="font-size: 15px">
                {{ `${row?.email}` }}
              </span>
            </div>
          </div>
        </td>
      </template>

      <template #row_role="{row}">
        <td>
          <div class="row">
            <div class="col-12 d-flex align-items-center">
              <span class="text-primary badge badge-pill" style="font-size: 15px">
                {{ `${row?.role.name}` }}
              </span>
            </div>
          </div>
        </td>
      </template>

      <template #row_status="{row}">
        <td>
          <div class="d-flex gap-2">
            <a class="badge bg-primary-subtle text-primary"
               :class="`bg-${row?.status === 'PASSIVE' ? 'danger' : 'primary'}-subtle`"> {{ row?.status }}</a>
          </div>
        </td>
      </template>

      <template #row_actions="{row}">
        <td class="last-col-sticky text-end" style="width: 30px; white-space: nowrap;">
          <div class="items-center">
            <UDropDown position="left" class="btn w-auto text-start p-0">
              <template #header>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="feather feather-more-vertical">
                  <circle cx="12" cy="12" r="1"></circle>
                  <circle cx="12" cy="5" r="1"></circle>
                  <circle cx="12" cy="19" r="1"></circle>
                </svg>
              </template>
              <template #body>
                <div class="bg-body rounded-1 font-size-15 p-2">
                  <v-btn v-if="row.status === 'ACTIVE'" size="small" variant="outlined" color="error" @click="updateStatus(row.id)">
                    {{ t("deactivate") }}
                  </v-btn>
                  <v-btn v-else size="small" variant="outlined" color="success" @click="updateStatus(row.id)">
                    {{ t("activate") }}
                  </v-btn>
                </div>
              </template>
            </UDropDown>
          </div>
        </td>
      </template>
    </UTable>
  </div>


  <!--  inspection modal-->
  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="calc(100vw - 700px)">
      <template #header>
        <div style="width: 100%">
          <div class="d-flex justify-content-between align-items-center">
            <div class="text-white">
              {{ data.id ? data.id : t('add') }} {{ t('user') }}
            </div>

            <v-btn icon variant="text" color="white" aria-label="Close" @click="onClose">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </div>
          <hr class="my-3 border-primary opacity-75">
        </div>
      </template>

      <template #body>
        <UForm @submit="onSave">
          <div class="row g-2"> <!-- g-3 adds uniform gutter spacing between all columns -->
            <div class="col-12">
              <div class="row g-4">
                <div class="col-6">
                  <UInput
                    v-model="data.username"
                    icon="mdi-number"
                    :label="t('username')"
                    :hint="t('enter_username')"
                    :name="t('username')"
                    :placeholder="t('username')"
                    :rules="(val) => (!val && $t('required'))"
                  />
                </div>
                <div class="col-6">
                  <UInput
                    v-model="data.password"
                    :label="t('password')"
                    :hint="t('password')"
                    :name="t('password')"
                    type="password"
                    :rules="(val) => (!val && $t('required'))"
                  />
                </div>
              </div>
              <hr class="my-4 border-primary opacity-75"> <!-- adds clear separation -->
            </div>
            <div>
              <div class="col-6">
                <UInput
                  v-model="data.email"
                  :label="t('email')"
                  :hint="t('email')"
                  :name="t('email')"
                  :rules="(val) => (!val && $t('required'))"
                />
              </div>
              <div class="col-6">
                <USelect v-model="data.status" :label="t('status')"
                          :items="[{name:'ACTIVE'},{name:'PASSIVE'}]" 
                          name="status"
                          option_name="name"
                          option_value="name"
                          classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>  
            </div>
            <div>
              <div class="col-6">
                <USelect v-model="data.role" :label="t('role')"
                          :items="[{name:'ADMIN'},{name:'USER'}]" name="Role"
                          option_name="name"
                          option_value="name"
                          classes="mb-2"
                          :rules="(val) => (!val && $t('required'))"
                ></USelect>
              </div>
            </div>
          </div>
          <!-- footer -->
          <div class="modal-footer d-flex justify-content-between align-items-center border-top border-primary pt-3 mt-3">
              <v-btn variant="tonal" color="secondary" @click="onClose">Cancel</v-btn>
              <v-btn type="submit" color="info">Save</v-btn>
          </div>
        </UForm>
      </template>
    </UDialog>
  </Teleport>

  <!--  inspection card-->
  <Teleport to="body">
    <UDialog :show="showModal && false" width="calc(100vw - 200px)" class="">
      <template #header>
        <div class="d-flex w-100 f-700">
          Inspection: {{ selectedRow.inspectionNumber }}
          <div class="text-end u-end">
            <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="showModal = false">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </div>
        </div>
      </template>

      <template #body>
        <UScrollArea height="calc(100vh - 200px)">
          <div class="u-scroll-slot-fill">
            <InspectionCard :data="selectedRow"/>
          </div>
        </UScrollArea>
      </template>
    </UDialog>
  </Teleport>
  

  <!--  inspection card-->
  <URightOverlay :isOpen="showModal" @close="showModal = false"
                 width="calc(100vw - 500px)" class="">
    <template #header>
      <div class="d-flex w-100 f-700 fs-4">
        Inspection: {{ selectedRow.inspectionNumber }}
        <div class="text-end u-end">
          <v-btn v-if="selectedRow.status === 'ACTIVE'" class="mx-3" size="small" variant="outlined" color="error" @click="updateStatus(selectedRow.id)">
            {{ t("deactivate") }}
          </v-btn>
          <v-btn v-else class="mx-3" size="small" variant="outlined" color="success" @click="updateStatus(selectedRow.id)">
            {{ t("activate") }}
          </v-btn>
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="showModal = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </div>
      </div>
    </template>

    <template #body>
      <UScrollArea height="calc(100vh - 50px)">
        <div class="u-scroll-slot-fill">
          <InspectionCard :data="selectedRow"/>
        </div>
      </UScrollArea>
    </template>
  </URightOverlay>
  
  <!--  file overlay-->
  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <div class="fw-bold text-white bg-primary p-2 rounded-2 d-flex p-2 mb-2">
        <div class="row">
          <div class="col-12 d-flex align-items-center font-size-14">
            {{ DOCUMENT_TYPES[selectedFileSection.data.type] }}
          </div>
          <div class="col-12 d-flex align-items-center font-size-11">
            {{ `${selectedRow?.inspectionNumber} ${selectedRow?.driverFirstName} ${selectedRow?.driverLastName}` }}
          </div>
        </div>
        <span class="text-end u-end">
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="selectedFileSection.dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </span>
      </div>
    </template>
    <template #body>
      <FileOverlay :url="`${URIS.INSPECTION}/attach-file`" :data="selectedFileSection.data"/>
    </template>
  </URightOverlay>
</template>



<style scoped>
.qm-badge {
  border: 1px solid transparent;
  border-radius: 3px;
  padding: 5px;
}

.custom-light-shadow {
  box-shadow: 0 0 2px #00000040 !important;
}

.permit-box {
  min-height: 64px;
  max-height: 84px;
  line-height: 140%;
  font-size: 11px;
  padding-bottom: 1px;
  padding-top: 1px;
}

.qm-badge--dim {
  background: #f6f6f6;
  padding: 3px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.justify-content-start {
  justify-content: flex-start !important;
}

.width-135px {
  width: 135px;
}

.min-height-64 {
  min-height: 64%;
}

.tab-panels-container {
  background-color: var(--bs-card-bg);
  border: 2px solid var(--bs-border-color, #e0e0e0);
  /* border-top: 2px solid var(--bs-border-color, #e0e0e0); */
  border-radius: 0 0 0.5rem 0.5rem;
  /* padding: 1rem; */
  margin-top: -2px;
  color: var(--bs-body-color);
  min-height: 400px;
  width: 100%;
  display: block;
  background-color: var(--bs-card-bg);
}

:deep(.tab-pane) {
  width: 100%;
  display: block;
}

:deep(.tab-pane.active) {
  animation: slideDownFade 0.6s ease-out;
}

@keyframes slideDownFade {
  from {
    opacity: 0;
    transform: translateY(-15px) scale(0.98);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.last-col-sticky {
  position: sticky;
  right: 0;
  z-index: 5;
}
</style>
