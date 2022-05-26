import * as house from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    houses: [],
    house: null,
    deals: [],
    dong: "",
    deals_Info: [],
    mouseover: "",
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dong });
      });
    },
    SET_DONG: (state, dong) => {
      state.dong = dong;
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_DEAL_LIST: (state, deals) => {
      state.deals = deals;
    },
    SET_DEALS_AVG: (state, deals) => {
      state.deals_Info = deals;
    },
    MOUSE_OVER: (state, aptCode) => {
      state.mouseover = aptCode;
    },
  },

  actions: {
    async mouseoverEvent({ commit }, aptCode) {
      commit("MOUSE_OVER", aptCode);
    },
    async getHouseDeal({ commit }, aptCode) {
      const params = {
        aptCode: aptCode,
      };
      await house.houseDeal(
        params,
        ({ data }) => {
          commit("SET_DEAL_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getSido({ commit }) {
      await house.sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getGugun({ commit }, sidoCode) {
      const params = {
        sido: sidoCode,
      };
      await house.gugunList(
        params,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getDong({ commit }, gugunCode) {
      const params = {
        gugun: gugunCode,
      };
      await house.dongList(
        params,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getAptDealInDate({ commit }, date) {
      await house.houseDealInDate(
        date,
        ({ data }) => {
          commit("SET_DEAL_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getHouseListByGugun({ commit, state }, gugunCode) {
      const params = {
        gugun: gugunCode,
      };

      await house.houseListByGugun(
        params,
        (response) => {
          state.gugun = gugunCode;
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getHouseListByDong({ commit, state }, dongCode) {
      const params = {
        dong: dongCode,
      };
      await house.houseListByDong(
        params,
        (response) => {
          state.dong = dongCode;
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getHouseListByAptName({ commit, state }, aptName) {
      let gugunTemp = state.gugun;
      let dongTemp = state.dong;
      if (gugunTemp == null) {
        gugunTemp = "";
      }
      if (dongTemp == null) {
        dongTemp = "";
      }
      const params = {
        aptName: aptName,
        gugun: gugunTemp,
        dong: dongTemp,
      };
      await house.houseListByAptName(
        params,
        (response) => {
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getHouseListIfMovedMap({ commit }, mapInfo) {
      await house.houseListIfMovedMap(
        mapInfo,
        (response) => {
          commit("SET_HOUSE_LIST", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getDealsAvg({ commit }, aptCode) {
      await house.getAptAvg(
        aptCode,
        (response) => {
          console.log(response);
          commit("SET_DEALS_AVG", response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default houseStore;
