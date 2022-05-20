import { apiInstance } from "@/api/index.js";

const api = apiInstance();

export default {
  methods: {
    async register(user, success, fail) {
      await api
        .post(`/user/join`, JSON.stringify(user))
        .then(success)
        .catch(fail);
    },
    async login(user, success, fail) {
      await api
        .post(`/user/login`, JSON.stringify(user))
        .then(success)
        .catch(fail);
    },
    async update(user, success, fail) {
      await api
        .post(`/user/update`, JSON.stringify(user))
        .then(success)
        .catch(fail);
    },
    async deleteUser(userid, success, fail) {
      await api.get(`/user/info/delete/${userid}`).then(success).catch(fail);
    },
    async findById(userid, success, fail) {
      api.defaults.headers["access-token"] = sessionStorage.getItem(
        "access-token"
      );
      await api.get(`/user/info/${userid}`).then(success).catch(fail);
    },
  },
};
