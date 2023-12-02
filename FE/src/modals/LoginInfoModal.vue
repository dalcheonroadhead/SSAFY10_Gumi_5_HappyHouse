<script setup>
import { defineProps, defineEmits, ref, watch } from "vue";
import { onClickOutside } from '@vueuse/core'
import { useRouter } from "vue-router";

const props = defineProps({
    isOpen: Boolean,
    isLogin: Boolean,
    isLoginError: Boolean,
    userInfo: Object,
    msg: String
});

const emit = defineEmits(["modal-close"]);

const target = ref(null)
onClickOutside(target, () => emit('modal-close'))

const router = useRouter();

const moveMain = () => {
    router.push({
        name: "home"
    })

}
</script>

<template>
    <div v-if="isOpen" class="modal-mask">
        <div v-if="isLogin" class="modal-wrapper">
            <div class="modal-container card text-white bg-info mb-3" style="max-width: 18rem;">
                <div class="card-header">로그인 성공!!</div>
                <div class="card-body">
                    <h5 class="card-title">{{ props.userInfo.id }}님 환영합니다!</h5>
                    <p class="card-text"> {{ props.msg }}</p>
                    <a href="#" class="btn btn-light text-dark" @click.stop="moveMain()">홈으로 가기</a>
                </div>
            </div>
        </div>
        <div v-else class="modal-wrapper">
            <div class="modal-container card text-white bg-danger mb-3" style="max-width: 18rem;">
                <div class="card-header">로그인 실패!!</div>
                <div class="card-body">
                    <h5 class="card-title"></h5>
                    <p class="card-text"> {{ props.msg }}</p>
                    <a href="#" class="btn btn-warning text-dark" @click.stop="emit('modal-close')">닫기</a>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
@font-face {
    font-family: "JalnanGothic";
    src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/JalnanGothic.woff") format("woff");
    font-weight: normal;
    font-style: normal;
}


div {
    font-family: "JalnanGothic";
}

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