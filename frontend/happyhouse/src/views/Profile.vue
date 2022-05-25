<template>
  <div class="container-fluid">
    <div
      class="mt-4 page-header min-height-300 border-radius-xl"
      :style="{
        backgroundImage:
          'url(' + require('@/assets/img/curved-images/curved14.jpg') + ')',
        backgroundPositionY: '50%',
      }"
    >
      <span class="mask bg-gradient-success opacity-6"></span>
    </div>

    <div class="mx-4 overflow-hidden card card-body blur shadow-blur mt-n6">
      <div class="row gx-4">
        <div class="col-auto">
          <div class="avatar avatar-xl position-relative">
            <img
              id="image"
              alt="profile_image"
              class="shadow-sm h-100 w-100 border-radius-lg"
            />
          </div>
        </div>
        <div class="col-auto my-auto">
          <div class="h-100">
            <h5 class="mb-1">{{ userInfo.username }}</h5>
            <p class="mb-0 text-sm font-weight-bold">
              {{ userInfo.email }}
            </p>
          </div>
        </div>
        <div
          class="mx-auto mt-3 col-lg-3 col-md-6 my-sm-auto ms-sm-auto me-sm-0"
        >
          <div class="nav-wrapper position-relative end-0">
            <ul
              class="p-1 bg-transparent nav nav-pills nav-fill"
              role="tablist"
            >
              <li class="nav-item" @click="toggleShowProfileDetail">
                <a
                  class="px-0 py-1 mb-0 nav-link active"
                  data-bs-toggle="tab"
                  href="javascript:;"
                  role="tab"
                  aria-selected="true"
                >
                  <svg
                    class="text-dark"
                    width="16px"
                    height="16px"
                    viewBox="0 0 40 44"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    xmlns:xlink="http://www.w3.org/1999/xlink"
                  >
                    <title>document</title>
                    <g
                      stroke="none"
                      stroke-width="1"
                      fill="none"
                      fill-rule="evenodd"
                    >
                      <g
                        transform="translate(-1870.000000, -591.000000)"
                        fill="#FFFFFF"
                        fill-rule="nonzero"
                      >
                        <g transform="translate(1716.000000, 291.000000)">
                          <g transform="translate(154.000000, 300.000000)">
                            <path
                              class="color-background"
                              d="M40,40 L36.3636364,40 L36.3636364,3.63636364 L5.45454545,3.63636364 L5.45454545,0 L38.1818182,0 C39.1854545,0 40,0.814545455 40,1.81818182 L40,40 Z"
                              opacity="0.603585379"
                            ></path>
                            <path
                              class="color-background"
                              d="M30.9090909,7.27272727 L1.81818182,7.27272727 C0.814545455,7.27272727 0,8.08727273 0,9.09090909 L0,41.8181818 C0,42.8218182 0.814545455,43.6363636 1.81818182,43.6363636 L30.9090909,43.6363636 C31.9127273,43.6363636 32.7272727,42.8218182 32.7272727,41.8181818 L32.7272727,9.09090909 C32.7272727,8.08727273 31.9127273,7.27272727 30.9090909,7.27272727 Z M18.1818182,34.5454545 L7.27272727,34.5454545 L7.27272727,30.9090909 L18.1818182,30.9090909 L18.1818182,34.5454545 Z M25.4545455,27.2727273 L7.27272727,27.2727273 L7.27272727,23.6363636 L25.4545455,23.6363636 L25.4545455,27.2727273 Z M25.4545455,20 L7.27272727,20 L7.27272727,16.3636364 L25.4545455,16.3636364 L25.4545455,20 Z"
                            ></path>
                          </g>
                        </g>
                      </g>
                    </g>
                  </svg>
                  <span class="ms-1">메인 프로필</span>
                </a>
              </li>
              <li class="nav-item" @click="toggleShowProfileUser">
                <a
                  class="px-0 py-1 mb-0 nav-link"
                  data-bs-toggle="tab"
                  href="javascript:;"
                  role="tab"
                  aria-selected="false"
                >
                  <svg
                    class="text-dark"
                    width="16px"
                    height="16px"
                    viewBox="0 0 40 40"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    xmlns:xlink="http://www.w3.org/1999/xlink"
                  >
                    <title>settings</title>
                    <g
                      stroke="none"
                      stroke-width="1"
                      fill="none"
                      fill-rule="evenodd"
                    >
                      <g
                        transform="translate(-2020.000000, -442.000000)"
                        fill="#FFFFFF"
                        fill-rule="nonzero"
                      >
                        <g transform="translate(1716.000000, 291.000000)">
                          <g transform="translate(304.000000, 151.000000)">
                            <polygon
                              class="color-background"
                              opacity="0.596981957"
                              points="18.0883333 15.7316667 11.1783333 8.82166667 13.3333333 6.66666667 6.66666667 0 0 6.66666667 6.66666667 13.3333333 8.82166667 11.1783333 15.315 17.6716667"
                            ></polygon>
                            <path
                              class="color-background"
                              d="M31.5666667,23.2333333 C31.0516667,23.2933333 30.53,23.3333333 30,23.3333333 C29.4916667,23.3333333 28.9866667,23.3033333 28.48,23.245 L22.4116667,30.7433333 L29.9416667,38.2733333 C32.2433333,40.575 35.9733333,40.575 38.275,38.2733333 L38.275,38.2733333 C40.5766667,35.9716667 40.5766667,32.2416667 38.275,29.94 L31.5666667,23.2333333 Z"
                              opacity="0.596981957"
                            ></path>
                            <path
                              class="color-background"
                              d="M33.785,11.285 L28.715,6.215 L34.0616667,0.868333333 C32.82,0.315 31.4483333,0 30,0 C24.4766667,0 20,4.47666667 20,10 C20,10.99 20.1483333,11.9433333 20.4166667,12.8466667 L2.435,27.3966667 C0.95,28.7083333 0.0633333333,30.595 0.00333333333,32.5733333 C-0.0583333333,34.5533333 0.71,36.4916667 2.11,37.89 C3.47,39.2516667 5.27833333,40 7.20166667,40 C9.26666667,40 11.2366667,39.1133333 12.6033333,37.565 L27.1533333,19.5833333 C28.0566667,19.8516667 29.01,20 30,20 C35.5233333,20 40,15.5233333 40,10 C40,8.55166667 39.685,7.18 39.1316667,5.93666667 L33.785,11.285 Z"
                            ></path>
                          </g>
                        </g>
                      </g>
                    </g>
                  </svg>
                  <span class="ms-1">회원 정보</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <profile-user-info v-if="showProfileUserInfo"></profile-user-info>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from "vuex";

import setNavPills from "@/assets/js/nav-pills.js";
import setTooltip from "@/assets/js/tooltip.js";
import ProfileUserInfo from "@/components/profile/ProfileUserInfo.vue";

const memberStore = "memberStore";
export default {
  name: "ProfileOverview",
  components: {
    ProfileUserInfo,
  },
  data() {
    return {
      show: false,
      showMenu: false,
      showProfileDetail: true,
      showProfileUserInfo: false,
      userInfo: {
        userid: "",
        username: "",
        email: "",
        filename: "",
      },
    };
  },
  created() {
    this.userInfo = this.$store.state.memberStore.userInfo;
  },
  computed: {
    ...mapState(memberStore, [
      "img_path",
      "isUpdate",
      "isUpdateError",
      "isDelete",
      "isDeleteError",
    ]),
  },
  mounted() {
    setNavPills();
    setTooltip(this.$store.state.bootstrap);

    var img = document.getElementById("image");
    img.src = "data:image/png;base64," + this.img_path;
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions(memberStore, [
      "updateConfirm",
      "deleteConfirm",
      "getUserInfo",
    ]),
    async deleteUser() {
      let token = sessionStorage.getItem("access-token");
      await this.deleteConfirm(token);
      console.log(this.isDelete);
      if (this.isDelete) {
        this.SET_IS_LOGIN(false);
        this.SET_USER_INFO(null);
        sessionStorage.removeItem("access-token");
        alert("회원정보 삭제 성공!");
        this.$router.push({ name: "login" });
        location.reload();
      }
    },
    async confirm() {
      let elem = document.getElementById("file");
      const formData = new FormData();
      let filename = "";

      if (elem.files.length === 0) return;

      if (elem.files[0]) {
        elem.id = "image";
        elem.type = "file";
        elem.accept = "image/*";
        formData.append("img", elem.files[0]);
        filename = elem.files[0].name;
      } else {
        formData.append("img", elem.files[0]);
        filename = this.userInfo.filename;
      }

      var data = {
        userid: this.userInfo.userid,
        username: document.getElementById("username").value,
        email: document.getElementById("email").value,
        filename: filename,
      };

      formData.append(
        "user",
        new Blob([JSON.stringify(data)], { type: "application/json" })
      );

      await this.updateConfirm(formData);

      if (this.isUpdate) {
        alert("정보 수정 성공!");
        location.reload();
      }
    },
    toggleShowProfileDetail() {
      this.showProfileUserDetail = true;
      this.showProfileUserInfo = false;
    },
    toggleShowProfileUser() {
      this.showProfileUserDetail = false;
      this.showProfileUserInfo = true;
    },
    readFile() {
      let elem = document.getElementById("file");
      var img = document.getElementById("image");
      let file = elem.files;
      document.getElementById("fileInput").value = file[0].name;
      let reader = new FileReader();
      reader.readAsDataURL(file[0]);
      reader.onload = function () {
        let dataUrl = reader.result;
        img.src = dataUrl;
      };
    },
  },
};
</script>
