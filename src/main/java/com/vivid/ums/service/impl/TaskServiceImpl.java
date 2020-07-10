package com.vivid.ums.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vivid.ums.db.dao.TaskAttachmentDO;
import com.vivid.ums.db.dao.TaskDO;
import com.vivid.common.api.base.Result;
import com.vivid.common.exception.ServiceException;
import com.vivid.common.utils.QiniuUtils;
import com.vivid.ums.db.TaskAttachmentRepository;
import com.vivid.ums.db.TaskRepository;
import com.vivid.ums.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskAttachmentRepository taskAttachmentRepository;

	@Override
	public Page<TaskDO> searchTask(int page, int count, String keyword) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC, "id"));

		Specification<TaskDO> spec = new Specification<TaskDO>() {
			@Override
			public Predicate toPredicate(Root<TaskDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				/*
				 * Path<String> nick = root.get("nick"); Path<String> loginName =
				 * root.get("loginName"); Predicate p1 = cb.like(nick, "%"+keyword+"%");
				 * Predicate p2 = cb.like(loginName, "%"+keyword+"%"); Predicate p = cb.or(p1,
				 * p2); return p;
				 */
				return null;
			}
		};

		return taskRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteTask(Integer id) {
		taskRepository.deleteById(id);
	}

	@Override
	public Integer saveTask(TaskDO dao) {
		TaskDO saved = taskRepository.saveAndFlush(dao);
		return saved.getId();
	}

	@Override
	public TaskDO findOne(Integer id) {
		return taskRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Integer createTask(TaskDO dao, MultipartFile[] files) throws ServiceException {

		TaskDO saved = taskRepository.saveAndFlush(dao);

		for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename() + ",,," + file.getName() + " = ");
			String key = dao.getUserId() + "-" + file.getOriginalFilename() + "-" + System.currentTimeMillis()
					+ (StringUtils.equals(file.getOriginalFilename(), "image") ? ".png" : ".aac");
			String url = "http://img.shellsports.cn/" + key;
			try {
				boolean bRet = QiniuUtils.uploadFile(key, file.getBytes());
				if (!bRet) {
					throw new ServiceException("文件上传错误");
				}

				TaskAttachmentDO attach = new TaskAttachmentDO();
				attach.setCreated(dao.getCreated());
				attach.setType(file.getOriginalFilename());
				attach.setUrl(url);
				attach.setTaskId(0);
				attach.setTaskId(saved.getId());
				taskAttachmentRepository.saveAndFlush(attach);

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ServiceException("文件上传错误");
			}

		}
		return saved.getId();
	}

	@Override
	public Page<TaskDO> listTask(int page, int size, int userId, String status) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, "id"));

		Specification<TaskDO> spec = new Specification<TaskDO>() {
			@Override
			public Predicate toPredicate(Root<TaskDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate p1 = cb.equal(root.get("userId"), userId);
				if (StringUtils.isNotBlank(status)) {
					Predicate p2 = cb.equal(root.get("status"), status);
					return cb.and(p1, p2);
				} else {
					return p1;
				}
			}
		};

		return taskRepository.findAll(spec, pageable);
	}

	@Override
	public Integer getLastMonthAvgScore(int userId) {
		// TODO Auto-generated method stub
		Integer val = taskRepository.getLastMonthAvgScore(userId);
		return val == null ? 0 : val;
	}

	@Override
	public Page<TaskDO> listCheckedTask(int userId) {
		Pageable pageable = PageRequest.of(0, 300, Sort.by(Direction.DESC, "id"));
		Specification<TaskDO> spec = new Specification<TaskDO>() {
			@Override
			public Predicate toPredicate(Root<TaskDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Calendar c = Calendar.getInstance();
				// 过去一月
				c.setTime(new Date());
				c.add(Calendar.MONTH, -1);

				Predicate p0 = cb.greaterThan(root.get("created"), c.getTime());
				Predicate p1 = cb.equal(root.get("userId"), userId);

				Predicate p2 = cb.notEqual(root.get("status"), "ASSIGNED");
				Predicate p3 = cb.notEqual(root.get("status"), "UPLOAD");
				Predicate p4 = cb.notEqual(root.get("score"), 0);
				return cb.and(p0, p1, p2, p3, p4);

			}
		};

		return taskRepository.findAll(spec, pageable);
	}

	@Override
	@Transactional
	public Integer reviewTask(TaskDO dao, MultipartFile[] files) throws ServiceException {

		TaskDO saved = taskRepository.saveAndFlush(dao);

		for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename() + ",,," + file.getName() + " = ");
			String key = dao.getUserId() + "-" + file.getOriginalFilename() + "-" + System.currentTimeMillis()
					+ (StringUtils.equals(file.getOriginalFilename(), "image") ? ".png" : ".aac");
			String url = "http://img.shellsports.cn/" + key;
			try {
				boolean bRet = QiniuUtils.uploadFile(key, file.getBytes());
				if (!bRet) {
					throw new ServiceException("文件上传错误");
				}

				TaskAttachmentDO attach = new TaskAttachmentDO();
				attach.setCreated(dao.getCreated());
				attach.setType(file.getOriginalFilename());
				attach.setUrl(url);
				attach.setTaskId(0);
				attach.setTaskId(saved.getId());
				taskAttachmentRepository.saveAndFlush(attach);

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ServiceException("文件上传错误");
			}

		}
		return saved.getId();
	}

	@Override
	public Page<TaskDO> listTodoTask(int userId) {
		Pageable pageable = PageRequest.of(0, 100, Sort.by(Direction.DESC, "id"));
		Specification<TaskDO> spec = new Specification<TaskDO>() {
			@Override
			public Predicate toPredicate(Root<TaskDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
			
				c1.add(Calendar.HOUR, -12);
				c2.add(Calendar.HOUR, -24 * 6);
				
				
				Predicate p1 = cb.equal(root.get("userId"), userId);
				
		
				Predicate p2 = cb.and(cb.equal(root.get("status"), "CHECKED"), cb.lessThan(root.get("updated"), c1.getTime()));
				Predicate p3 = cb.and(cb.equal(root.get("status"), "REVIEWED"), cb.lessThan(root.get("updated"), c2.getTime()));

				return cb.and(p1, cb.or(p2, p3, cb.equal(root.get("status"), "ASSIGNED"), cb.equal(root.get("status"), "RETURN")));

			}
		};

		return taskRepository.findAll(spec, pageable);
	}

	@Override
	public List<TaskDO> listNeedCheckTask(int userId) {
		Pageable pageable = PageRequest.of(0, 100, Sort.by(Direction.DESC, "id"));
		Specification<TaskDO> spec = new Specification<TaskDO>() {
			@Override
			public Predicate toPredicate(Root<TaskDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate p1 = cb.equal(root.get("userId"), userId);

				Predicate p2 = cb.equal(root.get("needCheck"), 1);
				return cb.and(p1, p2);

			}
		};

		return taskRepository.findAll(spec, pageable).getContent();
	}

	@Override
	public Integer checkTask(TaskDO dao, MultipartFile[] files) {
		TaskDO saved = taskRepository.saveAndFlush(dao);

		for (MultipartFile file : files) {
			System.out.println(file.getOriginalFilename() + ",,," + file.getName() + " = ");
			String key = dao.getUserId() + "-" + file.getOriginalFilename() + "-" + System.currentTimeMillis()
					+ (StringUtils.equals(file.getOriginalFilename(), "image") ? ".png" : ".aac");
			String url = "http://img.shellsports.cn/" + key;
			try {
				boolean bRet = QiniuUtils.uploadFile(key, file.getBytes());
				if (!bRet) {
					throw new ServiceException("文件上传错误");
				}

				TaskAttachmentDO attach = new TaskAttachmentDO();
				attach.setCreated(dao.getCreated());
				attach.setType(file.getOriginalFilename());
				attach.setUrl(url);
				attach.setTaskId(0);
				attach.setTaskId(saved.getId());
				taskAttachmentRepository.saveAndFlush(attach);

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ServiceException("文件上传错误");
			}

		}
		return saved.getId();
	}

	@Override
	public List<TaskDO> todayTask(int userId) {
		Pageable pageable = PageRequest.of(0, 100, Sort.by(Direction.DESC, "id"));
		Specification<TaskDO> spec = new Specification<TaskDO>() {
			@Override
			public Predicate toPredicate(Root<TaskDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate p1 = cb.equal(root.get("userId"), userId);

				Predicate p2 = cb.equal(root.get("status"), "ASSIGNED");
				Predicate p3 = cb.equal(root.get("status"), "UPLOAD");
				return cb.and(p1, cb.or(p2, p3));

			}
		};

		return taskRepository.findAll(spec, pageable).getContent();
	}

}
