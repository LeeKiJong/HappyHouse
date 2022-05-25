import { createRouter, createWebHistory } from "vue-router";
// User Domain
import UserView from "@/views/UserView.vue";
import UserLogin from "@/components/user/UserLogin.vue";
import UserJoin from "@/components/user/UserJoin.vue";

import MapView from "@/components/map/MapView.vue";

import Dashboard from "@/views/Dashboard.vue";
import Tables from "@/views/Tables.vue";
import Billing from "@/views/Billing.vue";
import VirtualReality from "@/views/VirtualReality.vue";
import Profile from "@/views/Profile.vue";
import Rtl from "@/views/Rtl.vue";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";

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
    name: "map",
    component: MapView,
    beforeEnter: onlyAuthUser,
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
    path: "/tables",
    name: "Tables",
    component: Tables,
  },
  {
    path: "/billing",
    name: "Billing",
    component: Billing,
  },
  {
    path: "/virtual-reality",
    name: "Virtual Reality",
    component: VirtualReality,
  },
  {
    path: "/profile",
    name: "Profile",
    beforeEnter: onlyAuthUser,
    component: Profile,
  },
  {
    path: "/rtl-page",
    name: "Rtl",
    component: Rtl,
  },
  {
    path: "/sign-in",
    name: "Sign In",
    component: SignIn,
  },
  {
    path: "/sign-up",
    name: "Sign Up",
    component: SignUp,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

export default router;
