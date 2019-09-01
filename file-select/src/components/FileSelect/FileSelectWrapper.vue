<template>
    <div>

        <el-button type="primary" size="small"
                   @click="toggleDialog"
        >点击选择图片
        </el-button>

        <el-dialog
            top="5vh"
            title="选择图片"
            width="80%"
            :visible.sync="dialogVisible">

            <file-select height="80%"
                         v-model="img"
                         :trigger="viewBackTrigger"
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
            }
        },
        created() {
            let self = this
            self.viewBackTrigger = true
        }
    }
</script>

<style scoped>

</style>
