import { createPinia } from "pinia";

// taskwise
import '@/assets/css/main.css'
import '@/assets/css/app.min.css'
import '@/assets/css/bootstrap.min.css'
import '@mdi/font/css/materialdesignicons.min.css';

const pinia = createPinia();

import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router/index.js";
import i18n from "@/plugins/i18n.js";

createApp(App)
    .use(pinia)
    .use(router)
    .use(i18n)
    .mount('#app')
