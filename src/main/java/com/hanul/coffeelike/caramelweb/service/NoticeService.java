package com.hanul.coffeelike.caramelweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.hanul.coffeelike.caramelweb.dao.NoticeDAO;
import com.hanul.coffeelike.caramelweb.data.Notice;
import com.hanul.coffeelike.caramelweb.data.Page;

@Service
public class NoticeService {
	@Autowired
	private NoticeDAO dao;

	public List<Notice> getNotice(Page notice) {
		return dao.getNotice(notice);
	}
	
	public int totalCount(@Nullable String search, @Nullable String keyword) {
		return dao.getTotalCount(search, keyword);
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

	public int getIndex(int noticeId, @Nullable String search, @Nullable String keyword) {
		return dao.getIndex(noticeId, search, keyword);
	}
}
