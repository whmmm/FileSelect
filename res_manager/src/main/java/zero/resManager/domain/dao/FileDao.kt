package zero.resManager.domain.dao

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository
import zero.resManager.domain.entity.ResFile
import zero.resManager.domain.entity.ResFileRequest

@Repository
interface FileDao {

    @Select("""
        <script>
            select * from res_file  WHERE type=#{sqlType} AND user_key = #{userKey}
            ORDER BY upload_time DESC
            LIMIT #{offset},#{limit}
        </script>
    """)
    fun getPageList(req: ResFileRequest): List<ResFile>

    @Select(""" select * from res_file where md5 = #{fileMd5} """)
    fun getFileByMd5(@Param("fileMd5") fileMd5: String): ResFile?

    @Insert("""
       insert INTO res_file (file_name, path, type, md5, user_key,dir_path) VALUES (#{fileName},#{path},#{type},#{md5},#{userKey},#{dirPath})
    """)
    fun addFile(resFile: ResFile)
}