package org.zero.uploadapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
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


    @RequestMapping("/upload/listFile")
    @ResponseBody
    fun listFile(resFileRequest: ResFileRequest): ResFileResult {
        return resFileService.getResFileResult(resFileRequest)
    }


}