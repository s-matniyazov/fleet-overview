<script setup>
import {ref} from "vue";
import axiosIns from "@/plugins/axios.js";
import {URIS} from "@/constants/UriConstants.js";
import {showMessage} from "@/util/utils.js";

import useUserStore from "@/store/UserStore";
import router from "@/router/index.js";
import {useAuthStore} from "@/store/UseAuthStore.js";
import UInput from "@/components/base/UInput.vue";
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

          router.push("/company-layout");
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
                    <p class="text-muted mt-2">Войдите, чтобы продолжить в Fleet.</p>
                  </div>
                  <form class="mt-4 pt-2">
                    <div class="mb-3">
                      <UInput v-model="username" placeholder="Введите имя пользователя" label="Username"/>
                    </div>
                    <div class="mb-3">
                      <UInput v-model="password" placeholder="Введите пароль" label="Password" type="password"/>
                    </div>
                    <div class="mb-3">
                      <button class="btn btn-primary w-100 waves-effect waves-light" type="button" @click="e => {onLogin(); e.stopPropagation()}">Войти</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xxl-9 col-lg-8 col-md-7">
          <div class="auth-bg pt-md-5 p-4 d-flex">
            <div class="bg-overlay bg-primary"></div>
            <div class="row justify-content-center align-items-center">
              <div class="col-xl-7">
                <div class="p-0 p-sm-4 px-xl-0">
                  <div class="carousel slide">
                    <div class="carousel-inner">
                      <div class="carousel-item active">
                        <div class="test-contain text-white">
                          <i class="bx bxs-quote-alt-left text-success display-6"></i>

                          <h4 class="mt-4 fw-medium lh-base text-white">“Fleet – это современный инструмент для
                            управления задачами и организации работы. Он позволяет пользователям планировать ежедневные
                            задачи, расставлять приоритеты и отслеживать их выполнение.”
                          </h4>
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
