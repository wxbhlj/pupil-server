package com.vivid.forum.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivid.forum.db.dao.ForumInfoDO;
import com.vivid.forum.db.ForumInfoRepository;
import com.vivid.forum.db.ForumReplyRepository;
import com.vivid.forum.service.ForumInfoService;

@Service
public class ForumInfoServiceImpl implements ForumInfoService {

	@Autowired
	private ForumInfoRepository forumInfoRepository;
	@Autowired
	private ForumReplyRepository forumReplyRepository;

	@Override
	public Page<ForumInfoDO> searchForumInfo(int page, int count, String keyword) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC,"id"));
		
		Specification<ForumInfoDO> spec = new Specification<ForumInfoDO>() {
			@Override    
		    public Predicate toPredicate(Root<ForumInfoDO> root, CriteriaQuery<?> query,CriteriaBuilder cb) { 
		    	/*
				Path<String> nick = root.get("nick");
				Path<String> loginName = root.get("loginName");
				Predicate p1 = cb.like(nick, "%"+keyword+"%");
				Predicate p2 = cb.like(loginName, "%"+keyword+"%");
				Predicate p = cb.or(p1, p2); 
				return p;*/
				return null;
				}
			}; 
		
		return forumInfoRepository.findAll(spec, pageable);
	}

	@Override
	@Transactional
	public void deleteForumInfo(Integer id) {
		forumInfoRepository.deleteById(id);
		forumReplyRepository.deleteByForumId(id);
	}

	@Override
	public Integer saveForumInfo(ForumInfoDO dao) {
		ForumInfoDO saved = forumInfoRepository.saveAndFlush(dao);
		return saved.getId();
	}

	@Override
	public ForumInfoDO findOne(Integer id) {
		return forumInfoRepository.findById(id).orElse(null);
	}
}
