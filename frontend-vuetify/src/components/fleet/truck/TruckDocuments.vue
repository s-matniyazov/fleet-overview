<script setup>
import {useTruckFileStore} from "@/store/TruckFileStore.js";
import DocumentMiniCard from "@/components/DocumentMiniCard.vue";
import URightOverlay from "@/components/base/URightOverlay.vue";
import FileOverlay from "@/components/FileOverlay.vue";
import {ref} from "vue";
import {DOCUMENT_TYPES, downloadResource, FLEET_TYPE_NAMES, makeResourceEntity, PERMIT_NAMES} from "@/util/utils.js";
import {URIS} from "@/constants/UriConstants.js";

const truckFileStore = useTruckFileStore();

const selectFileSection = (type, url) => {
  const file = truckFileStore.allDocuments().find(it => it.type===type) || {}

  selectedFileSection.value = {
    dialog: true,
    url: url,
    data: {
      ...file,
      fileName: file.resource?.fileName,
      size: file.resource?.size,
      truckId: props.data.id,
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
    truckId: ''
  }
});

function downloadAll(type) {
  if (type === 'truckFiles') {
    FLEET_TYPE_NAMES.forEach(item => {
      const resource = truckFileStore?.files.find(it => it.type === item.key)?.resource
      if (resource) {
        downloadResource(resource)
      }
    })
  } else if (type === 'permits') {
    PERMIT_NAMES.forEach(item => {
      const resource = truckFileStore?.permits.find(it => it.type === item.key)?.resource
      if (resource) {
        downloadResource(resource)
      }
    })
  }
}

</script>

<template>
  <div class="equipment-files-wrapper custom-light-shadow">
    <div class="files-container ng-star-inserted">
      <div class="row justify-content-center align-items-center mb-3">
        <div class="col-6">
          <div class="font-size-20 fw-bold text-secondary"> General Documents</div>
        </div>
        <div class="col-6 font-size-16 text-end">
          <v-btn variant="tonal" prepend-icon="mdi-cloud-download-outline" @click="downloadAll('truckFiles')">
            Download All Files
          </v-btn>
        </div>
      </div>
      <div class="row">
        <div v-for="item in FLEET_TYPE_NAMES"
             class="col-6 mb-8 mt-2 cursor-pointer ng-star-inserted">
          <DocumentMiniCard
              @click="(e) => {selectFileSection(item.key, `${URIS.TRUCK}/attach-file`); e.stopPropagation()}"
              :file="truckFileStore.files.find(it => it.type===item.key)"
              :type="item.key" :name="item.value"
          />
        </div>
      </div>
    </div>

    <div class="files-container ng-star-inserted mt-4">
      <div class="row justify-content-center align-items-center mb-3">
        <div class="col-6">
          <div class="font-size-20 fw-bold text-secondary"> Permits</div>
        </div>
        <div class="col-6 font-size-16 text-end">
          <v-btn variant="tonal" prepend-icon="mdi-cloud-download-outline" @click="downloadAll('permits')">
            Download All Files
          </v-btn>
        </div>
      </div>
      <div class="row">
        <div v-for="item in PERMIT_NAMES"
             class="col-6 mb-8 mt-2 cursor-pointer ng-star-inserted">
          <DocumentMiniCard
              @click="(e) => {selectFileSection(item.key, `${URIS.TRUCK}/${data?.id}/attach-permit`); e.stopPropagation()}"
              :file="truckFileStore.permits.find(it => it.type===item.key)"
              :type="item.key" :name="item.value"
          />
        </div>
      </div>
    </div>
  </div>

  <URightOverlay :isOpen="selectedFileSection.dialog" @close="selectedFileSection.dialog = false">
    <template #header>
      <h4 class="fw-bold text-white bg-primary p-2 rounded-2 d-flex">{{
          DOCUMENT_TYPES[selectedFileSection.data.type]
        }}
        <span class="text-end u-end">
          <v-btn icon variant="text" density="comfortable" aria-label="Close" @click="truckFileStore.init(data.id); selectedFileSection.dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </span>
      </h4>
    </template>
    <template #body>
      <FileOverlay :url="selectedFileSection.url" :data="selectedFileSection.data"/>
    </template>
  </URightOverlay>
</template>

<style scoped>
.equipment-files-wrapper {
  margin: 10px;
  padding: 10px;
}

.custom-light-shadow {
  box-shadow: 0 0 2px #00000040 !important;
}

.mb-3 {
  margin-bottom: .46875rem !important;
}

.align-items-center {
  align-items: center !important;
}

.justify-content-center {
  justify-content: center !important;
}

.row {
  --bs-gutter-x: 1.5rem;
  --bs-gutter-y: 0;
  display: flex;
  flex-wrap: wrap;
  margin-top: calc(-1 * var(--bs-gutter-y));
  margin-right: calc(-.5 * var(--bs-gutter-x));
  margin-left: calc(-.5 * var(--bs-gutter-x));
}

.custom-light-shadow {
  box-shadow: 0 0 2px #00000040 !important;
}

.qm-badge {
  border: 1px solid transparent;
  border-radius: 3px;
  padding: 5px;
}

.btn:disabled, .btn.disabled, fieldset:disabled .btn {
  pointer-events: none;
  opacity: .65;
}

.btn {
  font-size: 14px;
}

.btn {
  display: inline-block;
  font-weight: 700;
  line-height: 1.5;
  color: #212529;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  -webkit-user-select: none;
  user-select: none;
  background-color: transparent;
  border: 1px solid transparent;
  padding: 5px 12px;
  font-size: .875rem;
  border-radius: 3px;
  transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}
</style>