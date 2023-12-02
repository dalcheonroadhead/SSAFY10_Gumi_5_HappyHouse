<script setup>
import { ref, watch, onMounted } from "vue";
import NomalaptMaker from "@/assets/img/marker/aptMarker.png";
import selectedAptMarker from "@/assets/img/marker/selectedAptMarker.png";
var map;
// 포지션 - 지도 내 장소의 좌표 배열, marker는 그 위에 뜰 마커의 좌표 배열, 인포는 정보창 좌표 배열
const positions = ref([]);
const markers = ref([]);
const infos = ref([]);

let selectedMarker = null;

const props = defineProps({ DalSeoAPT: Array, selectApt: Object });
const emit = defineEmits(["target"]);

const GoDetail = (apt) => {
  console.log("클릭됨");
  emit("target", apt);
};

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer&autoload=false`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(35.8714354, 128.601445),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};

watch(
  () => props.DalSeoAPT,
  () => {
    // console.log(props.DalSeoAPT);
    positions.value = [];
    props.DalSeoAPT.forEach((apt) => {
      // console.log(apt);
      // console.log("위도", apt.lati);
      // console.log("경도", apt.long);

      //만약 위도 경도가 없는 api의 데이터라면
      if (apt.lati === undefined && apt.long === undefined) {
        var geocoder = new kakao.maps.services.Geocoder();
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(apt.addr_road, function (result, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            apt.lati = result[0].x;
            apt.long = result[0].y;
          }
        });
      }

      let obj = {};
      obj.latlng = new kakao.maps.LatLng(apt.long, apt.lati);
      obj.title = apt.apt_name;
      obj.long = apt.long;
      obj.lati = apt.lati;
      obj.apt = apt;
      positions.value.push(obj);
    });
    loadMarkers();
  },
  { deep: true }
);

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  const imgSrc = NomalaptMaker;

  // 마커 이미지의 이미지 크기 입니다
  const imgSize = new kakao.maps.Size(35, 35);
  const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도 -> marker.setMap(map)을 쓰지 않고, 바로 marker를 지도에 찍는 방법
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      image: markerImage, // 마커의 이미지
    });

    var iwContent = `<div style="padding:5px; width: 14rem;  ">${position.title} <br> <div style="flex: 1; flex-direction: row;"><a id="detail" class="btn btn-primary text-white mb-1"  target="_blank">자세히보기</a> <a class="btn btn-primary text-white" href="https://map.kakao.com/link/to/${position.title},${position.long},${position.lati}" style="color:blue" target="_blank">길찾기</a></div> </div>`;

    const parser = new DOMParser();
    const doc = parser.parseFromString(iwContent, "text/html");

    var iwPosition = new kakao.maps.LatLng(position.lati, position.long);

    var infowindow = new kakao.maps.InfoWindow({
      position: iwPosition,
      content: doc.body.firstChild,
      removable: true,
    });

    const btn = infowindow.getContent().querySelector("#detail");
    btn.addEventListener("click", () => {
      GoDetail(position.apt);
    });

    infos.value.push(infowindow);

    var openInfo = function () {
      infos.value.forEach(function (each) {
        each.close();
      }); // close all infos
      infowindow.open(map, marker);
    };

    // 마커에 mouseover 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "mouseover", function () {
      // 클릭된 마커가 없고, mouseover된 마커가 클릭된 마커가 아니면
      // 마커의 이미지를 오버 이미지로 변경합니다
      if (!selectedMarker || selectedMarker !== marker) {
        const imgSize = new kakao.maps.Size(50, 50);
        const overImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

        marker.setImage(overImage);
      }
    });

    // 마커에 mouseout 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "mouseout", function () {
      // 클릭된 마커가 없고, mouseout된 마커가 클릭된 마커가 아니면
      // 마커의 이미지를 기본 이미지로 변경합니다

      if (!selectedMarker || selectedMarker !== marker) {
        marker.setImage(markerImage);
      }
    });

    // 마커에 click 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "click", function () {
      console.log("현재 선택된 마커", marker);

      const imgSize = new kakao.maps.Size(35, 35);
      const clickImage = new kakao.maps.MarkerImage(selectedAptMarker, imgSize);

      const nomalImage = new kakao.maps.MarkerImage(NomalaptMaker, imgSize);

      // 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
      // 마커의 이미지를 클릭 이미지로 변경합니다
      if (!selectedMarker || selectedMarker !== marker) {
        // 클릭된 마커 객체가 null이 아니면
        // 클릭된 마커의 이미지를 기본 이미지로 변경하고
        !!selectedMarker && selectedMarker.setImage(nomalImage);

        // 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
        marker.setImage(clickImage);
      }

      // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
      selectedMarker = marker;
      openInfo();
    });

    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};
// 5. 마커 지우기
const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 5rem;
  height: 700px;
}
</style>
