<template>
  <div class="pt-4 p-3">
    <label for="comment">댓글</label>
    <div class="row">
      <vsud-input
        class="col-lg-5 mb-0"
        id="content"
        v-model="comment.content"
        placeholder="내용을 입력하세요."
      ></vsud-input>
      <vsud-button
        class="col-lg-1"
        color="info"
        variant="gradient"
        @click="registComment"
      >
        댓글 작성
      </vsud-button>
    </div>
    <hr />
  </div>
</template>

<script>
import { writeComment } from "@/api/comment";
import { mapState } from "vuex";
import VsudInput from "@/components/vsud/VsudInput.vue";
import VsudButton from "@/components/vsud/VsudButton.vue";
const memberStore = "memberStore";

export default {
  name: "CommentInputItem",
  components: {
    VsudInput,
    VsudButton,
  },
  data() {
    return {
      comment: {
        articleno: this.$route.params.articleno,
        userid: "",
        content: "",
      },
    };
  },
  props: {
    type: { type: String },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    this.comment.userid = this.userInfo.userid;
  },
  methods: {
    registComment() {
      if (document.getElementById("content").value != "") {
        writeComment(
          {
            articleno: this.comment.articleno,
            userid: this.comment.userid,
            content: document.getElementById("content").value,
          },
          ({ data }) => {
            let msg = "등록 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "등록이 완료되었습니다.";
            }
            alert(msg);
            location.reload();
          },
          (error) => {
            console.log(error);
          }
        );
      } else {
        alert("댓글을 입력해주세요");
      }
    },
  },
};
</script>

<style>
.alert {
}
</style>
