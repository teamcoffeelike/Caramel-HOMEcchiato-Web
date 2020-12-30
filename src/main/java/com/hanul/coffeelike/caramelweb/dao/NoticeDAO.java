package com.hanul.coffeelike.caramelweb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.Page;

@Repository
public class NoticeDAO {
	@Autowired
	private SqlSession sql;

	public List<Object> getNotice(Page notice) {
		return sql.selectList("notice.getNotices", notice);
	}

	public int getTotalCount() {
		return sql.selectOne("notice.getTotalCount");
	}
}
