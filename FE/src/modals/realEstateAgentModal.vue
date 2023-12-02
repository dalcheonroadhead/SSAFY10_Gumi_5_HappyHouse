<script setup>
import { defineProps, defineEmits, ref } from "vue";
import { onClickOutside } from '@vueuse/core'
import profile from "@/assets/img/realEstate.jpg"
const props = defineProps({
    isOpen: Boolean,
    apt: Object
});

const emit = defineEmits(["modal-close"]);

const target = ref(null)
onClickOutside(target, () => emit('modal-close'))

</script>

<template>
    <div v-if="isOpen" class="modal-mask">
        <div class="modal-wrapper">
            <div class="modal-container d-flex flex-row">
                <div class="card" style="width: 18rem;">
                    <img :src="profile" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">성영준의 수다 부동산 </h5>
                        <p class="card-text">전화번호: {{ apt.phone }}</p>
                        <a href="#" class="btn btn-primary" @click.stop="emit('modal-close')">닫기</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
}

.modal-container {
    width: 300px;
    margin: 150px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
}
</style>