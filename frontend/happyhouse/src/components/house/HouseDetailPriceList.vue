<template>
  <ul class="list-group">
    <li
      class="pb-0 border-0 list-group-item justify-content-between ps-0 border-radius-lg bg-transparent"
    >
      <div class="d-flex">
        <div class="d-flex align-items-center">
          <button
            class="p-3 mb-0 btn btn-icon-only btn-rounded btn-outline-warning me-3 btn-sm d-flex align-items-center justify-content-center"
          >
            <i class="fa fa-krw"></i>
          </button>
          <div class="d-flex flex-column">
            <h6 class="mb-1 text-sm text-dark">{{ deal.area }}평</h6>
            <span class="text-xs"
              >{{ deal.dealYear }}년 {{ deal.dealMonth }}월 거래
            </span>
          </div>
        </div>
        <div
          class="text-lg d-flex align-items-center text-gradient font-weight-bold ms-auto text-warning"
        >
          {{ deal.dealAmount }}만원
        </div>
      </div>
      <hr
        v-if="horizontalBreak && index !== item.length - 1"
        class="mt-3 mb-2 horizontal dark"
      />
      <div v-else class="mt-3 mb-2" />
    </li>
  </ul>
</template>
<script>
export default {
  name: "HouseDetailPriceList",
  props: {
    title: {
      type: String,
      default: "",
    },
    horizontalBreak: {
      type: Boolean,
      default: true,
    },
    deal: Object,
  },
};
</script>
