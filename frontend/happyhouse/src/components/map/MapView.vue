<template>
  <div class="container-fluid map-container">
    <div id="map" class="h-100 w-100" />
  </div>
  <house-sidebar
    :toggle="toggleConfigurator"
    :class="[this.$store.state.showConfig ? 'show' : '']"
    @address="setCenterByAddress"
    @house="setCenterByHouseLatLng"
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
      clusterer: [],
      customOverlays: [],
      houses: [],
      markers: [],
      clusterLevel: 6,
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
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=786ea11e71ca9d8b2f94bb6cbd905330&libraries=services,clusterer";
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapMutations(["toggleConfigurator"]),
    ...mapMutations(houseStore, ["SET_DONG"]),
    ...mapActions(houseStore, ["getHouseListIfMovedMap"]),
    // 지도 초기화
    async initMap() {
      const map = this.createMap();

      this.map = map;

      this.createClusterer(map);

      // boundary 정보를 가져옵니다.
      const boundary = this.getBoundary(map);

      await this.getHouseListIfMovedMap(boundary);

      this.houses = this.$store.state.houseStore.houses;

      // this.createMarkers(map);

      if (map.getLevel() < this.clusterLevel) {
        this.createCustomOverlays(map);
      }

      //지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
      this.addEventListener("zoom_changed", map);
      // 지도가  마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
      this.addEventListener("dragend", map);
    },
    createMap() {
      var mapContainer = document.getElementById("map"); // 지도를 표시할 div

      var mapOption = {
        center: new kakao.maps.LatLng(36.1007890373718, 128.430402123994), // 지도의 중심좌표
        level: 4, // 지도의 확대 레벨
      };

      return new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    },
    addEventListener(event, map) {
      kakao.maps.event.addListener(map, event, () => {
        this.toggleEvent(map);
      });
    },
    /**
     * zoom_changed, dragend 이벤트가 발생하면 실행되는 이벤트입니다.
     */
    async toggleEvent(map) {
      // boundary 정보를 가져옵니다.
      const boundary = this.getBoundary(map);

      await this.getHouseListIfMovedMap(boundary);

      this.houses = this.$store.state.houseStore.houses;

      this.removeMarkers();

      this.removeCustomOverlays();

      this.createMarkers(map);

      if (map.getLevel() < this.clusterLevel) {
        this.createCustomOverlays(map);
      }

      this.setEventIfCompletedCluster();

      this.clusterer.addMarkers(this.markers, true);

      const address = await this.searchCoordToDongCode(map);

      this.SET_DONG(address.code);
    },

    // 지도 경계선의 위도 경도 가져오기
    getBoundary(map) {
      let bounds = map.getBounds();

      const boundary = {
        level: map.getLevel(),
        swLat: bounds.getSouthWest().getLat(),
        swLng: bounds.getSouthWest().getLng(),
        neLat: bounds.getNorthEast().getLat(),
        neLng: bounds.getNorthEast().getLng(),
      };

      return boundary;
    },

    createClusterer(map) {
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: this.clusterLevel, // 클러스터 할 최소 지도 레벨
        styles: [
          {
            height: "100px",
            width: "100px",
            image:
              "url(https://cdn-icons-png.flaticon.com/512/2598/2598818.png)",
            textAlign: "center",
          },
        ],
      });
    },
    createCustomOverlays(map) {
      this.houses.forEach((element) => {
        var customOverlay = new kakao.maps.CustomOverlay({
          map: map,
          position: new kakao.maps.LatLng(element.lat, element.lng),
          content: this.getCustomOverlayContent(element),
          xAnchor: 0.5,
          yAnchor: 0.9,
        });
        this.customOverlays.push(customOverlay);
      });
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
    createMarkers() {
      // 마커를 생성합니다
      this.houses.forEach((element) => {
        var marker = new kakao.maps.Marker({
          // map: map.getLevel() < this.clusterLevel ? map : null,
          position: new kakao.maps.LatLng(element.lat, element.lng), // 마커를 표시할 위치
          title: element.avg, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: this.getMarkerImage(), // 마커 이미지
        });
        this.markers.push(marker);
      });
    },
    /**
     * 주소 state 변경 감지 시 마커 삭제
     */
    removeMarkers() {
      this.markers.forEach((element) => {
        element.setMap(null);
      });
      this.markers = [];
    },
    /**
     * 클러스터링이 완료됐을 때 발생하는 clustered 이벤트 등록
     */
    async setEventIfCompletedCluster() {
      new Promise(() => {
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
              cluster.getClusterMarker().setContent(`
              <div class="pin-wrap">
                <img class="cluster-pin-image" src="https://cdn-icons-png.flaticon.com/512/2598/2598818.png"/>
                <span class="text-sm text-center text-light font-weight-bold cluster-pin-text">
                ${parseFloat((sum / cluster.getSize()).toFixed(1))}억</span>
              </div>
                `);
            });
          }
        );
      });
    },
    /**
     * 마커로 표시할 이미지를 반환하는 메서드
     * return 마커 이미지
     */
    getMarkerImage() {
      // 마커 이미지의 이미지 주소입니다
      var imageSrc = require(`@/assets/img/pin.png`);
      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new kakao.maps.Size(1, 1);
      // 마커 이미지를 리턴합니다
      return new kakao.maps.MarkerImage(imageSrc, imageSize);
    },
    /**
     * 커스텀 오버레이에 표출될 내용(HTML 문자열이나 document element 가능) 반환
     * return String
     */
    getCustomOverlayContent(element) {
      return `
      <div class="pin-wrap">
        <img class="pin-image" src="https://cdn-icons-png.flaticon.com/512/2598/2598818.png"/>
        <span class="text-sm text-center text-light font-weight-bold pin-text">${element.avg}억</span>
      </div>
      `;
    },
    getClusterContent(element) {
      return `
      <div class="pin-wrap">
        <img class="cluster-pin-image" src="https://cdn-icons-png.flaticon.com/512/2598/2598818.png"/>
        <span class="text-sm text-center text-light font-weight-bold cluster-pin-text">${element}억</span>
      </div>
      `;
    },
    /**
     * 주소를 입력받으면 좌표값으로 변환합니다.
     * @param {String} address 지번 주소
     * @return {naver.maps.Point} 변환된 좌표값
     */
    async searchAddressToCoordinate(address) {
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      // 주소로 좌표를 검색합니다

      return new Promise((resolve, reject) => {
        geocoder.addressSearch(
          `${address.sido} ${address.gugun} ${address.dong}`,
          function (result, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {
              resolve(new kakao.maps.LatLng(result[0].y, result[0].x));
            } else {
              reject(status);
            }
          }
        );
      });
    },
    async searchCoordToDongCode(map) {
      const center = map.getCenter();

      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      return new Promise((resolve, reject) => {
        geocoder.coord2RegionCode(
          center.getLng(),
          center.getLat(),
          function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
              resolve(result[0]);
            } else {
              reject(status);
            }
          }
        );
      });
    },
    /**
     * 주소를 입력받고 해당하는 주소로 지도를 이동합니다.
     * @param {String} address 지번 주소
     */
    async setCenterByAddress(address) {
      const point = await this.searchAddressToCoordinate(address);

      this.map.setLevel(2);
      this.map.setCenter(point);
      this.toggleEvent(this.map);
    },
    setCenterByHouseLatLng(house) {
      this.map.setCenter(new kakao.maps.LatLng(house.lat, house.lng));
      this.map.setLevel(1);
      console.log(1);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.pin-wrap {
  position: relative;
}
.pin-image {
  height: 40px;
  width: 50px;
  vertical-align: middle;
}
.cluster-pin-image {
  height: 60px;
  width: 60px;
  vertical-align: middle;
}
.pin-text {
  position: absolute;
  top: -20%;
  left: 50%;
  width: 100%;
  transform: translate(-50%, 50%);
}
.cluster-pin-text {
  position: absolute;
  top: 5%;
  left: 50%;
  width: 100%;
  transform: translate(-50%, 50%);
}

.map-container {
  height: 80vh;
}
</style>
