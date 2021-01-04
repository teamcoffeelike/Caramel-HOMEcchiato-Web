package com.hanul.coffeelike.caramelweb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanul.coffeelike.caramelweb.data.Notice;
import com.hanul.coffeelike.caramelweb.data.Page;
import com.hanul.coffeelike.caramelweb.data.Qna;

@Repository
public class NoticeDAO {
	@Autowired
	private SqlSession sql;

	public List<Notice> getNotice(Page notice) {
		return sql.selectList("notice.getNotices", notice);
	}

	public int getTotalCount() {
		return sql.selectOne("notice.getTotalCount");
	}

	public int insertNotice(Notice notice) {
		return sql.insert("notice.insert", notice);
	}

	public Notice detailNotice(int id) {
		return sql.selectOne("notice.detail", id);
	}

	public int updateNotice(Notice notice) {
		return sql.update("notice.update", notice);
	}

	public int deleteNotice(int id) {
		return sql.delete("notice.delete", id);
	}


}
