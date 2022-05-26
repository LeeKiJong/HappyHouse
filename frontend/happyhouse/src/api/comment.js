import { apiInstance } from "@/api/index.js";

const api = apiInstance();

function listComment(articleno, success, fail) {
  api.get(`/comment/${articleno}`).then(success).catch(fail);
}

function writeComment(param, success, fail) {
  api.get(`/comment/write`, { params: param }).then(success).catch(fail);
}

function deleteComment(articleno, success, fail) {
  api.delete(`/comment/${articleno}`).then(success).catch(fail);
}

export { listComment, writeComment, deleteComment };
