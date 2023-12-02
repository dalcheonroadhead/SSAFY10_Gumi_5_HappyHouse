import { ref } from "vue";
import { defineStore } from "pinia";

export const favoriteListStore = defineStore(
  "favoriteListStore",
  () => {
    const favoriteList = ref([]);
    const curFavorite = ref({
      house_no: 0,
      addr_road: "",
      addr_dong: "",
      kind: "",
      house_name: "",
      household: 0,
      floor: 0,
      max_floor: 0,
      complete_date: 0,
      phone: "000-0000-0000",
      content: "",
      hit: 0,
      register_date: "",
      heart: "white",
    });

    return { favoriteList, curFavorite };
  },
  {
    persist: { storage: localStorage },
  }
);
