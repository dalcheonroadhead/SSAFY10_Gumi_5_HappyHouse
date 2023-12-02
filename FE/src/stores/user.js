import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useInfoStore = defineStore(
  "useInfoStore",
  () => {
    const userInfo = ref({
      id: "",
      pwd: "",
      name: "",
      phone: "",
      userRole: "",
      pk: 0,
    });

    return { userInfo };
  },
  { persist: { storage: localStorage } }
);
