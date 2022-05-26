<template>
  <div
    style="border-radius: 15px"
    onmouseout="this.style.backgroundColor=''"
    onmouseover="this.style.background='rgba(211, 211, 211, 0.3)';"
    class="mb-3 timeline-block cursor-pointer"
    :class="darkMode"
  >
    <span class="timeline-step" :class="darkMode ? 'bg-dark' : ''">
      <i class="ni text-gradient" :class="`ni-${icon} text-${color}`"></i>
    </span>
    <div
      class="timeline-content"
      @mouseover="mouseOver(aptcode)"
      @mouseout="mouseOut"
    >
      <h6
        class="mb-0 text-sm font-weight-bold"
        :class="darkMode ? 'text-white' : 'text-dark'"
      >
        {{ title }}
      </h6>
      <p class="mt-1 mb-0 text-xs text-secondary font-weight-bold">
        {{ dateTime }}
      </p>
      <p v-if="description" class="mt-3 mb-2 text-sm">
        {{ description }}
      </p>
      <span
        v-for="(badge, index) of badges"
        :key="index"
        class="badge badge-sm me-1"
        :class="`bg-gradient-${color}`"
      >
        {{ badge }}
      </span>
    </div>
  </div>
</template>
<script>
import { mapState, mapActions } from "vuex";
const houseStore = "houseStore";
export default {
  name: "HouseListItem",
  props: {
    color: {
      type: String,
      default: "",
    },
    icon: {
      type: String,
      default: "",
    },
    title: {
      type: String,
      default: "",
    },
    aptcode: {
      type: String,
      default: "",
    },
    dateTime: {
      type: String,
      default: "",
    },
    description: {
      type: String,
      default: "",
    },
    badges: {
      type: Array,
      default: () => [],
    },
    darkMode: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    ...mapActions(houseStore, ["mouseoverEvent"]),
    mouseOver: function (aptCode) {
      console.log(aptCode);
      this.mouseoverEvent(aptCode);
    },
    mouseOut: function () {
      this.mouseoverEvent(null);
    },
  },
  computed: {
    ...mapState(houseStore, ["mouseover"]),
  },
};
</script>

<style scoped>
.cursor-pointer {
  cursor: pointer;
}
</style>
