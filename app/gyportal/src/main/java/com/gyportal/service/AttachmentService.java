package com.gyportal.service;

import com.alibaba.fastjson.JSONObject;
import com.gyportal.model.Attachment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * create by lihuan at 18/11/30 11:16
 */
public interface AttachmentService {

    public List<Attachment> getAttachmentsByNewsId(Integer newsId);

    @Transactional
    public void insertAttachment(Attachment attachment);

    @Transactional
    public void updateAttachment(Attachment attachment);

    public void deleteAttachmentByNewsId(Integer newsId, String tableName);

    @Transactional
    public void batInsertAttachment(JSONObject jsonObject, Integer newsId, String tableName);

    public Map<String, String> asyncUploadAttachment(Map<String, MultipartFile> fileMap, String dir);

}
