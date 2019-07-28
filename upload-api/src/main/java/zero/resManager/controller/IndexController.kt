package zero.resManager.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import zero.resManager.domain.dao.FileDao
import zero.resManager.domain.entity.ResFileRequest
import zero.resManager.domain.entity.ResFileResult
import zero.resManager.service.ResFileService


@Controller
@CrossOrigin(origins = ["*"])
class IndexController {

    @Autowired
    lateinit var resFileService: ResFileService


    @RequestMapping("/index", "/")
    fun index(): String {
        return "index"
    }


    @RequestMapping("/upload/listFile")
    @ResponseBody
    fun listFile(resFileRequest: ResFileRequest): ResFileResult {
        return resFileService.getResFileResult(resFileRequest)
    }


}