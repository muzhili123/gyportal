package com.gyportal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyportal.model.AjaxResponseBody;
import com.gyportal.model.Enum.Status;
import com.gyportal.model.Hospital;
import com.gyportal.model.HospitalAddress;
import com.gyportal.model.PageResult;
import com.gyportal.service.HospitalAddressService;
import com.gyportal.service.HospitalService;
import com.gyportal.utils.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * create by lihuan at 18/11/4 11:37
 */
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private HospitalAddressService hospitalAddressService;

    private static final Integer MAX_STICK_HOSPITAL = 8;

    private static final String DEFAULT_URL = "http://htcc.org.cn";

    private static final String DEFAULT_IMAGE = "/u/cms/www/logo/cd169571-719f-4547-a99c-b45550d64978.png";


    /**
     * 查询所有医院
     * @return
     */
    @GetMapping("findAll")
    public AjaxResponseBody findAll() {
        List<Hospital> hospitals = hospitalService.findAll();

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(hospitals).build();
    }

    /**
     * 分页查询医院
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/findAllByPage")
    public AjaxResponseBody findAllByPage(int pageSize, int pageNum, HttpServletRequest request) {
        PageResult pageResult = hospitalService.findAll(pageSize, pageNum, RequestUtil.getContentLanguage(request));

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(pageResult).build();
    }

    /**
     * 名称查询所有医院地址
     * @return
     */
    @GetMapping("/findHospitalAddressByName")
    public AjaxResponseBody findHospitalAddressByName(@RequestParam(value = "addressName", required = false) String addressName) {

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(hospitalAddressService.findHospitalAddressByName(addressName)).build();
    }

    /**
     * 医院id查询
     * @param id
     * @return
     */
    @GetMapping("/getHospitalById")
    public AjaxResponseBody getHospitalById(int id, HttpServletResponse response) {

        Hospital hospital = hospitalService.getOne(id);
        if (hospital == null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("医院不存在").build();
        }

        HospitalAddress hospitalAddress = hospitalAddressService.getHospitalAddressById(hospital.getAddressId());
        if (hospitalAddress == null) {
            response.setStatus(400, "id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildData("id不存在").build();
        }

        HashMap<String, Object> map = hospitalService.packHospital(hospital, hospitalAddress);

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(map).build();
    }

    /**
     * 医院地址id查询
     * @return
     */
    @GetMapping("/getHospitalAddressByAddressId")
    public AjaxResponseBody getHospitalAddressByAddressId(int addressId) {

        HospitalAddress hospitalAddress = hospitalAddressService.getHospitalAddressById(addressId);
        if (hospitalAddress == null) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("医院地址不存在").build();
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildData(hospitalAddress).build();
    }

    /**
     * 查询置顶医院
     * @return
     */
    @GetMapping("/getStickHospital")
    public AjaxResponseBody getStickHospital(HttpServletRequest request,
                                             @RequestParam(value = "count", required = false, defaultValue = "0") Integer count) {

        //判断是否有参
        if (count == 0) {
            count = hospitalService.getStickHospitalsCount();
        }

        List<Hospital> hospitals = hospitalService.getStickHospitals(RequestUtil.getContentLanguage(request), count);

        List<HashMap<String, Object>> mapList = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            HospitalAddress hospitalAddress = hospitalAddressService.getHospitalAddressById(hospital.getAddressId());
            mapList.add(hospitalService.packHospital(hospital, hospitalAddress));
        }

        return new AjaxResponseBody.Builder().buildStatus(Status.OK)
                .buildData(mapList).build();
    }

    /**
     * 设置置顶医院
     * @return
     */
    @GetMapping("/setStickHospital")
    public AjaxResponseBody setStickHospital(int id, HttpServletResponse response) {

        Hospital hospital = hospitalService.getOne(id);
        if (hospital.getStick() == 0) { //置顶

//            Integer stickHospitalsCount = hospitalService.getStickHospitalsCount();
            Integer maxPriority = hospitalService.getMaxPriorityHospital();
            if (maxPriority == null) {
                maxPriority = 0;
            }

            //验证最大量
            if (maxPriority < MAX_STICK_HOSPITAL) {
                System.out.println(maxPriority + ":" + MAX_STICK_HOSPITAL);

                hospital.setStick(maxPriority + 1);
                hospitalService.updateHospital(hospital);
                return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("置顶成功").build();
            } else {
                response.setStatus(400, "置顶医院数量不得超过" + MAX_STICK_HOSPITAL + "个");
                return new AjaxResponseBody.Builder().buildStatus(Status.FAIL)
                        .buildMsg("置顶医院数量不得超过" + MAX_STICK_HOSPITAL + "个").build();
            }

        } else {  //取消置顶

            hospitalService.cancelStickHospital(hospital);

            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("取消置顶成功").build();
        }

    }

    /**
     * 设置置顶医院优先级
     * @param id
     * @param priority
     * @return
     */
    @GetMapping("/setHospitalPriority")
    public AjaxResponseBody setHospitalPriority(int id, String priority) {

        Hospital hospital = hospitalService.getOne(id);
        if (hospital.getStick() == 0) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("医院未置顶").build();
        }

        if (priority.equals("rise") && hospital.getStick() != 1){  //提升

            Hospital previous = hospitalService.findHospitalByStick(hospital.getStick() - 1);
            if (previous != null) { //原升一
                previous.setStick(hospital.getStick());
                hospitalService.updateHospital(previous);
            }

            hospital.setStick(hospital.getStick() - 1);
            hospitalService.updateHospital(hospital);

            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("优先级提升成功").build();

        } else if (priority.equals("drop") && hospital.getStick() != MAX_STICK_HOSPITAL){ //降低

            Hospital next = hospitalService.findHospitalByStick(hospital.getStick() + 1);

            if (next != null) {
                next.setStick(hospital.getStick());
                hospitalService.updateHospital(next);
            }

            hospital.setStick(hospital.getStick() + 1);
            hospitalService.updateHospital(hospital);

            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("优先级降低成功").build();

        }
        return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("优先级更改失败").build();

    }


    /**
     * 增加
     * @param
     * @return
     */
    @PostMapping("/insertHospital")
    public AjaxResponseBody insertHospital(@RequestBody String jsonString) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        Hospital hospital = new Hospital();
        hospital.setName(jsonObject.getString("name"));
        hospital.setNameEn(jsonObject.getString("nameEn"));
        hospital.setImage(jsonObject.getString("image"));
        hospital.setUrl(jsonObject.getString("url"));
        hospital.setStick(0);

        String addressName = jsonObject.getString("addressName");
        String addressNameEn = jsonObject.getString("addressNameEn");
        Integer addressId = hospitalAddressService.findByAddressName(addressName, addressNameEn);
        hospital.setAddressId(addressId);

        if (StringUtils.isBlank(hospital.getUrl())) {
            hospital.setUrl(DEFAULT_URL);
        }
        if (StringUtils.isBlank(hospital.getImage())) {
            hospital.setImage(DEFAULT_IMAGE);
        }

        try {
            hospitalService.insertHospital(hospital);
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("插入成功").build();
        } catch (Exception e) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

    }

    /**
     * 更新
     * @param
     * @return
     */
    @PostMapping("/updateHospital")
    public AjaxResponseBody updateHospital(@RequestBody String jsonString,
                                           HttpServletResponse response) {

        JSONObject jsonObject = JSON.parseObject(jsonString);
        Hospital hospital = new Hospital();
        hospital.setId((Integer) jsonObject.get("id"));

        Hospital saveHospital = hospitalService.getOne(hospital.getId());
        if (saveHospital == null) {
            response.setStatus(400, "id不存在");
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("id不存在").build();
        }

        hospital.setName(jsonObject.getString("name"));
        hospital.setNameEn(jsonObject.getString("nameEn"));
        hospital.setImage(jsonObject.getString("image"));
        hospital.setUrl(jsonObject.getString("url"));
        hospital.setStick(saveHospital.getStick());

        String addressName = jsonObject.getString("addressName");
        String addressNameEn = jsonObject.getString("addressNameEn");
        Integer addressId = hospitalAddressService.findByAddressName(addressName, addressNameEn);
        hospital.setAddressId(addressId);

        try {
            hospitalService.updateHospital(hospital);
            return new AjaxResponseBody.Builder().buildStatus(Status.OK).buildMsg("更新成功").build();
        } catch (Exception e) {
            return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg(e.getMessage()).build();
        }

    }

    /**
     * 删除
     * @param groupId 医院id集合list
     * @return
     */
    @PostMapping("/deleteHospital")
    public AjaxResponseBody deleteHospital(@RequestBody String groupId, HttpServletResponse response) {
        JSONObject jsonObject = JSON.parseObject(groupId);
        JSONArray jsonArray = (JSONArray) jsonObject.get("groupId");
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {

            Integer hospitalId = (Integer) jsonArray.get(i);
            Hospital hospital = hospitalService.getOne(hospitalId);
            if (hospital == null) {
                response.setStatus(400, "医院id不存在");
                return new AjaxResponseBody.Builder().buildStatus(Status.FAIL).buildMsg("医院id不存在").build();
            }

            if (hospital.getStick() != 0) { //取消置顶
                hospitalService.cancelStickHospital(hospital);
            }

            ids.add(hospitalId + "");
        }

        hospitalService.deleteHospital(ids);
        return new AjaxResponseBody.Builder().buildStatus(Status.OK).build();
    }

}
