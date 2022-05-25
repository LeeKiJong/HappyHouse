<template>
  <div class="card card-background-image py-4 container-fluid w-95">
    <div class="row">
      <div class="col-12">
        <div class="card container">
          <br />
          <div class="card-header pb-0 px-3">
            <div
              class="col-lg-2 col-md-4 pb-4 cursor-pointer"
              @click="historyBack()"
            >
              <i class="fa fa-arrow-left" />&nbsp;
              <small>돌아가기</small>
            </div>
            <h6 class="mb-3 text-sm" id="inputSubject">
              <h5>{{ article.subject }}</h5>
            </h6>
            <div class="row">
              <span class="col-auto text-xs">
                작성자:
                <span class="text-dark font-weight-bold ms-sm-2">
                  {{ article.userid }}
                </span>
              </span>
              <span class="col-auto text-xs">
                작성일:
                <span class="text-dark ms-sm-2 font-weight-bold">
                  {{ yyyyMMdd(article.regtime) }}
                </span>
              </span>
              <span class="col-auto text-xs">
                조회수:
                <span class="text-dark ms-sm-2 font-weight-bold">
                  {{ article.hit }}
                </span>
              </span>
            </div>
          </div>
          <div class="card-body pt-4 p-3">
            <ul class="list-group">
              <li
                class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg"
              >
                <div class="d-flex flex-column">
                  <span class="mb-2 text-xs" id="inputContent">
                    <h6>{{ article.content }}</h6>
                  </span>
                  <h6 class="mb-0">
                    <vsud-button
                      v-if="show"
                      color="info"
                      variant="gradient"
                      class="my-4 mb-2"
                      @click="modifyConfirm"
                    >
                      수정 완료
                    </vsud-button>
                  </h6>
                  <br />
                </div>
                <div class="ms-auto text-end" v-if="confirmShow">
                  <a
                    class="btn btn-link text-dark px-3 mb-0"
                    @click="moveModifyArticle"
                  >
                    <i
                      class="fas fa-pencil-alt text-dark me-2"
                      aria-hidden="true"
                    ></i>
                    Edit
                  </a>
                  <a
                    class="btn btn-link text-danger text-gradient px-3 mb-0"
                    @click="deleteArticle"
                  >
                    <i class="far fa-trash-alt me-2" aria-hidden="true"></i
                    >Delete
                  </a>
                </div>
              </li>
            </ul>
          </div>
          <comment-input-item />
          <comment-list :list-array="comments" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
//import { getArticle, deleteArticle } from "@/api/board";
import { getArticle, deleteArticle, modifyArticle } from "@/api/board";
import CommentInputItem from "@/components/board/item/CommentInputItem.vue";
import VsudButton from "@/components/vsud/VsudButton.vue";
import { listComment } from "@/api/comment";
const memberStore = "memberStore";
import { mapState } from "vuex";
import CommentList from "@/components/board/item/Comment_list.vue";
export default {
  name: "BoardDetail",
  components: {
    VsudButton,
    CommentList,
    CommentInputItem,
  },
  data() {
    return {
      show: false,
      confirmShow: false,
      article: {},
      comments: [],
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    this.comments.articleno = this.$route.params.articleno;
    getArticle(
      this.$route.params.articleno,
      (response) => {
        this.article = response.data;
        if (this.userInfo.userid == this.article.userid) {
          this.confirmShow = true;
        }
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
    if (this.userInfo.userid == this.article.userid) {
      this.confirmShow = true;
    }
    listComment(
      this.$route.params.articleno,
      (response) => {
        this.comments = response.data;
        console.log(this.comments);
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    back() {
      this.$router.push({ name: "boardList" });
    },
    modifyConfirm() {
      modifyArticle(
        {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: document.getElementById("subject").value,
          content: document.getElementById("content").value,
        },
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // filter로 쓸 filter ID 지정
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
    moveModifyArticle() {
      this.show = !this.show;
      const input = document.getElementById("inputSubject");
      while (input.hasChildNodes()) {
        input.removeChild(input.lastChild);
      }
      input.insertAdjacentHTML(
        "afterBegin",
        "<input type='text' id = 'subject' value='" +
          this.article.subject +
          "'/></input>"
      );
      const input2 = document.getElementById("inputContent");
      while (input2.hasChildNodes()) {
        input2.removeChild(input2.lastChild);
      }
      input2.insertAdjacentHTML(
        "afterBegin",
        "<textarea id = 'content' cols='80' rows='7'/>" +
          this.article.content +
          "</textarea>"
      );
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        deleteArticle(this.article.articleno, () => {
          this.$router.push({ name: "boardList" });
        });
      }
    },
    historyBack() {
      this.$router.go(-1);
    },
  },
};
</script>
