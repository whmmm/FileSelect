package pers.zero.upload.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import pers.zero.upload.api.domain.entity.ResFileRequest
import pers.zero.upload.api.domain.entity.ResFileResult
import pers.zero.upload.api.service.ResFileService


@Controller
@CrossOrigin(origins = ["*"])
class IndexController {

    @Autowired
    lateinit var resFileService: ResFileService


    @RequestMapping("/index", "/")
    fun index(): String {
        return "index"
    }


    @GetMapping("/file")
    @ResponseBody
    fun listFile(
                 resFileRequest: ResFileRequest): ResFileResult {
        // resFileRequest.type = type
        return resFileService.getResFileResult(resFileRequest)
    }


}