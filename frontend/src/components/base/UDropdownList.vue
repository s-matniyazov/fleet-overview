<script setup>
import {ref} from "vue";

const props = defineProps({
  label: {
    type: String,
    required: false,
    default: 'Select'
  },
  hint: {
    type: String,
    required: false,
    default: 'Please select item'
  },
  option_name: {
    type: String,
    required: false,
    default: 'id'
  },
  option_value: {
    type: String,
    required: false,
    default: 'id'
  },
  items: {
    type: Array,
    required: true,
    default: []
  },
  classes: {
    type: String,
    required: false,
    default: ''
  },
  styles: {
    type: String,
    required: false,
    default: ''
  },
})


const model = defineModel({});
const selected = ref({});

const selectItem = (item) => {
  selected.value = item;
  model.value = selected.value[props.option_value]
}

const open = ref(false);

</script>

<template>
  <div class="dropdown" @click="e => {e.stopPropagation(); open = !open;}">
    <button class="btn btn-primary" @click="e => {e.stopPropagation()}" >{{ selected && selected[option_name] }}</button>
    <ul v-if="open" class="dropdown-menu">
      <li v-for="item in items" :key="item[option_value]" @click="selectItem(item)">
        {{ item[option_name] }}
      </li>
    </ul>
  </div>
</template>

<style scoped>
.dropdown {
  position: relative;
  display: inline-block;
  cursor: pointer;
}
.dropdown-menu {
  position: absolute;
  background: white;
  list-style: none;
  padding: 0;
  margin: 0;
  border: 1px solid #ddd;
  width: 100%;
}
.dropdown-menu li {
  padding: 5px 10px;
  cursor: pointer;
}
.dropdown-menu li:hover {
  background: lightblue;
}
</style>
