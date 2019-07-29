<template>
    <div>
        <FileSelect
            :host="host"
            :trigger="fileSelectTrigger" :limit="2" type="image" v-model="fileList"></FileSelect>

        <!--当前选择的文件或图片.-->
        <div style="display: flex;flex-wrap: wrap">
            <div v-for="(i,k) in fileList" :key="k" style="width: 18%;margin:5px;">
                <img :src="i" style="width: 100%"/>
            </div>
        </div>
    </div>

</template>

<script>
    import {FileSelect, FileSelectMixin} from '@/FileSelect'

    export default {
        mixins: [FileSelectMixin],
        name: 'Index',
        components: {FileSelect},
        data() {
            return {
                fileList: ['/upload/user_test/acae50971deffe006986d0580357776b.jpg',
                    '/upload/user_test/983640b4e5ef2aca15758c73977cfb6b.jpg'],
                host:'http://localhost:8000'
            }
        },
        mounted: function () {
            this.fileSelectTrigger = true
        }
    }
</script>

<style scoped>

</style>
