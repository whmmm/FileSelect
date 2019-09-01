package pers.zero.upload.api.domain.entity


/**
 * 资源文件搜索显示结果...
 */
class ResFileResult {

    var fileList: List<ResFile> = emptyList()

    /**
     * 总条数
     */
    var count: Int = 0

    /**
     * 状态
     *  + success
     *  + error
     */
    var state: String = "success"

    /**
     *- 0 成功
     *- -1 未登录
     */
    var code: Int = 0

}