package com.gis.lbs.pojo;

public class Lesson {

	  /*`id` int(11) NOT NULL AUTO_INCREMENT,
	  `lessonName` varchar(255) DEFAULT NULL,
	  `teacherName` varchar(255) DEFAULT NULL,
	  `comment` varchar(255) DEFAULT NULL,
	  `lessonPicUrl` varchar(255) DEFAULT NULL,
	  `collection` char(255) DEFAULT NULL,
	  `detail` varchar(1000) DEFAULT NULL,*/
	private int id;
	private String lessonName;
	private String teacherName;
	private String comment;
	private String lessonPicUrl;
	private String collection;
	private String detail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLessonPicUrl() {
		return lessonPicUrl;
	}
	public void setLessonPicUrl(String lessonPicUrl) {
		this.lessonPicUrl = lessonPicUrl;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
}
