import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function register(user, success, fail) {
  await api.post(`/user/join`, JSON.stringify(user)).then(success).catch(fail);
}
async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}
async function update(userid, formData, success, fail) {
  userid = sessionStorage.getItem("access-token");
  await api
    .post(`/user/update/${userid}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(success)
    .catch(fail);
}
async function deleteUser(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/delete/${userid}`).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function IdCheck(userid, success, fail) {
  await api.get(`/user/info/idcheck/${userid}`).then(success).catch(fail);
}

async function PwdCheck(user, success, fail) {
  console.log(user);
  await api
    .post(`/user/info/pwdcheck`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

export { register, login };
export { update, deleteUser, findById, IdCheck, PwdCheck };
