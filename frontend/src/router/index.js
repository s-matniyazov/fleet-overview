import {createRouter, createWebHistory} from 'vue-router'

import routes from "@/router/routes.js";
import {useAuthStore} from "@/store/UseAuthStore.js";
import {useRouterStore} from "@/store/RouterStore.js";

const router = createRouter({
    history: createWebHistory("/fleet/"),
    routes,
})

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore() // ✅ Access Pinia store safely
    const routerStore = useRouterStore()

    if (to.meta.requiresAuth && !authStore.isAuthenticated()) {
        next('/login');
    } else if (to.path === '/login' && authStore.isAuthenticated()) {
        next('/');
        routerStore.currentRouter = '/';
        routerStore.currentRouterName = '/';
    } else {
        next();
        routerStore.currentRouter = to.path;
        routerStore.currentRouterName = to.name;
    }
})

export default router
