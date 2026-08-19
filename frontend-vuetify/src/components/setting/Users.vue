<script setup>
import { computed, onMounted, ref, watch } from "vue";
import axiosIns from "@/plugins/axios.js";
import { URIS } from "@/constants/UriConstants.js";
import { TABLE_STATUS_FILTER_ITEMS } from "@/constants/tableFilters.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import { useI18n } from "vue-i18n";
import { showMessage } from "@/util/utils.js";
import UForm from "@/components/base/UForm.vue";
import UDropDown from "@/components/base/UDropDown.vue";
import UDialog from "@/components/base/UDialog.vue";
import { mapSelectItems } from "@/util/selectItems.js";

const { t } = useI18n();

const columns = [
  { key: 'id',      name: 'id',      label: t('id'),      styles: 'min-width: 80px;',  classes: '' },
  { key: 'name',    name: 'name',    label: t('name'),    styles: 'min-width: 160px;', classes: '' },
  { key: 'email',   name: 'email',   label: t('email'),   styles: 'min-width: 200px;', classes: '' },
  { key: 'role',    name: 'role',    label: t('role'),    styles: 'min-width: 120px;', classes: '' },
  { key: 'status',  name: 'status',  label: t('status'),  styles: '',                  classes: '' },
  { key: 'actions', name: 'actions', label: t('actions'), styles: '',                  classes: 'last-col-sticky' },
]

const newModel = () => ({
  id: null,
  email: null,
  name: null,
  password: null,
  role: null,
  phone_number: null,
  status: null,
})

const addModal = ref(false);
const apiUrl = URIS.USERS;
const pagination = ref({ size: 10, page: 1, hasNext: true });
const filter = ref({ username: null, status: 'ACTIVE' });
const dataList = ref([]);
const data = ref(newModel());
const selectedRow = ref();

const roleItems = computed(() =>
  mapSelectItems([{ name: 'USER' }], "name", "name"),
);
const statusItems = computed(() =>
  mapSelectItems([{ name: 'ACTIVE' }, { name: 'PASSIVE' }], "name", "name"),
);

const onAdd = () => { data.value = newModel(); addModal.value = true; }
const onEdit = (d) => { data.value = { ...d }; addModal.value = true; }
const onClose = () => { addModal.value = false; }

const onSave = () => {
  axiosIns.post(apiUrl, data.value)
    .then(() => { getData(); onClose(); })
    .catch(e => showMessage(e));
}

const updateStatus = (row) => {
  axiosIns.post(`${apiUrl}/${row.id}/toggle-status`)
    .then(() => getData())
    .catch(e => showMessage(e));
}

function getData() {
  axiosIns.get(apiUrl)
    .then(res => {
      dataList.value = res.data.data;
      selectedRow.value = null;
      pagination.value.hasNext = dataList.value.length >= pagination.value.size;
    })
    .catch(e => showMessage(e));
}

onMounted(() => getData());

watch(() => pagination.value.page, () => getData());
watch(() => pagination.value.size, () => getData());
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

          <UInput v-model="filter.username" style="min-width: 23vw" :placeholder="t('search_by_name')" />
          <v-btn color="primary" size="small" icon variant="text" @click="getData">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </div>
      </template>

      <template #row_role="{ row }">
        <td>{{ row?.role?.join(', ') ?? '' }}</td>
      </template>

      <template #row_status="{row}">
        <td>
          <v-chip
            size="small"
            :color="row?.status === 'PASSIVE' ? 'error' : 'success'"
            variant="tonal"
          >
            {{ row?.status }}
          </v-chip>
        </td>
      </template>

      <template #row_actions="{row}">
        <td class="last-col-sticky">
          <div class="items-center">
            <UDropDown position="left" class="btn w-auto text-start p-0">
              <template #header>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="1"></circle>
                  <circle cx="12" cy="5" r="1"></circle>
                  <circle cx="12" cy="19" r="1"></circle>
                </svg>
              </template>
              <template #body>
                <div class="bg-body rounded-1 p-2">
                  <v-btn v-if="row.status === 'ACTIVE'" size="small" variant="outlined" color="error" @click="updateStatus(row)">
                    {{ t("deactivate") }}
                  </v-btn>
                  <v-btn v-else size="small" variant="outlined" color="success" @click="updateStatus(row)">
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

  <Teleport to="body">
    <UDialog :show="addModal" @close="addModal = false" width="500px">
      <template #header>
        <div class="d-flex align-center w-100">
          <span class="text-subtitle-1 font-weight-bold flex-grow-1">
            {{ data.id ? t('edit') : t('add') }} {{ t('user') }}
            <span v-if="data.id" class="text-caption text-medium-emphasis ms-1">#{{ data.id }}</span>
          </span>
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="onClose">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </div>
      </template>

      <template #body>
        <UForm id="settings-users-form" @submit="onSave">
          <div class="row">
            <div class="col-6 mb-3">
              <UInput
                  v-model="data.email"
                  label="Email"
                  :name="t('email')"
                  type="email"
                  :rules="(val) => (!val && $t('required'))"
              />
            </div>
            <div class="col-6 mb-3">
              <UInput
                v-model="data.password"
                label="Password"
                :name="t('password')"
                type="password"
                :rules="(val) => (!val && $t('required'))"
              />
            </div>
            <div class="col-12 mb-3">
              <UInput
                  v-model="data.name"
                  label="Full Name"
                  :name="t('name')"
                  :placeholder="t('enter_full_name')"
                  :rules="(val) => (!val && $t('required'))"
              />
            </div>
            <div class="col-6 mb-3">
              <v-select
                v-model="data.role"
                :items="roleItems"
                item-title="title"
                item-value="value"
                :label="t('role')"
                variant="outlined"
                density="compact"
                hide-details="auto"
                clearable
                bg-color="surface"
              />
            </div>
          </div>
        </UForm>
      </template>
      <template #actions>
        <v-btn type="submit" form="settings-users-form" color="primary">{{ t('save') }}</v-btn>
      </template>
    </UDialog>
  </Teleport>
</template>

<style scoped>
.last-col-sticky {
  position: sticky;
  right: 0;
  z-index: 5;
}
</style>
