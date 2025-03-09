

const routes = [
    {path: '/login', meta: {requiresAuth: false}, component: import('@/layouts/LoginLayout.vue')},
    {
        path: '/',
        meta: {requiresAuth: false},
        component: import('@/layouts/MainLayout.vue'),
        children: [
            {path: '/', meta: {requiresAuth: true}, redirect: "/dashboard"},
            {path: '/dashboard', meta: {requiresAuth: true}, component: import('@/components/Dashboard.vue')},

            // fleet
            {path: '/fleet-compliance', meta: {requiresAuth: true}, component: import('@/components/fleet/FleetCompliance.vue')},
            {path: '/fleet-overview', meta: {requiresAuth: true}, component: import('@/components/fleet/FleetOverview.vue')},
            {path: '/maintenance', meta: {requiresAuth: true}, component: import('@/components/fleet/Maintenance.vue')},

            {path: '/settings', meta: {requiresAuth: true}, component: import('@/components/setting/SettingsPage.vue')},
            {path: '/user-profile', meta: {requiresAuth: true}, component: import('@/components/profile/UserProfile.vue')},
        ]
    }
]

export default routes;
