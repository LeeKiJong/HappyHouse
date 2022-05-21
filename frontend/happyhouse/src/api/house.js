import { apiInstance } from "@/api/index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}
function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}
function dongList(params, success, fail) {
  api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}
// function houseList(params, success, fail) {
//   house.get(``, { params: params }).then(success).catch(fail);
// }
function houseListByGugun(params, success, fail) {
  api.get(`/map/apt/gugun`, { params: params }).then(success).catch(fail);
}

function houseListByDong(params, success, fail) {
  api.get(`/map/apt/dong`, { params: params }).then(success).catch(fail);
}

function houseDeal(params, success, fail) {
  api.get(`/map/apt/deal`, { params: params }).then(success).catch(fail);
}

function houseListByAptName(params, success, fail) {
  api.get(`/map/apt/aptName`, { params: params }).then(success).catch(fail);
}

export { sidoList, gugunList, dongList };
export { houseListByAptName, houseListByDong, houseListByGugun, houseDeal };
