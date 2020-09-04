package com.gis.lbs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.gis.lbs.pojo.Lesson;

import java.util.List;

@Mapper
public interface LBSMapper {

	@Select("select * from t_lbs where id = #{id}")
	public Lesson getLessonsById(int id);

	@Select("select * from t_lbs")
	public List<Lesson> queryLessons();
	
}
