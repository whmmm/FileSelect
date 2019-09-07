<template>
    <div class="pers-el-upload-dialog-wrapper">

        <el-button type="primary" size="small"
                   @click="toggleDialog"
        >点击选择图片
        </el-button>

        <div class="demo-image__preview" style="display: flex;flex-wrap: wrap;">
            <el-image v-if="typeof img=='string'"
                      fit="fill"
                      style="width: 100px; height: 100px"
                      :src="img"
                      :preview-src-list="typeof img=='string'? [img] : img ">
            </el-image>

            <el-image v-else
                      v-for="(i,k) in img" :key="k"
                      fit="fill"
                      style="width: 100px; height: 100px;margin-right:8px;"
                      :src="i"
                      :preview-src-list=" img ">
            </el-image>
        </div>

        <el-dialog
            top="5vh"
            title="选择图片"
            width="80%"
            :visible.sync="dialogVisible">

            <file-select height="80%"
                         style="padding: 0"
                         v-model="img"
                         :trigger="viewBackTrigger"
                         @selectimage="selectImg"
                         @cancelimage="cancelimage"
                         :host="host"
                         :limit="limit"

            />
        </el-dialog>

    </div>

</template>

<script>
    import FileSelect from "@/components/FileSelect/FileSelect";

    /**
     * 上传图片的时候，常常需要弹出 一个 dialog，而此 组件就是弹出一个对话框。
     */
    export default {
        name: "FileSelectWrapper",
        components: {FileSelect},
        model: {
            prop: 'value',
            event: 'changeimage'
        },
        props: {
            limit: {
                type: Number,
                default: 1
            },
            host: {
                type: String,
                default: 'http://localhost:8000'
            },
            value: {
                type: [String, Array],
                default: ''
            }
        },
        data() {
            return {
                dialogVisible: false,
                img: '/upload/user_test/win10.jpg',
                viewBackTrigger: false
            }
        },
        methods: {
            toggleDialog() {
                this.dialogVisible = !this.dialogVisible;
                if (this.dialogVisible) {
                    // 展示 模态框

                } else {
                    // 隐藏 模态框

                }
            },
            /**
             *
             * @param {string | Array} img
             */
            selectImg(img) {
                // console.log(img)
                this.$emit('changeimage', img)
                this.closeDialog()
            },
            cancelimage() {
                this.closeDialog()
            },
            closeDialog() {
                this.dialogVisible = false
            }
        },
        created() {
            let self = this
            self.viewBackTrigger = true
            if (this.value != null) {
                this.img = this.value;
            }
        },
        watch: {
            value(newV, oldV) {
                if (newV !== this.img) {
                    this.img = newV;
                }
            }
        }
    }
</script>

<style>
    .pers-el-upload-dialog-wrapper .el-dialog__body {
        padding: 5px !important;
    }
</style>
