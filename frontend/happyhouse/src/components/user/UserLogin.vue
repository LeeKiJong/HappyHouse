<template>
  <div
    class="mx-3 mt-3 border-radius-xl position-relative"
    :style="{
      backgroundImage: 'url(' + require('@/assets/img/vr-bg.jpg') + ')',
      backgroundSize: 'cover',
    }"
  >
    <main class="mt-1 main-content border-radius-lg">
      <div class="section min-vh-85 position-relative">
        <div class="pt-8 pb-0 text-center">
          <h1 class="font-weight-bolder text-info text-gradient">HappyHouse</h1>
          <p class="mb-0">서비스를 이용하기 전 로그인을 해주세요!</p>
        </div>
        <div class="container col-md-4">
          <div class="card-body">
            <form role="form" class="text-start">
              <label>아이디</label>
              <vsud-input
                id="userid"
                type="text"
                placeholder="아이디를 입력해주세요"
                name="userid"
                :value="user.userid"
              />
              <label>비밀번호</label>
              <vsud-input
                id="userpwd"
                type="password"
                placeholder="비밀번호를 입력해주세요"
                name="userpwd"
                :value="user.userpwd"
              />
              <vsud-switch id="rememberMe" name="rememberMe" checked>
                Remember me
              </vsud-switch>
              <div class="text-center">
                <vsud-button
                  class="my-4 mb-2"
                  variant="gradient"
                  color="info"
                  @click.prevent="confirm"
                  full-width
                  >로그인
                </vsud-button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </main>
  </div>
  <app-footer class="py-3 bg-white border-radius-lg" />
</template>

<script>
import AppFooter from "@/examples/Footer.vue";
import VsudInput from "@/components/vsud/VsudInput.vue";
import VsudSwitch from "@/components/vsud/VsudSwitch.vue";
import VsudButton from "@/components/vsud/VsudButton.vue";
import setTooltip from "@/assets/js/tooltip.js";

const body = document.getElementsByTagName("body")[0];
const memberStore = "memberStore";

import { mapMutations, mapState, mapActions } from "vuex";

export default {
  name: "VrInfo",
  components: {
    AppFooter,
    VsudInput,
    VsudSwitch,
    VsudButton,
  },
  data() {
    return {
      user: {
        userid: "",
        userpwd: "",
      },
    };
  },
  computed: {
    ...mapState(["isTransparent", "isNavFixed", "navbarFixed", "mcolor"]),
  },
  mounted() {
    setTooltip(this.$store.state.bootstrap);
    // if (this.$route.params.userid != null) {
    //   this.user.userid = this.$route.params.userid;
    //   this.user.userpwd = this.$route.params.userpwd;
    // }
  },
  beforeMount() {
    this.$store.state.showNavbar = false;
    this.$store.state.showSidenav = false;
    this.$store.state.showFooter = false;
    body.classList.add("virtual-reality");
    this.$store.state.isTransparent = "bg-white";
  },
  beforeUnmount() {
    this.$store.state.showNavbar = true;
    this.$store.state.showSidenav = true;
    this.$store.state.showFooter = true;
    body.classList.remove("virtual-reality");

    if (this.$store.state.isPinned === false) {
      const sidenav_show = document.querySelector(".g-sidenav-show");
      sidenav_show.classList.remove("g-sidenav-hidden");
      sidenav_show.classList.add("g-sidenav-pinned");
      this.$store.state.isPinned = true;
    }
    this.$store.state.isTransparent = "bg-transparent";
  },
  methods: {
    ...mapMutations(["navbarMinimize", "toggleConfigurator"]),
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),

    async confirm() {
      const user = {
        userid: document.getElementById("userid").value,
        userpwd: document.getElementById("userpwd").value,
      };

      await this.userConfirm(user);

      if (this.$store.state.memberStore.isLogin) {
        this.$router.push({ name: "Dashboard" });
      }
    },
  },
};
</script>
