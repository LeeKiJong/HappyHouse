<template>
  <div class="map-container">
    <div id="map" class="h-100 w-100" />
  </div>
  <house-sidebar
    :toggle="toggleConfigurator"
    :class="[
      this.$store.state.showConfig ? 'show' : '',
      this.$store.state.hideConfigButton ? 'd-none' : '',
    ]"
  ></house-sidebar>
</template>

<script>
import HouseSidebar from "@/components/house/HouseSidebar.vue";
import { mapMutations, mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "MapView",
  components: {
    HouseSidebar,
  },
  data() {
    return {
      map: [],
      mapInfo: {
        level: "",
        swLat: "",
        swLng: "",
        neLat: "",
        neLng: "",
      },
      tempLevel: 3,
      clusterer: [],
      customOverlays: [],
      houses: [],
      mapContainer: [],
      markers: [],
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=786ea11e71ca9d8b2f94bb6cbd905330&libraries=clusterer";
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapMutations(["toggleConfigurator"]),
    ...mapActions(houseStore, ["getHouseListIfMovedMap"]),
    // 지도 초기화
    initMap() {
      var mapContainer = document.getElementById("map"); // 지도를 표시할 div

      var mapOption = {
        center: new kakao.maps.LatLng(36.1007890373718, 128.430402123994), // 지도의 중심좌표
        level: 4, // 지도의 확대 레벨
      };

      this.map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

      this.createClustrer();

      //지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "zoom_changed", () => {
        // 지도의 현재 레벨을 얻어옵니다
        var level = this.map.getLevel();
        if (Math.abs(this.tempLevel - level) == 1) {
          this.getLatlng(level);
          if (this.mapInfo) this.getHouseListIfMovedMap(this.mapInfo);
        }
      });
      // 지도가  마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "dragend", () => {
        // 지도의 현재 레벨을 얻어옵니다
        var level = this.map.getLevel();

        this.getLatlng(level);

        if (this.mapInfo) this.getHouseListIfMovedMap(this.mapInfo);
      });
    },
    // 지도 경계선의 위도 경도 가져오기
    getLatlng(level) {
      this.tempLevel = level;

      let bounds = this.map.getBounds();
      this.mapInfo.level = level;
      this.mapInfo.swLat = bounds.getSouthWest().getLat();
      this.mapInfo.swLng = bounds.getSouthWest().getLng();
      this.mapInfo.neLat = bounds.getNorthEast().getLat();
      this.mapInfo.neLng = bounds.getNorthEast().getLng();
    },
    createClustrer() {
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: this.map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 6, // 클러스터 할 최소 지도 레벨
      });
    },
    createCustomOverlay(element) {
      var customOverlay = new kakao.maps.CustomOverlay({
        position: new kakao.maps.LatLng(element.lat, element.lng),
        content: this.getCustomOverlayContent(element),
        yAnchor: 0.2,
      });
      customOverlay.setMap(this.map);
      this.customOverlays.push(customOverlay);
    },
    removeCustomOverlays() {
      for (let i = 0; i < this.customOverlays.length; i++) {
        this.customOverlays[i].setMap(null);
      }
      this.customOverlays = [];
    },
    /**
     * 마커 생성
     */
    createMarker(element) {
      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(element.lat, element.lng), // 마커를 표시할 위치
        title: element.apartmentName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: this.getMarkerImage(), // 마커 이미지
      });
      this.markers.push(marker);
    },
    /**
     * 주소 state 변경 감지 시 마커 삭제
     */
    removeMarkers() {
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    /**
     * 특정 위도 경도로 지도 이동
     */
    setCenter() {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new kakao.maps.LatLng(
        this.houses[0].lat,
        this.houses[0].lng
      );

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
    },
    /**
     * 클러스터링이 완료됐을 때 발생하는 clustered 이벤트 등록
     */
    setEventIfCompletedCluster() {
      kakao.maps.event.addListener(
        this.clusterer,
        "clustered",
        function (clusters) {
          clusters.forEach((cluster) => {
            // 클러스터러에 포함된 마커 배열을 가져옵니다.
            var sum = 0;
            cluster.getMarkers().forEach((marker) => {
              sum += parseInt(marker.Gb);
            });
            // 클러스터러 오버레이의 콘텐츠 내부 텍스트 변경해주세요.
            cluster.getClusterMarker().getContent().innerText =
              parseFloat((sum / cluster.getSize()).toFixed(1)) + "억";
          });
        }
      );
    },
    /**
     * 마커로 표시할 이미지를 반환하는 메서드
     * return 마커 이미지
     */
    getMarkerImage() {
      // 마커 이미지의 이미지 주소입니다
      var imageSrc = require(`@/assets/img/ssafy.png`);
      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new kakao.maps.Size(24, 35);
      // 마커 이미지를 리턴합니다
      return new kakao.maps.MarkerImage(imageSrc, imageSize);
    },
    /**
     * 커스텀 오버레이에 표출될 내용(HTML 문자열이나 document element 가능) 반환
     * return String
     */
    getCustomOverlayContent(element) {
      return `
      <div class="customoverlay">
        <a href="https://map.kakao.com/link/map/11394059" target="_blank">
          <span class="title">
            ${element.avg}억
          </span>
        </a>
      </div>
      `;
    },
  },
  computed: {
    changeHouses() {
      return this.$store.state.houseStore.houses;
    },
  },
  watch: {
    changeHouses(value) {
      this.houses = value;

      if (this.clusterer.getMarkers().length > 0) {
        this.clusterer.clear();
      }

      this.createClustrer();

      this.removeMarkers();

      this.removeCustomOverlays();

      this.houses.forEach((element) => {
        this.createMarker(element);

        if (this.tempLevel < 6) {
          this.createCustomOverlay(element);
        }
      });

      this.setEventIfCompletedCluster();

      console.log(this.clusterer);
      console.log(this.markers);
      console.log(this.map);
      // 클러스터러에 마커들을 추가합니다
      this.clusterer.addMarkers(this.markers);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 400px;
  height: 400px;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}

.map-container {
  width: 85vw;
  height: 80vh;
}
</style>
