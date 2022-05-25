<template>
  <nav
    class="shadow-none navbar navbar-main navbar-expand-lg border-radius-xl"
    v-bind="$attrs"
    id="navbarBlur"
    data-scroll="true"
    v-if="userInfo != null"
  >
    <div class="px-3 py-1 container-fluid">
      <breadcrumbs :currentPage="currentRouteName" :textWhite="textWhite" />
      <div
        class="mt-2 collapse navbar-collapse mt-sm-0 me-md-0 me-sm-4"
        :class="this.$store.state.isRTL ? 'px-0' : 'me-sm-4'"
        id="navbar"
      >
        <div
          class="pe-md-3 d-flex align-items-center"
          :class="this.$store.state.isRTL ? 'me-md-auto' : 'ms-md-auto'"
        ></div>
        <ul class="navbar-nav justify-content-end">
          <li class="nav-item d-flex align-items-center">
            <!-- <i
              class="fa fa-user"
              :class="this.$store.state.isRTL ? 'ms-sm-2' : 'me-sm-1'"
            ></i> -->
            <span class="text-sm"
              ><span class="font-weight-bold">{{ userInfo.username }}</span
              >님 환영합니다.</span
            >
          </li>
          <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
            <a
              href="#"
              @click="toggleSidebar"
              class="p-0 nav-link text-body"
              id="iconNavbarSidenav"
            >
              <div class="sidenav-toggler-inner">
                <i class="sidenav-toggler-line"></i>
                <i class="sidenav-toggler-line"></i>
                <i class="sidenav-toggler-line"></i>
              </div>
            </a>
          </li>
          <li class="px-3 nav-item d-flex align-items-center">
            <a
              class="p-0 nav-link"
              @click="toggleConfigurator"
              :class="textWhite ? textWhite : 'text-body'"
            >
            </a>
          </li>
          <li
            class="nav-item dropdown d-flex align-items-center"
            :class="this.$store.state.isRTL ? 'ps-2' : 'pe-2'"
          >
            <a
              href="#"
              class="p-0 nav-link"
              :class="[
                textWhite ? textWhite : 'text-body',
                showMenu ? 'show' : '',
              ]"
              id="dropdownMenuButton"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              @click="showMenu = !showMenu"
            >
              <i class="cursor-pointer fa fa-user fixed-plugin-button-nav"></i>
            </a>
            <ul
              class="px-2 py-3 dropdown-menu dropdown-menu-end me-sm-n4"
              :class="showMenu ? 'show' : ''"
              aria-labelledby="dropdownMenuButton"
            >
              <li class="mb-2">
                <router-link to="/profile">
                  <a class="dropdown-item border-radius-md" href="javascript:;">
                    <div class="py-1 d-flex">
                      <div class="d-flex flex-column justify-content-center">
                        <h6 class="mb-1 text-sm font-weight-bold">Profile</h6>
                      </div>
                    </div>
                  </a>
                </router-link>
              </li>
              <li class="mb-2" @click="onClickLogout">
                <a class="dropdown-item border-radius-md" href="javascript:;">
                  <div class="py-1 d-flex">
                    <div class="d-flex flex-column justify-content-center">
                      <h6 class="mb-1 text-sm font-weight-bold">Logout</h6>
                    </div>
                  </div>
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
<script>
import Breadcrumbs from "../Breadcrumbs.vue";
import { mapMutations, mapActions, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  name: "navbar",
  data() {
    return {
      showMenu: false,
      token: localStorage.getItem("access-token"),
    };
  },
  props: ["minNav", "textWhite"],
  created() {
    this.minNav;
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapMutations(["navbarMinimize", "toggleConfigurator"]),
    ...mapActions(["toggleSidebarColor"]),
    toggleSidebar() {
      this.toggleSidebarColor("bg-white");
      this.navbarMinimize();
    },
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      this.$router.push({ name: "login" });
    },
  },
  components: {
    Breadcrumbs,
  },
  computed: {
    ...mapState(memberStore, [
      "isLogin",
      "userInfo",
      "img_path",
      "isUpdate",
      "isUpdateError",
      "isDelete",
      "isDeleteError",
    ]),
    currentRouteName() {
      return this.$route.name;
    },
  },
  updated() {
    const navbar = document.getElementById("navbarBlur");
    window.addEventListener("scroll", () => {
      if (window.scrollY > 10 && this.$store.state.isNavFixed) {
        navbar.classList.add("blur");
        navbar.classList.add("position-sticky");
        navbar.classList.add("shadow-blur");
      } else {
        navbar.classList.remove("blur");
        navbar.classList.remove("position-sticky");
        navbar.classList.remove("shadow-blur");
      }
    });
  },
};
</script>
