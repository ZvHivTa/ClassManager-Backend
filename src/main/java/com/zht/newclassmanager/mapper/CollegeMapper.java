package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollegeMapper {
    @Select("select college_id as id, college_name as name from college")
    List<College> selectAllColleges();
}
