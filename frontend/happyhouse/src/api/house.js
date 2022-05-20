import { apiInstance, houseInstance } from "@/api/index.js";

const api = apiInstance();
const house = houseInstance();

export default {
  methods: {
    sidoList(success, fail) {
      api.get(`/map/sido`).then(success).catch(fail);
    },
    gugunList(params, success, fail) {
      api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
    },
    dongList(params, success, fail) {
      api.get(`/map/dong`, { params: params }).then(success).catch(fail);
    },
    houseList(params, success, fail) {
      house.get(``, { params: params }).then(success).catch(fail);
    },
  },
};
