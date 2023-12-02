<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useInfoStore } from "@/stores/user";
import { storeToRefs } from "pinia";

const { userInfo } = storeToRefs(useInfoStore());
const props = defineProps({
  house_no: Number,
  addr_road: String,
});

const commentList = ref([]);
const comment = ref("");
const { VITE_SPRING_BASE_URL } = import.meta.env;

const showList = () => {
  axios
    .post(
      VITE_SPRING_BASE_URL + "/favorite/showComments",
      {
        house_no: props.house_no,
        addr_road: props.addr_road,
      },
      {
        header: {
          "Context-Type": "json/application",
        },
      }
    )
    .then((response) => {
      console.log(response);
      let { data } = response;

      if (response.request.status === 200) {
        console.log(data.list);
        commentList.value = [...data.list];
      }
    })
    .catch((err) => {
      console.log(err);
    });
};
onMounted(() => {
  showList();
});

const enter = () => {
  axios
    .post(
      VITE_SPRING_BASE_URL + "/favorite/addComment",
      {
        house_no: props.house_no,
        addr_road: props.addr_road,
        content: comment.value,
        like_cnt: 0,
        hate_cnt: 0,
        id: userInfo.value.id,
        password: userInfo.value.pwd,
      },
      {
        header: {
          "Context-Type": "json/application",
        },
      }
    )
    .then((response) => {
      console.log(response);
      let { data } = response;

      if (response.data === 200) {
        console.log(data.msg);
        showList();
        comment.value = "";
      }
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<template>
  <div class="container justify-content-center mt-5 border-left border-right">
    <div class="d-flex justify-content-center py-2">
      <div
        class="second py-2 px-2"
        style="width: 100%; background-color: var(--bs-secondary-color)"
      >
        <span class="text1" style="color: white"
          >해당 부동산에 관심을 보이셨군요! 매매업자에게 QnA와 방문후기를
          남겨보세요!</span
        >
        <div class="d-flex justify-content-between py-1 pt-2">
          <div>
            <img src="https://i.imgur.com/AgAC1Is.jpg" width="18" /><span
              class="text2"
              style="color: white"
              >[GM 전수민]</span
            >
          </div>
          <div>
            <span class="text3" style="color: white">개추</span>
            <span class="thumbup"
              ><a href=""
                ><svg
                  id="admin"
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="white"
                  class="bi bi-hand-thumbs-up-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"
                  /></svg></a
            ></span>
            <span class="text4" style="color: white">664</span> &nbsp;
            <span class="text3" style="color: white">비추</span>
            <span class="thumbup"
              ><a href=""
                ><svg
                  id="admin"
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="white"
                  class="bi bi-hand-thumbs-down-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"
                  /></svg></a
            ></span>
            <span class="text4" style="color: white">3</span>
          </div>
        </div>
      </div>
    </div>

    <!--여기는 이제 사용자 댓글이 적힐 공간-->
    <div
      v-for="comment in commentList"
      :key="comment.comment_no"
      class="d-flex justify-content-center py-2"
    >
      <div class="second py-2 px-2" style="width: 100%">
        <span class="text1">{{ comment.content }}</span>
        <div class="d-flex justify-content-between py-1 pt-2">
          <div>
            <img src="https://i.imgur.com/AgAC1Is.jpg" width="18" /><span
              class="text2"
              >{{ comment.id }}</span
            >
          </div>
          <div>
            <span class="text3">개추</span>
            <span class="thumbup"
              ><a href=""
                ><svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="black"
                  class="bi bi-hand-thumbs-up-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"
                  /></svg></a
            ></span>
            <span class="text4">{{ comment.like_cnt }}</span> &nbsp;
            <span class="text3">비추</span>
            <span class="thumbup"
              ><a href=""
                ><svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="black"
                  class="bi bi-hand-thumbs-down-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M6.956 14.534c.065.936.952 1.659 1.908 1.42l.261-.065a1.378 1.378 0 0 0 1.012-.965c.22-.816.533-2.512.062-4.51.136.02.285.037.443.051.713.065 1.669.071 2.516-.211.518-.173.994-.68 1.2-1.272a1.896 1.896 0 0 0-.234-1.734c.058-.118.103-.242.138-.362.077-.27.113-.568.113-.856 0-.29-.036-.586-.113-.857a2.094 2.094 0 0 0-.16-.403c.169-.387.107-.82-.003-1.149a3.162 3.162 0 0 0-.488-.9c.054-.153.076-.313.076-.465a1.86 1.86 0 0 0-.253-.912C13.1.757 12.437.28 11.5.28H8c-.605 0-1.07.08-1.466.217a4.823 4.823 0 0 0-.97.485l-.048.029c-.504.308-.999.61-2.068.723C2.682 1.815 2 2.434 2 3.279v4c0 .851.685 1.433 1.357 1.616.849.232 1.574.787 2.132 1.41.56.626.914 1.28 1.039 1.638.199.575.356 1.54.428 2.591z"
                  /></svg></a
            ></span>
            <span class="text4">{{ comment.hate_cnt }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="d-flex justify-content-center pt-3 pb-2">
      <input
        type="text"
        name="text"
        placeholder="+ Add a note"
        class="form-control addtxt"
        style="width: 100%"
        v-model.lazy="comment"
      />
      <button class="btn" @click="enter">(완)</button>
    </div>
  </div>
</template>

<style scoped>
.addtxt {
  padding-top: 10px;
  padding-bottom: 10px;
  text-align: center;
  font-size: 13px;
  width: 350px;
  background-color: #e5e8ed;
  font-weight: 500;
}
.form-control:focus {
  color: #000;
}
.second {
  width: 350px;
  background-color: white;
  border-radius: 4px;
  box-shadow: 10px 10px 5px #aaaaaa;
}
.text1 {
  font-size: 13px;
  font-weight: 500;
  color: #56575b;
}
.text2 {
  font-size: 13px;
  font-weight: 500;
  margin-left: 6px;
  color: #56575b;
}
.text3 {
  font-size: 13px;
  font-weight: 500;
  margin-right: 4px;
  color: #828386;
}
.text3o {
  color: #00a5f4;
}
.text4 {
  font-size: 13px;
  font-weight: 500;
  color: #828386;
}
.text4i {
  color: #00a5f4;
}
.text4o {
  color: white;
}
.thumbup {
  font-size: 13px;
  font-weight: 500;
  margin-right: 5px;
}
.thumbupo {
  color: #17a2b8;
}
svg:hover {
  fill: blue;
}

#admin:hover {
  fill: black;
}
</style>
