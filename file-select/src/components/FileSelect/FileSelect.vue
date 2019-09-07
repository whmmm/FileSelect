<template>

    <!-- type="border-card" -->
    <el-tabs type="border-card" @tab-click="tabClick">
        <!--
        <el-tab-pane>
            <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
            <p v-for="i in (1,100)">我的行程</p>
        </el-tab-pane>
        <el-tab-pane label="消息中心">消息中心</el-tab-pane>
        <el-tab-pane label="角色管理">角色管理</el-tab-pane>
        <el-tab-pane label="定时任务补偿">定时任务补偿</el-tab-pane>-->

        <el-tab-pane v-for="(i,k) in menuList" :key="k" :label="i.title">
            <div :style="{'height':height} " class="file-select-container">
                <div v-if="k===0">
                    <!--上传-->
                    <upload-file :type="type" :host="host"

                    ></upload-file>
                </div>

                <div v-if="k===1">
                    <!--图片列表-->
                    <list-file v-model="value"
                               :trigger="trigger"
                               ref="ListFile"
                               :type="type"
                               :limit="limit"
                               :index="index"
                               :host="displayhost||host"
                    ></list-file>
                </div>

                <div v-if="k===2">
                    <!--在线选择-一般是图片在线选择.-->
                </div>
            </div>

            <div v-if="k==1 || k==2">
                <div style="float:right;margin-top: 5px;">
                    <el-button
                        size="medium"
                        plain type="success"
                        style="margin-right:10px;" @click="confirmSelect(index)">确认
                    </el-button>
                    <el-button plain type="warning" size="medium" @click="cancelSelect">取消</el-button>
                </div>
            </div>

        </el-tab-pane>

    </el-tabs>

</template>

<script>

    /* import Vue from 'vue'
     import '!style-loader!css-loader!vue-photo-preview/dist/skin.css'
     import gallery from 'vue-photo-preview'

     Vue.use(gallery, {
         bgOpacity: 0.8
     })*/
    window.randomString_FileSelect = function (len) {
        len = len || 32
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'
        /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
        var maxPos = $chars.length
        var pwd = ''
        for (var i = 0; i < len; i++) {
            pwd += $chars.charAt(Math.floor(Math.random() * maxPos))
        }
        return pwd
    }

    window.getUrlParam_FileSelect = function (param) {
        var reg = new RegExp('(^|&)' + param + '=([^&]*)(&|$)', 'i')
        //var r = window.location.search.substr(1).match(reg)
        var arr = window.location.href.split('?')
        if (arr.length == 2) {

            var r = arr[1].substr(0).match(reg)
            if (r != null) {
                var val = unescape(r[2])
                if (val.trim() === '') {
                    return null
                } else {
                    return val
                }
            }
            return null
        } else {
            return null
        }
    }

    import UploadFile from '@/components/FileSelect/Sub/UploadFile'
    import ListFile from '@/components/FileSelect/Sub/ListFile'

    //import '../@/static/plugins/ali-font/iconfont.css'

    export default {
        components: {ListFile, UploadFile},
        model: {
            prop: 'value',
            event: 'selectImage'
        },

        name: 'FileSelect',
        props: {
            value: {
                //一张图片是 string,多张是 array
                type: [String, Array],
                default() {
                    return []
                }
            },
            trigger: {
                /* 监听回显-已选中图片 */
                type: Boolean,
                default: false
            },
            height: {
                type: String,
                default: '400px'
            },
            //上传文件类型
            //image ,doc, video, audio (1,2,3,4)
            type: {
                type: String,
                default: function () {
                    return getUrlParam_FileSelect('type') || 'image'
                }
            },
            //上传个数限制
            limit: {
                type: [Number, String],
                default: function () {
                    return Number(getUrlParam_FileSelect('limit')) || 1
                }
            },
            //上传文件的的 url
            uploadUrl: '',
            // 加载列表的url
            listUrl: '',
            // 主机地址(上传) 例如 http://localhost:8000
            host: {
                type: String,
                default() {
                    return 'http://localhost:8000'
                }
            },
            // 主机地址(展示) 展示图片的 host 有时候 是反向代理服务器主机
            displayhost: ''
        },
        data() {
            return {
                //功能列表
                menuList: [],

                index: 0,

                menu: {
                    'uploadImg': [
                        {title: '图片上传'},
                        {title: '已上传图片选择'},
                        {title: '在线插入'}
                    ],
                    'uploadDoc': [
                        {title: '文档上传'},
                        {title: '已上传文档选择'}
                    ],
                    'uploadVideo': [
                        {title: '视频上传'},
                        {title: '已上传视频选择'}
                    ],
                    'uploadAudio': [
                        {title: '音频上传'},
                        {title: '已上传音频选择'}
                    ],
                }
            }
        },
        created() {

            this.setMenuList()

        },
        methods: {
            setMenuList() {
                let self = this

                switch (self.type) {
                    case 'image':
                        self.menuList = self.menu.uploadImg
                        break
                    case 'doc':
                        self.menuList = self.menu.uploadDoc
                        break
                    case 'video':
                        self.menuList = self.menu.uploadVideo
                        break
                    case 'audio':
                        self.menuList = self.menu.uploadAudio
                }
            },
            tabClick: function (v1, v2) {
                this.index = eval(v1.index)
            },

            confirmSelect(index) {
                let host = this.displayhost || this.host;
                // 修改 此方法，后台直接返回 全路径！
                host = '';
                //文件或者图片列表.
                if (index == 1) {
                    let fileList = this.$refs.ListFile[0].selectFileMap

                    let img = []
                    if (this.limit === 1) {
                        //只获取 1 个.
                        for (let prop in fileList) {
                            //只需要循环一次就好.
                            img = host + prop
                            break
                        }

                    } else {
                        for (let prop in fileList) {
                            img.push(host + prop)
                        }
                    }
                    // console.log(img)
                    // limit 为 1张时,就是一个 字符串,多张是 ,是一个数组.
                    this.$emit('selectimage', img)

                } else if (index == 2) {
                    //在线选择.
                }
            },
            cancelSelect() {
                this.$emit("cancelimage")
            }
        }
    }
</script>

<style scoped>
    .el-tabs {
        border-radius: 10px;
        height: 100%;
    }

    .file-select-container {
        overflow-y: scroll;
        overflow-x: hidden;
    }

    .el-tabs_content {
        padding: 3px;
    }
</style>
