<script setup>

import VerticalMenu from "@/components/VerticalMenu.vue";
import {onMounted, ref} from "vue";
import feather from "feather-icons";
import router from "@/router/index.js";
import URightOverlay from "@/components/base/URightOverlay.vue";
import UDropDown from "@/components/base/UDropDown.vue";
import {useAuthStore} from "@/store/UseAuthStore.js";

const act = ref(false);

const authStore =  useAuthStore()
function onLogout() {
  authStore.logout();
  pushPage('/login');
}

function pushPage(page) {
  router.push(page);
}

onMounted(() => {
  feather.replace();
});
</script>

<template>
  <div id="layout-wrapper">

    <header id="page-topbar">
      <div class="navbar-header">
        <div class="d-flex">

          <div class="navbar-brand-box">
            <a href="index.html" class="logo logo-dark">
                                <span class="logo-sm">
                                    <img src="@/assets/images/logo-sm.svg" alt="" height="24">
                                </span>
              <span class="logo-lg">
                                    <img src="@/assets/images/logo-sm.svg" alt="" height="24"> <span class="logo-txt">FB-TASKWISE</span>
                                </span>
            </a>

            <a href="index.html" class="logo logo-light">
                                <span class="logo-sm">
                                    <img src="@/assets/images/logo-sm.svg" alt="" height="24">
                                </span>
              <span class="logo-lg">
                                    <img src="@/assets/images/logo-sm.svg" alt="" height="24"> <span class="logo-txt">FB-TASKWISE</span>
                                </span>
            </a>
          </div>

          <button type="button" class="btn btn-sm px-3 font-size-16 header-item" id="vertical-menu-btn">
            <i class="fa fa-fw fa-bars"></i>
          </button>

        </div>

        <div class="d-flex">

          <div class="dropdown d-none d-sm-inline-block">
            <button type="button" class="btn header-item"
                    data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <img id="header-lang-img" src="@/assets/images/flags/us.jpg" alt="Header Language" height="16">
            </button>
            <div class="dropdown-menu dropdown-menu-end">

              <a href="javascript:void(0);" class="dropdown-item notify-item language" data-lang="sp">
                <img src="@/assets/images/flags/spain.jpg" alt="user-image" class="me-1" height="12">
                <span class="align-middle">O'zbek</span>
              </a>

              <a href="javascript:void(0);" class="dropdown-item notify-item language" data-lang="ru">
                <img src="@/assets/images/flags/russia.jpg" alt="user-image" class="me-1" height="12">
                <span class="align-middle">Русский</span>
              </a>

              <a href="javascript:void(0);" class="dropdown-item notify-item language" data-lang="en">
                <img src="@/assets/images/flags/us.jpg" alt="user-image" class="me-1" height="12">
                <span class="align-middle">English</span>
              </a>
            </div>
          </div>

          <div class="dropdown d-none d-sm-inline-block">
            <button type="button" class="btn header-item" id="mode-setting-btn">
              <i data-feather="moon" class="icon-lg layout-mode-dark"></i>
              <i data-feather="sun" class="icon-lg layout-mode-light"></i>
            </button>
          </div>

          <div class="dropdown d-inline-block">
            <button type="button" class="btn header-item noti-icon position-relative" :class="act&&'show'" @click="act = !act">
              <i data-feather="bell" class="icon-lg"></i>
              <span class="badge bg-danger rounded-pill">5</span>
            </button>
            <div class="dropdown-menu dropdown-menu-lg dropdown-menu-end p-0" :class="act&&'show'">
              <div class="p-3">
                <div class="row align-items-center">
                  <div class="col">
                    <h6 class="m-0"> Уведомления </h6>
                  </div>
                  <div class="col-auto">
                    <a href="#!" class="small text-reset text-decoration-underline"> Непрочитано (3)</a>
                  </div>
                </div>
              </div>
              <div data-simplebar style="max-height: 230px;">
                <a href="#!" class="text-reset notification-item">
                  <div class="d-flex">
                    <div class="flex-shrink-0 me-3">
                      <img src="@/assets/images/users/avatar-3.jpg" class="rounded-circle avatar-sm" alt="user-pic">
                    </div>
                    <div class="flex-grow-1">
                      <h6 class="mb-1">James Lemire</h6>
                      <div class="font-size-13 text-muted">
                        <p class="mb-1">It will seem like simplified English.</p>
                        <p class="mb-0"><i class="mdi mdi-clock-outline"></i> <span>1 hour ago</span></p>
                      </div>
                    </div>
                  </div>
                </a>
                <a href="#!" class="text-reset notification-item">
                  <div class="d-flex">
                    <div class="flex-shrink-0 avatar-sm me-3">
                                                <span class="avatar-title bg-primary rounded-circle font-size-16">
                                                    <i class="bx bx-cart"></i>
                                                </span>
                    </div>
                    <div class="flex-grow-1">
                      <h6 class="mb-1">Your order is placed</h6>
                      <div class="font-size-13 text-muted">
                        <p class="mb-1">If several languages coalesce the grammar</p>
                        <p class="mb-0"><i class="mdi mdi-clock-outline"></i> <span>3 min ago</span></p>
                      </div>
                    </div>
                  </div>
                </a>
                <a href="#!" class="text-reset notification-item">
                  <div class="d-flex">
                    <div class="flex-shrink-0 avatar-sm me-3">
                                                <span class="avatar-title bg-success rounded-circle font-size-16">
                                                    <i class="bx bx-badge-check"></i>
                                                </span>
                    </div>
                    <div class="flex-grow-1">
                      <h6 class="mb-1">Your item is shipped</h6>
                      <div class="font-size-13 text-muted">
                        <p class="mb-1">If several languages coalesce the grammar</p>
                        <p class="mb-0"><i class="mdi mdi-clock-outline"></i> <span>3 min ago</span></p>
                      </div>
                    </div>
                  </div>
                </a>

                <a href="#!" class="text-reset notification-item">
                  <div class="d-flex">
                    <div class="flex-shrink-0 me-3">
                      <img src="@/assets/images/users/avatar-6.jpg" class="rounded-circle avatar-sm" alt="user-pic">
                    </div>
                    <div class="flex-grow-1">
                      <h6 class="mb-1">Salena Layfield</h6>
                      <div class="font-size-13 text-muted">
                        <p class="mb-1">As a skeptical Cambridge friend of mine occidental.</p>
                        <p class="mb-0"><i class="mdi mdi-clock-outline"></i> <span>1 hour ago</span></p>
                      </div>
                    </div>
                  </div>
                </a>
              </div>
              <div class="p-2 border-top d-grid">
                <a class="btn btn-sm btn-link font-size-14 text-center" href="javascript:void(0)">
                  <i class="mdi mdi-arrow-right-circle me-1"></i> <span>Посмотреть больше...</span>
                </a>
              </div>
            </div>
          </div>

          <div class="dropdown d-inline-block">
            <button type="button" class="btn header-item right-bar-toggle me-2" @click="pushPage('settings')">
              <i data-feather="settings" class="icon-lg"></i>
            </button>
          </div>

          <UDropDown btn_classes="header-item bg-light-subtle border-start border-end">
            <template #header>
              <img class="rounded-circle header-profile-user" src="@/assets/images/users/avatar-1.jpg"
                   alt="Header Avatar">
              <span class="d-none d-xl-inline-block ms-1 fw-medium">Shawn L.</span>
              <i class="mdi mdi-chevron-down d-none d-xl-inline-block"></i>
            </template>
            <template #body>
              <a class="dropdown-item" @click="e => {pushPage('user-profile'); e.stopPropagation()}"><i class="mdi mdi mdi-face-man font-size-16 align-middle me-1"></i> Профиль</a>
              <a class="dropdown-item" @click="e => {pushPage('user-block'); e.stopPropagation()}"><i class="mdi mdi-lock font-size-16 align-middle me-1"></i> Экран блокировки</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" @click="e => {onLogout(); e.stopPropagation()}"><i class="mdi mdi-logout font-size-16 align-middle me-1"></i> Выйти</a>
            </template>
          </UDropDown>

<!--          <div class="dropdown d-inline-block">-->
<!--            <button type="button" class="btn header-item bg-light-subtle border-start border-end">-->
<!--              <img class="rounded-circle header-profile-user" src="@/assets/images/users/avatar-1.jpg"-->
<!--                   alt="Header Avatar">-->
<!--              <span class="d-none d-xl-inline-block ms-1 fw-medium">Shawn L.</span>-->
<!--              <i class="mdi mdi-chevron-down d-none d-xl-inline-block"></i>-->
<!--            </button>-->
<!--            <div class="dropdown-menu dropdown-menu-end">-->

<!--              <a class="dropdown-item" href="user-profile.html"><i-->
<!--                  class="mdi mdi mdi-face-man font-size-16 align-middle me-1"></i> Профиль</a>-->
<!--              <a class="dropdown-item" href="auth-lock-screen.html"><i-->
<!--                  class="mdi mdi-lock font-size-16 align-middle me-1"></i> Экран блокировки</a>-->
<!--              <div class="dropdown-divider"></div>-->
<!--              <a class="dropdown-item" href="auth-login.html"><i-->
<!--                  class="mdi mdi-logout font-size-16 align-middle me-1"></i> Выйти</a>-->
<!--            </div>-->
<!--          </div>-->

        </div>
      </div>
    </header>

    <vertical-menu/>

    <div class="main-content">
      <div class="page-content">
        <div class="container-fluid">
          <router-view/>
        </div>

      </div>

      <footer class="footer">
        <div class="container-fluid">
          <div class="row">
            <div class="col-sm-6">
              <p class="mb-0">©2025 Taskwise. Все права защищены.</p>
            </div>
            <div class="col-sm-6">
              <div class="text-sm-end d-none d-sm-block">
                Дизайн и разработка <a href="https://www.fido-biznes.uz" target="blank"
                                       class="text-decoration-underline">Fido-Biznes</a>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </div>

  </div>

  <div class="right-bar">
    <div data-simplebar class="h-100">
      <div class="rightbar-title d-flex align-items-center p-3">

        <h5 class="m-0 me-2">Theme Customizer</h5>

        <a class="right-bar-toggle ms-auto">
          <i class="mdi mdi-close noti-icon"></i>
        </a>
      </div>


      <hr class="m-0"/>

      <div class="p-4">
        <h6 class="mb-3">Layout</h6>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout"
                 id="layout-vertical" value="vertical">
          <label class="form-check-label" for="layout-vertical">Vertical</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout"
                 id="layout-horizontal" value="horizontal">
          <label class="form-check-label" for="layout-horizontal">Horizontal</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2">Layout Mode</h6>

        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-mode"
                 id="layout-mode-light" value="light">
          <label class="form-check-label" for="layout-mode-light">Light</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-mode"
                 id="layout-mode-dark" value="dark">
          <label class="form-check-label" for="layout-mode-dark">Dark</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2">Layout Width</h6>

        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-width"
                 id="layout-width-fuild" value="fuild"
                 onchange="document.body.setAttribute('data-layout-size', 'fluid')">
          <label class="form-check-label" for="layout-width-fuild">Fluid</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-width"
                 id="layout-width-boxed" value="boxed"
                 onchange="document.body.setAttribute('data-layout-size', 'boxed')">
          <label class="form-check-label" for="layout-width-boxed">Boxed</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2">Layout Position</h6>

        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-position"
                 id="layout-position-fixed" value="fixed"
                 onchange="document.body.setAttribute('data-layout-scrollable', 'false')">
          <label class="form-check-label" for="layout-position-fixed">Fixed</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-position"
                 id="layout-position-scrollable" value="scrollable"
                 onchange="document.body.setAttribute('data-layout-scrollable', 'true')">
          <label class="form-check-label" for="layout-position-scrollable">Scrollable</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2">Topbar Color</h6>

        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="topbar-color"
                 id="topbar-color-light" value="light" onchange="document.body.setAttribute('data-topbar', 'light')">
          <label class="form-check-label" for="topbar-color-light">Light</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="topbar-color"
                 id="topbar-color-dark" value="dark" onchange="document.body.setAttribute('data-topbar', 'dark')">
          <label class="form-check-label" for="topbar-color-dark">Dark</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2 sidebar-setting">Sidebar Size</h6>

        <div class="form-check sidebar-setting">
          <input class="form-check-input" type="radio" name="sidebar-size"
                 id="sidebar-size-default" value="default"
                 onchange="document.body.setAttribute('data-sidebar-size', 'lg')">
          <label class="form-check-label" for="sidebar-size-default">Default</label>
        </div>
        <div class="form-check sidebar-setting">
          <input class="form-check-input" type="radio" name="sidebar-size"
                 id="sidebar-size-compact" value="compact"
                 onchange="document.body.setAttribute('data-sidebar-size', 'md')">
          <label class="form-check-label" for="sidebar-size-compact">Compact</label>
        </div>
        <div class="form-check sidebar-setting">
          <input class="form-check-input" type="radio" name="sidebar-size"
                 id="sidebar-size-small" value="small" onchange="document.body.setAttribute('data-sidebar-size', 'sm')">
          <label class="form-check-label" for="sidebar-size-small">Small (Icon View)</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2 sidebar-setting">Sidebar Color</h6>

        <div class="form-check sidebar-setting">
          <input class="form-check-input" type="radio" name="sidebar-color"
                 id="sidebar-color-light" value="light" onchange="document.body.setAttribute('data-sidebar', 'light')">
          <label class="form-check-label" for="sidebar-color-light">Light</label>
        </div>
        <div class="form-check sidebar-setting">
          <input class="form-check-input" type="radio" name="sidebar-color"
                 id="sidebar-color-dark" value="dark" onchange="document.body.setAttribute('data-sidebar', 'dark')">
          <label class="form-check-label" for="sidebar-color-dark">Dark</label>
        </div>
        <div class="form-check sidebar-setting">
          <input class="form-check-input" type="radio" name="sidebar-color"
                 id="sidebar-color-brand" value="brand" onchange="document.body.setAttribute('data-sidebar', 'brand')">
          <label class="form-check-label" for="sidebar-color-brand">Brand</label>
        </div>

        <h6 class="mt-4 mb-3 pt-2">Direction</h6>

        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-direction"
                 id="layout-direction-ltr" value="ltr">
          <label class="form-check-label" for="layout-direction-ltr">LTR</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="layout-direction"
                 id="layout-direction-rtl" value="rtl">
          <label class="form-check-label" for="layout-direction-rtl">RTL</label>
        </div>

      </div>

    </div>
  </div>
</template>

<style scoped>

</style>
`
