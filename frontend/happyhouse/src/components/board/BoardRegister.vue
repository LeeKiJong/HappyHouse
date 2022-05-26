<template>
  <div class="card card-background-image py-4 container-fluid w-95">
    <div class="row">
      <div class="col-12">
        <div class="container mt-">
          <div class="row mt-4">
            <div class="col-md-12">
              <div class="card">
                <br />
                <div class="card-header pb-0 px-3">
                  <div
                    class="col-lg-2 col-md-4 cursor-pointer"
                    @click="historyBack()"
                  >
                    <i class="fa fa-arrow-left" />&nbsp;
                    <small>돌아가기</small>
                  </div>
                  <h1 class="text-center">Happyhouse에 글을 작성해주세요!</h1>
                </div>
                <div class="card-body container">
                  <br />
                  <label for="subject">제목</label>
                  <vsud-input
                    id="subject"
                    placeholder="제목 입력..."
                  ></vsud-input>
                  <br />
                  <label for="content">내용</label>
                  <textarea
                    id="content"
                    class="form-control"
                    placeholder="내용 입력..."
                    rows="10"
                  />
                  <vsud-button
                    class="my-2"
                    color="info"
                    variant="gradient"
                    @click="writeConfirm"
                    >글 등록하기</vsud-button
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { writeArticle } from "@/api/board";
import VsudButton from "@/components/vsud/VsudButton.vue";
import { mapState } from "vuex";
import VsudInput from "@/components/vsud/VsudInput.vue";
import swal from "sweetalert";

const memberStore = "memberStore";
export default {
  name: "BoardRegister",
  components: {
    VsudButton,
    VsudInput,
  },
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        subject: "",
        content: "",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    writeConfirm() {
      this.article.userid = this.userInfo.userid;

      writeArticle(
        {
          userid: this.article.userid,
          subject: document.getElementById("subject").value,
          content: document.getElementById("content").value,
          type: "board",
        },
        ({ data }) => {
          if (data === "success") {
            swal({ title: "등록이 완료되었습니다.", icon: "success" });
          } else {
            swal({
              title: "등록 처리시 문제가 발생했습니다.",
              icon: "error",
            });
          }
          this.$router.push({ name: "Board" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    historyBack() {
      this.$router.go(-1);
    },
  },
};
</script>
