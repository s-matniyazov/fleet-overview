<template>
  <div class="container">

    <!-- Upload Drop Zone -->
    <div
        class="upload-box mb-4"
        @dragover.prevent="isDragging = true"
        @dragleave.prevent="isDragging = false"
        @drop.prevent="handleDrop"
        :class="{ 'dragging': isDragging }"
    >
      <label for="fileInput" class="text-center w-100 d-block">
        <i class="bi bi-cloud-arrow-up upload-icon"></i>
        <p class="mt-2 mb-1">Click to upload or drag and drop</p>
        <p class="text-muted small">JPEG, JPG, PNG, PDF</p>
        <input
            type="file"
            id="fileInput"
            class="d-none"
            accept=".jpg,.jpeg,.png,.pdf"
            multiple
            @change="handleFileInput"
        />
      </label>
    </div>

    <!-- Uploaded Files List -->

    <div
        v-for="(upload, index) in uploads"
        :key="index"
        class="card mb-3"
    >
      <UForm @submit="saveFile(index)">
        <div class="card-body">
          <div>
            <i class="bi bi-file-earmark-text fs-4 text-primary me-2"></i>
            <strong>{{ upload.file.name }}</strong>
            <span class="text-muted small"> - {{ formatSize(upload.file.size) }}</span>
            <div class="progress mt-2" style="height: 6px;">
              <div
                  class="progress-bar progress-bar-striped progress-bar-animated bg-info"
                  :style="{ width: upload.progress + '%' }"
              ></div>
            </div>
            <p class="small text-end mt-1 mb-2 text-muted">{{ upload.progress }}%</p>
          </div>

          <div class="d-flex flex-column align-items-center">
            <i v-if="upload.progress === 100" class="bi bi-check-circle-fill text-success fs-5"></i>
            <i class="bi bi-x-circle text-danger fs-5 mt-2" style="cursor: pointer;" @click="removeFile(index)"></i>
          </div>

          <div class="col-6">
            <UDateInput v-model="upload.expirationDate" placeholder="dd.mm.yyyy"
                        label="Expiration Date *"
                        :rules="(val) => (!val && $t('required'))"/>
          </div>
          <div class="col-md-12">
            <UTextarea v-model="upload.description" placeholder="description" :noLabel="true" rows="1"/>
          </div>

          <div class="mt-3 text-end">
            <button class="btn btn-secondary me-2" @click="removeFile(index)">Cancel</button>
            <button class="btn btn-success" type="submit">Save</button>
          </div>
        </div>
      </UForm>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import UTextarea from "@/components/base/UTextarea.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";
import useToastStore from "@/store/ToastStore.js";
import UForm from "@/components/base/UForm.vue";
import UInput from "@/components/base/UInput.vue";

const toastStore = useToastStore();

const props = defineProps({
  truckId: {
    type: Number,
    required: true
  },
  fileType: {
    type: String,
    required: true
  },
})

const isDragging = ref(false);
const uploads = ref([]);

const handleFileInput = (e) => {
  addFiles(e.target.files);
};

const handleDrop = (e) => {
  isDragging.value = false;
  addFiles(e.dataTransfer.files);
};

const addFiles = (fileList) => {
  [...fileList].forEach(file => {
    const upload = {
      file,
      progress: 0,
      expirationDate: '',
      description: '',
      type: '',
      truckId: props.truckId
    };
    uploads.value.push(upload);
    simulateUpload(upload);
  });
};

const simulateUpload = (upload) => {
  const interval = setInterval(() => {
    if (upload.progress < 100) {
      upload.progress += 10;
    } else {
      clearInterval(interval);
    }
  }, 100);
};

const removeFile = (index) => {
  uploads.value.splice(index, 1);
};

const saveFile = (index) => {
  const fileIn = uploads.value[index]

  const data = {
    truckId: fileIn.truckId,
    description: fileIn.description,
    expirationDate: fileIn.expirationDate,
    type: props.fileType,
  }

  const formData = new FormData();
  formData.append("file", fileIn.file);
  formData.append("data", new Blob([JSON.stringify(data)], {type: "application/json"}));

  axiosIns.post(URIS.TRUCK + "/attach-file", formData)
      .then(res => {

        toastStore.success({
          text: "File successfully uploaded"
        })

        removeFile(index);
      }).catch(e => {
    showMessage(e)
  })
};

const formatSize = (size) => {
  return (size / 1024).toFixed(2) + ' KB';
};
</script>

<style scoped>
.upload-box {
  border: 2px dashed #ccc;
  border-radius: 10px;
  padding: 50px;
  text-align: center;
  transition: border-color 0.3s, background-color 0.3s;
  cursor: pointer;
}

.upload-box.dragging {
  border-color: #0d6efd;
  background-color: #f0f8ff;
}

.upload-icon {
  font-size: 2rem;
  color: #6c757d;
}
</style>
