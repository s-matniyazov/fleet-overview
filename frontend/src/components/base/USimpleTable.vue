<script setup>
const props = defineProps({
  columns: {
    type: Array,
    required: true,
    default: [{
      key: '',
      name: '',
      styles: '',
      classes: '',
      label: ''
    }]
  },
  items: {
    type: Array,
    required: true,
    default: []
  },
  key: {
    type: String,
    required: false,
    default: 'id'
  },
  hideCols: {
    type: Boolean,
    required: false,
    default: false
  },
})

const selected = defineModel({});

const onRowClick = (row) => {
  if (selected.value && row[props.key] === selected.value[props.key]) {
    selected.value = null;
  } else {
    selected.value = row;
  }
}
</script>

<template>
  <div class="table-responsive row p-0">
    <table class="table align-middle datatable dt-responsive table-check nowrap table-hover"
           style="border-collapse: collapse; border-spacing: 0 8px; max-height: 300px">
      <thead v-if="!hideCols">
      <tr>
        <slot v-for="col in columns" :col="col" :name="`col_${col.name}`" :key="col.key">
          <th scope="col" :style="col.styles" :class="col.classes">
            {{ col.label }}
          </th>
        </slot>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(row, index) in items" :key="index" @click="(e) => {e.stopPropagation(); onRowClick(row);}"
          :class="row.id === selected?.id && 'table-light'"
      >
        <slot v-for="col in columns" :row="row" :name="`row_${col.name}`">
          <td>
            {{ row[col.name] }}
          </td>
        </slot>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.table-responsive thead th {
  position: sticky;
  top: 0;
  background: white;
  z-index: 10; /* Чтобы заголовок был поверх контента */
}
</style>
