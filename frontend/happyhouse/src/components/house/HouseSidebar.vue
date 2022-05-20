<template>
  <div class="fixed-plugin">
    <a
      class="px-3 py-2 fixed-plugin-button text-dark position-fixed"
      @click="toggle"
    >
      <i class="py-2 fa fa-building"> </i>
    </a>
    <div class="shadow-lg card blur">
      <div class="pt-3 pb-0 bg-transparent card-header">
        <div class="float-start">
          <h5 class="mt-3 mb-0">아파트 매매 내역</h5>
          <p>아파트 실거래 매매 가격을 확인하세요!</p>
        </div>
        <div class="mt-4 float-end" @click="toggle">
          <button class="p-0 btn btn-link text-dark fixed-plugin-close-button">
            <i class="fa fa-close"></i>
          </button>
        </div>
        <!-- End Toggle Button -->
      </div>
      <hr class="my-2 horizontal dark" />
      <button
        class="btn bg-gradient-info w-100"
        data-toggle="modal"
        data-target="#exampleModal"
        @click="modalToggle()"
      >
        주소 검색
      </button>
    </div>
    <house-modal></house-modal>
  </div>
</template>

<script>
import Modal from "bootstrap/js/src/modal";
import HouseModal from "@/components/house/HouseModal.vue";
import { mapMutations, mapActions } from "vuex";
export default {
  name: "configurator",
  components: {
    HouseModal,
  },
  props: ["toggle"],
  data() {
    return {
      fixedKey: "",
    };
  },
  methods: {
    ...mapMutations(["navbarMinimize", "sidebarType", "navbarFixed"]),
    ...mapActions(["toggleSidebarColor"]),

    sidebarColor(color = "info") {
      document.querySelector("#sidenav-main").setAttribute("data-color", color);
      this.$store.state.mcolor = `card-background-mask-${color}`;
    },

    sidebarType(type) {
      this.toggleSidebarColor(type);
    },

    setNavbarFixed() {
      if (this.$route.name !== "Profile") {
        this.$store.state.isNavFixed = !this.$store.state.isNavFixed;
      }
    },

    sidenavTypeOnResize() {
      let transparent = document.querySelector("#btn-transparent");
      let white = document.querySelector("#btn-white");
      if (window.innerWidth < 1200) {
        transparent.classList.add("disabled");
        white.classList.add("disabled");
      } else {
        transparent.classList.remove("disabled");
        white.classList.remove("disabled");
      }
    },
    modalToggle() {
      new Modal(document.getElementById("exampleModal"), {}).show();
    },
  },
  computed: {
    ifTransparent() {
      return this.$store.state.isTransparent;
    },
    sidenavResponsive() {
      return this.sidenavTypeOnResize;
    },
  },
  beforeMount() {
    this.$store.state.isTransparent = "bg-transparent";
    // Deactivate sidenav type buttons on resize and small screens
    window.addEventListener("resize", this.sidenavTypeOnResize);
    window.addEventListener("load", this.sidenavTypeOnResize);
  },
};
</script>
