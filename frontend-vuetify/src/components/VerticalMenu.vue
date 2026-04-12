<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/store/UseAuthStore.js";
import router from "@/router/index.js";

const authStore = useAuthStore();

const currentOpenMenu = ref([]);
const toggleSubmenu = (menu) => {
  if (currentOpenMenu.value.includes(menu)) {
    currentOpenMenu.value = currentOpenMenu.value.filter((it) => it !== menu);
  } else {
    currentOpenMenu.value.push(menu);
  }
};

function pushPage(page) {
  router.push(page);
}

function onLogout() {
  authStore.logout();
  pushPage("/login");
}

defineProps({
  collapsed: {
    type: Boolean,
    required: true,
  },
});

/** Rail: flyout menus — shared sheet styling */
const flyoutProps = {
  location: "end",
  offset: 8,
  closeOnContentClick: true,
};
</script>

<template>
  <div
    class="vertical-menu-nav d-flex flex-column flex-grow-1 text-white"
    style="min-height: 0"
  >
    <!-- Expanded drawer -->
    <div
      v-if="!collapsed"
      id="sidebar-menu"
      class="flex-grow-1 overflow-y-auto pa-1"
    >
      <v-list class="nav-list bg-transparent" density="compact" nav>
        <!-- Fleet -->
        <v-list-item
          class="nav-section-header rounded-lg mb-1"
          :class="{ 'bg-white bg-opacity-5': currentOpenMenu.includes('fleet') }"
          @click.stop="toggleSubmenu('fleet')"
        >
          <template #prepend>
            <v-icon icon="mdi-folder-cog-outline" size="22" class="text-white" />
          </template>
          <v-list-item-title class="text-body-2 font-weight-bold text-white">
            Fleet
          </v-list-item-title>
          <template #append>
            <v-icon
              :icon="
                currentOpenMenu.includes('fleet')
                  ? 'mdi-chevron-up'
                  : 'mdi-chevron-down'
              "
              size="small"
              class="text-medium-emphasis"
            />
          </template>
        </v-list-item>

        <v-expand-transition>
          <div v-show="currentOpenMenu.includes('fleet')">
            <v-list-item
              to="/fleet-compliance"
              title="Fleet Compliance"
              prepend-icon="mdi-truck-check-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/fleet-overview"
              title="Fleet overview"
              prepend-icon="mdi-map-marker-radius-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/maintenance"
              title="Maintenance"
              prepend-icon="mdi-wrench-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-divider class="my-2 border-opacity-25" />
          </div>
        </v-expand-transition>

        <!-- Safety -->
        <v-list-item
          class="nav-section-header rounded-lg mb-1"
          :class="{ 'bg-white bg-opacity-5': currentOpenMenu.includes('safety') }"
          @click.stop="toggleSubmenu('safety')"
        >
          <template #prepend>
            <v-icon icon="mdi-shield-car" size="22" class="text-white" />
          </template>
          <v-list-item-title class="text-body-2 font-weight-bold text-white">
            Safety
          </v-list-item-title>
          <template #append>
            <v-icon
              :icon="
                currentOpenMenu.includes('safety')
                  ? 'mdi-chevron-up'
                  : 'mdi-chevron-down'
              "
              size="small"
              class="text-medium-emphasis"
            />
          </template>
        </v-list-item>

        <v-expand-transition>
          <div v-show="currentOpenMenu.includes('safety')">
            <v-list-item
              to="/safety-compliance"
              title="Safety Compliance"
              prepend-icon="mdi-shield-check-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/inspection"
              title="Inspections"
              prepend-icon="mdi-clipboard-text-search-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/collisions"
              title="Collision & Claims"
              prepend-icon="mdi-car-brake-alert"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-divider class="my-2 border-opacity-25" />
          </div>
        </v-expand-transition>

        <!-- References -->
        <v-list-item
          class="nav-section-header rounded-lg mb-1"
          :class="{
            'bg-white bg-opacity-5': currentOpenMenu.includes('references'),
          }"
          @click.stop="toggleSubmenu('references')"
        >
          <template #prepend>
            <v-icon icon="mdi-tune-variant" size="22" class="text-white" />
          </template>
          <v-list-item-title class="text-body-2 font-weight-bold text-white">
            References
          </v-list-item-title>
          <template #append>
            <v-icon
              :icon="
                currentOpenMenu.includes('references')
                  ? 'mdi-chevron-up'
                  : 'mdi-chevron-down'
              "
              size="small"
              class="text-medium-emphasis"
            />
          </template>
        </v-list-item>

        <v-expand-transition>
          <div v-show="currentOpenMenu.includes('references')">
            <v-list-item
              to="/model-maker"
              title="Model maker"
              prepend-icon="mdi-hammer-wrench"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/ownership-type"
              title="Ownership type"
              prepend-icon="mdi-badge-account-horizontal-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/purchase-type"
              title="Purchase type"
              prepend-icon="mdi-cart-outline"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-list-item
              to="/inspection-level"
              title="Inspection Level"
              prepend-icon="mdi-format-list-checks"
              rounded="lg"
              class="nav-sublink mb-1"
              active-class="nav-sublink--active"
            />
            <v-divider class="my-2 border-opacity-25" />
          </div>
        </v-expand-transition>
      </v-list>
    </div>

    <!-- Rail: icon + flyout menus (explicit light icon color — theme "white" on text btns can wash out) -->
    <div
      v-else
      class="rail-menus d-flex flex-column align-center ga-2 py-2 flex-grow-1 overflow-y-auto"
    >
      <v-menu v-bind="flyoutProps">
        <template #activator="{ props }">
          <v-btn
            v-bind="props"
            icon
            variant="text"
            size="48"
            class="rail-fab rail-fab--activator rail-fab--fleet"
            aria-label="Fleet"
          >
            <!-- filled icon reads better than outline on narrow rail; teal pops on dark drawer -->
            <v-icon
              class="rail-fab-icon rail-fab-icon--fleet"
              icon="mdi-folder-cog"
              size="26"
            />
          </v-btn>
        </template>
        <v-sheet
          class="rail-flyout bg-surface"
          rounded="lg"
          elevation="12"
          border
          min-width="220"
        >
          <v-list density="compact" nav class="py-2">
            <v-list-item
              to="/fleet-compliance"
              title="Fleet Compliance"
              prepend-icon="mdi-truck-check-outline"
            />
            <v-list-item
              to="/fleet-overview"
              title="Fleet Overview"
              prepend-icon="mdi-map-marker-radius-outline"
            />
            <v-list-item
              to="/maintenance"
              title="Maintenance"
              prepend-icon="mdi-wrench-outline"
            />
          </v-list>
        </v-sheet>
      </v-menu>

      <v-menu v-bind="flyoutProps">
        <template #activator="{ props }">
          <v-btn
            v-bind="props"
            icon
            variant="text"
            size="48"
            class="rail-fab rail-fab--activator"
            aria-label="Safety"
          >
            <v-icon class="rail-fab-icon" icon="mdi-shield-car" size="24" />
          </v-btn>
        </template>
        <v-sheet
          class="rail-flyout bg-surface"
          rounded="lg"
          elevation="12"
          border
          min-width="220"
        >
          <v-list density="compact" nav class="py-2">
            <v-list-item
              to="/safety-compliance"
              title="Safety Compliance"
              prepend-icon="mdi-shield-check-outline"
            />
            <v-list-item
              to="/inspection"
              title="Inspections"
              prepend-icon="mdi-clipboard-text-search-outline"
            />
            <v-list-item
              to="/collisions"
              title="Collision & Claims"
              prepend-icon="mdi-car-brake-alert"
            />
          </v-list>
        </v-sheet>
      </v-menu>

      <v-menu v-bind="flyoutProps">
        <template #activator="{ props }">
          <v-btn
            v-bind="props"
            icon
            variant="text"
            size="48"
            class="rail-fab rail-fab--activator"
            aria-label="References"
          >
            <v-icon class="rail-fab-icon" icon="mdi-tune-variant" size="24" />
          </v-btn>
        </template>
        <v-sheet
          class="rail-flyout bg-surface"
          rounded="lg"
          elevation="12"
          border
          min-width="220"
        >
          <v-list density="compact" nav class="py-2">
            <v-list-item
              to="/model-maker"
              title="Model Makers"
              prepend-icon="mdi-hammer-wrench"
            />
            <v-list-item
              to="/ownership-type"
              title="Ownership Types"
              prepend-icon="mdi-badge-account-horizontal-outline"
            />
            <v-list-item
              to="/purchase-type"
              title="Purchase Types"
              prepend-icon="mdi-cart-outline"
            />
            <v-list-item
              to="/inspection-level"
              title="Inspection Levels"
              prepend-icon="mdi-format-list-checks"
            />
          </v-list>
        </v-sheet>
      </v-menu>
    </div>

    <div class="logout-container" :class="{ 'logout-container--rail': collapsed }">
      <v-btn
        block
        variant="text"
        color="error"
        class="logout-btn text-none justify-start"
        @click="(e) => { onLogout(); e.stopPropagation(); }"
      >
        <v-icon
          icon="mdi-logout"
          size="small"
          class="logout-icon"
          :class="{ 'me-2': !collapsed }"
        />
        <span v-show="!collapsed">Log out</span>
      </v-btn>
    </div>
  </div>
</template>

<style scoped>
.vertical-menu-nav {
  position: relative;
  width: 100%;
}

.nav-list :deep(.v-list-item__spacer) {
  width: 10px !important;
}

.nav-section-header {
  min-height: 44px;
  cursor: pointer;
  user-select: none;
}

.nav-sublink {
  padding-inline-start: 8px !important;
}

.nav-sublink :deep(.v-list-item__prepend) {
  width: 40px;
}

.nav-sublink--active {
  background: rgba(var(--v-theme-primary), 0.22) !important;
  color: rgb(var(--v-theme-primary)) !important;
}

.nav-sublink--active :deep(.v-icon) {
  color: rgb(var(--v-theme-primary)) !important;
}

.rail-fab {
  border-radius: 12px !important;
}

/* Rail: force readable icons on dark gradient drawer (avoid theme on-surface / "white" color bugs) */
.rail-fab--activator {
  color: #e8eef5 !important;
}

.rail-fab--activator :deep(.v-btn__overlay) {
  opacity: 0.12 !important;
}

.rail-fab--activator :deep(.v-btn__underlay) {
  opacity: 0 !important;
}

.rail-fab-icon {
  color: #e8eef5 !important;
  opacity: 1 !important;
}

.rail-fab:hover {
  background: rgba(255, 255, 255, 0.12) !important;
}

.rail-fab:hover .rail-fab-icon {
  color: #ffffff !important;
}

.rail-fab:hover .rail-fab-icon--fleet {
  color: #ecfeff !important;
}

/* Fleet rail: outline icon was too faint; filled + teal tint stays visible on gradient */
.rail-fab-icon--fleet {
  color: #7dd3fc !important;
  opacity: 1 !important;
}

.rail-fab--fleet.rail-fab--activator {
  color: #7dd3fc !important;
}

.rail-flyout {
  border-color: rgba(var(--v-border-color), var(--v-border-opacity));
}

.logout-container {
  flex-shrink: 0;
  width: 100%;
  padding: 8px;
  border-top: 1px solid rgba(255, 255, 255, 0.12);
}

.logout-btn {
  letter-spacing: 0.02em;
}

.logout-container--rail .logout-btn {
  justify-content: center !important;
}

.logout-container--rail :deep(.logout-icon) {
  opacity: 1 !important;
  margin-inline: 0 !important;
}
</style>
