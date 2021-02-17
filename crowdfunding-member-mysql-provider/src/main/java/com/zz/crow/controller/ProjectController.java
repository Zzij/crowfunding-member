package com.zz.crow.controller;

import com.zz.crow.response.ResultEntity;
import com.zz.crow.service.ProjectService;
import com.zz.crow.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){

        try{
            projectService.saveProject(projectVO, memberId);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            return ResultEntity.failed(e.getMessage());
        }
    }
}
