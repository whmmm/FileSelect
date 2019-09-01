package pers.zero.upload.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import pers.zero.upload.api.domain.entity.UploadResult
import pers.zero.upload.api.service.UploadService
import javax.servlet.http.HttpServletRequest

@Controller
@CrossOrigin(origins = ["*"])
class UploadController {


    @Autowired
    lateinit var uploadService: UploadService

    /**
     *定义上传文件接口
     */
    @PostMapping("/file")
    @ResponseBody
    fun upload(file: MultipartFile?, request: HttpServletRequest): UploadResult {
        return uploadService.upload(file, request)
    }
}