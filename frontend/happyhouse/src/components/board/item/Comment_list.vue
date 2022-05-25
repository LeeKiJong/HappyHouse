<template>
  <comment-input-item />
  <div class="card-body pt-0 p-3 comment-list-card">
    <ul class="list-group">
      <div
        v-for="comment in $store.state.boardStore.comments"
        :key="comment.commentno"
      >
        <li class="list-group-item border-0 bg-gray-100 border-radius-lg">
          <div>
            <span class="mb-2 text-xs col-auto">
              &nbsp;&nbsp;{{ comment.userid }}
            </span>
            <span
              class="ms-auto text-end col-auto"
              v-if="
                comment.userid == userInfo.userid && comment.commentno != show
              "
            >
              <a
                class="btn btn-link text-danger text-gradient px-2 py-0 mb-0"
                @click="deleteComment(comment)"
              >
                <i class="far fa-trash-alt me-2" aria-hidden="true"></i>
              </a>
            </span>
          </div>
          <div class="row">
            <span class="mb-2 text-xs">
              <p
                :id="comment.commentno"
                class="m-0 text-lg font-weight-bold align-self-center"
              >
                {{ comment.content }}
              </p>
            </span>
            <br />
          </div>
        </li>
      </div>
    </ul>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";
const boardStore = "boardStore";

export default {
  name: "comment-list",
  data() {
    return {
      commentno: "",
      show: "",
    };
  },
  created() {
    this.listComment(this.$route.params.articleno);
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    paginatedData() {
      return this.listArray;
    },
  },
  methods: {
    ...mapActions(boardStore, ["listComment", "deleteComment"]),
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";

      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);

      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();

      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }

      if (day < 10) {
        day = "0" + day;
      }

      // 최종 포맷 (ex - '2021-10-08')
      return year + "-" + month + "-" + day;
    },
  },
};
</script>

<style>
table {
  width: 100%;
  border-collapse: collapse;
}
table th {
  font-size: 1.2rem;
}
table tr {
  height: 2rem;
  text-align: center;
  border-bottom: 1px solid #505050;
}
table tr:first-of-type {
  border-top: 2px solid #404040;
}
table tr td {
  padding: 1rem 0;
  font-size: 1.1rem;
}
.btn-cover {
  margin-top: 1.5rem;
  text-align: center;
}
.btn-cover .page-btn {
  width: 5rem;
  height: 2rem;
  letter-spacing: 0.5px;
}
.btn-cover .page-count {
  padding: 0 1rem;
}
.comment-list-card {
  overflow: auto;
  height: calc(45vh - 180px);
}
</style>
