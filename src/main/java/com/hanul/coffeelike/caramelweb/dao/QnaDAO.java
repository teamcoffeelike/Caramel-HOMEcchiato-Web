package com.hanul.coffeelike.caramelweb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;
import com.hanul.coffeelike.caramelweb.data.QnaComment;

@Repository
public class QnaDAO {
	@Autowired
	private SqlSession sql;
	
	public int getTotalCount() {
		return sql.selectOne("qna.getTotalCount");
	}
	
	public List<Qna> getQna(Page page) {
		return sql.selectList("qna.getQnas", page);
	}

	public int insertQna(Qna qna) {
		return sql.insert("qna.insert", qna);
	}

	public Qna detailQna(int id) {
		return sql.selectOne("qna.detail", id);
	}

	public int updateQna(Qna qna) {
		return sql.update("qna.update", qna);
	}

	public int deleteQna(int id) {
		return sql.delete("qna.delete", id);
	}

	public int inserQnaComment(QnaComment qnaComment) {
		return sql.insert("qna.insertQnaComment", qnaComment);
	}

	public List<QnaComment> qnaCommentList(int qnaId) {
		return sql.selectList("qna.commentList", qnaId);
	}

	public int updateQnaComment(QnaComment qnaComment) {
		return sql.update("qna.updateComment", qnaComment);
	}

	public int deleteQnaComment(int id) {
		return sql.update("qna.deleteComment", id);
	}
}