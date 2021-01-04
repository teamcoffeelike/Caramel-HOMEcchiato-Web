package com.hanul.coffeelike.caramelweb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;

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
}
