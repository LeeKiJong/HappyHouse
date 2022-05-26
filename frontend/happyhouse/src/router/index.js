import { createRouter, createWebHistory } from "vue-router";
// User Domain
import UserView from "@/views/UserView.vue";
import UserLogin from "@/components/user/UserLogin.vue";
import UserJoin from "@/components/user/UserJoin.vue";

import MapView from "@/components/map/MapView.vue";

import Dashboard from "@/views/Dashboard.vue";
import Profile from "@/views/Profile.vue";
// Board Domainn
import BoardView from "@/components/board/BoardView.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";

import store from "@/store";

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];

  let token = sessionStorage.getItem("access-token");

  if (checkUserInfo == null && token) {
    await store.dispatch("memberStore/getUserInfo", { token }, { root: true });
    next();
    return;
  }

  if (checkUserInfo === null) {
    next({ name: "login" });
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "welcome",
    redirect: "/dashboard",
  },
  {
    path: "/user",
    name: "user",
    component: UserView,
    children: [
      {
        path: "login",
        name: "login",
        component: UserLogin,
      },
      {
        path: "join",
        name: "join",
        component: UserJoin,
      },
    ],
  },
  {
    path: "/map",
    name: "Map",
    component: MapView,
    beforeEnter: onlyAuthUser,
  },
  {
    path: "/board",
    name: "Board",
    beforeEnter: onlyAuthUser,
    component: BoardView,
  },
  {
    path: "/list",
    name: "boardList",
    beforeEnter: onlyAuthUser,
    component: BoardList,
  },
  {
    path: "/write",
    name: "boardRegister",
    beforeEnter: onlyAuthUser,
    component: () => import("@/components/board/BoardRegister.vue"),
  },
  {
    path: "/detail/:articleno",
    name: "boardDetail",
    beforeEnter: onlyAuthUser,
    component: BoardDetail,
  },
  // {
  //   path: "modify/:articleno",
  //   name: "boardModify",
  //   beforeEnter: onlyAuthUser,
  //   component: () => import("@/components/board/BoardModify.vue"),
  // },
  {
    path: "/dashboard",
    name: "Dashboard",
    beforeEnter: onlyAuthUser,
    component: Dashboard,
  },
  {
    path: "/profile",
    name: "Profile",
    beforeEnter: onlyAuthUser,
    component: Profile,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

export default router;
