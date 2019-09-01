package pers.zero.upload.api.service

import org.springframework.web.multipart.MultipartFile
import pers.zero.upload.api.domain.entity.UploadResult
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 文件上传接口.
 */

interface UploadService {

    /**
     * 文件上传方法
     * @param file  multipartFile 文件
     * @param request request 请求
     */
    fun upload(file: MultipartFile?, request: HttpServletRequest): UploadResult
}