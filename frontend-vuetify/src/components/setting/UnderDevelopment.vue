<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.back()
}
</script>

<template>
  <div class="development-page">
    <v-card
        class="development-card"
        elevation="0"
        rounded="xl"
    >
      <div class="icon-wrapper">
        <v-icon
            icon="mdi-hammer-wrench"
            size="48"
        />
      </div>

      <h1 class="title">
        Page Under Development
      </h1>

      <p class="description">
        We're currently working on this page.
        <br>
        Please check back soon.
      </p>

      <v-btn
          variant="tonal"
          prepend-icon="mdi-arrow-left"
          rounded="lg"
          @click="goBack"
      >
        Go Back
      </v-btn>
    </v-card>
  </div>
</template>

<style scoped>
.development-page {
  min-height: calc(100vh - 64px);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 32px;
}

.development-card {
  width: 100%;
  max-width: 520px;
  padding: 48px 32px;
  text-align: center;
  background: rgb(var(--v-theme-surface));
  border: 1px solid rgba(var(--v-border-color), 0.12);
}

.icon-wrapper {
  width: 96px;
  height: 96px;
  margin: 0 auto 24px;

  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 50%;
  color: rgb(var(--v-theme-primary));
  background: rgba(var(--v-theme-primary), 0.1);
}

.title {
  margin: 0 0 12px;
  font-size: 28px;
  font-weight: 600;
  line-height: 1.3;
  color: rgb(var(--v-theme-on-surface));
}

.description {
  margin: 0 0 28px;
  font-size: 15px;
  line-height: 1.7;
  color: rgba(var(--v-theme-on-surface), 0.65);
}

@media (max-width: 600px) {
  .development-page {
    padding: 16px;
  }

  .development-card {
    padding: 36px 20px;
  }

  .title {
    font-size: 24px;
  }
}
</style>