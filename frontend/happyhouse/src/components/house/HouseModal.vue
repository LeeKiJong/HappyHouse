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
            <select id="dong" class="form-select w-25" @change="bindDong">
              <option disabled selected>동</option>
              <option v-for="dong in dongs" :value="dong.value" :key="dong">
                {{ dong.text }}
              </option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-info"
            data-bs-dismiss="modal"
            @click="searchAptDong()"
          >
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
    this.sidoList();
  },
  unmounted() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseListByGugun",
      "getHouseListByDong",
      "getHouseListByAptName",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    /**
     * 시/도 리스트 가져오기
     */
    sidoList() {
      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.getSido();
    },
    /**
     * 구/군 리스트 가져오기
     */
    gugunList() {
      this.sidoCode = document.querySelector("#sido").value;
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    /**
     *  동 리스트 가져오기
     */
    dongList() {
      this.gugunCode = document.querySelector("#gugun").value;
      this.CLEAR_DONG_LIST();
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    /**
     * 구/군 정보로 아파트 리스트 가져오기
     */
    searchAptGugun() {
      if (this.gugunCode) this.getHouseListByGugun(this.gugunCode);
    },
    /**
     * 동 정보로 아파트 리스트 가져오기
     */
    searchAptDong() {
      const sido = document.querySelector("#sido");
      const gugun = document.querySelector("#gugun");
      const dong = document.querySelector("#dong");

      const address = {
        sido: sido.options[sido.selectedIndex].text,
        gugun: gugun.options[gugun.selectedIndex].text,
        dong: dong.options[dong.selectedIndex].text,
      };

      this.dongCode = document.querySelector("#dong").value;

      if (this.dongCode) {
        this.getHouseListByDong(this.dongCode);
        this.$emit("address", address);
      }
    },
    sendKeyword() {
      if (this.aptName) this.getHouseListByAptName(this.aptName);
    },
  },
};
</script>

<style></style>
