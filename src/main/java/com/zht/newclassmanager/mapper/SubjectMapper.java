package com.zht.newclassmanager.mapper;

import com.zht.newclassmanager.pojo.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SubjectMapper {
    List<Subject> selectAllSubjects();
}
