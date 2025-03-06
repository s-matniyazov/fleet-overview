<script setup>
import Tasks from "@/components/task/Tasks.vue";
import TaskPriorities from "@/components/task/TaskPriorities.vue";
import TaskTypes from "@/components/task/TaskTypes.vue";
import TaskStatuses from "@/components/task/TaskStatuses.vue";
import {ref} from "vue";
import UTabPanels from "@/components/base/UTabPanels.vue";
import UTabs from "@/components/base/UTabs.vue";
import {useI18n} from "vue-i18n";
import TabPanels from "@/components/base/UTabPanels.vue";
import Tabs from "@/components/base/UTabs.vue";

const {t} = useI18n();
const TABS = [
  {
    key:'tk',
    name:t('tasks'),
    description:'',
    component:Tasks,
  },
  {
    key:'tkp',
    name:t('priority_manual') ,
    description:'',
    component:TaskPriorities,
  },
  {
    key:'tkt',
    name: t('task_type_manual'),
    description:'',
    component:TaskTypes,
  },
  {
    key:'tks',
    name:t('status_manual'),
    description:'',
    component:TaskStatuses,
  }
];
const  activeTab = ref(TABS[0].key)
</script>

<template>

  <div class="row">
    <div class="col-12">
      <div class="card">
        <div class="card-header align-items-center d-flex">
          <h4 class="card-title mb-0 flex-grow-1">{{TABS[0].name}}</h4>
          <div class="flex-shrink-0">
            <tabs :tabs="TABS" v-model="activeTab"/>
          </div>
        </div>

        <div class="card-body">
          <tab-panels :tabs="TABS" v-model="activeTab"/>
        </div>

      </div>
    </div>
  </div>

</template>

<style scoped>

</style>
