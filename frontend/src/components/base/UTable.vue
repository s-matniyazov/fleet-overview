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
  }
})

const selected = defineModel({});

const onRowClick = (row) => {
  if (selected.value && row[props.rowKey] === selected.value[props.rowKey]) {
    selected.value = null;
  } else {
    selected.value = row;
  }
}
</script>

<template>
  <div class="table-responsive row p-0">
    <div class="col-12">
      <UScrollArea :height="height">
        <table class="table align-middle datatable dt-responsive table-check nowrap table-hover">
          <thead>
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
      </UScrollArea>
    </div>

    <div class="col-12 d-flex align-items-center px-2">
      <div class="d-flex align-items-center gap-2" style="right: 0; margin-left: auto">
        <ul class="pagination pagination-sm ul-style">
          <li class="page-item cursor-pointer"><a class="page-link">&laquo;</a></li>
          <li class="page-item active cursor-not-allowed"><a class="page-link">1</a></li>
          <li class="page-item cursor-pointer"><a class="page-link">&raquo;</a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.table-responsive thead th {
  position: sticky;
  top: 0;
  z-index: 10; /* Чтобы заголовок был поверх контента */
}
</style>
