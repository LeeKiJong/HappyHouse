<template>
  <div class="container top-0 position-sticky z-index-sticky">
    <div class="row">
      <div class="col-12">
        <navbar
          is-blur="blur blur-rounded my-3 py-2 start-0 end-0 mx-4 shadow"
          btn-background="bg-gradient-success"
          :dark-mode="true"
        />
      </div>
    </div>
  </div>
  <router-view></router-view>
</template>

<script>
import Navbar from "@/examples/PageLayout/Navbar.vue";

export default {
  components: {
    Navbar,
  },
};
</script>

<style></style>
