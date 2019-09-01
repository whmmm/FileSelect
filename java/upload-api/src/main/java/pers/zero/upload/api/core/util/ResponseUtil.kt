package pers.zero.upload.api.core.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpServletResponse
import org.springframework.web.context.request.ServletWebRequest


/**
 * 响应工具类
 */
class ResponseUtil {


    companion object {
        /**
         *  输出json,并且结束当前response请求
         *  @param response  响应流
         *  @param data  ajax要返回的数据..
         */
        fun send(response: HttpServletResponse, data: Any) {
            response.characterEncoding = "UTF-8";
            response.contentType = "application/json; charset=utf-8";

            val writer = response.writer
            writer.write(JSONObject.toJSON(data).toString())
            writer.close()
        }

        /**
         * 相应输出 response 返回 class
         * @see ResponseUtil.send
         */
        fun send(data: Any) {
            (RequestContextHolder.getRequestAttributes() as ServletWebRequest).response
                    ?.let {
                        send(it, data)
                    }

        }
    }
}