<script setup>
import {useI18n} from "vue-i18n";
import {dataOrTire, longToDate, longToDateTime} from "@/util/utils.js";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
});
</script>
<template>
  <div class="tw-w-full tw-flex tw-flex-col ma-3">

  <div class="tw-w-full tw-flex tw-justify-between tw-gap-x-4 mx-1 mt-3">
    <div
        class="tw-w-1/2 tw-rounded tw-bg-light-primary  tw-shadow-default tw-p-[7px]">
      <div class="tw-flex tw-justify-between">
        <div class="tw-w-1/2 tw-flex tw-flex-col tw-gap-y-4">
          <div class="tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Hired To</span><span
              class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data?.companyName) }} </span></div>
          <div class="tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Hired Date</span><span
              class="tw-text-dark-primary  tw-text-[14px]"> {{ longToDate(dataOrTire(data?.hireDate)) }} </span></div>
        </div>
        <div class="tw-w-1/2 tw-flex tw-flex-col tw-gap-y-4">
          <div class="tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Terminated Date</span><span
              class="tw-text-dark-primary  tw-text-[14px]"><span
              class="tw-text-dark-primary  tw-text-[14px] "> {{ dataOrTire(longToDateTime(data?.terminationDate)) }}
              </span></span></div>
          <div class="tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Terminated Reason</span><span
              class="tw-text-dark-primary  tw-text-[14px]"><span
              class="tw-text-dark-primary  tw-text-[14px] "> {{ dataOrTire(data?.terminationReason) }}
              </span></span></div>
        </div>
      </div>
    </div>
  </div>

  <div class="tw-w-full tw-flex tw-items-start tw-gap-x-4 mx-1 mt-3">
    <div
        class="tw-flex-1 tw-rounded tw-bg-light-primary  tw-shadow-default tw-p-[7px] tw-flex tw-flex-col">
      <div class="tw-flex tw-justify-between"><span
          class="tw-w-[21px] tw-h-[21px] tw-rounded-full tw-flex tw-items-center tw-justify-center tw-bg-secondary tw-text-light-primary ">
          1 </span></div>
      <span
          class="tw-mt-4 tw-text-lg tw-font-bold tw-text-dark-primary "> Personal Details </span>
      <div class="tw-mt-4 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">First Name</span><span
          class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data.firstName) }} </span></div>
      <div class="tw-mt-2 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Middle Name</span><span
          class="tw-text-dark-primary  tw-text-[14px]"><span
          class="tw-text-dark-primary  tw-text-[14px] "> {{ dataOrTire(data.middleName) }} </span></span>
      </div>
      <div class="tw-mt-2 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Last Name</span><span
          class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data.lastName) }} </span></div>
      <div class="tw-mt-2 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Phone</span><span
          class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data.phone) }} </span>
      </div>
      <div class="tw-mt-2 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Email</span><span
          class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data.email) }} </span>
      </div>
      <div class="tw-mt-2 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">DOB</span><span
          class="tw-text-dark-primary  tw-text-[14px]"> {{ longToDate(dataOrTire(data?.dateOfBirth)) }} </span></div>
      <div class="tw-mt-2 tw-flex tw-flex-col"><span class="tw-text-gray-dark tw-text-[12px]">Address</span><span
          class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data?.address) }} </span></div>
    </div>
    <div
        class="tw-flex-1 tw-rounded tw-bg-light-primary  tw-shadow-default tw-p-[7px] tw-flex tw-flex-col ">
      <div class="tw-flex tw-justify-between"><span
          class="tw-w-[21px] tw-h-[21px] tw-rounded-full tw-flex tw-items-center tw-justify-center tw-bg-secondary tw-text-light-primary ">
          2 </span></div>
      <span
          class="tw-mt-4 tw-text-lg tw-font-bold tw-text-dark-primary "> Contract </span>
      <div class="tw-mt-4 tw-flex tw-flex-col "><span class="tw-text-gray-dark tw-text-[12px]">Driver
          role</span><span class="tw-text-dark-primary  tw-text-[14px]"> {{ dataOrTire(data?.type) }} </span>
      </div>

    </div>
    <div
        class="tw-flex-1 tw-rounded tw-bg-light-primary  tw-shadow-default tw-p-[7px] tw-flex tw-flex-col">
      <div class="tw-flex tw-justify-between"><span
          class="tw-w-[21px] tw-h-[21px] tw-rounded-full tw-flex tw-items-center tw-justify-center tw-bg-secondary tw-text-light-primary ">
          3 </span></div>
      <span
          class="tw-mt-4 tw-text-lg tw-font-bold tw-text-dark-primary "> Operation Equipment
      </span>
      <div class="tw-mt-4 tw-flex tw-flex-col "><span class="tw-text-gray-dark tw-text-[12px]">Truck</span><span
          :class="`tw-w-fit ${data?.truckId && 'tw-text-light-primary tw-bg-secondary'} tw-px-2 tw-rounded tw-text-[14px] px-2`">
          {{ dataOrTire(data?.truckName) }} </span></div>
      <div class="tw-mt-2 tw-flex tw-flex-col "><span class="tw-text-gray-dark tw-text-[12px]">Trailer</span><span
          :class="`tw-w-fit ${data?.trailerId && 'tw-text-light-primary tw-bg-secondary'} tw-px-2 tw-rounded tw-text-[14px] px-2`">
          {{ dataOrTire(data?.trailerName) }} </span></div>
    </div>
  </div>
  </div>
</template>


<style scoped>
.tw-gap-x-4 {
  column-gap: 1rem;
}

.tw-justify-between {
  justify-content: space-between;
}

.tw-flex {
  display: flex;
}

.tw-gap-y-4 {
  row-gap: 1rem;
}

.tw-flex-col {
  flex-direction: column;
}

.tw-w-1\/2 {
  width: 50%;
}

.tw-shadow-default {
  --tw-shadow: 0px 0px 2px 0px #00000040;
  --tw-shadow-colored: 0px 0px 2px 0px;
  box-shadow: var(--tw-ring-offset-shadow, 0 0 #0000), 0 0 #0000, var(--tw-shadow);
}

.tw-bg-light-primary {
  --tw-bg-opacity: 1;
  background-color: rgb(255 255 255 / var(--tw-bg-opacity, 1));
}

.tw-p-\[7px\] {
  padding: 7px;
}

.tw-rounded {
  border-radius: .25rem;
}

.tw-text-gray-dark {
  --tw-text-opacity: 1;
  color: rgb(113 113 113 / var(--tw-text-opacity, 1));
}

.tw-text-\[12px\] {
  font-size: 12px;
}

.tw-text-dark-primary {
  --tw-text-opacity: 1;
  color: rgb(33 42 54 / var(--tw-text-opacity, 1));
}

.tw-font-bold {
  font-weight: 700;
}

.tw-text-lg {
  font-size: 1.125rem;
  line-height: 1.75rem;
}

.tw-text-base {
  font-size: 1rem;
  line-height: 1.5rem;
}

.tw-cursor-pointer {
  cursor: pointer;
}

.tw-w-full {
  width: 100%;
}

.tw-h-\[150px\] {
  height: 150px;
}

.tw-mt-2 {
  margin-top: .5rem;
}

.tw-relative {
  position: relative;
}

.tw-bg-gray-extra-light {
  --tw-bg-opacity: 1;
  background-color: rgb(242 242 242 / var(--tw-bg-opacity, 1));
}

.tw-p-1 {
  padding: .25rem;
}

.tw-overflow-y-auto {
  overflow-y: auto;
}

.tw-min-h-full {
  min-height: 100%;
}

.tw-max-h-full {
  max-height: 100%;
}

.tw-h-full {
  height: 100%;
}

.tw-text-light-primary {
  --tw-text-opacity: 1;
  color: rgb(255 255 255 / var(--tw-text-opacity, 1));
}

.tw-bg-secondary {
  --tw-bg-opacity: 1;
  background-color: rgb(0 64 91 / var(--tw-bg-opacity, 1));
}

.tw-justify-center {
  justify-content: center;
}

.tw-items-center {
  align-items: center;
}

.tw-w-\[21px\] {
  width: 21px;
}

.tw-h-\[21px\] {
  height: 21px;
}

.tw-bg-secondary {
  --tw-bg-opacity: 1;
  background-color: rgb(0 64 91 / var(--tw-bg-opacity, 1));
}

.tw-rounded-full {
  border-radius: 9999px;
}

.tw-justify-center {
  justify-content: center;
}

.tw-items-center {
  align-items: center;
}

.tw-w-\[21px\] {
  width: 21px;
}

.tw-h-\[21px\] {
  height: 21px;
}

.tw-flex {
  display: flex;
}
</style>
