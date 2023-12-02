<script setup>
import axios from "axios";
import { ref } from "vue";
import { useInfoStore } from "@/stores/user";
import { storeToRefs } from "pinia";
import ImgGrid from "@/common/ImgGrid.vue";
import VKakaoMap from "@/common/VKakaoMap.vue";
import realEstateAgentModal from "@/modals/realEstateAgentModal.vue";
import CommentInput from "@/common/CommentInput.vue";
import insidePic1 from "@/assets/img/house/insidePics/insidePic1.jpg";
import insidePic2 from "@/assets/img/house/insidePics/insidePic2.jpg";
import insidePic3 from "@/assets/img/house/insidePics/insidePic3.jpg";
import insidePic4 from "@/assets/img/house/insidePics/insidePic4.jpg";
import insidePic5 from "@/assets/img/house/insidePics/insidePic5.jpg";
const { VITE_SPRING_BASE_URL } = import.meta.env;

const { userInfo } = storeToRefs(useInfoStore());

const chargingStations = ref([]);
const apt = ref({});
const heart = ref("white");

const isModalOpened = ref(false);

const openModal = () => {
  isModalOpened.value = true;
};
const closeModal = () => {
  isModalOpened.value = false;
};

const imgs = [
  {
    img: insidePic1,
    name: "전방사진",
  },
  {
    img: insidePic2,
    name: "주변 거리",
  },
  {
    img: insidePic3,
    name: "화장실",
  },
  {
    img: insidePic4,
    name: "부엌",
  },
  {
    img: insidePic5,
    name: "창문",
  },
];

//vue-router 4 부터는 객체를 위에 표시하지 않고 낼라면 이렇게 해야함.

// 보낸 값 받기
const { temp } = history.state;
console.log(temp);
apt.value = JSON.parse(temp);
console.log(apt.value);
//원래 방식
// -> index.js에 경로 설정 및 props: true
// -> 설정한 경로 이름으로 값 보내기. 만약 /detail/:apt이면 보내는 곳에선 {apt : JSON.stringfiy(값)}
// 이렇게 해서 값을 쏴주면 여기선  route.param으로 받는다.
chargingStations.value.push(apt);

console.log(chargingStations.value);

console.log(userInfo.value.pk);

const temp2 = ref({
  pk: userInfo.value.pk,
  house_no: apt.value.house_no,
  addr_road: apt.value.addr_road,
  like_cnt: 0,
});

axios
  .post(VITE_SPRING_BASE_URL + "/favorite/checkFavorite", temp2.value, {
    header: {
      "Context-Type": "json/application",
    },
  })
  .then((response) => {
    console.log(response.status);
    let { data } = response;
    console.log(data.heartColor);
    if (response.status === 200) {
      heart.value = data.heartColor;
    }
  });

const toggleHeart = () => {
  if (heart.value === "red") {
    closeHeart();
  } else {
    openHeart();
  }
};

const openHeart = () => {
  heart.value = "red";
  let PK = userInfo.value.pk;
  console.log(PK);
  axios
    .post(
      VITE_SPRING_BASE_URL + "/favorite/pushFavorite",
      {
        pk: PK,
        house_no: apt.value.house_no,
        addr_road: apt.value.addr_road,
        addr_dong: apt.value.old_road,
        kind: apt.value.kind,
        house_name: apt.value.apt_name,
        household: apt.value.household,
        floor: apt.value.floor,
        max_floor: apt.value.floor,
        complete_date: apt.value.complete_date,
        phone: apt.value.phone,
        content: `가격은 ${apt.value.price}억 이고, 조회수는 ${apt.value.hit}`,
        hit: apt.value.hit,
        register_date: "",
      },
      {
        header: {
          "Context-Type": "json/application",
        },
      }
    )
    .then((response) => {
      console.log(response);
      let { data } = response;

      if (response.data === 200) {
        console.log(data.message);
      }
    })
    .catch((err) => {
      console.log(err);
    });
};

const closeHeart = () => {
  heart.value = "white";
  let PK = userInfo.value.pk;
  console.log(PK);
  axios
    .post(
      VITE_SPRING_BASE_URL + "/favorite/deleteFavorite",
      {
        pk: PK,
        house_no: apt.value.house_no,
        addr_road: apt.value.addr_road,
      },
      {
        header: {
          "Context-Type": "json/application",
        },
      }
    )
    .then((response) => {
      console.log(response);
      let { data } = response;

      if (response.data === 200) {
        console.log(data.message);
      }
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<template>
  <div>
    <div class="d-flex flex-row">
      <div
        class="col-10 border border-3 rounded mt-3 ms-1 me-1 mb-3"
        style="height: 100vh"
      >
        <v-kakao-map id="map" class="" :stations="chargingStations" />
      </div>
      <div class="mt-3 ms-4 mb-3">
        <div class="card">
          <div class="card-body">
            <h6 class="card-subtitle mb-4 mt-0 text-muted">
              매물번호 <span class="border ps-1 pe-1">{{ apt.house_no }}</span>
            </h6>
            <h5 class="card-title" style="font-weight: bold">
              {{ apt.kind }} {{ apt.price }} 억
            </h5>
            <h6 class="card-subtitle mb-3 mt-0 text-muted">
              최근 7일간 조회 수 {{ apt.hit }} 회
            </h6>
            <div class="card-text d-flex flex-column">
              <div class="mb-2">
                <span id="title">건축 연도 </span>: {{ apt.complete_date }}
              </div>
              <div class="mb-2">
                <span id="title">도로명 주소 </span>: <br />
                {{ apt.addr_road }}
              </div>
              <div class="mb-2">
                <span id="title">지번 </span>: <br />
                {{ apt.old_road }}
              </div>
              <div class="mb-2">
                <span id="title">층수 </span>: {{ apt.floor }} &nbsp;
                <span id="title">최고층수 </span>: {{ apt.maxFloor }}
              </div>
              <div class="mb-2">
                <span id="title">관리실 전화번호: </span>: {{ apt.phone }}
              </div>
            </div>
            <a @click="openModal" class="btn btn-primary me-3 mt-3"
              >연락처 보기
            </a>
            <a
              @click="toggleHeart"
              :style="{ color: heart }"
              class="btn btn-primary mt-3"
              id="heart"
              >♥️</a
            >
          </div>
        </div>
      </div>
    </div>
    <ImgGrid :imgs="imgs" />
    <comment-input
      v-if="heart === 'red'"
      :house_no="apt.house_no"
      :addr_road="apt.addr_road"
    />
    <real-estate-agent-modal
      :is-open="isModalOpened"
      @modal-close="closeModal"
      :apt="apt"
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

div {
  font-family: "JalnanGothic";
}

#title {
  font-weight: bold;
}

#heart {
  font-size: 40;
}

#heart:hover {
  color: red;
}
</style>
