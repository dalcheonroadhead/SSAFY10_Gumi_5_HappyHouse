<script setup>
import axios from "axios";



</script>

<template>
    <div>
        <form class="m-5" style="box-shadow: 0 0 10px;  border-radius: 5%;">
            <fieldset class="p-3">
                <legend class="text-center" style="font-family: 'JalnanGothic';">방을 올려보자!</legend>
                <label class="form-label mt-4">종류</label>
                <select class="form-select" id="kind">
                    <option>매매</option>
                    <option>전세</option>
                    <option>월세</option>
                </select>

                <label class="form-label mt-4">가격</label>
                <input type="number" class="form-control" id="price" />

                <label class="col-form-label mt-4">건물명</label>
                <input type="text" class="form-control" id="house_name" />

            <label class="col-form-label mt-4">도로명 주소</label>
            <input type="text" class="form-control" id="addr_road" />

            <label class="col-form-label mt-4">상세 주소</label>
            <input type="text" class="form-control" id="addr_detail" />
            <!-- 
                <label class="col-form-label mt-4">동수</label>
                <input type="text" class="form-control" id="dong" />

                <label class="col-form-label mt-4">세대수</label>
                <input type="text" class="form-control" id="household" />

                    <div>
                        <label class="col-form-label mt-4">층수</label>
                                            <input type="text" class="form-control" id="floor" />
                                            <label class="col-form-label mt-4">최대 층수</label>
                                            <input type="text" class="form-control" id="max_floor" />
                                        </div> -->

                <label class="col-form-label mt-4">건축 연도</label>
                <input type="text" class="form-control" id="complete_date" />

                <label class="col-form-label mt-4">연락처</label>
                <input type="text" class="form-control" id="phone" />

                <label class="col-form-label mt-4">내용</label>
                <textarea class="form-control" id="content" rows="3"></textarea>

                <label class="form-label mt-4">사진 첨부</label>
                <input class="form-control" type="file" id="files" multiple="multiple" accept="image/*" />
                <!-- <v-img v-for="(item,i) in uploadimageurl" :key="i" :src="item.url"
                                                                                                                                                                contain height="150px" width="200px" style="border: 2px solid black; margin-left:100px;"/> -->

                <button class="mt-3 btn " type="submit">등록</button>
                <!-- <button @click="closePop()">닫기</button> -->
            </fieldset>
        </form>
    </div>
</template>

<style scoped>
@font-face {
    font-family: 'JalnanGothic';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_231029@1.1/JalnanGothic.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}



@media (prefers-reduced-motion: reduce) {
    .form-control {
        transition: none;
    }
}

.form-label {
    margin-top: 1rem;
    font-family: 'JalnanGothic';
    font-weight: 600;
}

.col-form-label {
    padding-top: calc(1rem + var(--bs-border-width));
    padding-bottom: calc(1rem + var(--bs-border-width));
    margin-bottom: 0;
    font-size: inherit;
    font-weight: 600;
    line-height: 1.5;
    font-family: 'JalnanGothic';
}

.form-control {
    display: block;
    width: 100%;
    padding: 1rem 1.5rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: var(--bs-body-color);
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    background-color: #f0f5fa;
    background-clip: padding-box;
    border: var(--bs-border-width) solid var(--bs-border-color);
    border-radius: var(--bs-border-radius);
    box-shadow: var(--bs-box-shadow-inset);
    transition:
        border-color 0.15s ease-in-out,
        box-shadow 0.15s ease-in-out;
}

.form-control[type="file"] {
    overflow: hidden;
}

.form-control[type="file"]:not(:disabled):not([readonly]) {
    cursor: pointer;
}

.form-control:focus {
    color: var(--bs-body-color);
    background-color: #f0f5fa;
    border-color: #9bc6fe;
    outline: 0;
    box-shadow:
        var(--bs-box-shadow-inset),
        0 0 0 0.25rem rgba(55, 141, 252, 0.25);
}

.form-control::-webkit-date-and-time-value {
    min-width: 85px;
    height: 1.5em;
    margin: 0;
}

.form-control::-webkit-datetime-edit {
    display: block;
    padding: 0;
}

.form-control::-moz-placeholder {
    color: #adb6d3;
    opacity: 1;
}

.form-control::placeholder {
    color: #adb6d3;
    opacity: 1;
}

.form-control:disabled {
    background-color: var(--bs-secondary-bg);
    opacity: 1;
}

.form-control::-webkit-file-upload-button {
    padding: 1rem 1.5rem;
    margin: -1rem -1.5rem;
    -webkit-margin-end: 1.5rem;
    margin-inline-end: 1.5rem;
    color: var(--bs-body-color);
    background-color: #f0f5fa;
    pointer-events: none;
    border-color: inherit;
    border-style: solid;
    border-width: 0;
    border-inline-end-width: var(--bs-border-width);
    border-radius: 0;
    -webkit-transition:
        color 0.15s ease-in-out,
        background-color 0.15s ease-in-out,
        border-color 0.15s ease-in-out,
        box-shadow 0.15s ease-in-out;
    transition:
        color 0.15s ease-in-out,
        background-color 0.15s ease-in-out,
        border-color 0.15s ease-in-out,
        box-shadow 0.15s ease-in-out;
}

.form-control::file-selector-button {
    padding: 1rem 1.5rem;
    margin: -1rem -1.5rem;
    -webkit-margin-end: 1.5rem;
    margin-inline-end: 1.5rem;
    color: var(--bs-body-color);
    background-color: #f0f5fa;
    pointer-events: none;
    border-color: inherit;
    border-style: solid;
    border-width: 0;
    border-inline-end-width: var(--bs-border-width);
    border-radius: 0;
    transition:
        color 0.15s ease-in-out,
        background-color 0.15s ease-in-out,
        border-color 0.15s ease-in-out,
        box-shadow 0.15s ease-in-out;
}

@media (prefers-reduced-motion: reduce) {
    .form-control::-webkit-file-upload-button {
        -webkit-transition: none;
        transition: none;
    }

    .form-control::file-selector-button {
        transition: none;
    }
}

.form-control:hover:not(:disabled):not([readonly])::-webkit-file-upload-button {
    background-color: var(--bs-secondary-bg);
}

.form-control:hover:not(:disabled):not([readonly])::file-selector-button {
    background-color: var(--bs-secondary-bg);
}


.form-control-color {
    width: 3rem;
    height: calc(1.5em + 2rem + calc(var(--bs-border-width) * 2));
    padding: 1rem;
}

.form-control-color:not(:disabled):not([readonly]) {
    cursor: pointer;
}

.form-control-color::-moz-color-swatch {
    border: 0 !important;
    border-radius: var(--bs-border-radius);
}

.form-control-color::-webkit-color-swatch {
    border: 0 !important;
    border-radius: var(--bs-border-radius);
}

.form-control-color.form-control-sm {
    height: calc(1.5em + 0.5rem + calc(var(--bs-border-width) * 2));
}

.form-control-color.form-control-lg {
    height: calc(1.5em + 1rem + calc(var(--bs-border-width) * 2));
}

.form-select {
    --bs-form-select-bg-img: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='%237b8ab8' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='m2 5 6 6 6-6'/%3e%3c/svg%3e");
    display: block;
    width: 100%;
    padding: 1rem 4.5rem 1rem 1.5rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: var(--bs-body-color);
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    background-color: #f0f5fa;
    background-image: var(--bs-form-select-bg-img), var(--bs-form-select-bg-icon, none);
    background-repeat: no-repeat;
    background-position: right 1.5rem center;
    background-size: 16px 12px;
    border: var(--bs-border-width) solid var(--bs-border-color);
    border-radius: var(--bs-border-radius);
    box-shadow:
        inset 2px 2px 8px rgba(55, 94, 148, 0.3),
        inset -3px -2px 5px rgba(255, 255, 255, 0.8);
    transition:
        border-color 0.15s ease-in-out,
        box-shadow 0.15s ease-in-out;
}

@media (prefers-reduced-motion: reduce) {
    .form-select {
        transition: none;
    }
}

.form-select:focus {
    border-color: #9bc6fe;
    outline: 0;
    box-shadow:
        inset 2px 2px 8px rgba(55, 94, 148, 0.3),
        inset -3px -2px 5px rgba(255, 255, 255, 0.8),
        0 0 0 0.25rem rgba(55, 141, 252, 0.25);
}

.form-select[multiple],
.form-select[size]:not([size="1"]) {
    padding-right: 1.5rem;
    background-image: none;
}

.form-select:disabled {
    background-color: var(--bs-secondary-bg);
}

.form-select:-moz-focusring {
    color: transparent;
    text-shadow: 0 0 0 var(--bs-body-color);
}

[data-bs-theme="dark"] .form-select {
    --bs-form-select-bg-img: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='%23dee2e6' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='m2 5 6 6 6-6'/%3e%3c/svg%3e");
}

.btn {
    --bs-btn-padding-x: 1.5rem;
    --bs-btn-padding-y: 1rem;
    --bs-btn-font-family: ;
    --bs-btn-font-size: 1rem;
    --bs-btn-font-weight: 600;
    --bs-btn-line-height: 1.5;
    --bs-btn-color: var(--bs-body-color);
    --bs-btn-bg: transparent;
    --bs-btn-border-width: var(--bs-border-width);
    --bs-btn-border-color: transparent;
    --bs-btn-border-radius: 50rem;
    --bs-btn-hover-border-color: transparent;
    --bs-btn-box-shadow: 5px 5px 10px rgba(55, 94, 148, 0.2), -5px -5px 10px rgba(255, 255, 255, 0.4);
    --bs-btn-disabled-opacity: 0.65;
    --bs-btn-focus-box-shadow: 0 0 0 0.25rem rgba(var(--bs-btn-focus-shadow-rgb), 0.5);
    display: inline-block;
    padding: var(--bs-btn-padding-y) var(--bs-btn-padding-x);
    font-family: var(--bs-btn-font-family);
    font-size: var(--bs-btn-font-size);
    font-weight: var(--bs-btn-font-weight);
    line-height: var(--bs-btn-line-height);
    color: var(--bs-btn-color);
    text-align: center;
    text-decoration: none;
    vertical-align: middle;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    user-select: none;
    border: var(--bs-btn-border-width) solid var(--bs-btn-border-color);
    border-radius: var(--bs-btn-border-radius);
    background-color: var(--bs-btn-bg);
    box-shadow: var(--bs-btn-box-shadow);
    transition:
        color 0.15s ease-in-out,
        background-color 0.15s ease-in-out,
        border-color 0.15s ease-in-out,
        box-shadow 0.15s ease-in-out;
}

@media (prefers-reduced-motion: reduce) {
    .btn {
        transition: none;
    }
}

.btn:hover {
    color: var(--bs-btn-hover-color);
    background-color: var(--bs-btn-hover-bg);
    border-color: var(--bs-btn-hover-border-color);
}

.btn:focus-visible {
    color: var(--bs-btn-hover-color);
    background-color: var(--bs-btn-hover-bg);
    border-color: var(--bs-btn-hover-border-color);
    outline: 0;
    box-shadow: var(--bs-btn-box-shadow), var(--bs-btn-focus-box-shadow);
}

.btn-check:focus-visible+.btn {
    border-color: var(--bs-btn-hover-border-color);
    outline: 0;
    box-shadow: var(--bs-btn-box-shadow), var(--bs-btn-focus-box-shadow);
}

.btn-check:checked+.btn,
.btn.active,
.btn.show,
.btn:first-child:active,
:not(.btn-check)+.btn:active {
    color: var(--bs-btn-active-color);
    background-color: var(--bs-btn-active-bg);
    border-color: var(--bs-btn-active-border-color);
    box-shadow: var(--bs-btn-active-shadow);
}

.btn-check:checked+.btn:focus-visible,
.btn.active:focus-visible,
.btn.show:focus-visible,
.btn:first-child:active:focus-visible,
:not(.btn-check)+.btn:active:focus-visible {
    box-shadow: var(--bs-btn-active-shadow), var(--bs-btn-focus-box-shadow);
}

.btn.disabled,
.btn:disabled,
fieldset:disabled .btn {
    color: var(--bs-btn-disabled-color);
    pointer-events: none;
    background-color: var(--bs-btn-disabled-bg);
    border-color: var(--bs-btn-disabled-border-color);
    opacity: var(--bs-btn-disabled-opacity);
    box-shadow: none;
}

.btn-primary {
    --bs-btn-color: #fff;
    --bs-btn-bg: #378dfc;
    --bs-btn-border-color: #378dfc;
    --bs-btn-hover-color: #fff;
    --bs-btn-hover-bg: #2f78d6;
    --bs-btn-hover-border-color: #2c71ca;
    --bs-btn-focus-shadow-rgb: 85, 158, 252;
    --bs-btn-active-color: #fff;
    --bs-btn-active-bg: #2c71ca;
    --bs-btn-active-border-color: #296abd;
    --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
    --bs-btn-disabled-color: #fff;
    --bs-btn-disabled-bg: #378dfc;
    --bs-btn-disabled-border-color: #378dfc;
}
</style>