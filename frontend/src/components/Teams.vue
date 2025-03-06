<script setup>

</script>

<template>


  <div class="row">
    <div class="col-12">

      <div class="card">

        <div class="card-header align-items-center d-flex">
          <h4 class="card-title mb-0 flex-grow-1">Команды</h4>
          <div class="flex-shrink-0">
            <ul class="nav justify-content-end nav-tabs-custom rounded card-header-tabs" role="tablist">
              <li class="nav-item">
                <a class="nav-link active" data-bs-toggle="tab" href="#tab1" role="tab">
                  <span class="d-block d-sm-none"><i class="fas fa-home"></i></span>
                  <span class="d-none d-sm-block">Команды</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-bs-toggle="tab" href="#tab2" role="tab">
                  <span class="d-block d-sm-none"><i class="far fa-envelope"></i></span>
                  <span class="d-none d-sm-block">Сотрудники</span>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-bs-toggle="tab" href="#tab3" role="tab">
                  <span class="d-block d-sm-none"><i class="far fa-user"></i></span>
                  <span class="d-none d-sm-block">Спр. состояния</span>
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
                <div class="row align-items-center">

                  <div class="col-md-6">
                    <div class="d-flex flex-wrap align-items-center justify-content-start gap-2 mb-3">
                      <div>
                        <a href="#" class="btn btn-light"><i class="bx bx-plus me-1"></i> Add New</a>
                      </div>
                      <div class="dropdown">
                        <a class="btn btn-link text-muted py-1 font-size-16 shadow-none dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                          <i class="bx bx-dots-horizontal-rounded"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-end">
                          <li><a class="dropdown-item" href="#">Action</a></li>
                          <li><a class="dropdown-item" href="#">Another action</a></li>
                          <li><a class="dropdown-item" href="#">Something else here</a></li>
                        </ul>
                      </div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="d-flex flex-wrap align-items-center justify-content-end gap-2 mb-3">
                      <div>
                        <ul class="nav nav-pills">
                          <li class="nav-item">
                            <a class="nav-link active" href="teams.html" data-bs-toggle="tooltip" data-bs-placement="top" title="List"><i class="bx bx-list-ul"></i></a>
                          </li>
                          <li class="nav-item">
                            <a class="nav-link" href="teams-grid.html" data-bs-toggle="tooltip" data-bs-placement="top" title="Grid"><i class="bx bx-grid-alt"></i></a>
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>

                  <div class="table-responsive mb-4">
                    <table class="table align-middle datatable dt-responsive table-check nowrap" style="border-collapse: collapse; border-spacing: 0 8px; width: 100%;">
                      <thead>
                      <tr>
                        <th scope="col" style="width: 50px;">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="checkAll">
                            <label class="form-check-label" for="checkAll"></label>
                          </div>
                        </th>
                        <th scope="col">Name</th>
                        <th scope="col">Position</th>
                        <th scope="col">Email</th>
                        <th scope="col">Tags</th>
                        <th style="width: 80px; min-width: 80px;">Action</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck1">
                            <label class="form-check-label" for="contacusercheck1"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-2.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Phyllis Gatlin</a>
                        </td>
                        <td>UI/UX Designer</td>
                        <td>phyllisgatlin@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary">Photoshop</a>
                            <a href="#" class="badge bg-primary-subtle text-primary">illustrator</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck2">
                            <label class="form-check-label" for="contacusercheck2"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-1.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">James Nix</a>
                        </td>
                        <td>Frontend Developer</td>
                        <td>jamesnix@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Html</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Css</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">2 + more</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck3">
                            <label class="form-check-label" for="contacusercheck3"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-3.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Darlene Smith</a>
                        </td>
                        <td>Backend Developer</td>
                        <td>darlenesmith@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Php</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Java</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Python</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck4">
                            <label class="form-check-label" for="contacusercheck4"></label>
                          </div>
                        </th>
                        <td>
                          <div class="avatar-sm d-inline-block align-middle me-2">
                            <div class="avatar-title bg-light-subtle text-light font-size-24 m-0 rounded-circle">
                              <i class="bx bxs-user-circle"></i>
                            </div>
                          </div>
                          <a href="#" class="text-body">William Swift</a>
                        </td>
                        <td>Full Stack Developer</td>
                        <td>williamswift@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Ruby</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Php</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">2 + more</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck5">
                            <label class="form-check-label" for="contacusercheck5"></label>
                          </div>
                        </th>
                        <td>
                          <div class="avatar-sm d-inline-block align-middle me-2">
                            <div class="avatar-title bg-light-subtle text-light font-size-24 m-0 rounded-circle">
                              <i class="bx bxs-user-circle"></i>
                            </div>
                          </div>
                          <a href="#" class="text-body">Kevin West</a>
                        </td>
                        <td>Frontend Developer</td>
                        <td>kevinwest@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Html</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Css</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">2 + more</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck6">
                            <label class="form-check-label" for="contacusercheck6"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-6.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Tommy Hayes</a>
                        </td>
                        <td>UI/UX Designer</td>
                        <td>tommyhayes@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Photoshop</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">illustrator</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck7">
                            <label class="form-check-label" for="contacusercheck7"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-8.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Diana Owens</a>
                        </td>
                        <td>Graphic Designer</td>
                        <td>dianaowens@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Photoshop</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">illustrator</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck8">
                            <label class="form-check-label" for="contacusercheck8"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-9.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Paul Sanchez</a>
                        </td>
                        <td>Angular Developer</td>
                        <td>paulsanchez@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Php</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Javascript</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck9">
                            <label class="form-check-label" for="contacusercheck9"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-9.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Peter Dryer</a>
                        </td>
                        <td>Web Designer</td>
                        <td>peterdryer@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Html</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Css</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">2 + more</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck10">
                            <label class="form-check-label" for="contacusercheck10"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-4.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Gerald Moyer</a>
                        </td>
                        <td>Backend Developer</td>
                        <td>geraldmoyer@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Php</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Javascript</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th scope="row">
                          <div class="form-check font-size-16">
                            <input type="checkbox" class="form-check-input" id="contacusercheck11">
                            <label class="form-check-label" for="contacusercheck11"></label>
                          </div>
                        </th>
                        <td>
                          <img src="@/assets/images/users/avatar-2.jpg" alt="" class="avatar-sm rounded-circle me-2">
                          <a href="#" class="text-body">Gail McGuire</a>
                        </td>
                        <td>Backend Developer</td>
                        <td>gailmcGuire@minia.com</td>
                        <td>
                          <div class="d-flex gap-2">
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Php</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">Javascript</a>
                            <a href="#" class="badge bg-primary-subtle text-primary font-size-11">2+ more</a>
                          </div>
                        </td>
                        <td>
                          <div class="dropdown">
                            <button class="btn btn-link font-size-16 shadow-none py-0 text-muted dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                              <i class="bx bx-dots-horizontal-rounded"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                              <li><a class="dropdown-item" href="#">Action</a></li>
                              <li><a class="dropdown-item" href="#">Another action</a></li>
                              <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                      </tbody>
                    </table>

                  </div>


                </div>
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
