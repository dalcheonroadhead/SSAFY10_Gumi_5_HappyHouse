<script setup>
import Homeimg from "@/assets/img/logo.gif";
import { useEventListener } from "@vueuse/core";
import guideData from "@/assets/constants/GuideData.js";
import { onMounted, ref } from "vue";
const img = Homeimg;

const Datas = ref([...guideData.guideData]);
const mouseDown = (e) => { };
const GuideClick = (e, URL) => {
  console.log(URL);
  window.open(URL);
};
</script>

<template>
  <div id="body" class="d-flex" style="
          background-color: #fefefe;
          /* background-image: linear-gradient(134deg, #fefefe 0%, #00a4e4 74%); */
        ">
    <div class="flex-shrink-0 ms-5" style="width: 14rem">
      <img :src="img" alt="수달로고" class="mt-5" />
      <div class="card text-white mb-3" style="
              max-width: 18rem;
              background-color: #5d4604;
              background-image: linear-gradient(134deg, #5d4604 0%, #591002 74%);
            ">
        <div class="card-header text-center">
          수달이가 알려주는 <br />
          매매 가이드!
        </div>
        <div class="card-body text-white text-center">
          오늘의 추천 글이야 ^~^
        </div>
      </div>
    </div>
    <div class="flex-grow-1 ms-3 m-5 row">
      <button id="btn" class="card border-white ms-3 mb-3" style="max-width: 15rem; height: 15rem" v-for="data in Datas"
        :key="data.id" :style="{ backgroundColor: data.color }" @click="($event) => GuideClick($event, data.URL)">
        <div class="card-header text-white">{{ data.title }}</div>
        <div class="card-body">
          <h5 class="card-title text-white">{{ data.name }}</h5>
          <p class="card-text"></p>
        </div>
      </button>
    </div>
  </div>
</template>

<style scoped>
.card {
  background-image: var(--bs-gradient);
}

button:hover {
  background-color: black;
}
</style>
