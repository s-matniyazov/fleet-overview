<script setup>
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

import useUserStore from "@/store/UserStore";
import router from "@/router/index.js";
import {useAuthStore} from "@/store/UseAuthStore.js";
const userStore = useUserStore();

const username = ref("username");
const password = ref("password");

function onLogin() {
  axiosIns.post(URIS.LOGIN, {login: username.value, password: password.value})
      .then(res => {
        if (res.data.status === 200) {
          userStore.token = res.data.data;
          const authStore = useAuthStore()

          authStore.login(res.data.data);

          router.push("/");
        } else {
          showMessage("Please check your login or password")
        }
      }).catch(e => {
    showMessage(e)
  })
}
</script>

<template>
  <div class="auth-page">
    <div class="container-fluid p-0">
      <div class="row g-0">
        <div class="col-xxl-3 col-lg-4 col-md-5">
          <div class="auth-full-page-content d-flex p-sm-5 p-4">
            <div class="w-100">
              <div class="d-flex flex-column h-100">
                <div class="mb-4 mb-md-5 text-center">
                  <router-link to="/" class="d-block auth-logo">
                    <img src="@/assets/logo.png" alt="" size="12"> <span class="logo-txt">Fleet</span>
                  </router-link>
                </div>
                <div class="auth-content my-auto">
                  <div class="text-center">
                    <h5 class="mb-0">Добро пожаловать !</h5>
                    <p class="text-muted mt-2">Войдите, чтобы продолжить в Taskwise.</p>
                  </div>
                  <form class="mt-4 pt-2">
                    <div class="mb-3">
                      <label class="form-label">Имя пользователя</label>
                      <input v-model="username" type="text" class="form-control" id="username" placeholder="Введите имя пользователя">
                    </div>
                    <div class="mb-3">
                      <div class="d-flex align-items-start">
                        <div class="flex-grow-1">
                          <label class="form-label">Пароль</label>
                        </div>
                        <div class="flex-shrink-0">
                          <div class="">
                            <a href="auth-recoverpw.html" class="text-muted">Забыли пароль?</a>
                          </div>
                        </div>
                      </div>

                      <div class="input-group auth-pass-inputgroup">
                        <input v-model="password" type="password" class="form-control" placeholder="Введите пароль" aria-label="Password"
                               aria-describedby="password-addon">
                        <button class="btn btn-light shadow-none ms-0" type="button" id="password-addon"><i
                            class="mdi mdi-eye-outline"></i></button>
                      </div>
                    </div>
                    <div class="row mb-4">
                      <div class="col">
                        <div class="form-check">
                          <input class="form-check-input" type="checkbox" id="remember-check">
                          <label class="form-check-label" for="remember-check">Запомнить меня</label>
                        </div>
                      </div>
                    </div>
                    <div class="mb-3">
                      <button class="btn btn-primary w-100 waves-effect waves-light" type="button" @click="e => {onLogin(); e.stopPropagation()}">Войти</button>
                    </div>
                  </form>

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
    </div>
  </div>
</template>

<style scoped>

</style>
