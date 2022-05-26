import jwt_decode from "jwt-decode";
import {
  deleteUser,
  login,
  register,
  update,
  IdCheck,
  PwdCheck,
} from "@/api/member.js";
import { findById } from "../../api/member";

const memberStore = {
  namespaced: true,
  state: {
    isIdCheck: false,
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
    isPwdCheck: null,
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
    SET_ID_CHECK: (state, check) => {
      state.isIdCheck = check;
    },
    SET_IS_PWD: (state, pwd) => {
      state.isPwdCheck = pwd;
    },
  },
  actions: {
    async registerConfirm({ commit }, user) {
      await register(
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
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            console.log(1);
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
      await update(
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
      await deleteUser(
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

    async getUserPwd({ commit }, info) {
      await PwdCheck(
        info,
        (response) => {
          console.log(response.data);
          if (response.data != "") {
            console.log("성공!!");
            commit("SET_IS_PWD", response.data);
          } else {
            console.log("유저 정보 없음!!");
            commit("SET_IS_PWD", "");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async getUserId({ commit }, id) {
      await IdCheck(
        id,
        (response) => {
          if (response.data == "SUCCESS") {
            console.log("성공!!");
            commit("SET_ID_CHECK", true);
          } else {
            console.log("유저 정보 없음!!");
            commit("SET_ID_CHECK", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      await findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            console.log(response.data.userInfo);
            commit("SET_IMG_PATH", response.data.img);
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default memberStore;
