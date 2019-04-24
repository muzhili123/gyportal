package com.gyportal.controller;

import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import com.gyportal.service.AttachmentService;
import com.gyportal.utils.RequestUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * create by lihuan at 18/11/30 11:50
 */
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Resource
    private AttachmentService attachmentService;

    /**
     *
     * 附件上传
     */
    @PostMapping("/asyncUploadFile")
    public AjaxResponseBody asyncUploadFile(@RequestBody MultipartFile[] file) {
        MultipartFile[] files = file;
        //上传附件
        Map<String, MultipartFile> map = new TreeMap<>();
        for (int i = 0; i < files.length; i++) {
            map.put(files[i].getOriginalFilename(), files[i]);
        }

        Map<String, String> resultMap = attachmentService.asyncUploadAttachment(map, "/u/cms/file");

        List<Map<String, String>> data = new ArrayList<>();
        for (String filename : resultMap.keySet()) {
            Map<String, String> filemap = new HashMap<>();
            filemap.put("name", filename);
            filemap.put("url", resultMap.get(filename));
            data.add(filemap);
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(data).buildMsg("上传成功").build();

    }

    /**
     * 附件删除
     * @param newsId
     * @return
     */
    @PostMapping("/deleteAttachment")
    public AjaxResponseBody deleteAttachment(Integer newsId, HttpServletRequest request) {
        attachmentService.deleteAttachmentByNewsId(newsId, RequestUtil.getTableName(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("删除成功").build();
    }
}
