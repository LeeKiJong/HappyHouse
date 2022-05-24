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
        @click="modalToggle('exampleModal')"
      >
        주소 검색
      </button>
      <div v-if="display.list">
        <div class="house-list-card">
          <house-list
            title="검색된 아파트 목록"
            :description="houses.length + '개의 아파트가 검색되었습니다.'"
          >
            <house-list-item
              v-for="(house, index) in houses"
              :key="index"
              color="info"
              icon="square-pin"
              :title="house.apartmentName"
              :date-time="house.roadName + ' ' + house.bonbun"
              @click="onClickDetail(house)"
            />
          </house-list>
        </div>
        <vsud-input
          id="aptName"
          type="text"
          placeholder="아파트 이름으로 검색하세요!"
          name="aptName"
          @keyup="searchByAptName"
        />
      </div>
      <div v-if="display.detail">
        <div class="p-3 pb-0 card-header bg-transparent">
          <div class="row">
            <div class="col-md-1 cursor-pointer" @click="setDisplayList()">
              <i class="fa fa-arrow-left" />
            </div>
            <div class="col-md-10">
              <h6 class="text-center">아파트 세부 정보</h6>
            </div>
            <div class="col-md-1">
              <i
                class="far fa-calendar-alt cursor-pointer"
                @click="modalToggle('houseModalCalendar')"
              />
            </div>
          </div>
          <div class="text-center">
            <small>{{
              this.$store.state.houseStore.house.apartmentName
            }}</small>
          </div>
        </div>
        <br />
        <house-detail
          class="house-detail-card"
          :horizontal-break="false"
          :deals="deals"
        />
      </div>
    </div>
    <hr class="my-2 horizontal dark" />
    <house-modal @address="emit"></house-modal>
    <house-modal-calendar></house-modal-calendar>
  </div>
</template>

<script>
import Modal from "bootstrap/js/src/modal";
import HouseModal from "@/components/house/HouseModal.vue";
import HouseModalCalendar from "@/components/house/HouseModalCalendar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseListItem from "@/components/house/HouseListItem.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import VsudInput from "@/components/vsud/VsudInput.vue";

import { mapMutations, mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseSidebar",
  components: {
    HouseModal,
    HouseList,
    HouseListItem,
    HouseDetail,
    VsudInput,
    HouseModalCalendar,
  },
  props: ["toggle"],
  data() {
    return {
      houses: [],
      deals: [],
      display: {
        list: true,
        detail: false,
      },
    };
  },
  methods: {
    ...mapMutations(houseStore, ["SET_DETAIL_HOUSE"]),
    ...mapActions(houseStore, ["getHouseDeal", "getHouseListByAptName"]),
    modalToggle(id) {
      new Modal(document.getElementById(id), {}).show();
    },
    async onClickDetail(house) {
      this.setDetailHouse(house);
      await this.getHouseDeal(house.aptCode);
      this.setDisplayDetail();
    },
    setDetailHouse(house) {
      this.SET_DETAIL_HOUSE(house);
    },
    setDisplayList() {
      this.display.list = true;
      this.display.detail = false;
    },
    setDisplayDetail() {
      this.display.list = false;
      this.display.detail = true;
    },
    emit(address) {
      this.$emit("address", address);
    },
    searchByAptName() {
      this.getHouseListByAptName(document.querySelector("#aptName").value);
    },
  },
  computed: {
    changeHouses() {
      return this.$store.state.houseStore.houses;
    },
    changeDeals() {
      return this.$store.state.houseStore.deals;
    },
  },
  watch: {
    changeHouses(value) {
      this.houses = value;
      this.setDisplayList();
    },
    changeDeals(value) {
      this.deals = value;
    },
  },
};
</script>

<style scoped>
@import "https://cdn.jsdelivr.net/npm/animate.css@3.5.1";

.cursor-pointer {
  cursor: pointer;
}

.house-list-card {
  overflow: auto;
  height: calc(90vh - 150px);
}
.house-detail-card {
  overflow: auto;
  height: calc(80vh - 150px);
}
</style>
