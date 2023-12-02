<script setup>
import { onMounted, ref } from "vue";
var map;

const loc = ref("");

const places = ref([]);

const { temp } = history.state;
console.log(temp);
loc.value = JSON.parse(temp);

console.log(loc.value);

// 프로그램 시작
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();

    // 키워드로 장소를 검색합니다.
    // 이미 map이 만들어져 있다면 여기서 바로 검색 
    ps.keywordSearch(loc.value, placesSearchCB);
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
      }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

// 변수들은 맵이 load된 이후에 써야 한다.
// kakao 변수가 생기기도 전에 kakao 내재 변수들을 부르면 에러가 난다.
var infowindow;
var ps;

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 2,
  };
  map = new kakao.maps.Map(container, options);
  infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  // map을 만든 후에, map의 하위 매소드를 사용 
  ps = new kakao.maps.services.Places();
  ps.keywordSearch(loc.value, placesSearchCB);
  // loadMarkers();
};

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {
    // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
    // LatLngBounds 객체에 좌표를 추가합니다
    var bounds = new kakao.maps.LatLngBounds();

    for (var i = 0; i < data.length; i++) {
      displayMarker(data[i]);
      console.log(data[i])

      var newApt = {
        price: data[i]
      }

      places.value.push();
      bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
    }

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
  }
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
  // 마커를 생성하고 지도에 표시합니다
  var marker = new kakao.maps.Marker({
    map: map,
    position: new kakao.maps.LatLng(place.y, place.x),
  });

  // 마커에 클릭이벤트를 등록합니다
  kakao.maps.event.addListener(marker, "click", function () {
    // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
    infowindow.setContent(
      '<div style="padding:5px;font-size:12px;">' + place.place_name + "</div>"
    );
    infowindow.open(map, marker);
  });
}

// const deleteMarkers = () => {
//   if (markers.value.length > 0) {
//     markers.value.forEach((marker) => marker.setMap(null));
//   }
// };
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 700px;
}
</style>
