package com.vivid.ums.service.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vivid.ums.db.dao.MoonDO;
import com.vivid.ums.db.MoonRepository;
import com.vivid.ums.service.MoonService;

@Service
public class MoonServiceImpl implements MoonService {

	@Autowired
	private MoonRepository moonRepository;

	@Override
	public Page<MoonDO> searchMoon(int page, int count, String keyword) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC, "id"));

		Specification<MoonDO> spec = new Specification<MoonDO>() {
			@Override
			public Predicate toPredicate(Root<MoonDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				/*
				 * Path<String> nick = root.get("nick"); Path<String> loginName =
				 * root.get("loginName"); Predicate p1 = cb.like(nick, "%"+keyword+"%");
				 * Predicate p2 = cb.like(loginName, "%"+keyword+"%"); Predicate p = cb.or(p1,
				 * p2); return p;
				 */
				return null;
			}
		};

		return moonRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteMoon(Integer id) {
		moonRepository.deleteById(id);
	}

	@Override
	public Integer saveMoon(MoonDO dao) {
		MoonDO saved = moonRepository.saveAndFlush(dao);
		return saved.getId();
	}

	@Override
	public MoonDO findOne(Integer id) {
		return moonRepository.findById(id).orElse(null);
	}

	@Override
	public MoonDO findTodayMoon(Integer userId) {

		Pageable pageable = PageRequest.of(0, 1, Sort.by(Direction.DESC, "id"));
		Specification<MoonDO> spec = new Specification<MoonDO>() {
			@Override
			public Predicate toPredicate(Root<MoonDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate p1 = cb.equal(root.get("userId"), userId);

				return p1;
			}
		};

		Page<MoonDO> page = moonRepository.findAll(spec, pageable);
		if (page.getSize() > 0) {
			MoonDO moon = page.getContent().get(0);
			if (DateUtils.formatDate(moon.getCreated(), "yyyyMMdd")
					.equals(DateUtils.formatDate(new Date(), "yyyyMMdd"))) {
				return moon;
			}
		}
		return null;

	}
}
