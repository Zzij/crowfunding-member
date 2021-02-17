package com.zz.crow.service;

import com.zz.crow.vo.ProjectVO;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
}
