<template>
  <div class="map-container">
    <naver-maps
      class="h-100 w-100"
      :mapOptions="mapOptions"
      :initLayers="initLayers"
      @onLoad="onLoadMap($event)"
      @init="toggleEvent()"
      @dragend="toggleEvent()"
      @zoom_changed="toggleEvent()"
    >
      <naver-marker
        v-for="(house, index) in houses"
        :key="index"
        :latitude="parseFloat(house.lat)"
        :longitude="parseFloat(house.lng)"
        @onLoad="onLoadMarker($event)"
      >
        <!-- Html Icon  -->
      </naver-marker>
    </naver-maps>
  </div>
  <button @click="test()">test</button>
  <house-sidebar
    :toggle="toggleConfigurator"
    :class="[
      this.$store.state.showConfig ? 'show' : '',
      this.$store.state.hideConfigButton ? 'd-none' : '',
    ]"
    @address="setCenterByAddress"
  ></house-sidebar>
</template>

<script>
import { ref } from "vue";
import { NaverMaps, NaverMarker } from "vue3-naver-maps";
import HouseSidebar from "@/components/house/HouseSidebar.vue";
import { mapMutations, mapActions } from "vuex";
// import MarkerClustering from "@/vue-naver-map/index";

const houseStore = "houseStore";

export default {
  name: "MapView",
  components: {
    HouseSidebar,
    NaverMaps,
    NaverMarker,
  },
  setup: () => {
    const map = ref();
    const houses = ref();
    const marker = ref();
    const markers = ref();

    const mapOptions = {
      latitude: 37.56663888630603, // 지도 중앙 위도
      longitude: 126.97838310403904, // 지도 중앙 경도
      zoom: 16,
      zoomControl: false,
      zoomControlOptions: { position: "TOP_RIGHT" },
    };
    const initLayers = [
      "BACKGROUND",
      "BACKGROUND_DETAIL",
      "POI_KOREAN",
      "TRANSIT",
      "ENGLISH",
    ];

    const onLoadMap = (mapObject) => {
      map.value = mapObject;
      markers.value = [];
    };
    const onLoadMarker = (markerObject) => {
      marker.value = markerObject;
      markers.value.push(markerObject);
    };

    return {
      map,
      markers,
      houses,
      mapOptions,
      initLayers,
      onLoadMap,
      onLoadMarker,
    };
  },
  methods: {
    ...mapMutations(["toggleConfigurator"]),
    ...mapActions(houseStore, ["getHouseListIfMovedMap"]),

    /**
     * zoom_changed, dragend 이벤트가 발생하면 실행되는 이벤트입니다.
     */
    async toggleEvent() {
      await this.setHousesByBounds();

      this.houses = this.$store.state.houseStore.houses;

      // console.log(MarkerClustering());
    },
    /**
     * 지도의 경계값을 이용하여 현재 지도의  아파트 리스트를 state에 저장합니다.
     */
    async setHousesByBounds() {
      const level = this.map.getZoom();
      const bounds = this.map.getBounds();

      const mapInfo = {
        level: level,
        swLat: bounds._sw._lat,
        swLng: bounds._sw._lng,
        neLat: bounds._ne._lat,
        neLng: bounds._ne._lng,
      };

      await this.getHouseListIfMovedMap(mapInfo);
    },
    /**
     * 주소를 입력받으면 좌표값으로 변환합니다.
     * @param {String} address 지번 주소
     * @return {naver.maps.Point} 변환된 좌표값
     */
    searchAddressToCoordinate(address) {
      window.naver.maps.Service.geocode(
        {
          query: `${address.sido} ${address.gugun} ${address.dong}`,
        },
        (status, response) => {
          if (status === window.naver.maps.Service.Status.ERROR) {
            if (!address) {
              return alert("Geocode Error, Please check address");
            }
            return alert("Geocode Error, address:" + address);
          }

          if (response.v2.meta.totalCount === 0) {
            return alert("No result.");
          }

          var item = response.v2.addresses[0];

          return new window.naver.maps.Point(item.x, item.y);
        }
      );
    },
    /**
     * 주소를 입력받고 해당하는 주소로 지도를 이동합니다.
     * @param {String} address 지번 주소
     */
    setCenterByAddress(address) {
      const point = this.searchAddressToCoordinate(address);

      this.map.setZoom(18);
      this.map.setCenter(point);
      this.toggleEvent();
    },
    clusterMarkers() {},
  },
};
</script>
<style scoped>
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
