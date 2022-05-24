<template>
  <div
    class="modal fade"
    id="houseModalCalendar"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">날짜 범위 검색</h5>
        </div>
        <div class="modal-body">
          <div class="row justify-content-center">
            <date-picker v-model="range" is-range>
              <template v-slot="{ inputValue, inputEvents }">
                <div class="flex justify-center items-center">
                  <input
                    id="dateFrom"
                    :value="inputValue.start"
                    v-on="inputEvents.start"
                    placeholder="시작 날짜를 입력해주세요"
                    class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                  />
                  <svg
                    class="w-4 h-4 mx-2"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M14 5l7 7m0 0l-7 7m7-7H3"
                    />
                  </svg>
                  <input
                    id="dateTo"
                    :value="inputValue.end"
                    v-on="inputEvents.end"
                    placeholder="종료 날짜를 입력해주세요"
                    class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                  />
                </div>
              </template>
            </date-picker>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-info"
            data-bs-dismiss="modal"
            @click="dateSelect()"
          >
            검색
          </button>
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            닫기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { DatePicker } from "v-calendar";
import "v-calendar/dist/style.css";
import { mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  components: {
    DatePicker,
  },
  methods: {
    ...mapActions(houseStore, ["getAptDealInDate"]),
    async dateSelect() {
      const date = {
        from: document.querySelector("#dateFrom").value.replaceAll(".", "-"),
        to: document.querySelector("#dateTo").value.replaceAll(".", "-"),
        aptCode: this.$store.state.houseStore.house.aptCode,
      };

      if (date.from == null) {
        alert("시작일을 입력해주세요");
      } else if (date.to == null) {
        alert("종료일을 입력해주세요");
      } else {
        await this.getAptDealInDate(date);
      }
    },
  },
};
</script>

<style></style>
