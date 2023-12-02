<script setup>
import axios from "axios";
import otterSurprise from "@/assets/img/otter-surprise.gif";
import InputBar from "@/common/InputBar.vue";
import VKakaoMainMap from "@/common/VKakaoMainMap.vue";
import VPagination from "@/common/VPagination.vue";
import picData from "@/assets/constants/building.js";

import { onMounted, ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";

const {
  VITE_DALSEO_URL,
  VITE_SEO_URL,
  VITE_NAM_URL,
  VITE_SUSEONG_URL,
  VITE_BUK_URL,
  VITE_JUNG_URL,
  VITE_DONG_URL,
  VITE_DALSEONG_URL,
} = import.meta.env;
const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

// routing 변수
const router = useRouter();
const route = useRoute();

// 이미지 리스트
const imgList = ref([]);
imgList.value = [...picData.picData];

const region = ref("대구");
// 달서구 APT API 받는 용도
const DalSeoAPT = ref([]);
// 달서구 page 이동 처리
const page = ref(1);
const StartPage = ref((page.value / 5) * 5);
// console.log(StartPage.value);

// 랜덤한 이미지 중 하나 특정할 때 쓰기 위한 변수
const curImg = ref({
  name: "",
});

// 카드 색깔 토글 용
const isDark = ref("dark");

onMounted(() => {
  //페이지를 통해 들어오면 타이틀을 그 이름으로 바꾸기
  if (route.params.region !== undefined) {
    region.value = route.params.region;
  }
  if (region.value === "달서구") {
    getDalSeoApt();
  } else if (region.value === "서구") {
    getSeoGuAPT();
  } else if (region.value === "남구") {
    getNamGuAPT();
  } else if (region.value === "수성구") {
    getSuSeongGuAPT();
  } else if (region.value === "북구") {
    getBukguAPT();
  } else if (region.value === "중구") {
    getJungGuAPT();
  } else if (region.value === "동구") {
    getDongGuAPT();
  } else if (region.value === "달성군") {
    getDalseongGunAPT();
  } else {
    getDalSeoApt();
  }
});

// 달서구에 대한 API 검색 함수 ----------------
const getDalSeoApt = () => {
  // console.log(VITE_OPEN_API_SERVICE_KEY);
  // console.log(VITE_DALSEO_URL);

  axios
    .get(VITE_DALSEO_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        let obj = {
          house_no: 1000 + data.인구수,
          complete_date: data.건축연도,
          kind: "전세",
          lati: data.경도,
          long: data.위도,
          household: data.인구수,
          phone: data.관리실전화,
          addr_road: data.도로명주소,
          old_road: data.지번,
          apt_name: data.아파트명,
          price: Math.floor((data.인구수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.층수,
          maxFloor: data.최고층수,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 서구로 검색했을 때 API SEARCH 함수 ----------------------------
const getSeoGuAPT = () => {
  axios
    .get(VITE_SEO_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        console.log(data);
        let obj = {
          house_no: data.연번,
          complete_date: data.준공일,
          kind: "전세",
          lati: undefined,
          long: undefined,
          household: data.세대수,
          phone: "000-000-0000",
          addr_road: data.도로명주소,
          old_road: data.도로명주소,
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.세대수 / 100,
          maxFloor: data.세대수 / 100,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 남구로 서치할 때 api 함수 -------------------------------------
const getNamGuAPT = () => {
  axios
    .get(VITE_NAM_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        console.log(data);
        let obj = {
          house_no: data.구분,
          complete_date: data.건축연도,
          kind: "전세",
          lati: data.경도,
          long: data.위도,
          household: data.세대수,
          phone: data.전화번호,
          addr_road: data["소재지 도로명주소"], // 주소에 띄어쓰기 있을 경우 이겨내는 방법
          old_road: data["소재지 지번주소"],
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.동수,
          maxFloor: data.세대수 / 100,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 수성구로 서치할 때 api 함수 ------------------------------------------
const getSuSeongGuAPT = () => {
  axios
    .get(VITE_SUSEONG_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        console.log(data);
        let obj = {
          house_no: data.연번,
          complete_date: data.준공,
          kind: "전세",
          lati: undefined,
          long: undefined,
          household: data.세대수,
          phone: "000-0000-0000",
          addr_road: data.도로명주소, // 주소에 띄어쓰기 있을 경우 이겨내는 방법
          old_road: data.지번주소,
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.동수,
          maxFloor: data.세대수 / 100,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 북구로 서치할 때 api 함수 -------------------------------------------
const getBukguAPT = () => {
  axios
    .get(VITE_BUK_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        console.log(data);
        let obj = {
          house_no: data.동수,
          complete_date: data.준공년도,
          kind: "전세",
          lati: undefined,
          long: undefined,
          household: data.세대수,
          phone: data.관리사무소,
          addr_road: data.도로명, // 주소에 띄어쓰기 있을 경우 이겨내는 방법
          old_road: undefined,
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.동수,
          maxFloor: data.세대수 / 100,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 중구로 서치할 때 api 함수 -------------------------------------------
const getJungGuAPT = () => {
  axios
    .get(VITE_JUNG_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        console.log(data);
        let obj = {
          house_no: data.구분,
          complete_date: data.입주년도,
          kind: "전세",
          lati: undefined,
          long: undefined,
          household: data.세대수,
          phone: data["관리사무실 전화번호"],
          addr_road: data.도로명주소, // 주소에 띄어쓰기 있을 경우 이겨내는 방법
          old_road: undefined,
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.동수,
          maxFloor: data.세대수 / 100,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 동구로 서치할 때 api 함수 -------------------------------------------
const getDongGuAPT = () => {
  axios
    .get(VITE_DONG_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        console.log(data);
        let obj = {
          house_no: data.연번,
          complete_date: data.입주년도,
          kind: "전세",
          lati: undefined,
          long: undefined,
          household: data.세대수,
          phone: "000-0000-0000",
          addr_road: data.주소, // 주소에 띄어쓰기 있을 경우 이겨내는 방법
          old_road: undefined,
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.층수,
          maxFloor: data.세대수 / 100,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 달성군으로 서치할 때 api 함수 ----------------------------------------
const getDalseongGunAPT = () => {
  axios
    .get(VITE_DALSEONG_URL, {
      params: {
        page: page.value,
        perPage: 12,
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
      },
    })
    .then(({ data }) => {
      console.log(data.data);
      data.data.forEach((data) => {
        // console.log(data)
        let obj = {
          house_no: data.연번,
          complete_date: data.준공년도,
          kind: "전세",
          lati: undefined,
          long: undefined,
          household: data.세대수,
          phone: "000-000-0000",
          addr_road: data.도로명주소,
          old_road: data.도로명주소,
          apt_name: data.아파트명,
          price: Math.floor((data.세대수 + Math.random(500)) * 10) / 1000,
          hit: 0,
          floor: data.동수,
          maxFloor: data.층수,
        };
        DalSeoAPT.value.push(obj);
      });

      // DalSeoAPT.value = data.data;
      console.log("들어간 내용", DalSeoAPT.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 5장씩 페이징 처리 함수
const PerPageMoving = (index) => {
  console.log(index);

  if (index == 1 && StartPage.value == 1) {
    confirm("현재 보시는 화면이 첫 페이지 입니다!");
    return;
  }

  if (index === 1) {
    page.value = StartPage.value - 5;
    StartPage.value = page.value;
  }

  if (index === 2) {
    page.value = StartPage.value + 5;
    StartPage.value = page.value;
  }
};

const OnePageMoving = (index) => {
  page.value = index;
};

watch(page, () => {
  console.log("현 페이지", page.value);
  console.log("시작 페이지", StartPage.value);
  DalSeoAPT.value.length = 0;
  if (region.value === "달서구") {
    getDalSeoApt();
  } else if (region.value === "서구") {
    getSeoGuAPT();
  } else if (region.value === "남구") {
    getNamGuAPT();
  } else if (region.value === "수성구") {
    getSuSeongGuAPT();
  } else if (region.value === "북구") {
    getBukguAPT();
  } else if (region.value === "중구") {
    getJungGuAPT();
  } else if (region.value === "동구") {
    getDongGuAPT();
  } else if (region.value === "달성군") {
    getDalseongGunAPT();
  } else {
    getDalSeoApt();
  }
});

// 상세보기 가기 위한 함수
const GoDetail = (apt) => {
  console.log(apt);

  router.push({
    name: "DetailHome",
    state: {
      temp: JSON.stringify(apt),
    },
  });
};

// 이미지 랜덤하게 나오게 하는 함수
const randomImg = () => {
  let randomNumber = Math.floor(Math.random() * imgList.value.length);
  curImg.value.name = imgList.value[randomNumber];
};
</script>

<template>
  <div class="mt-5 me-1 row d-flex justify-content-center">
    <h1 style="font-family: Noto Sans KR">
      혹시 <text>{{ region }}</text> 을 찾고 계신가요?
      <img :src="otterSurprise" style="width: 10rem" alt="" />
    </h1>

    <InputBar />

    <v-kakao-main-map
      @target="GoDetail"
      :DalSeoAPT="DalSeoAPT"
      class="border border-3 rounded mt-3 ms-3 me-1 mb-3"
      style="width: 88%"
    />
    <div class="d-flex justify-content-center mt-4 mb-4" data-bs-theme="dark">
      <button class="btn mb-3" @click="PerPageMoving(1)">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="32"
          height="32"
          fill="currentColor"
          class="bi bi-caret-left-fill"
          viewBox="0 0 16 16"
        >
          <path
            d="m3.86 8.753 5.482 4.796c.646.566 1.658.106 1.658-.753V3.204a1 1 0 0 0-1.659-.753l-5.48 4.796a1 1 0 0 0 0 1.506z"
          />
        </svg>
      </button>
      <VPagination :start-no="StartPage" @page-no="OnePageMoving" />
      <button class="btn mb-3" @click="PerPageMoving(2)">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="32"
          height="32"
          fill="currentColor"
          class="bi bi-caret-right-fill"
          viewBox="0 0 16 16"
        >
          <path
            d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"
          />
        </svg>
      </button>
    </div>

    <div
      class="card mb-2 ms-3"
      style="width: 18rem"
      :data-bs-theme="isDark"
      v-for="(apt, index) in DalSeoAPT"
      :key="apt.경도"
    >
      {{ randomImg() }}
      <template
        v-if="index % 2 == 0 ? (isDark = 'white') : (isDark = 'dark')"
      />

      <img
        class="card-img-top mt-2"
        style="height: 12rem"
        :src="curImg.name"
        alt="Card image cap"
      />
      <div class="card-body">
        <h5 class="card-title">{{ apt.apt_name }}</h5>
        <p class="card-text" style="height: 6rem">
          주소: <br />
          {{ apt.addr_road }} <br />
          전화문의: {{ apt.phone }}
        </p>
        <a @click="GoDetail(apt)" class="btn btn-primary">집 보러가기</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * {
  font-family: "Noto Sans KR", sans-serif;
}

.card {
  background-image: var(--bs-gradient);
}

h1 {
  text-align: center;
  margin-top: 0;
  margin-bottom: 0.5rem;
  font-weight: 500;
  line-height: 1.2;
  color: var(--bs-heading-color);
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
}

text {
  color: rgb(50, 108, 249);
  font-weight: 900;
}

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
