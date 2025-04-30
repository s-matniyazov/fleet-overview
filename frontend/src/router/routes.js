

const routes = [
    {path: '/login', name: "Login", meta: {requiresAuth: false}, component: import('@/layouts/LoginLayout.vue')},
    {path: '/company', name: "Company", meta: {requiresAuth: true}, component: import('@/components/company/Company.vue')},
    {
        path: '/',
        meta: {requiresAuth: false},
        component: import('@/layouts/MainLayout.vue'),
        children: [
            {path: '/', name: "Home", meta: {requiresAuth: true}, redirect: "/dashboard"},
            {path: '/dashboard', name: "Dashboard", meta: {requiresAuth: true}, component: import('@/components/Dashboard.vue')},

            // fleet
            {
                path: '/fleet-compliance', name: "Fleet Compliance", meta: {requiresAuth: true}, component: import('@/components/fleet/FleetCompliance.vue'),
            },
            {path: '/fleet-overview', name: "Fleet Overview", meta: {requiresAuth: true}, component: import('@/components/fleet/FleetOverview.vue')},
            {path: '/maintenance', name: "Maintenance", meta: {requiresAuth: true}, component: import('@/components/fleet/Maintenance.vue')},


            // references
            {path: '/country', name: "Country", meta: {requiresAuth: true}, component: import('@/components/references/Country.vue')},
            {path: '/city', name: "States", meta: {requiresAuth: true}, component: import('@/components/references/City.vue')},
            {path: '/fuel-type', name: "Fuel Type", meta: {requiresAuth: true}, component: import('@/components/references/FuelType.vue')},
            {path: '/model-maker', name: "Model Maker", meta: {requiresAuth: true}, component: import('@/components/references/ModelMaker.vue')},
            {path: '/owner-operator', name: "Owner Operator", meta: {requiresAuth: true}, component: import('@/components/references/OwnerOperator.vue')},
            {path: '/ownership-type', name: "Ownership Type", meta: {requiresAuth: true}, component: import('@/components/references/OwnershipType.vue')},
            {path: '/purchase-type', name: "Purchase Type", meta: {requiresAuth: true}, component: import('@/components/references/PurchaseType.vue')},

            {path: '/settings', name: "Settings", meta: {requiresAuth: true}, component: import('@/components/setting/SettingsPage.vue')},
            {path: '/user-profile', name: "User Profile", meta: {requiresAuth: true}, component: import('@/components/profile/UserProfile.vue')},
        ]
    }
]

export default routes;
