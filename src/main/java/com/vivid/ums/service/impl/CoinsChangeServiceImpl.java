package com.vivid.ums.service.impl;

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

import com.vivid.ums.db.dao.CoinsChangeDO;
import com.vivid.ums.db.dao.UserDO;
import com.vivid.ums.db.CoinsChangeRepository;
import com.vivid.ums.db.UserRepository;
import com.vivid.ums.service.CoinsChangeService;

@Service
public class CoinsChangeServiceImpl implements CoinsChangeService {

	@Autowired
	private CoinsChangeRepository coinsChangeRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<CoinsChangeDO> searchCoinsChange(int page, int count, Integer userId) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC, "id"));

		Specification<CoinsChangeDO> spec = new Specification<CoinsChangeDO>() {
			@Override
			public Predicate toPredicate(Root<CoinsChangeDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Path<String> uid = root.get("userId");
				return cb.equal(uid, userId);

			}
		};

		return coinsChangeRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteCoinsChange(Integer id) {
		coinsChangeRepository.deleteById(id);
	}

	@Override
	public Integer saveCoinsChange(CoinsChangeDO dao) {
		CoinsChangeDO saved = coinsChangeRepository.saveAndFlush(dao);
		return saved.getId();
	}

	@Override
	public CoinsChangeDO findOne(Integer id) {
		return coinsChangeRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public String exchange(CoinsChangeDO dao) {
		UserDO user = userRepository.findById(dao.getUserId()).orElse(null);
		if ((user.getCoinsTotal() - user.getCoinsUsed()) >= Math.abs(dao.getCoins())) {
			user.setCoinsUsed(user.getCoinsUsed() + Math.abs(dao.getCoins()));
			userRepository.saveAndFlush(user);
			dao.setChangeType(0);

			coinsChangeRepository.saveAndFlush(dao);
			return null;
		} else {
			return "金币不足无法兑换";
		}
	}

	@Override
	@Transactional
	public String award(CoinsChangeDO dao) {
		UserDO user = userRepository.findById(dao.getUserId()).orElse(null);

		user.setCoinsTotal(user.getCoinsTotal() + Math.abs(dao.getCoins()));
		userRepository.saveAndFlush(user);
		coinsChangeRepository.saveAndFlush(dao);
		return null;

	}
}
