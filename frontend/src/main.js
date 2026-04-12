import { createPinia } from "pinia";

import "@mdi/font/css/materialdesignicons.min.css";
import vuetify from "@/plugins/vuetify.js";

import "@/assets/css/main.css";
import "@/assets/css/app.min.css";
import "@/assets/css/bootstrap.min.css";

const pinia = createPinia();

import { createApp } from "vue";
import App from "./App.vue";
import router from "@/router/index.js";
import i18n from "@/plugins/i18n.js";

createApp(App)
  .use(pinia)
  .use(vuetify)
  .use(router)
  .use(i18n)
  .mount("#app");
