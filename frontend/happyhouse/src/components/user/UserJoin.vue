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
        <div class="pt-4 container">
          <div class="row justify-content-center">
            <div class="mx-auto col-xl-4 col-lg-5 col-md-7">
              <div class="card z-index-0">
                <div class="pt-4 text-center card-header">
                  <img src="@/assets/img/home.png" style="width: 25%" />
                  <br /><br />
                  <h3>Welcome to</h3>
                  <h3>HAPPY HOUSE</h3>
                </div>
                <div class="card-body">
                  <form role="form">
                    <div class="mb-3">
                      <vsud-input
                        id="userid"
                        type="text"
                        placeholder="아이디를 입력해주세요..."
                        aria-label="userid"
                        style="width: 55%; float: left"
                        @value="setUserinfo"
                      />
                      <vsud-button
                        variant="gradient"
                        color="info"
                        style="width: 38%; float: right"
                        @click.prevent="idCheckConfirm"
                        >아이디 중복 체크
                      </vsud-button>
                    </div>
                    <div class="mb-3">
                      <vsud-input
                        id="userpwd"
                        type="password"
                        placeholder="비밀번호를 입력해주세요..."
                        aria-label="userpwd"
                        @value="setUserinfo"
                      />
                    </div>
                    <div class="mb-3">
                      <vsud-input
                        id="username"
                        type="text"
                        placeholder="사용하실 이름을 입력해주세요..."
                        aria-label="username"
                        @value="setUserinfo"
                      />
                    </div>
                    <div class="mb-3">
                      <vsud-input
                        id="email"
                        type="email"
                        placeholder="이메일을 입력해주세요..."
                        aria-label="email"
                        @value="setUserinfo"
                      />
                    </div>
                    <div class="text-center">
                      <vsud-button
                        color="info"
                        full-width
                        variant="gradient"
                        class="my-4 mb-2"
                        @click.prevent="confirm()"
                        >회원가입</vsud-button
                      >
                    </div>
                    <p class="text-sm mt-3 mb-0">
                      비밀번호를 잊으셨나요?
                      <span
                        @click.prevent="findPass"
                        class="text-dark font-weight-bolder"
                      >
                        비밀번호 찾기
                      </span>
                    </p>
                  </form>
                </div>
              </div>
            </div>
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
import VsudButton from "@/components/vsud/VsudButton.vue";
import setTooltip from "@/assets/js/tooltip.js";
import swal from "sweetalert";
const body = document.getElementsByTagName("body")[0];
const memberStore = "memberStore";

import { mapMutations, mapState, mapActions } from "vuex";

export default {
  name: "VrInfo",

  components: {
    AppFooter,
    VsudInput,
    VsudButton,
  },
  data() {
    return {
      IdCheck: false,
      useridInput: "",
      user: {
        userid: "",
        userpwd: "",
        username: "",
        email: "",
      },
    };
  },
  computed: {
    ...mapState(["isTransparent", "isNavFixed", "navbarFixed", "mcolor"]),
    ...mapState(memberStore, [
      "isRegister",
      "isRegisterError",
      "isIdCheck",
      "isPwdCheck",
    ]),
  },
  mounted() {
    setTooltip(this.$store.state.bootstrap);
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
    ...mapActions(memberStore, ["registerConfirm", "getUserId", "getUserPwd"]),
    async findPass() {
      var tempId = await swal({
        title: "아이디를 입력해주세요.",
        content: {
          element: "input",
          attributes: {
            placeholder: "아이디를 입력하세요...",
            type: "text",
          },
        },
      });
      var tempEmail = await swal({
        title: "이메일을 입력해주세요.",
        content: {
          element: "input",
          attributes: {
            placeholder: "이메일을 입력하세요...",
            type: "text",
          },
        },
      });

      let param = {
        userid: tempId,
        email: tempEmail,
      };
      await this.getUserPwd(param);

      if (this.isPwdCheck != "") {
        swal({
          title: "비밀번호는 " + this.isPwdCheck + "입니다.",
          icon: "success",
        });
      } else {
        swal({ title: "아이디 혹은 이메일이 틀렸습니다.", icon: "error" });
      }
    },
    async idCheckConfirm() {
      if (!this.user.userid) {
        swal({ title: "아이디를 입력해주세요.", icon: "error" });
        return false;
      }
      await this.getUserId(document.getElementById("userid").value);
      if (this.isIdCheck) {
        this.IdCheck = true;
        this.useridInput = document.getElementById("userid").value;

        swal({ title: "사용가능한 아이디입니다.", icon: "success" });
      } else {
        this.IdCheck = false;
        swal({ title: "이미 존재하는 아이디입니다.", icon: "error" });
      }
    },
    setUserinfo(value) {
      this.user.userid = value[0].value;
      this.user.userpwd = value[1].value;
      this.user.username = value[2].value;
      this.user.email = value[3].value;
    },
    async confirm() {
      if (!this.user.userid) {
        swal({ title: "아이디를 입력해주세요.", icon: "error" });
        document.getElementById("userid").focus;
        return false;
      } else if (this.useridInput != document.getElementById("userid").value) {
        swal({ title: "아이디 중복 검사를 해주세요.", icon: "error" });
        return false;
      }
      if (!this.IdCheck) {
        swal({ title: "아이디 중복 검사를 해주세요.", icon: "error" });
        return false;
      }
      if (!this.user.userpwd) {
        swal({ title: "비밀번호를 입력해주세요.", icon: "error" });
        return false;
      }
      if (this.user.userpwd.length < 8 || this.user.userpwd.length > 20) {
        swal({
          title: "비밀번호를 8자리 ~ 20자리 이내로 입력해주세요.",
          icon: "error",
        });
        return false;
      }
      if (!this.user.username) {
        swal({ title: "닉네임을 입력해주세요.", icon: "error" });
        return false;
      }
      if (!this.user.email) {
        swal({ title: "이메일을 입력해주세요.", icon: "error" });
        return false;
      } else {
        // eslint-disable-next-line
        let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
        if (regEmail.test(this.user.email) === false) {
          swal({ title: "이메일 형식을 다시 입력해주세요.", icon: "error" });
          return false;
        }
      }
      await this.registerConfirm(this.user);
      if (this.isRegister) {
        swal({ title: "회원가입 성공!", icon: "success" });
        this.$router.push({
          name: "login",
          params: { userid: this.user.userid, userpwd: this.user.userpwd },
        });
      }
    },
  },
};
</script>
<style>
.swal-overlay {
  background-color: rgba(61, 142, 213, 0.585);
}
.swal-footer {
  background-color: rgb(245, 248, 250);
  margin-top: 32px;
  border-top: 1px solid #e9eef1;
  overflow: hidden;
}
</style>
