<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import picData from "@/assets/constants/building.js";
import { useInfoStore } from "@/stores/user";
const { VITE_SPRING_BASE_URL } = import.meta.env;

// 로그인 성공 시 로그인한 회원의 정보를 받는 전역 변수
const userStore = useInfoStore();
const { userInfo } = storeToRefs(userStore);

// 랜덤한 이미지 중 하나 특정할 때 쓰기 위한 변수
const curImg = ref({
  name: "",
});
// 이미지 리스트
const imgList = ref([]);
imgList.value = [...picData.picData];

// 카드 색깔 토글 용
const isDark = ref("dark");

const list = ref([]);
// routing 변수
const router = useRouter();

onMounted(() => {
  axios
    .get(
      VITE_SPRING_BASE_URL + `/favorite/getFavoriteList/${userInfo.value.pk}`
    )
    .then((response) => {
      console.log(response.status);
      let { data } = response;
      console.log(data.list);

      list.value = [...data.list];
    })
    .catch((err) => {
      console.log(err);
    });
});

// 상세보기 가기 위한 함수
const GoDetail = (apt) => {
  console.log(apt);

  router.push({
    name: "DetailHome",
    state: {
      temp: JSON.stringify(apt),
    },
  });
};

// 이미지 랜덤하게 나오게 하는 함수
const randomImg = () => {
  let randomNumber = Math.floor(Math.random() * imgList.value.length);
  curImg.value.name = imgList.value[randomNumber];
};
</script>

<template>
  <div class="mt-5 me-1 row d-flex justify-content-center">
    <div
      class="card mb-2 ms-3"
      style="width: 18rem"
      :data-bs-theme="isDark"
      v-for="(apt, index) in list"
      :key="apt.house_no"
    >
      {{ randomImg() }}
      <template
        v-if="index % 2 == 0 ? (isDark = 'white') : (isDark = 'dark')"
      />

      <img
        class="card-img-top mt-2"
        style="height: 12rem"
        :src="curImg.name"
        alt="Card image cap"
      />
      <div class="card-body">
        <h5 class="card-title">{{ apt.house_name }}</h5>
        <p class="card-text" style="height: 6rem">
          주소: <br />
          {{ apt.addr_road }} <br />
          전화문의: {{ apt.phone }}
        </p>
        <a @click="GoDetail(apt)" class="btn btn-primary">집 보러가기</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * {
  font-family: "Noto Sans KR", sans-serif;
}

.card {
  background-image: var(--bs-gradient);
}

h1 {
  text-align: center;
  margin-top: 0;
  margin-bottom: 0.5rem;
  font-weight: 500;
  line-height: 1.2;
  color: var(--bs-heading-color);
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
}

text {
  color: rgb(50, 108, 249);
  font-weight: 900;
}

button {
  color: white;
  background-image: var(--bs-gradient);
}

button:hover {
  color: white;
  background-image: none;
  background-color: var(--bs-tertiary-color);
}
</style>
