<template>
  <div class="card card-background-image py-4 container-fluid w-95">
    <div class="row">
      <div class="col-12">
        <board-list :list-array="articles" />
      </div>
    </div>
  </div>
</template>

<script>
import setTooltip from "@/assets/js/tooltip.js";
import BoardList from "@/components/board/BoardList.vue";
import { listArticle } from "@/api/board.js";

export default {
  name: "BoardView",
  data() {
    return {
      articles: [],
    };
  },
  components: {
    BoardList,
  },
  created() {
    if (this.$route.params.list == null) {
      let param = {
        pg: 1,
        spp: 20,
        key: null,
        word: null,
        type: "board",
      };
      listArticle(
        param,
        (response) => {
          this.articles = response.data;
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      this.articles = this.$route.params.list;
    }
  },
  mounted() {
    setTooltip();
  },
  methods: {
    moveWrite() {
      //this.$router.push({ name: "boardRegister" });
    },
  },
};
</script>
<style>
.card-background-image {
  background-image: url("../../assets/img/street-wallpaper.jpg");
  height: 80vh;
}
</style>
