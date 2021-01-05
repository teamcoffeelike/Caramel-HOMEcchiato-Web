package com.hanul.coffeelike.caramelweb.data;

import java.sql.Date;

public class Qna{
	private int id;
	private String title;
	private String content;
	private String writer;
	private String name;
	private Date writeDate;

	public Qna(){}
	public Qna(int id, String title, String content, String writer, String name, Date writeDate){
		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.name = name;
		this.writeDate = writeDate;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getWriter(){
		return writer;
	}
	public void setWriter(String writer){
		this.writer = writer;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public Date getWriteDate(){
		return writeDate;
	}
	public void setWriteDate(Date writeDate){
		this.writeDate = writeDate;
	}
}
