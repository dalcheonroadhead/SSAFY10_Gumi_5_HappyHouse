<script setup>
import { defineProps, defineEmits, ref, watch } from "vue";
import { onClickOutside } from '@vueuse/core'
import { useRouter } from "vue-router";
import axios from "axios";

const id = ref("");
const password = ref('');
const errMsg = ref('');
const { VITE_SPRING_BASE_URL } = import.meta.env;

const props = defineProps({
    isOpen: Boolean,
})
const emit = defineEmits(["modal-close"]);

const submit = () => {
    axios
        .get(VITE_SPRING_BASE_URL + `/user/search/${id.value}`)
        .then((response) => {
            console.log(response.status);
            let { data } = response;

            if (response.status === 200) {
                console.log(data);
                password.value = data.password;
            }
        })
        .catch((err) => {
            console.log(err);
            errMsg.value = "입력하신 ID가 존재하지 않습니다."
        })
}

</script>

<template>
    <div v-if="isOpen" class="modal-mask">
        <div class="modal-wrapper">
            <div class="modal-container card text-white bg-info mb-3" style="max-width: 18rem;">
                <div class="card-header">비 밀 번 호 찾 기</div>
                <div class="card-body">
                    <h5 class="card-title"></h5>
                    <p class="card-text"> <input class="form-control" type="text" name="id" placeholder="ID를 입력해주세요!"
                            v-model="id" required /> </p>
                    <button class="btn btn-light text-dark me-2" @click="submit">제출하기</button>
                    <a href="#" class="btn btn-light text-dark" @click.stop="emit('modal-close')">닫기</a>

                    <div v-if="password !== ''">비밀번호는 {{ password }} 입니다.</div>
                    <div v-if="errMsg !== ''">{{ errMsg }}</div>
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