<template>
  <div>
    <navbar
      :min-nav="navbarMinimize"
      :toggle="toggleConfigurator"
      :class="isNavFixed ? navbarFixed : ''"
    />
  </div>
  <div class="dashboard-container">
    <div class="row mb-5">
      <div class="col-sm-12">
        <div
          id="carouselExampleIndicators"
          class="carousel slide carousel-fade"
          data-bs-ride="carousel"
        >
          <div class="carousel-indicators">
            <button
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="0"
              class="active"
              aria-current="true"
              aria-label="Slide 1"
            ></button>
            <button
              type="button"
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="1"
              aria-label="Slide 2"
            ></button>
          </div>
          <div class="carousel-inner">
            <div class="carousel-item active">
              <div>
                <div class="pt-10 position-absolute start-50 translate-middle">
                  <div class="pt-10">
                    <div class="pt-10">
                      <img
                        class="pt-10 cursor-pointer"
                        src="../assets/img/dashboard-title.png"
                        @click="routeToMap"
                      />
                    </div>
                  </div>
                </div>
                <img
                  src="../assets/img/dashboard_1.jpg"
                  class="d-block"
                  alt="..."
                />
              </div>
            </div>
            <div class="carousel-item">
              <div class="pt-10 position-absolute start-50 translate-middle">
                <div class="pt-10">
                  <div class="pt-10">
                    <img
                      class="pt-10 cursor-pointer"
                      src="../assets/img/dashboard-title.png"
                      @click="routeToMap"
                    />
                  </div>
                </div>
              </div>
              <img
                src="../assets/img/dashboard_2.jpg"
                class="d-block"
                alt="..."
              />
            </div>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="prev"
          >
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="next"
          >
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
    </div>
  </div>
  <app-footer class="py-3 bg-white border-radius-lg" />
</template>

<script>
import AppFooter from "@/examples/Footer.vue";
import Navbar from "@/examples/Navbars/Navbar.vue";
import setTooltip from "@/assets/js/tooltip.js";

import * as Bootstrap from "bootstrap";

import { mapMutations, mapState } from "vuex";

export default {
  name: "VrInfo",
  components: {
    AppFooter,
    Navbar,
  },
  computed: {
    ...mapState(["isTransparent", "isNavFixed", "navbarFixed", "mcolor"]),
  },
  mounted() {
    var myCarousel = document.querySelector("#myCarousel");
    new Bootstrap.Carousel(myCarousel);
    setTooltip(this.$store.state.bootstrap);
  },
  beforeMount() {
    this.$store.state.showNavbar = false;
    this.$store.state.showSidenav = false;
    this.$store.state.showFooter = false;
    this.$store.state.isTransparent = "bg-white";
  },
  beforeUnmount() {
    this.$store.state.showNavbar = true;
    this.$store.state.showSidenav = true;
    this.$store.state.showFooter = true;

    if (this.$store.state.isPinned === false) {
      const sidenav_show = document.querySelector(".g-sidenav-show");
      sidenav_show.classList.remove("g-sidenav-hidden");
      sidenav_show.classList.add("g-sidenav-pinned");
      this.$store.state.isPinned = true;
    }
    this.$store.state.isTransparent = "bg-transparent";
  },
  methods: {
    ...mapMutations(["navbarMinimize", "toggleConfigurator"]),
    routeToMap() {
      this.$router.push({ name: "Map" });
    },
  },
};
</script>
<style scoped>
.cursor-pointer {
  cursor: pointer;
}
.dashboard-container {
  height: calc(100vh - 135px);
  overflow: hidden;
}
</style>
