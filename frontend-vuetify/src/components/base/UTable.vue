<script setup>
import { computed, useSlots } from "vue";
import UScrollArea from "@/components/base/UScrollArea.vue";

const props = defineProps({
  rowKey: {
    type: String,
    required: false,
    default: "id",
  },
  columns: {
    type: Array,
    required: true,
    default: () => [
      {
        key: "",
        name: "",
        styles: "",
        classes: "",
        label: "",
      },
    ],
  },
  items: {
    type: Array,
    required: true,
    default: () => [],
  },
  styles: {
    type: String,
    required: false,
    default: "",
  },
  height: {
    type: String,
    required: false,
    default: "100%",
  },
  /** When true, built-in bottom pagination is hidden (e.g. file lists). */
  hidePagination: {
    type: Boolean,
    required: false,
    default: false,
  },
  /** API key for page size on the pagination object (e.g. `size` for inspections). */
  paginationRowsKey: {
    type: String,
    default: "rowsPerPage",
  },
  pageSizeOptions: {
    type: Array,
    default: () => [5, 10, 20, 50],
  },
  classes: {
    type: String,
    required: false,
    default: "",
  },
});

const emits = defineEmits(["row-dblclick"]);

const selected = defineModel({});
const pagination = defineModel("pagination", { required: false });

const slots = useSlots();

/** Explicit height from `styles` or `height` (e.g. calc/vh/px); otherwise fill parent via flex. */
const explicitTableHeight = computed(() => {
  const m = props.styles?.match?.(/height\s*:\s*([^;]+)/i);
  const raw = m ? m[1].trim() : props.height;
  if (!raw || raw === "100%" || raw === "auto") return null;
  return raw;
});

const tableOuterStyle = computed(() => {
  if (explicitTableHeight.value) {
    return { height: explicitTableHeight.value, minHeight: 0 };
  }
  return { flex: "1 1 auto", minHeight: 0, height: "100%" };
});

const showBuiltinPagination = computed(
  () =>
    pagination.value != null &&
    !props.hidePagination &&
    slots.bottom == null,
);

const showBottomSection = computed(
  () => slots.bottom != null || showBuiltinPagination.value,
);

const headers = computed(() =>
  props.columns.map((c) => ({
    title: c.label,
    key: c.name,
    sortable: false,
    headerProps: {
      style: [c.styles, "text-transform: uppercase"].filter(Boolean).join(";"),
      class: c.classes,
    },
  })),
);

const rowsPerPageValue = computed({
  get: () => pagination.value?.[props.paginationRowsKey],
  set: (v) => {
    if (!pagination.value) return;
    const n = typeof v === "string" ? Number(v) : v;
    pagination.value[props.paginationRowsKey] = n;
    pagination.value.page = 1;
  },
});

const pagePrev = () => {
  if (!pagination.value || pagination.value.page <= 1) return;
  pagination.value.page--;
};

const pageNext = () => {
  if (!pagination.value || !pagination.value.hasNext) return;
  pagination.value.page++;
};

const onRowClick = (row) => {
  if (selected.value && row[props.rowKey] === selected.value[props.rowKey]) {
    selected.value = null;
  } else {
    selected.value = row;
  }
};

const onRowDoubleClick = (row) => {
  emits("row-dblclick", row);
};

const isRowSelected = (row) =>
  selected.value && row[props.rowKey] === selected.value[props.rowKey];
</script>

<template>
  <div
    class="ut-page-table w-100"
    :class="classes"
    :style="tableOuterStyle"
  >
    <div class="ut-page-table__inner d-flex flex-column">
      <div v-if="slots.top" class="ut-page-table__top">
        <slot name="top" />
      </div>

      <div class="ut-page-table__scroll">
        <UScrollArea height="100%">
          <v-data-table
            :headers="headers"
            :items="items"
            :item-value="rowKey"
            class="ut-v-datatable align-middle nowrap"
            hover
            :mobile="false"
            items-per-page="-1"
            hide-default-footer
          >
            <template #headers>
              <tr>
                <slot
                  v-for="col in columns"
                  :key="col.key || col.name"
                  :name="`col_${col.name}`"
                  :col="col"
                >
                  <th
                    scope="col"
                    class="v-data-table__th"
                    :style="[col.styles, { textTransform: 'uppercase' }]"
                    :class="col.classes"
                  >
                    {{ col.label }}
                  </th>
                </slot>
              </tr>
            </template>

            <template #body="{ items: bodyItems }">
              <tr
                v-for="(row, index) in bodyItems"
                :key="row[rowKey] ?? index"
                class="v-data-table__tr v-data-table__tr--clickable"
                :class="{ 'table-light': isRowSelected(row) }"
                @click.stop="onRowClick(row)"
                @dblclick.stop="onRowDoubleClick(row)"
              >
                <slot
                  v-for="col in columns"
                  :key="col.key || col.name"
                  :name="`row_${col.name}`"
                  :row="row"
                >
                  <td class="v-data-table__td">{{ row[col.name] }}</td>
                </slot>
              </tr>
            </template>
          </v-data-table>
        </UScrollArea>
      </div>

      <div v-if="showBottomSection" class="ut-page-table__bottom">
        <slot name="bottom">
          <div
            v-if="showBuiltinPagination && pagination"
            class="ut-page-table__pagination d-flex align-center justify-center flex-wrap gap-2"
          >
            <v-select
              v-model="rowsPerPageValue"
              :items="pageSizeOptions"
              density="compact"
              variant="outlined"
              hide-details
              class="ut-page-table__page-size"
            />
            <div class="d-flex align-center">
              <v-btn
                icon
                size="small"
                variant="text"
                :disabled="pagination.page <= 1"
                @click="pagePrev"
              >
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
              <span class="text-body-2 mx-1 text-medium-emphasis">{{
                pagination.page
              }}</span>
              <v-btn
                icon
                size="small"
                variant="text"
                :disabled="!pagination.hasNext"
                @click="pageNext"
              >
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </div>
          </div>
        </slot>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ut-page-table__inner {
  min-height: 0;
  flex: 1 1 auto;
  height: 100%;
  overflow: hidden;
}

.ut-page-table {
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
  min-height: 0;
  width: 100%;
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  border-radius: 12px;
  overflow: hidden;
  background: rgb(var(--v-theme-surface));
}

.ut-page-table__top {
  flex-shrink: 0;
  padding: 12px 16px;
  border-bottom: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgb(var(--v-theme-surface));
}

.ut-page-table__scroll {
  flex: 1 1 auto;
  min-height: 0;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.ut-page-table__scroll :deep(.scroll-container) {
  flex: 1 1 auto;
  min-height: 0;
  height: auto !important;
}

.ut-page-table__bottom {
  flex-shrink: 0;
  padding: 8px 16px;
  border-top: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  background: rgba(var(--v-theme-surface-variant), 0.35);
}

.ut-page-table__page-size {
  max-width: 96px;
}

.ut-page-table :deep(thead th) {
  position: sticky;
  top: 0;
  z-index: 4;
  overflow: visible;
  background: rgb(var(--v-theme-surface));
  box-shadow: inset 0 -1px 0 rgba(var(--v-border-color), var(--v-border-opacity));
  vertical-align: middle;
  padding-top: 10px;
  padding-bottom: 10px;
}

.ut-v-datatable :deep(tbody td) {
  vertical-align: middle;
  padding-top: 10px;
  padding-bottom: 10px;
}

.ut-v-datatable :deep(th.last-col-sticky),
.ut-v-datatable :deep(td.last-col-sticky) {
  position: sticky !important;
  right: 0;
  min-width: 4.5rem;
  background: rgb(var(--v-theme-surface)) !important;
  border-left: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  box-shadow: -6px 0 12px -8px rgba(0, 0, 0, 0.35);
}

.ut-v-datatable :deep(thead th.last-col-sticky) {
  z-index: 6;
  background: rgb(var(--v-theme-surface-variant)) !important;
}

.ut-v-datatable :deep(tbody td.last-col-sticky) {
  z-index: 3;
}

.ut-v-datatable.v-table--hover :deep(tbody tr:hover td.last-col-sticky) {
  background: rgb(var(--v-theme-surface)) !important;
}

.ut-v-datatable.v-table--hover :deep(tbody tr:hover td.last-col-sticky)::after {
  background: rgba(var(--v-border-color), var(--v-hover-opacity)) !important;
}

.ut-v-datatable :deep(tr.table-light td.last-col-sticky) {
  background-color: rgba(var(--v-theme-primary), 0.16) !important;
}

.ut-v-datatable.v-table--hover :deep(tr.table-light:hover td.last-col-sticky) {
  background-color: rgba(var(--v-theme-primary), 0.22) !important;
}
</style>
