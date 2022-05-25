<template>
  <div class="py-4">
    <div class="mt-3 row">
      <div class="mt-4">
        <div class="card">
          <div class="card-header my-3 col-lg-4 m-auto">
            <div>
              <h3 class="text-center">회원 정보</h3>
            </div>
            <div class="row">
              <label>이름 :</label>
              <div class="text-center">
                <vsud-input
                  id="username"
                  required
                  :value="userInfo.username"
                ></vsud-input>
              </div>
            </div>
            <div>
              <label>이메일 :</label>
              <div class="text-center">
                <vsud-input
                  id="email"
                  required
                  :value="userInfo.email"
                ></vsud-input>
              </div>
            </div>
            <div>
              <label>프로필 사진</label>
              <div class="bootstrap-filestyle">
                <div class="row">
                  <div class="col-md-10">
                    <input
                      type="text"
                      id="fileInput"
                      class="form-control"
                      disabled
                    />
                    <input
                      type="file"
                      id="file"
                      class="form-control d-none"
                      name="file"
                      @change="readFile"
                    />
                  </div>
                  <div class="col-md-2">
                    <label class="group-span-filestyle" for="file">
                      <div for="fileInput" class="btn btn-default">
                        <span class="glyphicon fa fa-upload"></span>
                      </div>
                    </label>
                  </div>
                </div>
              </div>
            </div>
            <div class="text-center">
              <vsud-button
                color="info"
                variant="gradient"
                class="my-4 mb-2"
                @click="confirm"
              >
                정보 수정
              </vsud-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState, mapMutations } from "vuex";

import VsudInput from "@/components/vsud/VsudInput.vue";
import VsudButton from "@/components/vsud/VsudButton.vue";

const memberStore = "memberStore";

export default {
  name: "ProfileUserInfo",
  components: {
    VsudInput,
    VsudButton,
  },
  data() {
    return {
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
    toggleShow() {
      console.log("change");
      this.show = !this.show;
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

<style></style>
