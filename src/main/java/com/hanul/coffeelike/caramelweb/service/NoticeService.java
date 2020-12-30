package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanul.coffeelike.caramelweb.dao.NoticeDAO;
import com.hanul.coffeelike.caramelweb.data.Page;

@Service
public class NoticeService {
	@Autowired
	private NoticeDAO dao;

	public List<Object> getNotice(Page notice) {
		return dao.getNotice(notice);
	}
	
	public int totalCount() {
		return dao.getTotalCount();
	}
}
