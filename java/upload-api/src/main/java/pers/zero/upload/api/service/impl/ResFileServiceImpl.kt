package pers.zero.upload.api.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.DigestUtils
import org.springframework.web.multipart.MultipartFile
import pers.zero.upload.api.core.AppValue
import pers.zero.upload.api.domain.dao.FileDao
import pers.zero.upload.api.domain.entity.ResFile
import pers.zero.upload.api.domain.entity.ResFileRequest
import pers.zero.upload.api.domain.entity.ResFileResult
import pers.zero.upload.api.domain.entity.UploadResult
import pers.zero.upload.api.service.ResFileService
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.Exception as Exception1


@Service
open class ResFileServiceImpl : ResFileService {

    @Autowired
    lateinit var fileDao: FileDao

    /*@Autowired
    lateinit var AppValue:AppValue*/

    override fun getResFileResult(resFileRequest: ResFileRequest): ResFileResult {

        resFileRequest.initData()
        //在这里执行分页.

        val result = ResFileResult()

        result.fileList = fileDao.getPageList(resFileRequest)

        return result

    }

    @Transactional
    override fun saveFile(file: MultipartFile): UploadResult {
        val uploadResult = UploadResult()
        var srcName = file.originalFilename
        if (srcName === null) {
            //没有名字.随机命名一个.
            srcName = UUID.randomUUID().toString().replace("-", "")
        }


        /*检测文件的签名.如果数据库中存在.那么直接获取此url*/
        val fileMd5 = DigestUtils.md5DigestAsHex(file.inputStream)

        val daoResFile = fileDao.getFileByMd5(fileMd5)

        if (daoResFile !== null) {
            if (daoResFile.id != 0) {
                //如果id 不为0. 那么代表 数据库存在这张图片了.
                if (daoResFile.userKey == AppValue.UserKey) {
                    uploadResult.state = "warning"
                    uploadResult.msg = "已上传过."
                }
            } else {
                daoResFile.userKey = AppValue.UserKey
                //修改文件的 所有人,将数据添加到数据库.
                fileDao.addFile(daoResFile)
            }
        } else {
            // 将文件保存到服务器.
            val resFile = ResFile()
            resFile.md5 = fileMd5

            uploadResult.state = "success"

            //上传保存的文件夹.
            val savePath = AppValue.UploadPath + "/" + AppValue.UserKey


            //上传文件夹 + 文件名.
            var fileFullPath = "$savePath/$srcName"

            //验证文件是否 已经存在了.
            if (Files.exists(Paths.get(fileFullPath))) {
                fileFullPath = "$savePath/" + System.currentTimeMillis() + "_$srcName"
            }

            //相对于服务器的绝对路径.
            resFile.path = "/upload/${AppValue.UserKey}/$srcName"
            //默认为图片上传类型.
            resFile.type = 1
            resFile.fileName = srcName
            resFile.userKey = AppValue.UserKey
            resFile.dirPath = savePath

            try {
                val path = Paths.get(savePath)
                if (!Files.exists(path)) {
                    Files.createDirectories(path)
                }

                //数据库中添加这行数据..
                fileDao.addFile(resFile)

                //执行文件复制...
                Files.copy(file.inputStream, Paths.get(fileFullPath), StandardCopyOption.REPLACE_EXISTING)
            } catch (e: Exception1) {
                println(e.message)
                throw e
            }


        }

        //println(AppValue.UploadPath)

        return uploadResult

    }
}