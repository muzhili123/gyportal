package com.gyportal.service;

import com.gyportal.model.Hospital;
import com.gyportal.model.HospitalAddress;
import com.gyportal.model.News;
import com.gyportal.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * create by lihuan at 19/1/11 13:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HospitalServiceTest {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private HospitalAddressService hospitalAddressService;

    String HTML = "<div class=\"logos\">\n" +
            "          <div class=\"hospital-logo\">\n" +
            "            <a target=\"_blank\" href=\"\">\n" +
            "              <img src=\"/assets/images/logos/defualt_logo.png\" alt=\"\">\n" +
            "              <div class=\"logo-text\">\n" +
            "                <span>鄢陵县中医院</span>\n" +
            "              </div>\n" +
            "            </a>\n" +
            "          </div>\n" +
            "          <div class=\"hospital-logo\">\n" +
            "            <a target=\"_blank\" href=\"\">\n" +
            "              <img src=\"/assets/images/logos/defualt_logo.png\" alt=\"\">\n" +
            "              <div class=\"logo-text\">\n" +
            "                <span>禹州市中心医院</span>\n" +
            "              </div>\n" +
            "            </a>\n" +
            "          </div>\n" +
            "          <div class=\"hospital-logo\">\n" +
            "            <a target=\"_blank\" href=\"http://www.ylxrmyy.com/\">\n" +
            "              <img src=\"/assets/images/logos/组 265.png\" alt=\"\">\n" +
            "              <div class=\"logo-text\">\n" +
            "                <span>鄢陵县中心医院</span>\n" +
            "              </div>\n" +
            "            </a>\n" +
            "          </div>\n" +
            "          <div class=\"hospital-logo\">\n" +
            "            <a target=\"_blank\" href=\"http://www.xcszxyy.cn/\">\n" +
            "              <img src=\"/assets/images/logos/defualt_logo.png\" alt=\"\">\n" +
            "              <div class=\"logo-text\">\n" +
            "                <span>许昌市中心医院</span>\n" +
            "              </div>\n" +
            "            </a>\n" +
            "          </div>\n" +
            "          <div class=\"hospital-logo\">\n" +
            "            <a target=\"_blank\" href=\"http://www.cgrmyy.cn/\">\n" +
            "              <img src=\"/assets/images/logos/defualt_logo.png\" alt=\"\">\n" +
            "              <div class=\"logo-text\">\n" +
            "                <span>长葛市人民医院</span>\n" +
            "              </div>\n" +
            "            </a>\n" +
            "          </div>";

    @Test
    public void insertHospitalAddress() {
        HospitalAddress hospitalAddress = new HospitalAddress();
        hospitalAddress.setAddress_name("郑州");
        hospitalAddress.setParent_id(0);

        hospitalAddressService.insertHospitalAddress(hospitalAddress);
    }

    @Test
    public void batInsertHospital() throws Exception {

        String html = HTML;

        int index = 0;

        int tag = 0;
        while (index < html.length() && index >= 0) {
            if (tag == 14) {
                break;
            }
            tag ++;

            //查找指定字符第一次出现的位置
            int urlFirst = html.indexOf("href=\"", index);
            int urlLast = html.indexOf("\">", urlFirst);

            int imgFirst = html.indexOf("<img src=\"", urlLast);
            int imgLast = html.indexOf("\" alt=\"\">", imgFirst);

            int nameFirst = html.indexOf("<span>", imgLast);
            int nameLast = html.indexOf("</span>", nameFirst);

//            if (nameLast > html.length() || nameFirst > html.length()) {
//                break;
//            }

            index = nameLast;

            Hospital hospital = new Hospital();

            hospital.setUrl(html.substring(urlFirst + 6, urlLast));
            if (StringUtils.isBlank(hospital.getUrl())) {
                hospital.setUrl("http://htcc.org.cn");
            }
            hospital.setImage(html.substring(imgFirst + 10, imgLast));
            if (hospital.getImage().equals("/assets/images/logos/defualt_logo.png")) {
                hospital.setImage("http://localhost:8080/static/defualt_logo.png");
            }
            hospital.setName(html.substring(nameFirst + 6, nameLast));
            hospital.setStick(0);
            hospital.setAddressId(10);

            hospitalService.insertHospital(hospital);

        }
    }
}
