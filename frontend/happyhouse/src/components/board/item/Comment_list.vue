<template>
<div class="card-body pt-4 p-3">
<ul class="list-group">
           <div v-for="comments in paginatedData" :key="comments.commentno">
                <li
          class="list-group-item border-0 bg-gray-100 border-radius-lg"
        >
          <div class="flex-column">
            <span class="mb-2 text-xs">
              <span :id="comments.commentno" class="text-dark font-weight-bold" style="font-size: 17px;">
                {{ comments.content }}</span
              >
              <vsud-button
              v-if="comments.commentno == show"
              class="m-1" style="width: 10.5%;float:right;"  
                                  color="info"
                    variant="gradient"
              @click="updateComment"
            >
              수정 완료
            </vsud-button>
            <vsud-button
              v-if="comments.userid == userInfo.userid  && comments.commentno != show"
                                  color="info"
                    variant="gradient"
              style="width: 5%;float:right;"   
              class="m-1"
              @click="clickDelete(comments)"
            >
              삭제
            </vsud-button>
            <vsud-button
              v-if="comments.userid == userInfo.userid && comments.commentno != show"
              style="width: 5%; float:right;"                    color="info"
                    variant="gradient"
              class="m-1"
              @click="clickUpdate(comments)"
            >
            수정
            </vsud-button>
              
            
            </span>
            <br/>
            <span class="mb-2 text-xs">
              작성일: 
              <span class="text-dark ms-sm-2 font-weight-bold">
                {{ yyyyMMdd(comments.regtime) }}</span
              >
            </span>
            <span class="mb-2 text-xs">
              작성자: 
              <span class="text-dark ms-sm-2 font-weight-bold">
                {{ comments.userid }}</span
              >
            </span>
            
            
            
          </div>
          </li>


   </div>
        </ul>
          </div>
</template>

<script>
const memberStore = "memberStore";
import { mapState } from "vuex";
import { modifyComment, deleteComment } from "@/api/comment";
import VsudButton from "@/components/vsud/VsudButton.vue"
export default {
  name: "comment-list",
  components:{
    VsudButton,
  },
  data() {
    return {
      commentno: "",
      show:"",
    };
  },
  props: {
    listArray: {
      type: Array,
      required: true,
    },
  },
  created() {
    console.log("asd");
  },
  methods: {
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
    updateComment() {
      var text = document.getElementById("temp1").value;
      if (text == "") {
        console.log("입력하세요");
      } else {
        console.log("확인");
        console.log(this.commentno);
        modifyComment(
          {
            commentno: this.commentno,
            content: text,
          },
          ({ data }) => {
            let msg = "수정 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "수정이 완료되었습니다.";
            }
            alert(msg);
            location.reload();
          },
          (error) => {
            console.log(error);
          },
        );
      }

      //   const input = document.getElementById("inputContent");
      //   console.log(input.innerText);
    },
    clickUpdate(comment) {
      this.commentno = comment.commentno;
      this.show=comment.commentno;
      const input = document.getElementById(comment.commentno);
      while (input.hasChildNodes()) {
        input.removeChild(input.lastChild);
      }
      input.insertAdjacentHTML("afterBegin", "<input id ='temp1' value='" + comment.content+"'/>");
    },
    clickDelete(comment) {
      this.commentno = comment.commentno;
      deleteComment(
        comment.commentno,
        ({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          location.reload();
        },
        (error) => {
          console.log(error);
        },
      );
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    paginatedData() {
      return this.listArray;
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
</style>
