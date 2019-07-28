package org.zero.uploadapi.service

import org.springframework.web.multipart.MultipartFile
import org.zero.uploadapi.domain.entity.ResFile
import org.zero.uploadapi.domain.entity.ResFileRequest
import org.zero.uploadapi.domain.entity.ResFileResult
import org.zero.uploadapi.domain.entity.UploadResult

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