<script setup>
const props = defineProps({
    imgs: Array
})



// console.log(props.imgs)

</script>

<template>
    <div class="card-group mt-3 ms-1 me-1 mb-3">
        <div class="card" v-for="img in props.imgs" :key="img.img">
            <img class="card-img-top" :src="img.img" alt="Card image cap">
            <div class="card-footer">
                <small class="fw-bold text-muted ">{{ img.name }}</small>
            </div>
        </div>
    </div>
</template>

<style scoped>
img {
    align-self: center;
    height: 20rem;

}
</style>