<script setup>
import { ref, onMounted, onUnmounted, watch, nextTick } from "vue";

const props = defineProps({
  height: {
    type: String,
    default: "100%",
  },
  barColor: {
    type: String,
    default: "#a5adbd",
  },
  thumbColor: {
    type: String,
    default: "#002973",
  },
  hideDelay: {
    type: Number,
    default: 1000,
  },
});

const scrollArea = ref(null);
const thumb = ref(null);
const isVisible = ref(false);
let hideTimeout = null;

const updateThumb = () => {
  if (!scrollArea.value) return;

  const {scrollTop, scrollHeight, clientHeight} = scrollArea.value;
  const thumbHeight = (clientHeight / scrollHeight) * clientHeight;
  const thumbPosition = (scrollTop / scrollHeight) * clientHeight;

  if (thumb.value) {
    thumb.value.style.height = `${thumbHeight}px`;
    thumb.value.style.transform = `translateY(${thumbPosition}px)`;
  }

  isVisible.value = true;
  clearTimeout(hideTimeout);
  hideTimeout = setTimeout(() => (isVisible.value = false), props.hideDelay);
};

const onScroll = () => {
  updateThumb();
};

watch(() => props.height, () => {
  nextTick(updateThumb);
});

onMounted(() => {
  if (scrollArea.value) {
    scrollArea.value.addEventListener("scroll", onScroll);
    updateThumb();
  }
});

onUnmounted(() => {
  if (scrollArea.value) {
    scrollArea.value.removeEventListener("scroll", onScroll);
  }
});
</script>

<template>
  <div class="scroll-container" :style="{ height }">
    <div ref="scrollArea" class="scroll-area">
      <slot/>
    </div>
    <div class="scrollbar" :style="{ backgroundColor: barColor }" v-show="isVisible">
      <div ref="thumb" class="scroll-thumb" :style="{ backgroundColor: thumbColor }"></div>
    </div>
  </div>
</template>

<style scoped>
.scroll-container {
  position: relative;
  width: 100%;
  overflow: hidden;
}

.scroll-area {
  width: 100%;
  height: 100%;
  overflow: auto;
  padding-right: 10px;
  scrollbar-width: none; /* Hide default scrollbar */
}

.scroll-area::-webkit-scrollbar {
  display: none; /* Hide scrollbar for Chrome/Safari */
}

.scrollbar {
  position: absolute;
  top: 0;
  right: 2px;
  width: 6px;
  height: 100%;
  opacity: 0.6;
  border-radius: 4px;
  transition: opacity 0.3s;
}

.scroll-thumb {
  width: 100%;
  border-radius: 4px;
  position: absolute;
  top: 0;
  transition: transform 0.1s linear;
}
</style>
