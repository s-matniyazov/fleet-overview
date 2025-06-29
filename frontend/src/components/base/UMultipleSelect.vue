<script setup>
import { inject, ref, computed, watch } from "vue";

const props = defineProps({
  name: {
    type: String,
    required: false,
  },
  label: {
    type: String,
    required: false,
  },
  hint: {
    type: String,
    required: false,
    default: ''
  },
  optionName: {
    type: String,
    required: false,
    default: 'id'
  },
  optionValue: {
    type: String,
    required: false,
    default: 'id'
  },
  items: {
    type: Array,
    required: true,
    default: () => []
  },
  classes: {
    type: String,
    required: false,
    default: ''
  },
  styles: {
    type: String,
    required: false,
    default: ''
  },
  rules: Function,
  placeholder: {
    type: String,
    default: 'Tanlang'
  }
});

const selected = defineModel({
  type: Array,
  default: () => []
});
const isOpen = ref(false);
const errorMessage = ref('');
const el = ref(null);

const formState = inject('formState', null);
const registerField = inject('registerField', () => {});

const validate = () => {
  errorMessage.value = props.rules ? props.rules(selected.value) : '';
  return errorMessage.value;
};

registerField(props.name, validate);

const toggleOpen = () => {
  isOpen.value = !isOpen.value;
};

const isSelected = (item) => {
  return selected.value.includes(item[props.optionValue]);
};

const selectItem = (item) => {
  const value = item[props.optionValue];
  if (selected.value.includes(value)) {
    selected.value = selected.value.filter(v => v !== value);
  } else {
    selected.value = [...selected.value, value];
  }
};

const displaySelected = computed(() => {
  if (selected.value.length === 0) {
    return props.placeholder;
  }
  return props.items
      .filter(item => selected.value.includes(item[props.optionValue]))
      .map(item => item[props.optionName])
      .join(', ');
});

// Tashqarida bosilganda yopish
watch(isOpen, (newIsOpen) => {
  if (newIsOpen) {
    document.addEventListener('click', handleClickOutside);
  } else {
    document.removeEventListener('click', handleClickOutside);
  }
});

const handleClickOutside = (event) => {
  if (isOpen.value && el.value && !el.value.contains(event.target)) {
    isOpen.value = false;
  }
};
</script>

<template>
  <div :class="`${classes} ${errorMessage && 'has-danger'} p-1 relative`" :style="styles" ref="el">
    <label v-if="label" class="form-label">{{ label }}</label>
    <div class="form-control form-select font-size-12 cursor-pointer" @click="toggleOpen">
      {{ displaySelected }}
    </div>
    <div v-if="isOpen" class="absolute top-full left-0 right-0 bg-white border border-gray-300 rounded-b-md shadow-md z-10 overflow-y-auto max-h-48">
      <ul class="list-none p-0 m-0">
        <li
            v-for="item in items"
            :key="item[optionValue]"
            @click.stop="selectItem(item)"
            :class="{ 'bg-gray-100': isSelected(item) }"
            class="p-2 cursor-pointer hover:bg-gray-200"
        >
          <input
              type="checkbox"
              :value="item[optionValue]"
              :checked="isSelected(item)"
              class="mr-2"
              @click.stop="selectItem(item)"
          >
          {{ item[optionName] }}
        </li>
      </ul>
    </div>
    <div class="invalid-feedback">{{ hint }}</div>
    <p v-if="errorMessage" class="pristine-error text-help">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.top-full {
  top: 100%;
}

.left-0 {
  left: 0;
}

.right-0 {
  right: 0;
}

.bg-white {
  background-color: #fff;
}

[data-bs-theme=dark] .bg-white {
  background-color: #000000;
}

.border {
  border: 1px solid #e5e7eb;
}

[data-bs-theme=dark] .border {
  border: 1px solid #1d1d1e;
}

.border-gray-300 {
  border-color: #d1d5db;
}

[data-bs-theme=dark] .border-gray-300 {
  border-color: #494b4d;
}

.rounded-b-md {
  border-bottom-left-radius: 0.375rem;
  border-bottom-right-radius: 0.375rem;
}

.shadow-md {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

.z-10 {
  z-index: 10;
}

.overflow-y-auto {
  overflow-y: auto;
}

.max-h-48 {
  max-height: 12rem; /* 192px */
}

.list-none {
  list-style-type: none;
}

.p-0 {
  padding: 0;
}

.m-0 {
  margin: 0;
}

.p-2 {
  padding: 0.5rem;
}

.cursor-pointer {
  cursor: pointer;
}

.hover\:bg-gray-200:hover {
  background-color: #e5e7eb;
}

[data-bs-theme=dark] .hover\:bg-gray-200:hover {
  background-color: #49494b;
}

.mr-2 {
  margin-right: 0.5rem;
}

.top-1\/2 {
  top: 50%;
}

.transform {
  transform: translate(var(--tw-translate-x), var(--tw-translate-y)) rotate(var(--tw-rotate)) skewX(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y));
  --tw-translate-x: 0;
  --tw-translate-y: 0;
  --tw-rotate: 0;
  --tw-skew-x: 0;
  --tw-skew-y: 0;
  --tw-scale-x: 1;
  --tw-scale-y: 1;
}

.-translate-y-1\/2 {
  --tw-translate-y: -50%;
}

.rotate-180 {
  --tw-rotate: 180deg;
}

.arrow {
  transition: transform 0.2s ease-in-out;
}
</style>
