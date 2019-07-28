package zero.resManager.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import zero.resManager.domain.entity.UploadResult
import zero.resManager.service.ResFileService
import zero.resManager.service.UploadService
import javax.servlet.http.HttpServletRequest

/**
 * 文件上传实现类
 */
@Service
class UploadServiceImpl : UploadService {

    @Autowired
    lateinit var resfileService: ResFileService

    override fun upload(file: MultipartFile?, request: HttpServletRequest): UploadResult {
        if (file === null) {
            return UploadResult.error("上传文件不能为空.");
        }

        // 文件不为空,执行保存文件.
        return resfileService.saveFile(file)

    }


}