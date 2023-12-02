<script setup>
import VKakaoSerach from "@/common/VKakaoSerach.vue";
</script>

<template>
  <div class="d-flex" style="flex-direction: column">
    <div
      class="mt-3"
      style="align-self: center; font-size: 24px; font-weight: bold"
    >
      검색 결과
    </div>
    <VKakaoSerach
      style="width: 100%"
      class="border border-3 rounded mt-3 ms-1 me-1 mb-3"
    />
  </div>
</template>

<style scoped></style>
