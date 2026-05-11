<script setup>
import DocumentMiniCard from "@/components/DocumentMiniCard.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import {ref} from "vue";
import {DOCUMENT_TYPES, downloadResource, SAFETY_TYPE_NAMES} from "@/util/utils.js";
import {URIS} from "@/constants/UriConstants.js";
import {useDriverFileStore} from "@/store/DriverFileStore.js";

const driverFileStore = useDriverFileStore();

const selectFileSection = (type, url) => {
  const file = driverFileStore.allDocuments().find(it => it.type===type) || {}

  selectedFileSection.value = {
    dialog: true,
    url: url,
    data: {
      ...file,
      fileName: file.resource?.fileName,
      size: file.resource?.size,
      driverId: props.data.id,
      type: type
    }
  };
}

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const selectedFileSection = ref({
  dialog: false,
  url: '',
  data: {
    description: '',
    expirationDate: new Date(),
    type: '',
    driverId: ''
  }
});

function downloadAll(type) {
  if (type === 'driverFiles') {
    SAFETY_TYPE_NAMES.forEach(item => {
      const resource = driverFileStore.files.find(it => it.type === item.key)?.resource
      if (resource) {
        downloadResource(resource)
      }
    })
  }
}

</script>

<template>
  <v-sheet border rounded="lg" color="surface" class="driver-documents-sheet">
    <div class="files-container">
      <div class="d-flex flex-wrap align-center justify-space-between ga-3 mb-4">
        <div class="text-subtitle-1 font-weight-bold text-high-emphasis">
          General Documents
        </div>
        <v-btn
          variant="tonal"
          prepend-icon="mdi-cloud-download-outline"
          @click="downloadAll('driverFiles')"
        >
          Download All Files
        </v-btn>
      </div>
      <div class="row">
        <div
          v-for="item in SAFETY_TYPE_NAMES"
          :key="item.key"
          class="col-12 col-md-6 mb-6 cursor-pointer"
        >
          <DocumentMiniCard
            :file="driverFileStore.files.find(it => it.type === item.key)"
            :type="item.key"
            :name="item.value"
            @click="
              (e) => {
                selectFileSection(item.key, `${URIS.DRIVER}/attach-file`);
                e.stopPropagation();
              }
            "
          />
        </div>
      </div>
    </div>
  </v-sheet>

  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <div class="d-flex align-center justify-space-between pa-2 rounded-lg bg-primary">
        <span class="text-subtitle-2 font-weight-bold text-on-primary">
          {{ DOCUMENT_TYPES[selectedFileSection.data.type] }}
        </span>
        <v-btn icon variant="text" density="comfortable" color="on-primary" aria-label="Close"
          @click="driverFileStore.init(data.id); selectedFileSection.dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </div>
    </template>
    <template #body>
      <FileOverlay :url="selectedFileSection.url" :data="selectedFileSection.data"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
.driver-documents-sheet {
  margin: 8px;
  padding: 16px 18px;
}

.row {
  --bs-gutter-x: 1.25rem;
  --bs-gutter-y: 0;
  display: flex;
  flex-wrap: wrap;
  margin-right: calc(-0.5 * var(--bs-gutter-x));
  margin-left: calc(-0.5 * var(--bs-gutter-x));
}
</style>