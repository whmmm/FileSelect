<template>
    <div class="upload-container">

        <div v-show="listItem.length > 0" class="upload-head">

            <span style="font-size:18px">
                已选择 {{listItem.length}} 张
            </span>

            <div>
                <el-button palin size='small' id="addImage" @click="triggerUploadBtn">继续添加</el-button>
                <el-button plain size="small" type="primary" style="margin-left: 5px" @click="startUpload()">开始上传
                </el-button>
            </div>
        </div>

        <div class="image-container">
            <div v-for="(i,k) in listItem" :key="k" class="list-item">
                <!--<img src="@/assets/image.png"/>-->
                <img :src="i.src"/>

                <!--加载层.-->
                <div class="loading-div" v-if="i.state===1">
                    <img src="@/assets/loading.gif"/>
                </div>

                <!--上传成功.-->
                <div class="upload-success" v-else-if="i.state===2">
                    <p class="upload-success-p">上传成功 <span style="float:right">✔</span></p>
                </div>

                <!--上传失败.-->
                <div class="upload-error" v-if="i.state===3">
                    <p class="upload-error-p">{{i.msg||'上传失败'}} <span style="float:right">✖</span></p>
                </div>

                <!--上传警告.-->
                <div class="upload-error" v-if="i.state===4">
                    <p class="upload-warning-p">{{i.msg}} <span style="float:right">✖</span></p>
                </div>

            </div>

            <el-tooltip class="item" effect="dark" content="点击继续添加图片" placement="top-start">
                <div class="list-item" @click="triggerUploadBtn" v-show="listItem.length>0" style="cursor: pointer">
                    <!--<img src="@/assets/image.png"/>-->
                    <img src="@/assets/image.png" alt="点击继续添加图片"/>


                </div>
            </el-tooltip>

        </div>

        <div class="file-picker-div" v-show="listItem.length ===0">
            <img src="@/assets/image.png"/>
            <div :id="uploadBtnId" class="webUploader-pick">点击选择图片</div>
        </div>

    </div>
</template>

<script>

    export default {
        name: 'UploadFile',
        props: {
            type: {type: String, default: 'image'},
            uploadUrl: {type: String, default: '/file'},
            // 主机访问 形式 http/https 开头
            host: {type: String, default: 'http://localhost:8000'}
        },
        data() {
            return {
                uploadBtnId: 'webUploader_' + randomString_FileSelect(10),

                listItem: [],

                uploader: {}
            }
        },
        methods: {
            triggerUploadBtn() {
                let self = this
                $('#' + self.uploadBtnId).find('.webuploader-element-invisible').trigger('click')
            },
            startUpload() {
                this.listItem.forEach((item) => {
                    if (item.state === 0) {
                        item.state = 1
                    }
                })
                try {
                    this.uploader.upload()
                } catch (e) {
                    console.log(22222)
                }
            }
        },

        mounted() {
            let self = this

            /**
             * 存储 file.id => 在 listItem 数组中的索引
             * */
            let KeyMap = {}

            let accepts = []

            switch (self.type) {
                case 'image':
                    accepts = {
                        title: 'Images',
                        extensions: 'gif,jpg,jpeg,bmp,png,ico,icon',
                        mimeTypes: 'image/*'
                    }
                    break
                case 'file':
                    accepts = {
                        title: 'file',
                        extensions: 'doc,docx,jpeg,bmp,png',
                        mimeTypes: 'image/*'
                    }
                    break
                case 'video':
                    accepts = {
                        title: 'video',
                        extensions: 'mp4,',
                        mimeTypes: 'audio/*'
                    }
                    break
                case 'audio':
                    accepts = {
                        title: 'audio',
                        extensions: 'mp3,m4a',
                        mimeTypes: 'image/*'
                    }
                    break
            }

            self.uploader = new WebUploader.Uploader({
                //自动上传...
                crop: false,
                auto: false,
                // 文件接收服务端。
                server: self.host + self.uploadUrl,
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: {
                    id: '#' + self.uploadBtnId,
                    multiple: true
                },
                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,
                accept: accepts,
                fileNumLimit: 50,
                chunked: true,
                compress: {
                    // 图片质量，只有type为`image/jpeg`的时候才有效。
                    quality: 20,
                    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                    allowMagnify: false,
                    // 是否允许裁剪。
                    crop: false,
                    // 是否保留头部meta信息。
                    preserveHeaders: false,
                    // 如果发现压缩后文件大小比原来还大，则使用原来图片
                    // 此属性可能会影响图片自动纠正功能
                    noCompressIfLarger: false,
                    // 单位字节，如果图片大小小于此值，不会采用压缩。
                    compressSize: 0
                }
            })

            let uploader = self.uploader

            uploader.on('fileQueued', function (file) {
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        src = '缩略图失败.'
                    }
                    /* 0 待上传, 1 上传中 ,2.上传成功, 3.上传失败(错误信息) , 4. 上传错误(警告信息) */
                    self.listItem.push({'id': file.id, 'src': src, 'state': 0, 'msg': ''})
                    KeyMap[file.id] = self.listItem.length - 1

                }, '200', '200')
            })

            uploader.on('uploadSuccess', function (file, resp) {
                console.log(resp)
                let item = self.listItem[KeyMap[file.id]]
                if (resp.state === 'success') {
                    // 上传成功 通知 fileSelect 组件 添加 图片--
                    self.$emit('uploadsuccess', {
                        path: resp.filePath,
                        fileName: file.name,
                        srcIndex: 0
                    });
                    item.state = 2
                } else if (resp.state === 'warning') {
                    item.state = 4
                    item.msg = resp.msg
                }
            })

            uploader.on('uploadError', function (file) {
                let item = self.listItem[KeyMap[file.id]]
                item.state = 3
                item.msg = '网络错误'
            })

            uploader.on('error', function (file) {
                console.log(file)
                if (file === 'F_DUPLICATE') {
                    self.$message({
                        message: '文件已经存在',
                        type: 'warning',
                        showClose: true
                    })
                }
            })

        }
    }
</script>


<style scoped>
    .upload-head {
        margin-bottom: 10px;
        display: flex;
        justify-content: space-between;
        margin-right: 10px;
        margin-left: 10px;
    }

    .upload-container {
        min-height: 300px;
    }

    .file-picker-div {
        position: relative;
        text-align: center;
        padding-top: 50px;
    }

    .list-item {
        width: 18%;
        margin-left: 2%;
        margin-bottom: 10px;
        position: relative;
    }

    .list-item img {
        border-radius: 5px;
        width: 100%;
    }

    .loading-div {
        position: absolute;
        top: 0;
        background: rgba(0, 0, 0, 0.2);
        width: 100%;
        height: 100%;
    }

    .loading-div img {
        width: 50%;
        text-align: center;
        margin-top: 25%;
        margin-left: 25%;
    }

    .upload-success {
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    .upload-success-p {
        width: 100%;
        margin-bottom: 0;
        background: #007bff;
        color: #fff;
        /*font-size: 14px;*/
        opacity: 1;

        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .upload-error {
        position: absolute;
        bottom: 0;
        width: 100%;
    }

    .upload-error-p {
        width: 100%;
        margin-bottom: 0;
        background: #dc3545;
        color: #fff;
        /*font-size: 18px;*/
        opacity: 1;

        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .upload-warning-p {
        width: 100%;
        margin-bottom: 0;
        background: #ffc107;
        color: #000;
        /*font-size: 18px;*/
        opacity: 1;

        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .image-container {
        display: flex;
        flex-wrap: wrap;
    }


</style>


<style>

    .webuploader-container {
        position: relative;
    }

    .webuploader-element-invisible {
        position: absolute !important;
        clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
        clip: rect(1px, 1px, 1px, 1px);
    }

    .webuploader-pick {
        position: relative;
        display: inline-block;
        cursor: pointer;
        background: #007bff;
        padding: 2px 10px;
        color: #fff;
        text-align: center;
        border-radius: 3px;
        overflow: hidden;
    }

    .webuploader-pick-hover {
        background: #0069d9;
        -webkit-box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.5);
        box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.5);
    }

    .webuploader-pick-disable {
        opacity: 0.6;
        pointer-events: none;
    }

</style>
