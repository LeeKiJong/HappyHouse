import * as comment from "@/api/comment.js";

const boardStore = {
  namespaced: true,
  state: {
    comments: [],
  },
  getters: {},
  mutations: {
    SET_COMMENTS: (state, comments) => {
      state.comments = comments;
    },
  },
  actions: {
    async writeComment({ commit }, element) {
      console.log(commit);
      await comment.writeComment(
        element,
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
    },
    async deleteComment({ commit }, element) {
      console.log(commit);
      this.commentno = element.commentno;
      await comment.deleteComment(
        element.commentno,
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
        }
      );
    },
    async listComment({ commit }, articleno) {
      await comment.listComment(
        articleno,
        (response) => {
          commit("SET_COMMENTS", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default boardStore;
