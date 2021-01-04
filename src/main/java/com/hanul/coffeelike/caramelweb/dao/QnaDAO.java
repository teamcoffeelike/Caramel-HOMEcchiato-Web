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
		return sql.selectOne("notice.getTotalCount");
	}
	
	public List<Object> getQna(Page qna) {
		return sql.selectList("qna.getQnas", qna);
	}

	public int qna_insert(Qna qna) {
		return sql.insert("qna.insert", qna);
	}

	public Qna qna_detail(int id) {
		return sql.selectOne("qna.detail", id);
	}

	public int qna_update(Qna qna) {
		return sql.update("qna.update", qna);
	}
}
