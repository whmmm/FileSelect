package pers.zero.upload.api.service

import org.springframework.web.multipart.MultipartFile
import pers.zero.upload.api.domain.entity.ResFile
import pers.zero.upload.api.domain.entity.ResFileRequest
import pers.zero.upload.api.domain.entity.ResFileResult
import pers.zero.upload.api.domain.entity.UploadResult

/**
 * 资源文件服务层.
 */
interface ResFileService {

    /**
     * 根据请求获取响应的文件数据.列出文件
     */
    fun getResFileResult(resFileRequest: ResFileRequest): ResFileResult


    /**
     * 保存 request file 文件.
     */
    fun saveFile(file: MultipartFile): UploadResult


}