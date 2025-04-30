<script setup>
import {onMounted,ref} from "vue";

import {useI18n} from "vue-i18n";

const {t} = useI18n();

const  columns = [
  {
    key:'id',
    name:'id',
    label:'',
    style:'width:50px',
    classes:'',
  },
  {
    key:'name',
    name:'name',
    label:('name'),
    style:'width:400px',
    classes:'',
  },
  {
    key:'address',
    name:'address',
    label:('address'),
    style:'width:600px',
    classes:'',
  },
  {
    key:'phone',
    name:'phone',
    label:('phone'),
    style:'width:200px',
    classes:'',
  },
  {
    key:'status',
    name:'status',
    label:('status'),
    style:'width:200px',
    classes:'',
  },
]

const newModal = () =>{
  return {
    id:null,
    name:null,
    description:null,
  }
}

onMounted(() => {

})

</script>

<template>
  <div class="h-100 col-12 p-2">
    <div class="container-fluid mb-2 px-2 d-flex align-items-center"
         style="border: 1px dashed #eae1e1; border-radius: 5px; height: 50px; font-weight: bold; font-size: 16px;">

    </div>


  </div>
</template>

<style scoped>

</style>