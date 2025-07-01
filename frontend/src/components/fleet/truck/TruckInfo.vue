<script setup>
import {useI18n} from "vue-i18n";
import {dataOrTire} from "@/util/utils.js";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
});
</script>
<template>
  <div class="d-flex ng-star-inserted mt-1">
    <div class="width-50 d-flex flex-column px-3">
      <span class="title mb-3">Unit Details</span>
      <div class="two-column-content">
        <div class="contents">
          <span class="left"> Operated By: </span>
          <span class="right"> {{ dataOrTire(data?.companyName) }} </span>
        </div>
        <div class="contents">
          <span class="left">Current Driver(S):</span>
          <span class="right"> {{ `${dataOrTire(data?.driverName)}` }} </span>
        </div>
      </div>
      <div class="two-column-content">
        <div class="contents">
          <span class="left"> Unit #: </span>
          <span class="right"> {{ dataOrTire(data?.unit) }} </span>
        </div>
        <div class="contents">
          <span class="left"> License Plate #:</span>
          <span class="right"> {{ dataOrTire(data?.licensePlate) }} </span>
        </div>
        <div class="contents">
          <span class="left"> State: </span>
          <span class="right"> {{ dataOrTire(data?.stateName) }} </span>
        </div>
        <div class="contents">
          <span class="left"> Make: </span>
          <span class="right"> {{ dataOrTire(data?.modelMakerName) }} </span>
        </div>
        <div class="contents">
          <span class="left"> Year: </span>
          <span class="right"> {{ dataOrTire(data?.year) }} </span>
        </div>
        <div class="contents">
          <span class="left"> Fuel Type: </span>
          <span class="right"> {{ dataOrTire(data?.fuelTypeName) }} </span>
        </div>
        <div class="contents">
          <span class="left"> Gross Weight: </span>
          <span class="right"> {{ dataOrTire(data?.grossWeight) }} </span>
        </div>
        <div class="contents">
          <span class="left"> Number of Axles:</span>
          <span class="right"> {{ dataOrTire(data?.axles) }} </span>
        </div>
        <div class="contents">
          <span class="left"> VIN #: </span>
          <span class="right"> {{ dataOrTire(data?.vin) }} </span>
        </div>
      </div>
      <span class="title">Ownership Details</span>
      <div class="two-column-content">
        <div class="contents">
          <div class="left">Ownership:</div>
          <div class="right"> {{ dataOrTire(data?.ownershipTypeName) }} </div>
        </div>
        <div class="contents">
          <div class="left">Include to the IFTA:</div>
          <div class="right"> {{ data?.includeIFTA ? 'YES' : 'NO' }} </div>
        </div>
      </div>
      <span class="title">Other Details</span>
      <div class="two-column-content">
        <div class="contents">
          <div class="left">Purchase Type:</div>
          <div class="right"> {{ dataOrTire(data?.purchaseTypeName) }} </div>
        </div>
      </div>
    </div>
    <div class="width-50 d-flex flex-column px-3">
      <span class="title mb-3">AdditionalNotes</span>
      <div class="note-container overflow-y">{{ data?.description || 'N/A' }}</div>
    </div>
  </div>
</template>

<style scoped lang="css">
.d-flex {
  display: flex !important;
}

.width-50 {
  width: 50%;
}

.px-3 {
  padding-right: .46875rem !important;
  padding-left: .46875rem !important;
}

.flex-column {
  flex-direction: column !important;
}

.two-column-content {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding-bottom: 12px;
  border-bottom: 1px solid #C5C5C5;
  margin-bottom: 10px;
}

.contents {
  display: flex;
  justify-content: space-between;
  align-items: start;
  margin-top: 6px;
  width: 100%;
}

.title {
  color: #00405B;
  font-size: 18px;
  font-style: normal;
  font-weight: 700;
  line-height: 140%;
}

.left {
  width: 40%;
  color: #717171;
  text-align: right;
  font-size: 14px;
  font-style: normal;
  font-weight: 600;
  line-height: 140%;
}

.right {
  width: 55%;
  color: #000;
  font-size: 14px;
  font-style: normal;
  font-weight: 600;
  line-height: 140%;
  word-wrap: break-word;
}

.overflow-y {
  overflow-y: auto;
}

.note-container {
  background: #f4f4f4;
  max-height: 150px;
  min-height: 80px;
  border: none;
  overflow-x: hidden;
  word-wrap: break-word;
  text-wrap: wrap;
  border-radius: 3px;
  height: 100%;
  width: 100%;
  padding: 7px 10px;
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 1px solid rgba(0, 0, 0, .125);
  border-radius: .25rem;
}
</style>
