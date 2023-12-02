<script setup>
const props = defineProps({
  startNo: Number,
});

const emit = defineEmits(["pageNo"]);

const GoPage = (num) => {
  emit("pageNo", props.startNo + num - 1);

  const btns = document.getElementsByClassName(`page-link`);

  for (let i = 0; i < btns.length; i++) {
    console.log(btns[i].style.opacity);
    if (btns[i].style.opacity == 0.5) {
      btns[i].style.opacity = 1;
    }

    const btn = document.getElementById(`${num}`);

    btn.style.opacity = 0.5;
  }
};
</script>

<template>
  <div class="ms-1 me-1">
    <ul class="pagination pagination-lg" data-bs-theme="dark">
      <li class="page-item active">
        <a id="1" class="page-link" @click="GoPage(1)">{{
          props.startNo + 0
        }}</a>
      </li>
      <li class="page-item">
        <a id="2" class="page-link" @click="GoPage(2)">{{
          props.startNo + 1
        }}</a>
      </li>
      <li class="page-item">
        <a id="3" class="page-link" @click="GoPage(3)">{{
          props.startNo + 2
        }}</a>
      </li>
      <li class="page-item">
        <a id="4" class="page-link" @click="GoPage(4)">{{
          props.startNo + 3
        }}</a>
      </li>
      <li class="page-item">
        <a id="5" class="page-link" @click="GoPage(5)">{{
          props.startNo + 4
        }}</a>
      </li>
    </ul>
  </div>
</template>

<style scoped>
a {
  color: white;
  background-image: var(--bs-gradient);
}

a:hover {
  color: white;
  background-image: none;
  background-color: var(--bs-tertiary-color);
}
</style>
