<script setup>
import { ref, onMounted } from "vue";
import selectedAptMarker from "@/assets/img/marker/selectedAptMarker.png";

// 맵과 로드뷰를 감싸는 변수 
var mapWrapper
var mapCenter

// 맵 변수
var map;

// 표시할 장소, 장소 위의 Marker, info 창 
const positions = ref([]);
var markers = []
const infos = ref([]);

// 검색한 장소 객체
var ps;
// 장소를 맵 위에 띄울 객체
var placeOverlay;

// 현재 선택된 주인공 장소를 표현하는 객체
let selectedMarker = null;

var contentNode = document.createElement('div')

// 로드뷰와 이어지는 마커 이미지를 생성합니다.
var markImage

// 현재 선택된 카테고리를 말해주는 변수 
var currCategory = '';

var rvContainer//로드뷰를 표시할 div
var rv //로드뷰 객체
var rvClient  //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

var rvMarker // 드래그가 가능한 마커 

const props = defineProps({
  stations: Array,
  // selectStation: Object
});

// 값이 담겨 있는 곳
console.log(props.stations[0].value);

onMounted(() => {
  // 이미 카카오맵이 존재한다면 그것을 띄우고, 아니면 api 공수 받아서 화면에 띄운다.
  if (window.kakao && window.kakao.maps) {
    console.log("이미 지도가 만들어졌다.");
    initMap();

    positions.value = [];
    props.stations.forEach((station) => {
      console.log(station.value);
      let obj = {};
      obj.lati = station.value.lati;
      obj.long = station.value.long;
      obj.latlng = new kakao.maps.LatLng(
        station.value.long,
        station.value.lati
      );
      obj.title = station.value.apt_name;


      mapCenter = new kakao.maps.LatLng(station.value.long, station.value.lati);

      // 드래그가 가능한 마커를 생성합니다.
      rvMarker = new kakao.maps.Marker({
        image: markImage,
        position: mapCenter,
        draggable: true,
        map: map
      });

      positions.value.push(obj);
    });
    loadMarkers();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
      }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

// 처음 맵 시작하는 용도 -----------------------------------------------------
const initMap = () => {
  mapWrapper = document.getElementById('mapWrapper'); //지도를 감싸고 있는 DIV태그
  const container = document.getElementById("map");
  mapCenter = new kakao.maps.LatLng(33.450701, 126.570667);
  const options = {
    // 처음 위도 경도
    center: mapCenter,
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW); //지도 위에 로드뷰 도로 올리기

  rvContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
  rv = new kakao.maps.Roadview(rvContainer); //로드뷰 객체
  rvClient = new kakao.maps.RoadviewClient();

  toggleRoadview(mapCenter);


  // 마커 이미지를 생성합니다.
  markImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png',
    new kakao.maps.Size(26, 46),
    {
      // 스프라이트 이미지를 사용합니다.
      // 스프라이트 이미지 전체의 크기를 지정하고
      spriteSize: new kakao.maps.Size(1666, 168),
      // 사용하고 싶은 영역의 좌상단 좌표를 입력합니다.
      // background-position으로 지정하는 값이며 부호는 반대입니다.
      spriteOrigin: new kakao.maps.Point(705, 114),
      offset: new kakao.maps.Point(13, 46)
    }
  );

  // 드래그가 가능한 마커를 생성합니다.
  rvMarker = new kakao.maps.Marker({
    image: markImage,
    position: mapCenter,
    draggable: true,
    map: map
  });


  //마커에 dragend 이벤트를 할당합니다
  kakao.maps.event.addListener(rvMarker, 'dragend', function (mouseEvent) {
    var position = rvMarker.getPosition(); //현재 마커가 놓인 자리의 좌표
    toggleRoadview(position); //로드뷰를 토글합니다
  });

  //지도에 클릭 이벤트를 할당합니다
  kakao.maps.event.addListener(map, 'click', function (mouseEvent) {

    // 현재 클릭한 부분의 좌표를 리턴 
    var position = mouseEvent.latLng;

    rvMarker.setPosition(position);
    toggleRoadview(position); //로드뷰를 토글합니다
  });



  // 마커를 지도 위에 올리는 변수 
  placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });

  // 장소 검색 객체를 생성합니다
  ps = new kakao.maps.services.Places(map);

  // 지도에 idle 이벤트를 등록합니다
  kakao.maps.event.addListener(map, 'idle', searchPlaces)

  // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다 
  contentNode.className = 'placeinfo_wrap';

  // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
  // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다 
  addEventHandle(contentNode, 'mousedown', kakao.maps.event.preventMap);
  addEventHandle(contentNode, 'touchstart', kakao.maps.event.preventMap);

  // 커스텀 오버레이 컨텐츠를 설정합니다
  placeOverlay.setContent(contentNode);

  // 각 카테고리에 클릭 이벤트를 등록합니다
  addCategoryClickEvent();

};


// 장소 좌표들을 받아서 화면에 띄우는 함수 ------------------------------------
const loadMarkers = () => {
  // 기존 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  // deleteMarkers();

  const imgSize = new kakao.maps.Size(35, 35);
  const markerImage = new kakao.maps.MarkerImage(selectedAptMarker, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      image: markerImage, // 마커의 이미지
    });
    var iwPosition = new kakao.maps.LatLng(position.lati, position.long);

    var iwContent = `<div style="padding:5px; width: 14rem;  ">${position.title} <br> <a class="btn btn-primary text-white" href="https://map.kakao.com/link/to/${position.title},${position.long},${position.lati}" style="color:blue" target="_blank">길찾기</a></div>`;

    var infowindow = new kakao.maps.InfoWindow({
      position: iwPosition,
      content: iwContent,
      removable: true,
    });

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
        const overImage = new kakao.maps.MarkerImage(
          selectedAptMarker,
          imgSize
        );

        marker.setImage(overImage);
      }
    });

    // 마커에 mouseout 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "mouseout", function () {
      // 클릭된 마커가 없고, mouseout된 마커가 클릭된 마커가 아니면
      // 마커의 이미지를 기본 이미지로 변경합니다

      if (!selectedMarker || selectedMarker !== marker) {
        marker.setImage(selectedAptMarker);
      }
    });

    // 마커에 click 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "click", function () {
      console.log("현재 선택된 마커", marker);

      const imgSize = new kakao.maps.Size(35, 35);
      const clickImage = new kakao.maps.MarkerImage(selectedAptMarker, imgSize);

      const nomalImage = new kakao.maps.MarkerImage(selectedAptMarker, imgSize);

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

    markers.value.push(marker); // marker들을 marker 배열에 넣음
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};


// 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
function addEventHandle(target, type, callback) {
  if (target.addEventListener) {
    target.addEventListener(type, callback);
  } else {
    target.attachEvent('on' + type, callback);
  }
}

// 카테고리 검색을 요청하는 함수입니다
function searchPlaces() {
  if (!currCategory) {
    return;
  }

  // 커스텀 오버레이를 숨깁니다 
  placeOverlay.setMap(null);

  // 지도에 표시되고 있는 마커를 제거합니다
  removeMarker();

  ps.categorySearch(currCategory, placesSearchCB, { useMapBounds: true });
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {

    // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
    displayPlaces(data);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요

  } else if (status === kakao.maps.services.Status.ERROR) {
    // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요

  }
}

// 지도에 마커를 표출하는 함수입니다
function displayPlaces(places) {

  // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
  // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
  var order = document.getElementById(currCategory).getAttribute('data-order');



  for (var i = 0; i < places.length; i++) {

    // 마커를 생성하고 지도에 표시합니다
    var marker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

    // 마커와 검색결과 항목을 클릭 했을 때
    // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
    (function (marker, place) {
      kakao.maps.event.addListener(marker, 'click', function () {
        displayPlaceInfo(place);
      });
    })(marker, places[i]);
  }
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, order) {
  var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
    imageSize = new kakao.maps.Size(27, 28),  // 마커 이미지의 크기
    imgOptions = {
      spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
      spriteOrigin: new kakao.maps.Point(46, (order * 36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
      offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
    },
    markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
    marker = new kakao.maps.Marker({
      position: position, // 마커의 위치
      image: markerImage
    });

  marker.setMap(map); // 지도 위에 마커를 표출합니다
  markers.push(marker);  // 배열에 생성된 마커를 추가합니다

  return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
}

// 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
function displayPlaceInfo(place) {
  var content = '<div class="placeinfo">' +
    '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">' + place.place_name + '</a>';

  if (place.road_address_name) {
    content += '    <span title="' + place.road_address_name + '">' + place.road_address_name + '</span>' +
      '  <span class="jibun" title="' + place.address_name + '">(지번 : ' + place.address_name + ')</span>';
  } else {
    content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
  }

  content += '    <span class="tel">' + place.phone + '</span>' +
    '</div>' +
    '<div class="after"></div>';

  contentNode.innerHTML = content;
  placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
  placeOverlay.setMap(map);
}

// 각 카테고리에 클릭 이벤트를 등록합니다
function addCategoryClickEvent() {
  var children = document.getElementById('category').childNodes;

  for (var i = 0; i < children.length; i++) {
    children[i].onclick = onClickCategory;
  }
}

// 카테고리를 클릭했을 때 호출되는 함수입니다
function onClickCategory() {
  var id = this.id,
    className = this.className;

  placeOverlay.setMap(null);

  if (className === 'on') {
    currCategory = '';
    changeCategoryClass();
    removeMarker();
  } else {
    currCategory = id;
    changeCategoryClass(this);
    searchPlaces();
  }
}

// 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
function changeCategoryClass(el) {
  console.log(document.getElementById('category').childElementCount)
  var children = document.getElementById('category').childNodes;

  var i;

  for (i = 0; i < children.length; i++) {
    children[i].className = '';
  }

  if (el) {
    el.className = 'on';
  }
}

//로드뷰 toggle함수
function toggleRoadview(position) {

  //전달받은 좌표(position)에 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄웁니다
  rvClient.getNearestPanoId(position, 50, function (panoId) {
    if (panoId === null) {
      rvContainer.style.display = 'none'; //로드뷰를 넣은 컨테이너를 숨깁니다
      mapWrapper.style.width = '100%';
      map.relayout();
    } else {
      mapWrapper.style.width = '100%';
      map.relayout(); //지도를 감싸고 있는 영역이 변경됨에 따라, 지도를 재배열합니다
      rvContainer.style.display = 'block'; //로드뷰를 넣은 컨테이너를 보이게합니다
      rv.setPanoId(panoId, position); //panoId를 통한 로드뷰 실행
      rv.relayout(); //로드뷰를 감싸고 있는 영역이 변경됨에 따라, 로드뷰를 재배열합니다
    }
  });
}

</script>

<template>
  <div class="map_wrap" style=" width:100%; height: 50%;">
    <div id="mapWrapper" style="width:100%; height: 100%;float:left">
      <div id="map" style="width:100%;height:100%">
        <ul id="category">
          <li id="BK9" data-order="0">
            <span class="category_bg bank"></span>
            은행
          </li>
          <li id="MT1" data-order="1">
            <span class="category_bg mart"></span>
            마트
          </li>
          <li id="PM9" data-order="2">
            <span class="category_bg pharmacy"></span>
            약국
          </li>
          <li id="OL7" data-order="3">
            <span class="category_bg oil"></span>
            주유소
          </li>
          <li id="CE7" data-order="4">
            <span class="category_bg cafe"></span>
            카페
          </li>
          <li id="CS2" data-order="5">
            <span class="category_bg store"></span>
            편의점
          </li>
        </ul>
      </div>
    </div>
  </div>
  <div id="rvWrapper" style="width:100%;height:50%;">
    <div id="roadview" style="width:100%;height:100%"></div> <!-- 로드뷰를 표시할 div 입니다 -->
  </div>
</template>



<style>
#map {
  width: 100%;
  height: 700px;
}

.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
  font-size: 12px;
}

.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}

#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}

#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}

#category li.on {
  background: #eee;
}

#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}

#category li:last-child {
  margin-right: 0;
  border-right: 0;
}

#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}

#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;
}

#category li .bank {
  background-position: -10px 0;
}

#category li .mart {
  background-position: -10px -36px;
}

#category li .pharmacy {
  background-position: -10px -72px;
}

#category li .oil {
  background-position: -10px -108px;
}

#category li .cafe {
  background-position: -10px -144px;
}

#category li .store {
  background-position: -10px -180px;
}

#category li.on .category_bg {
  background-position-x: -46px;
}

.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}

.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}

.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}

.placeinfo_wrap .after {
  content: '';
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}

.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}

.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;
}

.placeinfo .tel {
  color: #0f7833;
}

.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
