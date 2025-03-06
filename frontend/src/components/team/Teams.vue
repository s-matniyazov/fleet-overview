<script setup>
import {onMounted, ref} from "vue";

import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import UTable from "@/components/base/UTable.vue";
import UInput from "@/components/base/UInput.vue";
import UTextarea from "@/components/base/UTextarea.vue";
import {longToDateTime, showMessage} from "@/util/utils.js";
import UDialog from "@/components/base/UDialog.vue";
import {Stack} from "@/util/stack.js";
import USelect from "@/components/base/USelect.vue";
import USimpleTable from "@/components/base/USimpleTable.vue";
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
    key: 'members',
    name: 'members',
    label: t('members'),
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

const memberColumns = [
  {
    key: 'id',
    name: 'id',
    label: '',
    styles: '',
    classes: '',
  },
  {
    key: 'name',
    name: 'name',
    label: t('NSP'),
    styles: '',
    classes: '',
  },
  {
    key: 'email',
    name: 'email',
    label: t('email'),
    styles: '',
    classes: '',
  }
]

const newModel = () => {
  return {
    id: null,
    name: null,
    description: null,
    members: []
  }
}

const newUserModel = () => {
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

const users = ref([]);
const members = ref(new Stack([newUserModel()]));

const apiUrl = URIS.TEAMS;
const dataList = ref([]);
const data = ref(newModel())
const selectedRow = ref();

// FUNCTIONS
const onAdd = () => {
  data.value = newModel();

  addModal.value = true;
}
const onAddMember = () => {
  let user = users.value.find(it => it?.id === members.value.peek().id)
  members.value.pop();
  members.value.push({
    id: user?.id,
    name: user?.name,
    departmentsId: user?.department.id,
    positionsId: user?.position.id,
    email: user?.email,
    rolesId: user?.rolesId
  });

  members.value.push(newUserModel());
}
const onEdit = (d) => {
  data.value = {...d};

  addModal.value = true;
}
const onClose = () => {
  addModal.value = false;
  members.value = new Stack([newUserModel()]);
}

// API FUNCTIONS
const onSave = () => {
  data.value.members = members?.value.getItems().map(it => { return {usersId: it.id}});

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
    axiosIns.delete(apiUrl, { data: d })
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

function getUsers() {
  axiosIns.get(URIS.USERS)
      .then(res => {
        users.value = res.data.data;
      }).catch(e => {
    showMessage(e)
  });
}

// HOOKS
onMounted(() => {
  getData();
  getUsers();
})
</script>

<template>
  <div class="mb-0">
    <div class="row align-items-center">
      <div class="col-12">
        <div class="d-flex flex-wrap align-items-center justify-content-start gap-2 mb-2">
          <div>
            <button @click="onAdd" class="btn btn-primary btn-sm mx-1"><span class="mdi mdi-plus"></span> {{ t('add') }}</button>
            <button @click="onEdit(selectedRow)" class="btn btn-primary btn-sm" :disabled="!selectedRow"><span class="mdi mdi-pen"></span> {{ t('edit') }} </button>
            <button @click="onDelete(selectedRow)" class="btn btn-primary btn-sm mx-1" :disabled="!selectedRow"><span class="mdi mdi-delete"></span> {{ t('delete') }} </button>
            <button @click="getData" class="btn btn-primary btn-sm"><span class="mdi mdi-reload"></span></button>
          </div>
        </div>
      </div>

      <UTable :items="dataList" :columns="columns" v-model="selectedRow">
        <template #row_name="{row}">
          <td>
            <div class="d-flex gap-2">
              <a class="badge bg-primary-subtle text-primary">{{row?.name}}</a>
            </div>
          </td>
        </template>

        <template #row_members="{row}">
          <td>
            <div class="d-flex gap-2">
              <a v-for="member in row?.members" class="badge bg-primary-subtle text-primary">{{member?.user.name}}</a>
            </div>
          </td>
        </template>

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
            {{ data.id ? 'Изменить' : 'Добавить' }} {{t('team')}}
          </div>
          <div class="text-end" style="right: 0; margin-left: auto">
            <button class="btn-close" @click="onClose"></button>
          </div>
        </div>
      </template>

      <template #body>
        <form class="needs-validation" name="event-form" novalidate="">
          <div class="row p-0">
            <!--            name-->
            <div class="col-12 p-0">
              <UInput v-model="data.name" :label="t('name')" :hint="t('name')" :name="t('name')" :placeholder="t('u_team_name')" classes="mb-3" />
            </div>

            <!--            description-->
            <div class="col-12 p-0">
              <UTextarea v-model="data.description" :label="t('description')" :placeholder="t('short_team_description')" classes="mb-3" />
            </div>

            <!--            members-->
            <div class="col-12 row p-0 m-1 border">
              <div class="col-12 mb-2 form-label text-center">
                <label class="form-label">{{ t('members') }}</label>
              </div>
              <div class="col-10">
                <USelect v-model="members.peek().id"
                         :items="users.filter(it => !members.getItems().map(it => it.id).includes(it.id))"
                         option_name="name"
                         classes="mb-3"
                ></USelect>
              </div>
              <div class="col-2">
                <button @click="(e) => {onAddMember(); e.stopPropagation()}" type="button" class="btn btn-primary btn-sm mb-3"><span class="mdi mdi-plus"></span></button>
              </div>
              <div class="col-12">
                <USimpleTable :items="members.getItems()" :columns="memberColumns" :hide-cols="true"/>
              </div>

              <div class="col-5">
                <ul class="list-group p-0" style="width: 100%">
                  <li class="list-group-item d-flex" v-for="item in users.filter(it => !members.getItems().map(it => it.id).includes(it.id))">
                    <input class="form-check-input me-1" type="checkbox" v-model="item.checked">
                    {{ item?.name }}
                  </li>
                </ul>
              </div>
              <div class="col-2 bg-body"></div>
              <div class="col-5">
                <ul class="list-group p-0" style="width: 100%">
                  <li class="list-group-item d-flex" v-for="item in members.getItems()">
                    <input class="form-check-input me-1" type="checkbox" v-model="item.checked">
                    {{ item?.name }}
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </form>
      </template>
    </UDialog>
  </Teleport>
</template>

<style scoped>

</style>
