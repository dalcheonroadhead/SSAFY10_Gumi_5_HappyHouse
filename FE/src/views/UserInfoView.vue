<script setup>
import { ref } from "vue";
import { useInfoStore } from "@/stores/user";
import { storeToRefs } from "pinia";
import axios from "axios";
const userStore = useInfoStore();
const { userInfo } = storeToRefs(userStore);
const { VITE_SPRING_BASE_URL } = import.meta.env;

const isChange = ref(false);

const wantChange = () => {
  isChange.value = !isChange.value;
};

const user = ref({
  name: userInfo.value.name,
  id: userInfo.value.id,
  password: userInfo.value.pwd,
  phone: userInfo.value.phone,
  userRole: userInfo.value.userRole,
});

const submit = () => {
  console.log(user.value);

  axios
    .post(VITE_SPRING_BASE_URL + "/user/update", user.value, {
      header: {
        "Context-Type": "json/application",
      },
    })
    .then((response) => {
      let { data } = response;

      console.log(data);

      if (response.status === 200) {
        userInfo.value.id = data.userInfo.id;
        userInfo.value.name = data.userInfo.name;
        userInfo.value.phone = data.userInfo.phone;
        userInfo.value.pwd = data.userInfo.password;
        userInfo.value.userRole = data.userInfo.userRole;
      }
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<template>
  <div class="container">
    <div class="main-body">
      <div class="row gutters-sm">
        <div class="col-md-4 mb-3">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img
                  src="https://bootdey.com/img/Content/avatar/avatar7.png"
                  alt="Admin"
                  class="rounded-circle"
                  width="150"
                />
                <div class="mt-3">
                  <h4>{{ userInfo.name }}</h4>
                  <p class="text-secondary mb-1">{{ userInfo.userRole }}</p>
                  <p class="text-muted font-size-sm">
                    구미 인의동 삼성공단 제2 후문
                  </p>
                  <button class="btn btn-primary">Follow</button>
                  <button class="btn btn-outline-primary">Message</button>
                </div>
              </div>
            </div>
          </div>
          <div class="card mt-3">
            <ul class="list-group list-group-flush">
              <li
                class="list-group-item d-flex justify-content-between align-items-center flex-wrap"
              >
                <h6 class="mb-0">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="feather feather-globe mr-2 icon-inline"
                  >
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="2" y1="12" x2="22" y2="12"></line>
                    <path
                      d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"
                    ></path></svg
                  >Website
                </h6>
                <span class="text-secondary">https://bootdey.com</span>
              </li>
              <li
                class="list-group-item d-flex justify-content-between align-items-center flex-wrap"
              >
                <h6 class="mb-0">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="feather feather-github mr-2 icon-inline"
                  >
                    <path
                      d="M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22"
                    ></path></svg
                  >Github
                </h6>
                <span class="text-secondary">없음</span>
              </li>
              <li
                class="list-group-item d-flex justify-content-between align-items-center flex-wrap"
              >
                <h6 class="mb-0">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="feather feather-twitter mr-2 icon-inline text-info"
                  >
                    <path
                      d="M23 3a10.9 10.9 0 0 1-3.14 1.53 4.48 4.48 0 0 0-7.86 3v1A10.66 10.66 0 0 1 3 4s-4 9 5 13a11.64 11.64 0 0 1-7 2c9 5 20 0 20-11.5a4.5 4.5 0 0 0-.08-.83A7.72 7.72 0 0 0 23 3z"
                    ></path></svg
                  >Twitter
                </h6>
                <span class="text-secondary">없음</span>
              </li>
              <li
                class="list-group-item d-flex justify-content-between align-items-center flex-wrap"
              >
                <h6 class="mb-0">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="feather feather-instagram mr-2 icon-inline text-danger"
                  >
                    <rect
                      x="2"
                      y="2"
                      width="20"
                      height="20"
                      rx="5"
                      ry="5"
                    ></rect>
                    <path
                      d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"
                    ></path>
                    <line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line></svg
                  >Instagram
                </h6>
                <span class="text-secondary">없음</span>
              </li>
              <li
                class="list-group-item d-flex justify-content-between align-items-center flex-wrap"
              >
                <h6 class="mb-0">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="feather feather-facebook mr-2 icon-inline text-primary"
                  >
                    <path
                      d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"
                    ></path></svg
                  >Facebook
                </h6>
                <span class="text-secondary">없음</span>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-8">
          <div class="card mb-3">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Full Name</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  {{ userInfo.name }}
                </div>
              </div>

              <hr />

              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Mobile</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  {{ userInfo.phone }}
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-3">
                  <h6 class="mb-0">Address</h6>
                </div>
                <div class="col-sm-9 text-secondary">
                  구미 인의동 삼성공단 제2 후문
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-12">
                  <a class="btn btn-info" @click="wantChange">Edit</a>
                </div>
              </div>
            </div>
          </div>
          <div v-if="isChange" class="col-lg-12 mb-3">
            <div class="card">
              <div class="card-body">
                <div class="row mb-3">
                  <div class="col-sm-3">
                    <h6 class="mb-0">Full Name</h6>
                  </div>
                  <div class="col-sm-9 text-secondary">
                    <input
                      type="text"
                      class="form-control"
                      v-model="user.name"
                    />
                  </div>
                </div>
                <div class="row mb-3">
                  <div class="col-sm-3">
                    <h6 class="mb-0">Phone</h6>
                  </div>
                  <div class="col-sm-9 text-secondary">
                    <input
                      type="text"
                      class="form-control"
                      v-model="user.phone"
                    />
                  </div>
                </div>
                <div class="row mb-3">
                  <div class="col-sm-3">
                    <h6 class="mb-0">Role</h6>
                  </div>
                  <div class="col-sm-9 text-secondary">
                    <select
                      name="userRole"
                      id="userRole"
                      v-model="user.userRole"
                    >
                      <option value="고객">고객</option>
                      <option value="중개업자">중개업자</option>
                    </select>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-3"></div>
                  <div class="col-sm-9 text-secondary">
                    <input
                      type="button"
                      value="Save Change"
                      class="btn btn-primary px-4"
                      @click="submit"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-12 mb-3">
            <div class="card h-100">
              <div class="card-body">
                <h6 class="d-flex align-items-center mb-3">
                  <i class="material-icons text-info mr-2">{{
                    userInfo.userRole
                  }}</i
                  >&nbsp; 로서의 status
                </h6>
                <small
                  >게시물 개수:
                  <i class="material-icons text-info mr-2">80</i></small
                >
                <div class="progress mb-3" style="height: 5px">
                  <div
                    class="progress-bar bg-primary"
                    role="progressbar"
                    style="width: 80%"
                    aria-valuenow="80"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                </div>
                <small
                  >좋아요 받은 매매물의 개수:
                  <i class="material-icons text-info mr-2">72</i></small
                >
                <div class="progress mb-3" style="height: 5px">
                  <div
                    class="progress-bar bg-primary"
                    role="progressbar"
                    style="width: 72%"
                    aria-valuenow="72"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                </div>
                <small
                  >댓글 개수:
                  <i class="material-icons text-info mr-2">89</i></small
                >
                <div class="progress mb-3" style="height: 5px">
                  <div
                    class="progress-bar bg-primary"
                    role="progressbar"
                    style="width: 89%"
                    aria-valuenow="89"
                    aria-valuemin="0"
                    aria-valuemax="100"
                  ></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
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

div {
  font-family: "JalnanGothic";
}

body {
  margin-top: 20px;
  color: #1a202c;
  text-align: left;
  background-color: #e2e8f0;
}

.main-body {
  padding: 15px;
}

.card {
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid rgba(0, 0, 0, 0.125);
  border-radius: 0.25rem;
}

.card-body {
  flex: 1 1 auto;
  min-height: 1px;
  padding: 1rem;
}

.gutters-sm {
  margin-right: -8px;
  margin-left: -8px;
}

.gutters-sm > .col,
.gutters-sm > [class*="col-"] {
  padding-right: 8px;
  padding-left: 8px;
}

.mb-3,
.my-3 {
  margin-bottom: 1rem !important;
}

.bg-gray-300 {
  background-color: #e2e8f0;
}

.h-100 {
  height: 100% !important;
}

.shadow-none {
  box-shadow: none !important;
}
</style>
