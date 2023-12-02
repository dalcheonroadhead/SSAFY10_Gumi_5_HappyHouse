<script setup>
import { RouterLink, RouterView } from "vue-router";
import NavHome from "@/components/NavHome.vue";
import AboutUsVue from "@/components/AboutUsVue.vue";
</script>

<template>
  <div>
    <NavHome />
    <RouterLink to=" /">
    </RouterLink>
    <RouterView />
    <AboutUsVue />
  </div>
</template>

<style scoped></style>
