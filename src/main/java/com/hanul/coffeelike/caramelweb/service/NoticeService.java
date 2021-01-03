package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanul.coffeelike.caramelweb.dao.NoticeDAO;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;

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

	public List<Object> getQna(Page qna) {
		return dao.getQna(qna);
	}

	public int qna_insert(Qna qna) {
		return dao.qna_insert(qna);
	}

	public Qna qna_detail(int id) {
		return dao.qna_detail(id);
	}

	public int qna_update(Qna qna) {
		return dao.qna_update(qna);
	}
}
