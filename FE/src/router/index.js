import { createRouter, createWebHistory } from "vue-router";
import { useInfoStore } from "@/stores/user";
import { storeToRefs } from "pinia";
import HomeView from "@/views/HomeView.vue";
import CardHome from "@/components/CardHome.vue";
import DetailHome from "@/components/DetailHome.vue";
import SearchResultView from "@/views/SearchResultView.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import SubDivisionView from "@/views/SubDivisionView.vue";
import FavoriteHouseView from "@/views/FavoriteHouseView.vue";
import InsertHomeView from "@/views/InsertHomeView.vue";
import CardHome2 from "@/components/CardHome2.vue";
import UserInfoView from "@/views/UserInfoView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/subDivision",
      name: "SubDivisionView",
      component: SubDivisionView,
    },
    {
      path: "/cardHome",
      name: "cardHome",
      component: CardHome,
    },
    {
      path: "/cardHome2/:region",
      name: "cardHome2",
      component: CardHome2,
    },
    {
      path: "/detail",
      name: "DetailHome",
      component: DetailHome,
      beforeEnter: (to, from, next) => {
        // 컴포넌트 외부에서 사용시 이렇게 함수 안에 적어줘야 함.
        // 이 공간은 모든 컴포넌트로 가는 기차역이라서 컴포넌트 외부임.
        const userStore = useInfoStore();
        const { userInfo } = storeToRefs(userStore);

        if (userInfo.value.id !== "") {
          return next();
        }

        alert("로그인이 필요한 서비스 입니다.");
        next(false);
      },
    },
    {
      path: "/search",
      name: "SearchResult",
      component: SearchResultView,
    },
    {
      path: "/login",
      name: "LoginView",
      component: LoginView,
    },
    {
      path: "/register",
      name: "RegisterView",
      component: RegisterView,
    },
    {
      path: "/favorite",
      name: "FavoriteHouseView",
      component: FavoriteHouseView,
      beforeEnter: (to, from, next) => {
        // 컴포넌트 외부에서 사용시 이렇게 함수 안에 적어줘야 함.
        // 이 공간은 모든 컴포넌트로 가는 기차역이라서 컴포넌트 외부임.
        const userStore = useInfoStore();
        const { userInfo } = storeToRefs(userStore);

        if (userInfo.value.id !== "") {
          return next();
        }

        alert("로그인이 필요한 서비스 입니다.");
        next(false);
      },
    },
    {
      path: "/insertHome",
      name: "InsertHomeView",
      component: InsertHomeView,
      beforeEnter: (to, from, next) => {
        // 컴포넌트 외부에서 사용시 이렇게 함수 안에 적어줘야 함.
        // 이 공간은 모든 컴포넌트로 가는 기차역이라서 컴포넌트 외부임.
        const userStore = useInfoStore();
        const { userInfo } = storeToRefs(userStore);

        if (userInfo.value.id !== "") {
          return next();
        }

        alert("로그인이 필요한 서비스 입니다.");
        next(false);
      },
    },
    {
      path: "/userInfo",
      name: "UserInfoView",
      component: UserInfoView,
      beforeEnter: (to, from, next) => {
        // 컴포넌트 외부에서 사용시 이렇게 함수 안에 적어줘야 함.
        // 이 공간은 모든 컴포넌트로 가는 기차역이라서 컴포넌트 외부임.
        const userStore = useInfoStore();
        const { userInfo } = storeToRefs(userStore);

        if (userInfo.value.id !== "") {
          return next();
        }

        alert("로그인이 필요한 서비스 입니다.");
        next(false);
      },
    },
  ],
});

export default router;
