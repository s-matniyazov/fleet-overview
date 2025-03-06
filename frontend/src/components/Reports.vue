<script setup>

</script>

<template>
  <div class="row">
    <div class="col-12">
      <div class="card">
        <div class="card-header align-items-center d-flex">
          <h4 class="card-title mb-0 flex-grow-1">Отчёты</h4>
          <div class="flex-shrink-0">
            <ul class="nav justify-content-end nav-tabs-custom rounded card-header-tabs" role="tablist">
              <li class="nav-item">
                <a class="nav-link active" data-bs-toggle="tab" href="#tab1" role="tab">
                  <span class="d-block d-sm-none"><i class="fas fa-home"></i></span>
                  <span class="d-none d-sm-block">Отчет 1</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-bs-toggle="tab" href="#tab2" role="tab">
                  <span class="d-block d-sm-none"><i class="far fa-envelope"></i></span>
                  <span class="d-none d-sm-block">Отчет 2</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-bs-toggle="tab" href="#tab3" role="tab">
                  <span class="d-block d-sm-none"><i class="far fa-user"></i></span>
                  <span class="d-none d-sm-block">Отчет 3</span>
                </a>
              </li>
            </ul>
          </div>
        </div>

        <div class="card-body">
          <div class="tab-content text-muted">
            <div class="tab-pane active" id="tab1" role="tabpanel">
              <p class="mb-0">
                1
              </p>
            </div>
            <div class="tab-pane" id="tab2" role="tabpanel">
              <p class="mb-0">
                2
              </p>
            </div>
            <div class="tab-pane" id="tab3" role="tabpanel">
              <p class="mb-0">
                3
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <div class="row">

  </div>


</template>

<style scoped>

</style>
