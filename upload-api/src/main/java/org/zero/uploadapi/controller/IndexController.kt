package org.zero.uploadapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.zero.uploadapi.domain.dao.FileDao
import org.zero.uploadapi.domain.entity.ResFileRequest
import org.zero.uploadapi.domain.entity.ResFileResult
import org.zero.uploadapi.service.ResFileService


@Controller
@CrossOrigin(origins = ["*"])
class IndexController {

    @Autowired
    lateinit var resFileService: ResFileService


    @RequestMapping("/index", "/")
    fun index(): String {
        return "index"
    }


    @GetMapping("/file/{type}")
    @ResponseBody
    fun listFile(@PathVariable type: String,
                 resFileRequest: ResFileRequest): ResFileResult {
        resFileRequest.type = type
        return resFileService.getResFileResult(resFileRequest)
    }


}