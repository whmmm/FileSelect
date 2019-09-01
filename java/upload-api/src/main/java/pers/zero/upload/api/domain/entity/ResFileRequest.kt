package pers.zero.upload.api.domain.entity

import pers.zero.upload.api.core.AppValue

/**
 * 搜索文件请求.
 */
class ResFileRequest {
    var type: String = ""

    var sqlType: Int = 1

    var userKey: String = ""

    /**
     * 初始化信息.分页,在数据中的类型等等.
     */
    fun initData() {
        val type = this.type
        if (type.equals("image", true)) {
            this.sqlType = 1
        } else if (type.equals("doc", true)) {
            this.sqlType = 2
        } else if (type.equals("video", false)) {
            this.sqlType = 3
        } else if (type.equals("audio", false)) {
            this.sqlType = 4
        }


        this.page = if (this.page < 1) {
            1
        } else {
            this.page
        }

        this.limit = if (this.limit < 1) {
            1
        } else {
            this.limit
        }

        this.userKey = AppValue.UserKey

        this.offset = (this.page - 1) * this.limit
    }

    //分页信息.(LIMIT 后面的偏移量)
    var offset = 0


    // 分页请求信息.
    var page: Int = 1
    var limit: Int = 10
}