import jwt_decode from "jwt-decode";
import * as member from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isRegister: false,
    isRegisterError: false,
    isLogin: false,
    isLoginError: false,
    isUpdate: false,
    isUpdateError: false,
    isDelete: false,
    isDeleteError: false,
    userInfo: null,
    img_path: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_REGISTER: (state, isRegister) => {
      state.isRegister = isRegister;
    },
    SET_IS_REGISTER_ERROR: (state, isRegisterError) => {
      state.isRegisterError = isRegisterError;
    },
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_UPDATE: (state, isUpdate) => {
      state.isUpdate = isUpdate;
    },
    SET_IS_UPDATE_ERROR: (state, isUpdateError) => {
      state.isUpdateError = isUpdateError;
    },
    SET_IS_DELETE: (state, isDelete) => {
      state.isDelete = isDelete;
    },
    SET_IS_DELETE_ERROR: (state, isDeleteError) => {
      state.isDeleteError = isDeleteError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_IMG_PATH: (state, img_path) => {
      state.img_path = img_path;
    },
  },
  actions: {
    async getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token.token);

      return new Promise((resolve, reject) => {
        member.findById(
          decode_token.userid,
          (response) => {
            if (response.data.message === "success") {
              commit("SET_IMG_PATH", response.data.img);
              commit("SET_USER_INFO", response.data.userInfo);
              resolve();
            } else {
              console.log("유저 정보 없음!!");
              reject();
            }
          },
          (error) => {
            console.log(error);
          }
        );
      });
    },
    async registerConfirm({ commit }, user) {
      await member.register(
        user,
        (response) => {
          if (response.data === "success") {
            commit("SET_IS_REGISTER", true);
            commit("SET_IS_REGISTER_ERROR", false);
          } else {
            commit("SET_IS_REGISTER", false);
            commit("SET_IS_REGISTER_ERROR", true);
          }
        },
        () => {}
      );
    },
    async userConfirm({ commit }, user) {
      await member.login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    async updateConfirm({ commit }, formData) {
      let token = sessionStorage.getItem("access-token");
      let decode_token = jwt_decode(token);
      await member.update(
        decode_token.userid,
        formData,
        (response) => {
          if (response.data === "success") {
            commit("SET_IS_UPDATE", true);
            commit("SET_IS_UPDATE_ERROR", false);
          } else {
            commit("SET_IS_UPDATE", false);
            commit("SET_IS_UPDATE_ERROR", true);
          }
        },
        () => {}
      );
    },
    async deleteConfirm({ commit }, token) {
      let decode_token = jwt_decode(token);
      await member.deleteUser(
        decode_token.userid,
        (response) => {
          if (response.data === "success") {
            commit("SET_IS_DELETE", true);
            commit("SET_IS_DELETE_ERROR", false);
          } else {
            commit("SET_IS_DELETE", false);
            commit("SET_IS_DELETE_ERROR", true);
          }
        },
        () => {}
      );
    },
  },
};

export default memberStore;
