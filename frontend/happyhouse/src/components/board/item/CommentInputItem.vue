<template>
  <div class="m-3">
    <hr class="mt-4" />
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
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import VsudInput from "@/components/vsud/VsudInput.vue";
import VsudButton from "@/components/vsud/VsudButton.vue";
import swal from "sweetalert";

const memberStore = "memberStore";
const boardStore = "boardStore";

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
    ...mapActions(boardStore, ["listComment", "writeComment"]),
    async registComment() {
      if (document.getElementById("content").value != "") {
        const comment = {
          articleno: this.comment.articleno,
          userid: this.comment.userid,
          content: document.getElementById("content").value,
        };
        await this.writeComment(comment);
      } else {
        swal({ title: "댓글을 입력해주세요.", icon: "warning" });
      }
    },
  },
};
</script>
