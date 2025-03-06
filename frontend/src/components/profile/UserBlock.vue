<script setup>

import {useAuthStore} from "@/store/UseAuthStore.js";
import router from "@/router/index.js";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";

const authStore = useAuthStore()
function onLogin() {
  authStore.logout();
  window.location.href = "/login";
}

function onUnlock() {
  axiosIns.post(URIS.UNLOCK)
      .then(res => {

      })
}

</script>

<template>
  <div class="row g-0">
    <div class="col-xxl-3 col-lg-4 col-md-5">
      <div class="auth-full-page-content d-flex p-sm-5 p-4">
        <div class="w-100">
          <div class="d-flex flex-column h-100">
            <div class="mb-4 mb-md-5 text-center">
              <a href="index.html" class="d-block auth-logo">
                <img src="@/assets/images/logo-sm.svg" alt="" height="28"> <span class="logo-txt">FB-TASKWISE</span>
              </a>
            </div>
            <div class="auth-content my-auto">
              <div class="text-center">
                <h5 class="mb-0">Экран блокировки</h5>
                <p class="text-muted mt-2">Введите пароль, чтобы разблокировать экран!</p>
              </div>
              <div class="user-thumb text-center mb-4 mt-4 pt-2">
                <img src="@/assets/images/users/avatar-1.jpg" class="rounded-circle img-thumbnail avatar-lg"
                     alt="thumbnail">
                <h5 class="font-size-15 mt-3">Shawn</h5>
              </div>
              <form class="mt-4" action="">
                <div class="mb-3">
                  <label class="form-label" for="userpassword">Пароль</label>
                  <input type="password" class="form-control" id="userpassword" placeholder="Введите пароль">
                </div>
                <div class="mb-3 mt-4">
                  <button class="btn btn-primary w-100 waves-effect waves-light" type="button" @click="onUnlock">Разблокировать</button>
                </div>
              </form>

              <div class="mt-5 text-center">
                <p class="text-muted mb-0">Не вы? вернуться
                  <a @click="onLogin" class="text-primary fw-semibold">Войти </a>
                </p>
              </div>
            </div>
            <div class="mt-4 mt-md-5 text-center">
              <p class="mb-0">©2025 Taskwise. Все права защищены.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-xxl-9 col-lg-8 col-md-7">
      <div class="auth-bg pt-md-5 p-4 d-flex">
        <div class="bg-overlay bg-primary"></div>
        <ul class="bg-bubbles">
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
          <li></li>
        </ul>
        <div class="row justify-content-center align-items-center">
          <div class="col-xl-7">
            <div class="p-0 p-sm-4 px-xl-0">
              <div id="reviewcarouselIndicators" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                  <div class="carousel-item active">
                    <div class="testi-contain text-white">
                      <i class="bx bxs-quote-alt-left text-success display-6"></i>

                      <h4 class="mt-4 fw-medium lh-base text-white">“Taskwise – это современный инструмент для
                        управления задачами и организации работы. Он позволяет пользователям планировать ежедневные
                        задачи, расставлять приоритеты и отслеживать их выполнение.”
                      </h4>
                      <div class="mt-4 pt-3 pb-5">
                        <div class="d-flex align-items-start">

                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
