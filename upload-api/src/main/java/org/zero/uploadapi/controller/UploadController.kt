package org.zero.uploadapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import org.zero.uploadapi.domain.entity.UploadResult
import org.zero.uploadapi.service.UploadService
import javax.servlet.http.HttpServletRequest

@Controller
@CrossOrigin(origins = ["*"])
class UploadController {


    @Autowired
    lateinit var uploadService: UploadService

    /**
     *定义上传文件接口
     */
    @RequestMapping("/upload")
    @ResponseBody
    fun upload(file: MultipartFile?, request: HttpServletRequest): UploadResult {
        return uploadService.upload(file, request)
    }
}