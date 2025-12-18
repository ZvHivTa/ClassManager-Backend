package com.zht.newclassmanager.service.impl;

import com.zht.newclassmanager.mapper.CollegeMapper;
import com.zht.newclassmanager.pojo.College;
import com.zht.newclassmanager.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<College> getColleges() {
        List<College> colleges = collegeMapper.selectAllColleges();
        return colleges;
    }
}
