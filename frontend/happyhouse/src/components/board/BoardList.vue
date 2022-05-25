<template>
  <div class="card container">
    <div class="mb-4">
      <div class="row">
        <div class="card-header col-lg-11">
          <h3>HAPPYHOUSE BOARD</h3>
        </div>
        <div class="mb-0 col-lg-1 align-self-center">
          <vsud-button color="info" variant="gradient" @click="moveWrite"
            >글쓰기</vsud-button
          >
        </div>
      </div>
      <div class="card-body px-0 pt-0 pb-2">
        <div class="table-responsive p-0">
          <table class="table align-items-center justify-content-center mb-0">
            <thead>
              <tr>
                <th
                  class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
                >
                  제목
                </th>
                <th
                  class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-4"
                >
                  작성자
                </th>
                <th
                  class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-4"
                >
                  작성일
                </th>
                <th
                  class="text-uppercase text-secondary text-xxs font-weight-bolder text-center opacity-7 ps-4"
                >
                  조회수
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="article in paginatedData" :key="article.articleno">
                <td>
                  <div class="px-2">
                    <div class="my-auto">
                      <h6 class="mb-0 text-sm">
                        <router-link
                          :to="{
                            name: 'boardDetail',
                            params: { articleno: article.articleno },
                          }"
                        >
                          {{ article.subject }}
                        </router-link>
                      </h6>
                    </div>
                  </div>
                </td>
                <td>
                  <p class="text-sm font-weight-bold mb-0">
                    {{ article.username }}
                  </p>
                </td>
                <td>
                  <span class="text-xs font-weight-bold">{{
                    yyyyMMdd(article.regtime)
                  }}</span>
                </td>
                <td class="align-middle text-center">
                  <span class="text-xs font-weight-bold">{{
                    article.hit
                  }}</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <br />
      <div class="row justify-content-center">
        <vsud-input
          class="col-md-4 mb-0"
          id="content"
          placeholder="검색하실 내용을 입력해주세요."
        ></vsud-input>
        <img
          src="../../assets/img/magnify-glass.png"
          class="p-0 cursor-pointer align-self-center"
          style="height: 30px; width: 30px"
          @click="searchContent"
        />
      </div>
      <vsud-pagination
        class="justify-content-center my-3"
        color="info"
        style="margin-left: auto; margin-right: auto"
      >
        <vsud-pagination-item prev @click="prevPage" :disabled="pageNum == 1" />
        <div v-for="pageNum in pageCount" :key="pageNum">
          <div v-if="curNum == pageNum">
            <vsud-pagination-item
              :label="pageNum"
              @click="movePage(pageNum)"
              active
            />
          </div>
          <div v-else>
            <vsud-pagination-item :label="pageNum" @click="movePage(pageNum)" />
          </div>
        </div>
        <vsud-pagination-item
          next
          @click="nextPage"
          :disabled="pageNum == pageCount"
        />
      </vsud-pagination>
    </div>
  </div>
</template>

<script>
import VsudPagination from "@/components/vsud/VsudPagination.vue";
import VsudPaginationItem from "@/components/vsud/VsudPaginationItem.vue";
import VsudButton from "@/components/vsud/VsudButton.vue";
import VsudInput from "@/components/vsud/VsudInput.vue";
import { searchTitle } from "@/api/board.js";

export default {
  name: "paginated-list",

  data() {
    return {
      articles: [],
      pageNum: 1,
      curNum: 1,
    };
  },
  components: {
    VsudButton,
    VsudInput,
    VsudPagination,
    VsudPaginationItem,
  },
  props: {
    listArray: {
      type: Array,
      required: true,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 9,
    },
  },
  methods: {
    async searchContent() {
      let param = {
        title: document.getElementById("content").value + "",
      };
      await searchTitle(
        param,
        (response) => {
          this.$router.push({ name: "list", params: { list: response.data } });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveWrite() {
      this.$router.push({ name: "boardRegister" });
    },
    nextPage() {
      if (this.pageNum < this.pageCount) this.pageNum += 1;
      this.curNum = this.pageNum;
    },
    movePage(num) {
      this.pageNum = num;
      this.curNum = this.pageNum;
    },
    prevPage() {
      if (this.pageNum - 1 > 0) this.pageNum -= 1;
      this.curNum = this.pageNum;
    },
    // filter로 쓸 filter ID 지정
    yyyyMMdd: function (value) {
      // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
      if (value == "") return "";

      // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
      var js_date = new Date(value);

      // 연도, 월, 일 추출
      var year = js_date.getFullYear();
      var month = js_date.getMonth() + 1;
      var day = js_date.getDate();

      // 월, 일의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
      if (month < 10) {
        month = "0" + month;
      }

      if (day < 10) {
        day = "0" + day;
      }

      // 최종 포맷 (ex - '2021-10-08')
      return year + "-" + month + "-" + day;
    },
  },
  computed: {
    pageCount() {
      let listLeng = this.listArray.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      /*
      아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
      이런식으로 if 문 없이 고칠 수도 있다!
      */
      return page;
    },
    paginatedData() {
      const start = (this.pageNum - 1) * this.pageSize,
        end = start + this.pageSize;

      return this.listArray.slice(start, end);
    },
  },
};
</script>

<style>
.cursor-pointer {
  cursor: pointer;
}
</style>
