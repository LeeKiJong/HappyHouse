import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function sidoList(success, fail) {
  await api.get(`/map/sido`).then(success).catch(fail);
}

async function gugunList(params, success, fail) {
  await api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

async function dongList(params, success, fail) {
  await api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}

// function houseList(params, success, fail) {
//   house.get(``, { params: params }).then(success).catch(fail);
// }

async function houseListByGugun(params, success, fail) {
  await api.get(`/map/apt/gugun`, { params: params }).then(success).catch(fail);
}

async function houseListByDong(params, success, fail) {
  await api.get(`/map/apt/dong`, { params: params }).then(success).catch(fail);
}

async function houseDeal(params, success, fail) {
  await api.get(`/map/apt/deal`, { params: params }).then(success).catch(fail);
}

async function houseDealInDate(params, success, fail) {
  await api
    .get(`/map/apt/dealInDate`, { params: params })
    .then(success)
    .catch(fail);
}

async function houseListByAptName(params, success, fail) {
  await api
    .get(`/map/apt/aptName`, { params: params })
    .then(success)
    .catch(fail);
}

async function houseListIfMovedMap(params, success, fail) {
  await api
    .post(`/map/apt/map`, JSON.stringify(params))
    .then(success)
    .catch(fail);
}

export { sidoList, gugunList, dongList, houseListIfMovedMap, houseDealInDate };
export { houseListByAptName, houseListByDong, houseListByGugun, houseDeal };
