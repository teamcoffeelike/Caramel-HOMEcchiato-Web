package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanul.coffeelike.caramelweb.dao.NoticeDAO;
import com.hanul.coffeelike.caramelweb.data.Notice;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;

@Service
public class NoticeService {
	@Autowired
	private NoticeDAO dao;

	public List<Notice> getNotice(Page notice) {
		return dao.getNotice(notice);
	}
	
	public int totalCount() {
		return dao.getTotalCount();
	}
	
	public int insertNotice(Notice notice) {
		return dao.insertNotice(notice);
	}

	public Notice detailNotice(int id) {
		return dao.detailNotice(id);
	}

	public int updateNotice(Notice notice) {
		return dao.updateNotice(notice);
	}

	public int deleteNotice(int id) {
		return dao.deleteNotice(id);
	}


}
