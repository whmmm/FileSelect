package zero.resManager.core.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import javax.servlet.http.HttpServletResponse

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
    }
}