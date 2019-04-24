package com.gyportal.service;

import com.gyportal.model.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * create by lihuan at 18/11/15 18:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PictureServiceTest {

    @Resource
    private PictureService pictureService;

    @Test
    public void insertPictureTest() {
        List<Picture> pictures = new ArrayList<>();

        pictures.add(new Picture("/u/cms/www/201805/24181638g2am.jpg", "测试"));
        pictures.add(new Picture("/u/cms/www/201805/24181638g2am.jpg", "测试2"));

        for (Picture picture : pictures) {
            pictureService.insertPicture(picture);
        }
    }

    @Test
    public void findAllTest() {
//        Page<Picture> pictures = pictureService.findAll(3, 1);
        Page<Picture> pictures = pictureService.findAllByDescription(3, 1, "2");
        for (Picture picture : pictures) {
            System.out.println(picture);
        }
    }
}
