package zero.resManager.domain.entity

class UploadResult {

    var filePath: String = ""
    var msg: String = ""


    /**
     * 状态
     *  + success
     *  + error
     */
    var state: String = ""


    companion object {

        fun error(msg: String): UploadResult {
            val res = UploadResult()
            res.msg = msg
            res.state = "error"
            return res
        }

        fun success(filePath: String, msg: String = "上传成功!"): UploadResult {
            val res = UploadResult()
            res.msg = msg
            res.filePath = filePath
            res.state = "success"
            return res
        }

    }
}
