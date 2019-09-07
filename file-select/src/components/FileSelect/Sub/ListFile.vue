<template>
    <div class="list-container">
        <!--排列方式.-->
        <div class="view-type-select-div"
             style="display: flex;
             align-items: center;
             justify-content: space-between;padding-top:3px;padding-bottom: 6px">
            <div style="padding-top: 3px">
                <label style="align-self: center;margin-bottom: 0">
                    查看视图:
                </label>
                <span :style="{'color':viewType=='timeline'?'#007bff':'black'}"
                      class="iconfont icon-shijianzhou"
                      @click="changeType('timeline')"></span>
                <span :style="{'color':viewType=='list'?'#007bff':'black'}"
                      class="iconfont icon-jiugongge"
                      @click="changeType('list')"></span>
            </div>

            <!--:class="viewCurrentVisible?'btn-warning':'btn-primary'"-->
            <!-- style="font-size: 16px;cursor: pointer;margin-right:4px " -->
            <el-button
                plain
                type="primary"
                size="medium"
                style="margin-right:5px;"
                @click="viewCurrentVisible=!viewCurrentVisible"
                >
                {{viewCurrentVisible?'隐藏当前':'查看当前已选'}}
            </el-button>
        </div>



        <!--当前已选择的图片列表.-->
        <div class="file-list-container view-selected-img-div"
             v-show="viewCurrentVisible&&getObjLen(selectFileMap)>1">
            <el-divider></el-divider>
            <!--@click="select(i,k)"-->
            <div v-for="(i,k) in selectFileMap" :key="k" class="file-item">
                <div style="height: 110px;overflow: hidden;display: flex">
                    <img class="list-img" :src=" k" style="align-self: flex-end;"/>
                </div>
                <span style="text-align: center;cursor: pointer;background: rgb(61,161,219)"
                      class="text-overflow">{{i.fileName}}</span>

                <span @click="removeSelect(i,k)"
                      style="padding: 2px;position: absolute;right:0;top:0;cursor: pointer;background:rgba(255,0,0,0.6)">✖</span>
            </div>
            <el-divider></el-divider>
        </div>


        <div class="item-container">

            <div class="file-item-container">


                <div class="file-list-container" v-if="viewType==='list'">
                    <div v-for="(i,k) in fileList" :key="k" class="file-item" @click="select(i,k)">
                        <img class="list-img" :src="i.path" :preview="k"
                             :preview-text="i.fileName"/>
                        <span style="margin-bottom: 0" class="text-overflow" v-html="i.fileName"></span>

                        <!--选中层.-->
                        <!--v-if="i.state===1"-->
                        <div class="confirm-div" v-if="i.selected">
                            <img class="confirm-img" src="@/assets/yes.png"/>
                        </div>

                    </div>
                </div>

                <div v-else-if="viewType==='timeline'" v-for="(i,k) in timelineObject" :key="k"
                     style="border-bottom: 2px solid #888888">
                    <!--日期..-->
                    <p style="font-size:25px;font-weight: 600;">{{k}}</p>
                    <div class="file-list-container">

                        <div v-for="(i,k) in timelineObject[k]" :key="k" class="file-item" @click="select(i,k)">
                            <img class="list-img" :src="i.path" :preview="k"
                                 :preview-text="i.fileName"/>
                            <span style="margin-bottom: 0" class="text-overflow" v-html="i.fileName"></span>

                            <!--选中层.-->
                            <!--v-if="i.state===1"-->
                            <div class="confirm-div" v-if="i.selected">
                                <img class="confirm-img" src="@/assets/yes.png"/>
                            </div>

                        </div>

                    </div>
                </div>

            </div>

        </div>

    </div>
</template>

<script>

    /*import Vue from 'vue'
    import '!style-loader!css-loader!vue-photo-preview/dist/skin.css'
    import gallery from 'vue-photo-preview'

    Vue.use(gallery, {
        bgOpacity: 0.8
    })*/

    export default {
        name: 'ListFile',

        model: {
            prop: 'value',
            event: 'changeSelect'
        },

        props: {
            /**
             * 当trigger 变更为true 时.更新 回显的数据....
             * */
            trigger: {
                type: Boolean,
                default: false
            },
            /**
             * 真正要传递的 图片 url.
             */
            value: {
                type: [String, Array], default: () => {
                    return []
                }
            },
            index: {type: Number, default: 1},

            type: {
                type: String, default: function () {
                    return 'image'
                }
            },
            limit: {type: Number, default: 1},
            fetchUrl: {type: String, default: '/file'},
            // 主机访问 形式 http/https 开头
            host: {type: String, default: 'http://localhost:8000'},


        },
        data() {
            return {
                //文件列表
                fileList: [],

                //时间轴 文件列表.
                timelineObject: {},

                requestParam: {page: 1, limit: 10},
                endPage: 0,

                //处于加载中,不进行再次加载
                loading: false,
                //为true时,结束加载..
                endLoad: false,

                //查看列表类型.默认list
                viewType: 'list',

                viewCurrentVisible: false,

                /**
                 * 当前选择图片的 url . 以及 索引:
                 * {'1.jpg':1,'2.jpg':2}
                 */
                selectFileMap: {}
            }
        },
        created() {
            if (this.trigger === true) {
                this.updateViewBack()
            }
        },
        mounted() {
            let self = this

            self.requestParam.type = self.type

            self.loadData()

            //监听滚动..
            $('.file-select-container').scroll(function () {
                if (self.index != 1) {
                    return
                }
                if (self.loading || self.loading == undefined) {
                    return
                }

                let dom = $(this)

                let scrollTop = dom.scrollTop()
                let scrollHeight = dom.height()
                let windowHeight = $('.list-container').height()

                let h = scrollTop + scrollHeight

                if (self.endPage < self.requestParam.page) {
                    if (h + 1 >= windowHeight) {
                        self.loadData()
                    }
                }
            })

        },

        methods: {
            loadData() {
                let self = this
                if (self.endPage < self.requestParam.page) {
                    console.log('%c开始执行 ajax 加载', 'background:orange;color:#fff')
                    let getUrl = self.host + self.fetchUrl /* + self.type*/
                    $.get(getUrl, self.requestParam, function (resp) {
                        if (resp != null && resp.fileList != null && resp.fileList.length > 0) {
                            self.requestParam.page++


                            resp.fileList.forEach((item) => {

                                item.selected = false

                                //记录原索引.
                                item.srcIndex = self.fileList.length

                                if (self.selectFileMap[item.path] != null) {
                                    self.selectFileMap[item.path].index = item.srcIndex
                                    item.selected = true
                                }

                                //添加到文件列表.
                                let i = self.fileList.push(item)

                                let newItem = self.fileList[i - 1]

                                //添加到时间轴 文件列表.
                                let uploadTime = item.uploadTime.substr(0, 10)
                                let arr = self.timelineObject[uploadTime]
                                if (arr == null) {
                                    arr = self.timelineObject[uploadTime] = []
                                }
                                arr.push(newItem)

                            })

                        } else {
                            self.endPage = self.requestParam.page
                        }
                        setTimeout(() => {
                            let h1 = $('.file-select-container').height()
                            let h2 = $('.list-container').height()
                            if (h1 >= h2 && h2 != 0) {
                                console.log('高度太低.再次加载')
                                self.loadData()
                            }
                        }, 300)
                        //self.$previewRefresh()
                    })
                }
            },
            changeType(type) {
                this.viewType = type
                //可能需要重拍数据..
            },
            /**
             * @param item
             * @param index 被废弃, 使用 item.srcIndex
             * */
            select(item, index) {
                if (this.limit == 1) {
                    // 限制为1 先删除旧的.
                    let prop = ''
                    let index2 = 0
                    for (let props in this.selectFileMap) {
                        prop = props
                        if (this.selectFileMap.hasOwnProperty(props)) {
                            index2 = this.selectFileMap[props]
                        }
                        break
                    }
                    if (prop != '') {
                        let file = this.fileList[index2.index]
                        if (file != null) {
                            file.selected = false
                        }

                        this.selectFileMap[prop] = null
                        delete this.selectFileMap[prop]
                    }
                } else {
                    if (!this.selectFileMap.hasOwnProperty(item.path)) {
                        if (this.limit < this.getObjLen(this.selectFileMap)) {
                            this.$alert('最多上传 <span style="font-weight: 600;color:red">' + this.limit + ' </span>个,请先移除旧数据', '提示', {
                                type: 'error',
                                dangerouslyUseHTMLString: true
                            }).catch(function () {
                            })
                            return
                        }
                    }
                }

                item.selected = !item.selected
                if (item.selected) {
                    this.selectFileMap[item.path] = {index: item.srcIndex, fileName: item.fileName}
                } else {
                    this.selectFileMap[item.path] = null
                    delete this.selectFileMap[item.path]
                }
            },
            getObjLen(obj) {
                return Object.getOwnPropertyNames(obj).length
            },

            /**
             *
             * @param i  所在原数组(fileList) 索引 以及 名字.
             * @param k  图片或者资源的 path (全路径.)..
             */
            removeSelect(i, k) {
                console.log(k)

                if (i.index != null) {
                    this.selectFileMap[k] = null
                    delete this.selectFileMap[k]
                    this.fileList[i.index].selected = false
                } else {
                    //Object.assign(this.selectFileMap, this.selectFileMap)
                    this.$delete(this.selectFileMap, k)
                }

            },
            updateViewBack() {
                //获取从父组件传递的数组,或字符串.
                let val = this.value
                if (val == null || val.trim === '') {
                    return
                }
                if (this.limit == 1) {
                    this.selectFileMap[val] = {
                        index: null,
                        fileName: val.replace(/[\s\S]+(?<=[/|\\])/, '')
                    }
                } else {
                    val.forEach((item) => {
                        let arr = item.split('/')
                        this.selectFileMap[item] = {index: null, fileName: arr.pop()}
                    })
                }
                console.log(this.selectFileMap)
                console.log('%c回显数据更新完毕!', 'background:blue;color:#fff')
            }
        },
        watch: {
            index: function (newVal) {
                if (newVal == 1) {
                    this.loadData()
                }
            },
            trigger: function (newVal, oldVal) {
                /*console.log(newVal)*/
                if (newVal === true) {

                    this.updateViewBack()
                }
            }
        }
    }
</script>

<style scoped>
    .view-selected-img-div {
        padding-top: 5px;
        padding-bottom: 5px;
      /*  border-top: 2px dashed #3da1db;
        border-bottom: 2px dashed #3da1db;*/
        margin-top: 5px;
        margin-bottom: 5px
    }

    .confirm-div {
        position: absolute;
        top: 0;
        background: rgba(255, 255, 255, 0.4);
        width: 100%;
        height: 100%;
        border: 3px rgb(0, 123, 255) solid;
    }

    .confirm-img {
        width: 35%;
        text-align: center;
        right: 0;
        bottom: 0;
        position: absolute;
    }

    .view-type-select-div {
        font-size: 18px;
    }

    .view-type-select-div span {
        margin-left: 15px;
        font-size: 25px;
        cursor: pointer;
    }

    .file-list-container {
        display: flex;
        flex-wrap: wrap;
    }

    .file-item {
        position: relative;
        width: 18%;
        margin-left: 2%;
        margin-bottom: 10px;
        align-self: flex-end;
    }

    .list-img {
        width: 100%;
        cursor: pointer;
    }

    .file-item-container {
        display: flex;
        flex-direction: column;
    }

    .list-container {
        display: flex;
        flex-direction: column;
    }

    .text-overflow {
        overflow: hidden;
        display: inherit;
        white-space: nowrap;
        text-overflow: ellipsis;
        width: 100%;
        color: #fff;
        background-color: rgba(0, 0, 0, 0.8);
        border-color: #007bff;
        /*font-size: 15px;*/
        padding-left: 4px;
        padding-bottom: 3px;
    }
</style>
