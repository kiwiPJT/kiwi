import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";

import Signup from "@/views/user/Signup"
import Login from "@/views/user/Login"
import CommunityMain from "@/views/community/CommunityMain"
import Health from "@/views/community/Health"
import Diet from "@/views/community/Diet"
import Heart from "@/views/community/Heart"
import WithMain from "@/views/challenge/with/WithMain"
import WithMake from "@/views/challenge/with/WithMake"

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },

  {
    path: "/user/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: "/user/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/community/Communitymain",
    name: "CommunityMain",
    component: CommunityMain,
  },
  {
    path: "/community/health",
    name: "Health",
    component: Health,
  },
  {
    path: "/community/diet",
    name: "Diet",
    component: Diet,
  },
  {
    path: "/community/heart",
    name: "Heart",
    component: Heart,
  },
  {
    path: "/challenge/with/withmain",
    name: "WithMain",
    component: WithMain,
  },
  {
    path: "/challenge/with/withmake",
    name: "WithMake",
    component: WithMake,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
