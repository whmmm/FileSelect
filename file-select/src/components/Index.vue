<template>

    <el-card class="box-card" style="margin-top:20px;margin-left:20px;margin-right:20px;" shadow="hover">
        <div slot="header" class="clearfix">
            <span>图片上传示例</span>
            <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
        </div>


        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="图片上传">

                <file-select-wrapper
                    :limit="2"
                    host="http://localhost:8000"
                    v-model="form.fileList"/>

            </el-form-item>

            <el-form-item>
                <el-button type="primary">立即创建</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>

    </el-card>


</template>

<script>
    import {FileSelect, FileSelectMixin} from '@/FileSelect'
    import FileSelectWrapper from "@/components/FileSelect/FileSelectWrapper";

    export default {
        mixins: [FileSelectMixin],
        name: 'Index',
        components: {FileSelectWrapper, FileSelect},
        data() {
            return {
                form: {
                    /*fileList: ['/upload/user_test/acae50971deffe006986d0580357776b.jpg',
                        '/upload/user_test/983640b4e5ef2aca15758c73977cfb6b.jpg'],*/
                    fileList: ['/upload/user_test/win10.jpg'],
                    host: 'http://localhost:8000'
                }
            }
        },
        mounted: function () {
            this.fileSelectTrigger = true
        }
    }
</script>

<style scoped>

</style>
