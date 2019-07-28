package org.zero.uploadapi.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppValue {


    @Value("\${upload-path}")
    fun setUploadPath(uploadPath: String) {
        UploadPath = uploadPath

    }

    companion object {
        /**
         * 存储在 user_key (测试用的,真实的环境这个应该是一个变量.)
         */
        var UserKey: String = "user_test"


        /**
         *  静态资源上传路径
         */
        var UploadPath: String? = null
    }


}