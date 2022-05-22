<template>
  <div>
    <div class="p-3 card-body">
      <h6 class="text-uppercase text-body text-xs font-weight-bolder mb-3 pt-2">
        아파트 매매
      </h6>
      <house-detail-price-list
        v-for="(deal, index) in deals"
        :key="index"
        :deal="deal"
        :horizontal-break="horizontalBreak"
      />
      <slot />
    </div>
  </div>
</template>

<script>
import HouseDetailPriceList from "@/components/house/HouseDetailPriceList.vue";
export default {
  name: "HouseDetail",
  data() {
    return {
      house: "",
    };
  },
  components: {
    HouseDetailPriceList,
  },
  props: {
    horizontalBreak: {
      type: Boolean,
      default: true,
    },
    deals: Object,
  },
};
</script>
