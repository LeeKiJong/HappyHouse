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
import { mapMutations } from "vuex";

// const houseStore = "houseStore";

export default {
  name: "MapView",
  components: {
    HouseSidebar,
  },
  data() {
    return {
      houses: [],
      map: null,
      mapContainer: null,
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
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=786ea11e71ca9d8b2f94bb6cbd905330";
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapMutations(["toggleConfigurator"]),
    // 지도 초기화
    initMap() {
      this.mapContainer = document.getElementById("map"); // 지도를 표시할 div

      var mapOption = {
        center: new kakao.maps.LatLng(36.1007890373718, 128.430402123994), // 지도의 중심좌표
        level: 4, // 지도의 확대 레벨
      };

      this.map = new kakao.maps.Map(this.mapContainer, mapOption); // 지도를 생성합니다
    },
    // 주소 state 변경 감지 시 마커 생성
    createMarkers() {
      // 마커 이미지의 이미지 주소입니다
      // var imageSrc =
      //   "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      this.houses.forEach((element) => {
        // 마커 이미지의 이미지 크기 입니다
        // var imageSize = new kakao.maps.Size(24, 35);

        // // 마커 이미지를 생성합니다
        // var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: new kakao.maps.LatLng(element.lat, element.lng), // 마커를 표시할 위치
          title: element.apartmentName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          // image: markerImage, // 마커 이미지
        });

        this.markers.push(marker);
      });
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(this.map);
      }
    },
    // 주소 state 변경 감지 시 마커 삭제
    removeMarkers() {
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    setCenter() {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new kakao.maps.LatLng(
        this.houses[0].lat,
        this.houses[0].lng
      );

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
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

      this.removeMarkers();

      this.createMarkers();

      this.setCenter();
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
