<script setup>
</script>

<template>
  <div class="vertical-menu">
    <div data-simplebar class="h-100">
      <div id="sidebar-menu">
        <ul class="metismenu list-unstyled" id="side-menu">
          <li class="menu-title" data-key="t-menu">Меню</li>
          <li>
            <router-link to="dashboard">
              <i data-feather="home"></i>
              <span data-key="t-dashboard">Дашборд</span>
            </router-link>
          </li>

          <li>
            <router-link to="projects">
              <i data-feather="grid"></i>
              <span data-key="t-projects">Проекты</span>
            </router-link>
          </li>

          <li>
            <router-link to="tasks">
              <i data-feather="check-circle"></i>
              <span data-key="t-tasks">Задачи</span>
            </router-link>
          </li>

          <li>
            <router-link to="teams">
              <i data-feather="users"></i>
              <span data-key="t-teams">Команды</span>
            </router-link>
          </li>

          <li>
            <router-link to="calendar">
              <i data-feather="calendar"></i>
              <span data-key="t-teams">Планирование</span>
            </router-link>
          </li>

          <li>
            <router-link to="reports">
              <i data-feather="pie-chart"></i>
              <span data-key="t-reports">Отчёты</span>
            </router-link>
          </li>

        </ul>
        <!--
        <div class="card sidebar-alert border-0 text-center mx-4 mb-0 mt-5">
          <div class="card-body">
            <img src="@/assets/images/giftbox.png" alt="">
            <div class="mt-4">
              <h5 class="alertcard-title font-size-16">Неограниченный доступ</h5>
              <p class="font-size-13">Обновите свой тарифный план с бесплатной пробной версии, выбрав
                «Бизнес-план».</p>
              <router-link to="#!" class="btn btn-primary mt-2">Обновите сейчас</router-link>
            </div>
          </div>
        </div>
        -->
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>
