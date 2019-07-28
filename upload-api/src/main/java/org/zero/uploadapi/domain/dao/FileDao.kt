package org.zero.uploadapi.domain.dao

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.intellij.lang.annotations.Language
import org.springframework.stereotype.Repository
import org.zero.uploadapi.domain.entity.ResFile
import org.zero.uploadapi.domain.entity.ResFileRequest

/**
 * @author Zero
 */
@Repository
interface FileDao {


    @Select("""
        <script>
            SELECT * FROM res_file  WHERE type=#{sqlType} AND user_key = #{userKey}
            ORDER BY upload_time DESC
            LIMIT #{offset},#{limit}
        </script>
    """)
    fun getPageList(req: ResFileRequest): List<ResFile>

    @Select(""" select * from res_file where md5 = #{fileMd5} """)
    fun getFileByMd5(@Param("fileMd5") fileMd5: String): ResFile?


    @Insert("""
       INSERT INTO res_file (file_name, path, type, md5, user_key,dir_path) 
       VALUES (#{fileName},#{path},#{type},#{md5},#{userKey},#{dirPath})
    """)
    fun addFile(resFile: ResFile)
}