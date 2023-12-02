<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
// 검색어를 받는 변수
const userSearch = ref("");

const router = useRouter();
const GoSearch = () => {
  userSearch.value += " 맛집";
  router.push({
    name: "SearchResult",
    state: {
      temp: JSON.stringify(userSearch.value),
    },
  });
};
</script>

<template>
  <div
    class="input-group mb-3 ms-5 me-5"
    data-bs-theme="dark"
    style="width: 40rem; padding: 0%"
  >
    <input
      type="text"
      class="form-control"
      placeholder="원하는 지역을 입력해주세요"
      aria-label="Recipient's username"
      aria-describedby="searchbtn"
      v-model.lazy="userSearch"
      @keyup.enter="GoSearch"
    />
    <button class="btn" type="button" id="searchbtn" @click="GoSearch()">
      검색
    </button>
  </div>
</template>

<style scoped>
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
