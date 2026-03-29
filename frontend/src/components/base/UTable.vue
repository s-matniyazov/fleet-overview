<script setup>
import UScrollArea from "@/components/base/UScrollArea.vue";

const props = defineProps({
  rowKey: {
    type: String,
    required: false,
    default: 'id'
  },
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
  styles: {
    type: String,
    required: false,
    default: 'height: calc(100vh - 328px)'
  },
  height: {
    type: String,
    required: false,
    default: 'height: calc(100vh - 328px)'
  },
  hidePagination: {
    type: Boolean,
    required: false,
    default: false
  },
  classes: {
    type: String,
    required: false,
    default: ""
  },

})

const emits = defineEmits(['row-dblclick']);

const selected = defineModel({});

const onRowClick = (row) => {
  if (selected.value && row[props.rowKey] === selected.value[props.rowKey]) {
    selected.value = null;
  } else {
    selected.value = row;
  }
}

const onRowDoubleClick = (row) => {
  emits('row-dblclick', row);
}
</script>

<template>
  <div class="table-responsive row p-0">
    <div class="col-12">
      <UScrollArea :height="height">
        <table class="table align-middle datatable dt-responsive table-check nowrap table-hover rounded-4">
          <thead>
          <tr>
            <slot v-for="col in columns" :col="col" :name="`col_${col.name}`" :key="col.key">
              <th scope="col" :style="col.styles" :class="col.classes" style="text-transform: uppercase;">
                {{ col.label }}
              </th>
            </slot>
          </tr>
          </thead>

          <tbody>
          <tr v-for="(row, index) in items" :key="index" @click="(e) => { e.stopPropagation(); onRowClick(row); }"
              @dblclick="(e) => { e.stopPropagation(); onRowDoubleClick(row); }"
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
      </UScrollArea>
    </div>
  </div>
</template>

<style scoped>
.table-responsive thead th {
  top: 0;
  z-index: 100;
  overflow: visible;
}

.table-responsive thead {
}

.last-col-sticky {
  position: sticky;
  right: 0;
  background: #f8f9fa;
  z-index: 10;
}


</style>
