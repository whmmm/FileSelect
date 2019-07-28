package org.zero.uploadapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.zero.uploadapi.domain.entity.UploadResult
import org.zero.uploadapi.service.ResFileService
import org.zero.uploadapi.service.UploadService
import javax.servlet.http.HttpServletRequest

/**
 * 文件上传实现类
 */
@Service
class UploadServiceImpl : UploadService {

    @Autowired
    lateinit var resFileService: ResFileService

    override fun upload(file: MultipartFile?, request: HttpServletRequest): UploadResult {
        if (file === null) {
            return UploadResult.error("上传文件不能为空.");
        }

        // 文件不为空,执行保存文件.
        return resFileService.saveFile(file)

    }


}