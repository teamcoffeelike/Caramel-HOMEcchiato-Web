package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.QnaDAO;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;

@Service
public class QnaService {
	@Autowired
	private QnaDAO dao;
	
	
	public int totalCount() {
		return dao.getTotalCount();
	}
	
	public List<Qna> getQna(Page page) {
		return dao.getQna(page);
	}

	public int insertQna(Qna qna) {
		return dao.insertQna(qna);
	}

	public Qna detailQna(int id) {
		return dao.detailQna(id);
	}

	public int updateQna(Qna qna) {
		return dao.updateQna(qna);
	}

	public int deleteQna(int id) {
		return dao.deleteQna(id);
	}
}
