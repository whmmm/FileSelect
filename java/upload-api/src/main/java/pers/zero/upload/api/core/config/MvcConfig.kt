package pers.zero.upload.api.core.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pers.zero.upload.api.core.interceptor.UploadInterceptor

/**
 * 配置拦截器
 */
@SpringBootConfiguration
open class MvcConfig : WebMvcConfigurer {

    /**
     * 上传文件 拦截器.
     */
    @Autowired
    lateinit var uploadInterceptor: UploadInterceptor

    override fun addInterceptors(registry: InterceptorRegistry?) {
        if (registry !== null) {
            //上传文件的拦截器.
            registry.addInterceptor(uploadInterceptor).addPathPatterns("/upload");

        }
    }
}