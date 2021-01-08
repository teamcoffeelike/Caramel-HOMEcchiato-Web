package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.QnaDAO;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;
import com.hanul.coffeelike.caramelweb.data.QnaComment;

@Service
public class QnaService {
	@Autowired
	private QnaDAO dao;
	
	
	public int totalCount(@Nullable String search, @Nullable String keyword) {
		return dao.getTotalCount(search, keyword);
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

	public int insertQnaComment(QnaComment qnaComment) {
		return dao.inserQnaComment(qnaComment);
	}

	public List<QnaComment> qnaCommentList(int qnaId) {
		return dao.qnaCommentList(qnaId);
	}

	public int updateQnaComment(QnaComment qnaComment) {
		return dao.updateQnaComment(qnaComment);
	}

	public int deleteQnaComment(int id) {
		return dao.deleteQnaComment(id);
	}

	
}
