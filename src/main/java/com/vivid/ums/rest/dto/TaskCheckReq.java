package com.vivid.ums.rest.dto;


public class TaskCheckReq {
	//
	private Integer id;

	//
	private String title;

	//
	private Integer score;
	
	private Integer spendTime;

	//
	private String comments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(Integer spendTime) {
		this.spendTime = spendTime;
	}

	@Override
	public String toString() {
		return "TaskCheckReq [id=" + id + ", title=" + title + ", score=" + score + ", spendTime=" + spendTime
				+ ", comments=" + comments + "]";
	}
	

}
