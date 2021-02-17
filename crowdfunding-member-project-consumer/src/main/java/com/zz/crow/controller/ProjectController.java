package com.zz.crow.controller;

import com.netflix.discovery.converters.Auto;
import com.zz.crow.api.MysqlRemoteService;
import com.zz.crow.config.CustomProperties;
import com.zz.crow.constant.CrowdConstant;
import com.zz.crow.response.ResultEntity;
import com.zz.crow.util.CrowUtil;
import com.zz.crow.vo.MemberConfirmInfoVO;
import com.zz.crow.vo.MemberLoginVO;
import com.zz.crow.vo.ProjectVO;
import com.zz.crow.vo.ReturnVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private MysqlRemoteService mysqlRemoteService;

    @RequestMapping("/create/project/information")
    public String saveProjectInfo(ProjectVO projectVO,
                                  //头图
                                  MultipartFile headerPicture,
                                  //申请的多张照片
                                  List<MultipartFile> detailPictureList,
                                  HttpSession session,
                                  ModelMap modelMap) throws IOException {
        //头图处理
        boolean empty = headerPicture.isEmpty();
        if(!empty){
            ResultEntity<String> resultEntity = CrowUtil.uploadPicture(customProperties.uploadPath, headerPicture.getInputStream(), headerPicture.getOriginalFilename());

            if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
                projectVO.setHeaderPicturePath(resultEntity.getData());
            }else{
                modelMap.addAttribute("message", CrowdConstant.PICTURE_UPLOAD_FAILED);
                return "project-launch";
            }
        }else{
            modelMap.addAttribute("message", CrowdConstant.HEAD_PICTURE_EMPTY);
            return "project-launch";
        }

        //详图处理
        ArrayList<String> detailPaths = new ArrayList<>();
        for (MultipartFile detailPicture : detailPictureList) {
            if(detailPicture.isEmpty()){
                continue;
            }
            ResultEntity<String> resultEntity = CrowUtil.uploadPicture(customProperties.uploadPath, detailPicture.getInputStream(), detailPicture.getOriginalFilename());
            if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
                detailPaths.add(resultEntity.getData());
            }
        }
        projectVO.setDetailPicturePathList(detailPaths);

        //存放session里
        session.setAttribute(CrowdConstant.SESSION_TEMP_PROJECTVO, projectVO);
        return "redirect:http://localhost/project/return/project/page";

    }

    @RequestMapping("create/upload/return/picture")
    @ResponseBody
    public ResultEntity<String> uploadReturnPicture(MultipartFile returnPicture) throws IOException {

        boolean empty = returnPicture.isEmpty();
        if(empty){
            return ResultEntity.failed(CrowdConstant.RETURN_PICTURE_EMPTY);
        }
        ResultEntity<String> resultEntity = CrowUtil.uploadPicture(customProperties.uploadPath, returnPicture.getInputStream(), returnPicture.getOriginalFilename());
        return resultEntity;
    }

    @RequestMapping("create/save/return")
    @ResponseBody
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {

        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.SESSION_TEMP_PROJECTVO);
        if(projectVO == null){
            return ResultEntity.failed(CrowdConstant.SESSION_TEMP_PROJECTVO_NOT_EXIST);
        }
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        if(returnVOList == null){
            returnVOList = new ArrayList<>();
            projectVO.setReturnVOList(returnVOList);
        }
        returnVOList.add(returnVO);
        session.setAttribute(CrowdConstant.SESSION_TEMP_PROJECTVO, projectVO);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("create/confirm")
    public String confirm(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session,
                          ModelMap modelMap) {

        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.SESSION_TEMP_PROJECTVO);
        if(projectVO == null){
            modelMap.addAttribute("message", CrowdConstant.SESSION_TEMP_PROJECTVO_NOT_EXIST);
            return "redirect:http://localhost/project/launch/project/page";
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.MEMBER_SESSION_NAME);

        ResultEntity<String> resultEntity = mysqlRemoteService.saveProjectVORemote(projectVO, memberLoginVO.getId());

        logger.info("result {}", resultEntity);

        if(ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute("message", resultEntity.getMessage());
            return "project-confirm";
        }

        session.removeAttribute(CrowdConstant.SESSION_TEMP_PROJECTVO);

        return "redirect:http://localhost/project/create/success/page";
    }

}
