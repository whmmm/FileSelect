package pers.zero.upload.api.core.interceptor

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import pers.zero.upload.api.core.util.ResponseUtil
import pers.zero.upload.api.domain.entity.UploadResult
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UploadInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        // return super.preHandle(request, response, handler)
        if (request !== null && response !== null) {
            val token = request.getHeader("upload-token");

            //先禁用拦截器.
            if (token === null && false) {
                ResponseUtil.send(UploadResult.error("token为空,不能上传!"))
                return false
            }


            return true

        } else {
            return false
        }
    }


}