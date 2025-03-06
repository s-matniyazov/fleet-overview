import {createRouter, createWebHistory} from 'vue-router'

import routes from "@/router/routes.js";
import {useAuthStore} from "@/store/UseAuthStore.js";

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore() // ✅ Access Pinia store safely

    if (to.meta.requiresAuth && !authStore.isAuthenticated()) {
        next('/login');
    } else if (to.path === '/login' && authStore.isAuthenticated()) {
        next('/');
    } else {
        next();
    }
})

export default router
