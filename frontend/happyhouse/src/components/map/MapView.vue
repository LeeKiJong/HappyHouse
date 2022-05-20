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

export default {
  name: "KakaoMap",
  components: {
    HouseSidebar,
  },
  data() {},
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
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
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
