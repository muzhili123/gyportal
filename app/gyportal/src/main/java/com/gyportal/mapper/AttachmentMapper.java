package com.gyportal.mapper;

import com.gyportal.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by lihuan at 18/11/5 15:41
 */
public interface AttachmentMapper extends JpaRepository<Attachment, Integer> {

    @Query(value = "SELECT * FROM attachment WHERE news_id = ?1 and table_name = ?2 and effective = 1", nativeQuery = true)
    public List<Attachment> getAttachmentsByNewsId(Integer newsId, String tableName);

}
