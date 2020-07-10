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

import com.vivid.forum.db.dao.ForumReplyDO;
import com.vivid.forum.db.ForumReplyRepository;
import com.vivid.forum.service.ForumReplyService;

@Service
public class ForumReplyServiceImpl implements ForumReplyService {

	@Autowired
	private ForumReplyRepository forumReplyRepository;

	@Override
	public Page<ForumReplyDO> searchForumReply(int page, int count, int forumId) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC,"id"));
		
		Specification<ForumReplyDO> spec = new Specification<ForumReplyDO>() {
			@Override    
		    public Predicate toPredicate(Root<ForumReplyDO> root, CriteriaQuery<?> query,CriteriaBuilder cb) { 
		    	
			
				return cb.equal(root.get("forumId"), forumId);
		
				}
			}; 
		
		return forumReplyRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteForumReply(Integer id) {
		forumReplyRepository.deleteById(id);
	}

	@Override
	public Integer saveForumReply(ForumReplyDO dao) {
		ForumReplyDO saved = forumReplyRepository.saveAndFlush(dao);
		return saved.getId();
	}

}
