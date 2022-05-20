<template>
  <div
    class="modal fade"
    id="exampleModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">주소 검색</h5>
        </div>
        <div class="modal-body">
          <div class="row justify-content-center">
            <select id="sido" class="form-select w-25" @change="gugunList">
              <option disabled selected>시/도</option>
              <option v-for="sido in sidos" :value="sido.value" :key="sido">
                {{ sido.text }}
              </option>
            </select>
            &nbsp;&nbsp;
            <select id="gugun" class="form-select w-25" @change="dongList">
              <option disabled selected>구/군</option>
              <option v-for="gugun in guguns" :value="gugun.value" :key="gugun">
                {{ gugun.text }}
              </option>
            </select>
            &nbsp;&nbsp;
            <select id="dong" class="form-select w-25">
              <option disabled selected>동</option>
              <option v-for="dong in dongs" :value="dong.value" :key="dong">
                {{ dong.text }}
              </option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-info" @click="confirm()">
            검색
          </button>
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            닫기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  data() {
    return {
      sidoCode: "",
      gugunCode: "",
      dongCode: "",
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList_gugun",
      "getHouseList_dong",
      "getHouseList_aptName",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    gugunList() {
      this.sidoCode = document.querySelector("#sido").value();
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      this.gugunCode = document.querySelector("#sido").value();
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt_gugun() {
      if (this.gugunCode) this.getHouseList_gugun(this.gugunCode);
    },
    searchApt_dong() {
      console.log("searchApt_dong" + this.dongCode);
      if (this.dongCode) this.getHouseList_dong(this.dongCode);
    },
    sendKeyword() {
      console.log("aptName" + this.aptName);
      if (this.aptName) this.getHouseList_aptName(this.aptName);
    },
  },
};
</script>

<style></style>
