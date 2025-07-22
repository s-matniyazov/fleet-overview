<script setup>
import {useI18n} from "vue-i18n";

const {t} = useI18n();

const props = defineProps({
  data: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <div class="tw-m-auto tw-w-[80%] ng-star-inserted p-2">
    <div class="!tw-shadow-default dark:!tw-bg-dark-primary tw-bg-white tw-rounded">
      <div class="tw-relative tw-px-4">
        <div class="tw-flex tw-mb-4 tw-justify-between">
          <div class="tw-flex-grow">
            <div class="tw-flex tw-items-center tw-justify-between tw-my-4">
              <div
                  class="tw-ml-2.5 tw-text-secondary dark:!tw-text-gray-light tw-text-lg tw-leading-snug tw-capitalize">
                Company Details
              </div>
            </div>
            <div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">USDOT</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.dot }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">MC</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.mc }}</p>
                </div>
              </div>
            </div>
            <div>
              <hr class="tw-mt-2 tw-mb-4 tw-text-gray-dark dark:!tw-text-gray-light">
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Company Name</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.name }}</p>
                </div>
              </div><div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Owner full name</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.ownerName }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">DBA</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.dba }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">FEIN</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.fein }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Address</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.address1 }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Time Zone</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.timeZone }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Email Address</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.email }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Phone</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.phone }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Pin number</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.pinNumber }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Mailing address</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.address2 }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">Status</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.status }}</p>
                </div>
              </div>
              <div class="tw-grid tw-grid-cols-[240px_auto] tw-gap-[33px] tw-gap-y-0 tw-items-start">
                <div class="tw-text-right tw-text-gray-dark dark:!tw-text-gray-400 tw-text-sm tw-m-0 tw-pt-0.5">
                  <p class="tw-m-0">New Entrant Status</p>
                </div>
                <div
                    class="tw-text-black dark:!tw-text-gray-light tw-text-lg tw-font-semibold tw-leading-snug tw-break-words tw-m-0">
                  <p class="tw-m-0">{{ data.entrantStatus }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.tw-justify-between {
  justify-content: space-between;
}

.tw-flex {
  display: flex;
}

.tw-mb-4 {
  margin-bottom: 1rem;
}

.tw-items-center {
  align-items: center;
}

.tw-flex {
  display: flex;
}

.tw-px-4 {
  padding-left: 1rem;
  padding-right: 1rem;
}

.tw-relative {
  position: relative;
}

.tw-grid {
  display: grid;
}

.tw-capitalize {
  text-transform: capitalize;
}

.tw-break-words {
  overflow-wrap: break-word;
}

.tw-justify-between {
  justify-content: space-between;
}

.tw-flex {
  display: flex;
}

.tw-mb-4 {
  margin-bottom: 1rem;
}

.tw-justify-between {
  justify-content: space-between;
}

.tw-items-center {
  align-items: center;
}

.tw-flex {
  display: flex;
}

.tw-my-4 {
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.tw-gap-y-0 {
  row-gap: 0px;
}

.tw-gap-\[33px\] {
  gap: 33px;
}

.tw-items-start {
  align-items: flex-start;
}

.tw-grid-cols-\[240px_auto\] {
  grid-template-columns: 240px auto;
}

.tw-grid {
  display: grid;
}

.tw-text-gray-dark {
  --tw-text-opacity: 1;
  color: rgb(113 113 113 / var(--tw-text-opacity, 1));
}

.tw-text-sm {
  font-size: .875rem;
  line-height: 1.25rem;
}

.tw-text-right {
  text-align: right;
}

.tw-pt-0\.5 {
  padding-top: .125rem;
}

.tw-m-0 {
  margin: 0;
}

.tw-text-black {
  --tw-text-opacity: 1;
  color: rgb(0 0 0 / var(--tw-text-opacity, 1));
}

.tw-leading-snug {
  line-height: 1.375;
}

.tw-font-semibold {
  font-weight: 600;
}

.tw-text-lg {
  font-size: 1.125rem;
  line-height: 1.75rem;
}

.tw-break-words {
  overflow-wrap: break-word;
}

.tw-m-0 {
  margin: 0;
}

.tw-m-0 {
  margin: 0;
}

.tw-rounded {
  border-radius: .25rem;
}

.\!tw-shadow-default {
  --tw-shadow: 0px 0px 2px 0px #00000040 !important;
  --tw-shadow-colored: 0px 0px 2px 0px var(--tw-shadow-color) !important;
  box-shadow: var(--tw-ring-offset-shadow, 0 0 #0000), var(--tw-ring-shadow, 0 0 #0000), var(--tw-shadow) !important;
}

.tw-bg-white {
  --tw-bg-opacity: 1;
  background-color: rgb(255 255 255 / var(--tw-bg-opacity, 1));
}

.tw-rounded {
  border-radius: .25rem;
}
</style>
