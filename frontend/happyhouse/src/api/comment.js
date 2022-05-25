import { apiInstance } from "@/api/index.js";

const api = apiInstance();

function listComment(articleno, success, fail) {
  console.log(articleno);
  api.get(`/comment/${articleno}`).then(success).catch(fail);
}

function writeComment(comment, success, fail) {
  api.post(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function modifyComment(comment, success, fail) {
  api
    .put(`/comment/${comment.commentno}`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

function deleteComment(articleno, success, fail) {
  api.delete(`/comment/${articleno}`).then(success).catch(fail);
}

export { listComment, writeComment, modifyComment, deleteComment };
