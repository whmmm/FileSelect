package pers.zero.upload.api.domain.entity

/**
 * 上传后的相应结果
 */
class UploadResult {

    var filePath: String = ""
    var msg: String = ""


    /**
     * 状态
     *  + success
     *  + error
     */
    var state: String = ""

    /**
     *- 0 成功
     *- -1 未登录
     */
    var code: Int = 0


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
