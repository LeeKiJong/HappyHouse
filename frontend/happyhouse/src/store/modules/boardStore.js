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
        () => {
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
        () => {
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
