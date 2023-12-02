<script setup>
import LoginInfoModal from "@/modals/LoginInfoModal.vue";
import logo from "@/assets/img/logo.gif";

import axios from "axios";
import { ref } from "vue";
const { VITE_SPRING_BASE_URL } = import.meta.env;

// 사용자가 입력한 값을 받는 변수 -> 전역 변수가 아닌 임시 저장하는 변수
const user = ref({
  id: "",
  password: "",
  name: "",
  phone: "",
  pk: 0,
  userRole: "",
});

// 로그인 성공여부, 로그인 에러 여부를 나타내는 토글 버튼
const isLogin = ref(false);
const isLoginError = ref(false);

//로그인 성공 혹은 실패 시 해당 공지 사항을 띄우는 모달 토글 버튼
const isModalOpened = ref(false);
// 로그인 결과를 서버로 부터 받아오는 msg
const msg = ref("");

const openModal = () => {
  isModalOpened.value = true;
};
const closeModal = () => {
  isModalOpened.value = false;
};

const submit = () => {
  const forms = document.querySelectorAll(".requires-validation");
  Array.from(forms).forEach(function (form) {
    form.addEventListener(
      "submit",
      function (event) {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }

        form.classList.add("was-validated");

        console.log(form.checkVisibility());

        if (form.checkVisibility() === true) {
          axios
            .post(VITE_SPRING_BASE_URL + "/user/signup", user.value, {
              header: {
                "Context-Type": "json/application",
              },
            })
            .then((response) => {
              console.log(response.status);
              let { data } = response;
              if (response.status === 201) {
                console.log(data);
                isLogin.value = true;
                isLoginError.value = false;

                msg.value = data.message;

                openModal();
              } else {
                console.log(data.message);
                isLogin.value = false;
                isLoginError.value = true;

                msg.value = data.message;

                openModal();
              }
            })
            .catch((err) => {
              console.log(err);
              if (err.status == 400) {
                console.log(err);
                isLogin.value = false;
                isLoginError.value = true;

                msg.value = data.message;

                openModal();
              }
            });
        }
      },
      false
    );
  });
};
</script>

<template>
  <div id="bg-video">
    <chest>
      <div class="form-body">
        <div class="row">
          <div class="form-holder">
            <h2
              style="position: relative; top: 50px; margin-top: 3%; color: #fff"
            >
              집은 또 하나의 가족입니다.
            </h2>
            <div class="form-content">
              <div class="form-items">
                <div style="display: flex; flex-direction: column">
                  <img
                    :src="logo"
                    style="width: 5rem; align-self: center"
                    alt=""
                  />

                  <h3 style="text-align: center; margin-top: 10px">
                    회 원 가 입
                  </h3>
                </div>
                <form
                  class="requires-validation"
                  novalidate
                  action="javascript:void(0)"
                >
                  <div class="col-md-12">
                    <label for="id" class="form-label">아 이 디</label>
                    <input
                      class="form-control"
                      style="margin-top: 0; margin-bottom: 1rem"
                      type="text"
                      id="id"
                      name="id"
                      placeholder="ID를 입력해주세요!"
                      v-model="user.id"
                      required
                    />
                    <div class="valid-feedback">
                      ID를 올바르게 입력하였습니다!
                    </div>
                    <div class="invalid-feedback">ID는 필수 입력 값입니다!</div>
                  </div>

                  <div class="col-md-12">
                    <label for="name" class="form-label">이 름</label>
                    <input
                      class="form-control"
                      style="margin-top: 0; margin-bottom: 1rem"
                      type="text"
                      id="name"
                      name="name"
                      placeholder="이름을 입력해주세요!"
                      v-model="user.name"
                      required
                    />
                    <div class="valid-feedback">
                      이름을 올바르게 입력하였습니다!
                    </div>
                    <div class="invalid-feedback">
                      이름은 필수 입력 값입니다!
                    </div>
                  </div>

                  <div class="col-md-12">
                    <label for="password" class="form-label">비 밀 번 호</label>
                    <input
                      class="form-control"
                      style="margin-top: 0; margin-bottom: 1rem"
                      id="password"
                      type="password"
                      name="password"
                      placeholder="비밀번호를 입력해주세요!"
                      v-model="user.password"
                      required
                    />
                    <div class="valid-feedback">
                      비밀번호가 올바르게 입력되었습니다!
                    </div>
                    <div class="invalid-feedback">
                      비밀번호는 비어 있어서는 안됩니다!
                    </div>
                  </div>

                  <div class="col-md-12">
                    <label for="phone" class="form-label">핸 드 폰 번 호</label>
                    <input
                      class="form-control"
                      id="phone"
                      style="margin-top: 0; margin-bottom: 1rem"
                      type="tel"
                      name="phone"
                      placeholder="휴대폰 번호를 입력해주세요!"
                      v-model="user.phone"
                      required
                    />
                    <div class="valid-feedback">
                      휴대전화가 올바르게 입력되었습니다!
                    </div>
                    <div class="invalid-feedback">
                      휴대전화가 비어있지 않습니다
                    </div>
                  </div>

                  <div class="col-md-12">
                    <label for="userRole" class="form-label"
                      >무엇을 위해 술술 맞는 수달을 사용하시나요?</label
                    >
                    <select
                      name="userRole"
                      id="userRole"
                      v-model="user.userRole"
                    >
                      <option value="고객">고객</option>
                      <option value="중개업자">중개업자</option>
                    </select>
                    <div class="valid-feedback">
                      휴대전화가 올바르게 입력되었습니다!
                    </div>
                    <div class="invalid-feedback">
                      휴대전화가 비어있지 않습니다
                    </div>
                  </div>

                  <div class="form-check">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      value=""
                      id="invalidCheck"
                    />
                    <label class="form-check-label"
                      >회원가입 약관 동의하시겠습니까?</label
                    >
                  </div>

                  <div class="form-button mt-3">
                    <button
                      id="submit"
                      type="submit"
                      class="btn btn-light"
                      @click="submit"
                      style="margin-bottom: 10px"
                    >
                      회원가입
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </chest>
    <login-info-modal
      :is-open="isModalOpened"
      @modal-close="closeModal"
      :user-info="user"
      :msg="msg"
      :is-login="isLogin"
      :is-login-error="isLoginError"
    />
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

#bg-video {
  background-image: url("@/assets/img/animatedBackground.gif");
  background-size: cover;
}
chest {
  font-family: "JalnanGothic";
  font-weight: 400;
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
  width: 100%;
}

.form-holder {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.form-holder .form-content {
  position: relative;
  text-align: center;
  display: -webkit-box;
  display: -moz-box;
  display: -ms-flexbox;
  display: -webkit-flex;
  display: flex;
  -webkit-justify-content: center;
  justify-content: center;
  -webkit-align-items: center;
  align-items: center;
  padding: 60px;
}

.form-content .form-items {
  border: 10px solid #fff;
  padding-left: 40px;
  padding-right: 40px;
  padding-bottom: 40px;
  display: inline-block;
  width: 100%;
  min-width: 540px;
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
  text-align: left;
  -webkit-transition: all 0.4s ease;
  transition: all 0.4s ease;
}

.form-content h3 {
  color: #fff;
  text-align: left;
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 5px;
}

.form-content h3.form-title {
  margin-bottom: 30px;
}

.form-content p {
  color: #000;
  text-align: left;
  font-size: 17px;
  font-weight: 300;
  line-height: 20px;
}

.form-content label,
.was-validated .form-check-input:invalid ~ .form-check-label,
.was-validated .form-check-input:valid ~ .form-check-label {
  color: #fff;
}

.form-content input[type="text"],
.form-content input[type="password"],
.form-content input[type="tel"],
.form-content select {
  width: 100%;
  padding: 9px 20px;
  text-align: left;
  border: 0;
  outline: 0;
  border-radius: 6px;
  background-color: #fff;
  font-size: 15px;
  font-weight: 300;
  color: #000;
  /*입력값 색깔 */
  -webkit-transition: all 0.3s ease;
  transition: all 0.3s ease;
  margin-top: 16px;
}

.btn-primary:hover,
.btn-primary:focus,
.btn-primary:active {
  background-color: #495056;
  outline: none !important;
  border: none !important;
  box-shadow: none;
}

.form-content textarea {
  position: static !important;
  width: 100%;
  padding: 8px 20px;
  border-radius: 6px;
  text-align: left;
  /* background-color: #fff; */
  border: 0;
  font-size: 15px;
  font-weight: 300;
  color: #8d8d8d;
  outline: none;
  resize: none;
  height: 120px;
  -webkit-transition: none;
  transition: none;
  margin-bottom: 14px;
}

.form-content textarea:hover,
.form-content textarea:focus {
  border: 0;
  background-color: #ebeff8;
  color: #8d8d8d;
}

.mv-up {
  margin-top: -9px !important;
  margin-bottom: 8px !important;
}

.invalid-feedback {
  color: #ff606e;
}

.valid-feedback {
  color: #2acc80;
}
</style>
