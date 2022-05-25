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
      <span class="mask bg-gradient-info opacity-6"></span>
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
        ></div>
      </div>
    </div>
    <profile-user-info></profile-user-info>
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
