

const routes = [
    {path: '/login', meta: {requiresAuth: false}, component: import('@/layouts/LoginLayout.vue')},
    {path: '/user-block', meta: {requiresAuth: true}, component: import('@/components/profile/UserBlock.vue')},
    {
        path: '/',
        meta: {requiresAuth: false},
        component: import('@/layouts/MainLayout.vue'),
        children: [
            {path: '/', meta: {requiresAuth: true}, redirect: "/dashboard"},
            {path: '/dashboard', meta: {requiresAuth: true}, component: import('@/components/Dashboard.vue')},
            {path: '/projects', meta: {requiresAuth: true}, component: import('@/components/project/ProjectsPage.vue')},
            {path: '/tasks', meta: {requiresAuth: true}, component: import('@/components/task/TasksPage.vue')},
            {path: '/teams', meta: {requiresAuth: true}, component: import('@/components/team/TeamsPage.vue')},
            {path: '/calendar', meta: {requiresAuth: true}, component: import('@/components/Calendar.vue')},
            {path: '/reports', meta: {requiresAuth: true}, component: import('@/components/Reports.vue')},
            {path: '/settings', meta: {requiresAuth: true}, component: import('@/components/setting/SettingsPage.vue')},
            {path: '/user-profile', meta: {requiresAuth: true}, component: import('@/components/profile/UserProfile.vue')},
        ]
    }
]

export default routes;
