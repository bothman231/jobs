package com.botham.news.db.jobs;

public class ErrorMessage {
private String message;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public ErrorMessage(String message, String code) {
	super();
	this.message = message;
	this.code = code;
}
private String code;
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
}
