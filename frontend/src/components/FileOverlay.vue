<script setup>
import {onMounted, ref} from 'vue';
import UTextarea from "@/components/base/UTextarea.vue";
import UDateInput from "@/components/base/UDateInput.vue";
import axiosIns from "@/plugins/axios.js";
import {DRIVER_FILE_CLASSES, filePeriods, showMessage} from "@/util/utils.js";
import useToastStore from "@/store/ToastStore.js";
import UForm from "@/components/base/UForm.vue";
import UScrollArea from "@/components/base/UScrollArea.vue";
import UInput from "@/components/base/UInput.vue";
import USelect from "@/components/base/USelect.vue";
import {useStateStore} from "@/store/StateStore.js";
import {useI18n} from "vue-i18n";
import UMultipleSelect from "@/components/base/UMultipleSelect.vue";
import {useDriverReferenceStore} from "@/store/DriverReferenceStore.js";

let INDEX_COUNTER = 0;
const FILE_STATUS = {
  DELETED: 1,
  UPLOADED: 2
}

const {t} = useI18n();
const toastStore = useToastStore();
const stateStore = useStateStore();
const driverReferenceStore = useDriverReferenceStore();

const props = defineProps({
  data: {
    type: Object,
    required: true,
    default: {
      description: '',
      expirationDate: null,
      type: '',
      //truckId: '', // or
      //trailerId: '', // or
      //driverId: '', // or
      // companyId: ''
    }
  },
  url: {
    type: String,
    required: true
  },
  file: {
    type: Object,
    required: false
  }
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
      id: INDEX_COUNTER++,
      file,
      progress: 0,
      data: {
        expirationDate: new Date(),
        type: props.data.type,
        truckId: props.data.truckId,
        trailerId: props.data.trailerId,
        driverId: props.data.driverId,
        companyId: props.data.companyId
      }
    };
    simulateUpload(upload);
    uploads.value.push(upload);
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

const uploadFile = (index) => {
  uploads.value[index].status = FILE_STATUS.UPLOADED;
};

const saveFile = (index) => {
  const fileIn = uploads.value[index]

  fileIn.data.endorsements = fileIn.data.endorsements ? fileIn.data.endorsements : []

  fileIn.data.endorsement = fileIn.data.endorsements.map(it => {
    return {
      id: it
    }
  })

  const formData = new FormData();
  formData.append("file", fileIn.file);
  formData.append("data", new Blob([JSON.stringify(fileIn.data)], {type: "application/json"}));

  axiosIns.post(props.url, formData)
      .then(() => {

        toastStore.success({
          text: "File successfully uploaded"
        })

        uploadFile(index);
      }).catch(e => {
    showMessage(e)
  })
};

const formatSize = (size) => {
  return (size / 1024).toFixed(2) + ' KB';
};

onMounted(() => {
  if (props.data.id) {
    uploads.value.push({
      id: INDEX_COUNTER++,
      file: {
        name: props.data.fileName,
        size: props.data.size,

      },
      data: props.data,
      status: FILE_STATUS.UPLOADED
    });
  }
})
</script>

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
    <UScrollArea height="calc(100vh - 290px)">
      <div
          v-for="(upload, index) in uploads.sort((a, b) => a.id > b.id ? -1 : 1)"
          :key="index"
          class="card mb-3" :class="[FILE_STATUS.UPLOADED].includes(upload.status) && 'bg-light'"
      >
        <UForm @submit="saveFile(index)">
          <div class="card-body row">
            <div>
              <i class="bi bi-file-earmark-text fs-4 text-primary me-2"></i>
              <strong>{{ upload.file.name }}</strong>
              <span class="text-muted small"> - {{ formatSize(upload.file.size) }}</span>
              <div class="progress mt-2" v-if="![FILE_STATUS.UPLOADED].includes(upload.status)" style="height: 6px;">
                <div
                    class="progress-bar progress-bar-striped progress-bar-animated bg-info"
                    :style="{ width: upload.progress + '%' }"
                ></div>
              </div>
              <p class="small text-end mt-1 mb-1 text-muted" v-if="![FILE_STATUS.UPLOADED].includes(upload.status)">
                {{ upload.progress }}%</p>
            </div>

            <div class="d-flex flex-column align-items-center">
              <i v-if="upload.progress === 100" class="bi bi-check-circle-fill text-success fs-5"></i>
              <i class="bi bi-x-circle text-danger fs-5 mt-2" style="cursor: pointer;" @click="removeFile(index)"></i>
            </div>

            <template v-if="['CDL'].includes(data.type)">
              <div class="col-6">
                <UInput v-model="upload.data.driversLicense" placeholder="Driver Licence"
                        label="Driver Licence *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                        :rules="(val) => (!val && $t('required'))" type="text"/>
              </div>
              <div class="col-6">
                <USelect v-model="upload.data.countryId" :label="t('country')"
                         :items="stateStore.countries" name="country"
                         option_name="name" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                         :rules="(val) => (!val && $t('required'))"
                ></Uselect>
              </div>
              <div class="col-6">
                <USelect v-model="upload.data.stateId" :label="t('state')"
                         :items="stateStore.getStates(upload.data.countryId)" name="state"
                         option_name="name" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                         :rules="(val) => (!val && $t('required'))"
                ></Uselect>
              </div>
              <div class="col-6">
                <USelect v-model="upload.data.classType" :label="t('class')"
                         :items="DRIVER_FILE_CLASSES" name="class"
                         option_name="value" option_value="key"
                         :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                         :rules="(val) => (!val && $t('required'))"
                ></Uselect>
              </div>
              <div class="col-6">
                <UDateInput v-model="upload.data.issuedDate" placeholder="dd.mm.yyyy"
                            label="Issued Date *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                            :rules="(val) => (!val && $t('required'))"/>
              </div>
              <div class="col-6">
                <UDateInput v-model="upload.data.expirationDate" placeholder="dd.mm.yyyy"
                            label="Expiration Date *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                            :rules="(val) => (!val && $t('required'))"/>
              </div>
              <div class="col-12">
                <UMultipleSelect v-model="upload.data.endorsements"
                                 :items="driverReferenceStore.endorsements"
                                 option-name="name" option-value="id"
                                 placeholder="Select one or more Endorsements"
                />
              </div>
            </template>
            <template v-else-if="['SSN'].includes(data.type)">
              <div class="col-6">
                <UInput v-model="upload.data.socialSecurityNumber" placeholder="Social Security Number"
                        label="Social Security Number *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                        :rules="(val) => (!val && $t('required'))" type="number"/>
              </div>
            </template>
            <template v-else-if="['HVUT_2290'].includes(data.type)">
              <div class="col-6">
                <USelect v-model="upload.data.filedPeriod" :label="t('filedPeriod')"
                         :items="filePeriods()" name="filedPeriod"
                         option_name="name" option_value="name"
                         :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                         :rules="(val) => (!val && $t('required'))"
                ></Uselect>
              </div>
            </template>
            <template v-else-if="['W_9'].includes(data.type)">
              <div class="col-6">
                <UDateInput v-model="upload.data.nextUpdateDate" placeholder="dd.mm.yyyy"
                            label="Next Update Date *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                            :rules="(val) => (!val && $t('required'))"/>
              </div>
            </template>
            <template v-else-if="['DRUG_TEST_RESULT'].includes(data.type)">
              <div class="col-6">
                <UDateInput v-model="upload.data.expirationDate" placeholder="dd.mm.yyyy"
                            label="Test date *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                            :rules="(val) => (!val && $t('required'))"/>
              </div>
            </template>
            <template
                v-else-if="['ARTICLES_OF_INCORPORATION', 'MC_CERTIFICATE', 'OWNER_OPERATOR_AGREEMENT', 'DRIVER_AGREEMENT'].includes(data.type)">
              <!--              no data-->
            </template>
            <template v-else>
              <div class="col-6">
                <UDateInput v-model="upload.data.expirationDate" placeholder="dd.mm.yyyy"
                            label="Expiration Date *" :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                            :rules="(val) => (!val && $t('required'))"/>
              </div>
            </template>

            <div class="col-md-12">
              <UTextarea v-model="upload.data.description" placeholder="description"
                         :readonly="[FILE_STATUS.UPLOADED].includes(upload.status)"
                         :noLabel="true" rows="1"/>
            </div>

            <div class="mt-3 text-end" v-if="![FILE_STATUS.UPLOADED].includes(upload.status)">
              <button class="btn btn-secondary me-2" @click="(e) => {removeFile(index); e.stopPropagation()}">Cancel
              </button>
              <button class="btn btn-success" type="submit">Save</button>
            </div>
          </div>
        </UForm>
      </div>
    </UScrollArea>
  </div>
</template>

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
