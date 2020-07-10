package com.vivid.ums.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivid.ums.db.dao.UserDO;

import com.vivid.ums.db.UserRepository;
import com.vivid.ums.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Page<UserDO> searchUser(int page, int count, String keyword) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC,"id"));
		
		Specification<UserDO> spec = new Specification<UserDO>() {
			@Override    
		    public Predicate toPredicate(Root<UserDO> root, CriteriaQuery<?> query,CriteriaBuilder cb) { 
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
		
		return userRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public Integer saveUser(UserDO dao) {
		UserDO saved = userRepository.saveAndFlush(dao);
		return saved.getId();
	}

	@Override
	public UserDO findOne(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserDO findByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userRepository.findByLoginName(loginName);
	}

	@Override
	@Transactional
	public UserDO register(String mobile) {
		Date now = new Date();
		
		String token = DigestUtils.md5Hex(mobile + String.valueOf(System.currentTimeMillis()) + "@DETREDWwse");
		Date expired = new Date(200, 1, 1); //TODO long expired time first
		
		UserDO user = new UserDO();
		user.setAvatar("");
		user.setCreated(now);
		user.setLoginName(mobile);
		user.setNick("");
		user.setToken(token);
		user.setTokenExpired(expired);
		user.setUpdated(now);
		user = userRepository.saveAndFlush(user);
		return user;
	}

	@Override
	public UserDO findByToken(String token) {
		return userRepository.findByToken(token);
	}

}
	