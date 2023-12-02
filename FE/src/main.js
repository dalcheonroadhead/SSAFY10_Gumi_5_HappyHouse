import { createApp } from "vue";
import { createPinia } from "pinia";

import "./assets/main.css";
import App from "./App.vue";
import router from "./router";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.css";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const app = createApp(App);

app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);
app.mount("#app");
