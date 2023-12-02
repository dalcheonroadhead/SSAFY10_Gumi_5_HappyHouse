<script setup>
import axios from "axios";
import slogan from "@/assets/img/slogan.png";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useInfoStore } from "@/stores/user";
import { onMounted, ref } from "vue";
const userStore = useInfoStore();
const { userInfo } = storeToRefs(userStore);
const router = useRouter();
const { VITE_SPRING_BASE_URL } = import.meta.env;
console.log(userInfo.value.id);

const addCnt = ref(0);

onMounted(() => {
  axios
    .get(
      VITE_SPRING_BASE_URL + `/favorite/getFavoriteList/${userInfo.value.pk}`
    )
    .then((response) => {
      console.log(response.status);
      let { data } = response;
      console.log(data.list);
      addCnt.value = data.list.length;
    })
    .catch((err) => {
      console.log(err);
    });
});

const clickLogo = () => {
  // 로고를 눌렀을 시 HOME으로 페이지 전환되도록 바꾸기!!

  console.log("Home으로 돌아감");

  router.push({
    name: "home",
  });
};

const clickMap = () => {
  router.push({
    name: "home",
  });
};

const clickSubDivision = () => {
  router.push({
    name: "SubDivisionView",
  });
};

const clickFavorite = () => {
  router.push({
    name: "FavoriteHouseView",
  });
};

const clickInsertHome = () => {
  router.push({
    name: "InsertHomeView",
  });
};

const clickRegister = () => {
  console.log("회원 가입 화면으로 간다.");
  router.push({
    name: "RegisterView",
  });
};

const clickLogin = () => {
  console.log("Login 화면으로 간다.");
  router.push({
    name: "LoginView",
  });
};

const clickLogOut = () => {
  localStorage.clear();
  router.go(0);
};

const clickUserInfo = () => {
  console.log("userInfo 화면으로 간다.");
  router.push({
    name: "UserInfoView",
  });
};
</script>

<template>
  <div style="box-shadow: rgba(0, 0, 0, 0.45) 0px 25px 20px -20px">
    <nav
      class="navbar navbar-expand-lg bg-primary bg-gradient"
      data-bs-theme="dark"
    >
      <div class="container-fluid">
        <img
          :src="slogan"
          style="width: 10rem; cursor: pointer"
          @click="clickLogo"
          class="me-2"
        />
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarColor01"
          aria-controls="navbarColor01"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarColor01">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <a class="nav-link" @click="clickMap" style="cursor: pointer"
                >지도
                <span class="visually-hidden">(current)</span>
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                @click="clickSubDivision"
                style="cursor: pointer"
                >분양</a
              >
            </li>
            <li class="nav-item">
              <a class="nav-link" @click="clickFavorite" style="cursor: pointer"
                >관심 목록
                <span id="cnt">({{ addCnt }})</span>
              </a>
            </li>
            <li class="nav-item">
              <a
                class="nav-link"
                @click="clickInsertHome"
                style="cursor: pointer"
                >방 내놓기</a
              >
            </li>
            <template v-if="userInfo.id === ''">
              <li class="nav-item d-flex flex-row">
                <a
                  id="login"
                  class="nav-link"
                  @click="clickLogin"
                  style="cursor: pointer"
                  >로그인&nbsp; |</a
                >
                <a
                  id="login"
                  class="nav-link"
                  @click="clickRegister"
                  style="cursor: pointer"
                >
                  회원가입</a
                >
              </li>
            </template>
            <template v-else>
              <li class="nav-item d-flex flex-row">
                <a
                  id="login"
                  class="nav-link"
                  @click="clickLogOut"
                  style="cursor: pointer"
                  >로그아웃&nbsp; |</a
                >
                <a
                  id="login"
                  class="nav-link"
                  @click="clickUserInfo"
                  style="cursor: pointer"
                  >회원정보</a
                >
              </li>
            </template>
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                data-bs-toggle="dropdown"
                href="#"
                role="button"
                aria-haspopup="true"
                aria-expanded="false"
                >머구! 이웃(DaeGu Neighbor's)</a
              >
              <div class="dropdown-menu">
                <a class="dropdown-item" href="#">머구! 부동산 랭킹</a>
                <a class="dropdown-item" href="#">머구! 지역 별 맛집 추천!</a>
                <a class="dropdown-item" href="#">머구! 산책로 추천</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">머구! 커넥트</a>
              </div>
            </li>
          </ul>
          <form class="d-flex col-lg-3">
            <button class="btn me-1">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="32"
                height="32"
                fill="white"
                class="bi bi-binoculars-fill me-1"
                viewBox="0 0 16 16"
              >
                <path
                  d="M4.5 1A1.5 1.5 0 0 0 3 2.5V3h4v-.5A1.5 1.5 0 0 0 5.5 1h-1zM7 4v1h2V4h4v.882a.5.5 0 0 0 .276.447l.895.447A1.5 1.5 0 0 1 15 7.118V13H9v-1.5a.5.5 0 0 1 .146-.354l.854-.853V9.5a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v.793l.854.853A.5.5 0 0 1 7 11.5V13H1V7.118a1.5 1.5 0 0 1 .83-1.342l.894-.447A.5.5 0 0 0 3 4.882V4h4zM1 14v.5A1.5 1.5 0 0 0 2.5 16h3A1.5 1.5 0 0 0 7 14.5V14H1zm8 0v.5a1.5 1.5 0 0 0 1.5 1.5h3a1.5 1.5 0 0 0 1.5-1.5V14H9zm4-11H9v-.5A1.5 1.5 0 0 1 10.5 1h1A1.5 1.5 0 0 1 13 2.5V3z"
                />
              </svg>
            </button>
            <input
              class="form-control"
              type="search"
              placeholder="지역 또는 단지명을 입력하세요"
            />
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "JalnanGothic";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/JalnanGothic.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

#cnt {
  color: var(--bs-danger-border-subtle);
}

div {
  font-family: "JalnanGothic";
}

.dropdown-menu {
  background-image: var(--bs-gradient);
}

.dropdown-item:hover {
  background-color: unset;
  color: var(--bs-light);
}

input[type="search"] {
  background-color: var(--bs-light);
  color: black;
}

input[type="search"]::placeholder {
  color: var(--bs-gray-400);
}

button {
  box-shadow: none;
}

button:hover {
  box-shadow: none;
}

svg:hover {
  fill: black;
}
</style>
