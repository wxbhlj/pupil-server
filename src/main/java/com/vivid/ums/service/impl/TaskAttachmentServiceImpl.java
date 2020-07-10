package com.vivid.ums.service.impl;

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
import org.springframework.web.multipart.MultipartFile;

import com.vivid.ums.db.dao.TaskAttachmentDO;
import com.vivid.common.exception.ServiceException;
import com.vivid.common.utils.QiniuUtils;
import com.vivid.ums.db.TaskAttachmentRepository;
import com.vivid.ums.service.TaskAttachmentService;

@Service
public class TaskAttachmentServiceImpl implements TaskAttachmentService {

	@Autowired
	private TaskAttachmentRepository taskAttachmentRepository;

	@Override
	public Page<TaskAttachmentDO> searchTaskAttachment(int page, int count, String keyword) {
		Pageable pageable = PageRequest.of(page - 1, count, Sort.by(Direction.DESC, "id"));

		Specification<TaskAttachmentDO> spec = new Specification<TaskAttachmentDO>() {
			@Override
			public Predicate toPredicate(Root<TaskAttachmentDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				/*
				 * Path<String> nick = root.get("nick"); Path<String> loginName =
				 * root.get("loginName"); Predicate p1 = cb.like(nick, "%"+keyword+"%");
				 * Predicate p2 = cb.like(loginName, "%"+keyword+"%"); Predicate p = cb.or(p1,
				 * p2); return p;
				 */
				return null;
			}
		};

		return taskAttachmentRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteTaskAttachment(Integer id) {
		taskAttachmentRepository.deleteById(id);
	}

	@Override
	public Integer saveTaskAttachment(TaskAttachmentDO dao) {
		TaskAttachmentDO saved = taskAttachmentRepository.saveAndFlush(dao);
		return saved.getId();
	}

	@Override
	public TaskAttachmentDO findOne(Integer id) {
		return taskAttachmentRepository.findById(id).orElse(null);
	}

	@Override
	public List<TaskAttachmentDO> findByTaskId(int taskId) {
		// TODO Auto-generated method stub
		return taskAttachmentRepository.findByTaskId(taskId);
	}

	@Override
	public Integer updateAttachment(int userId, TaskAttachmentDO one, MultipartFile file) {

		System.out.println(file.getOriginalFilename() + ",,," + file.getName() + " = ");
		String key = userId + "-" + file.getOriginalFilename() + "-" + System.currentTimeMillis()
				+ (StringUtils.equals(file.getOriginalFilename(), "image") ? ".png" : ".aac");
		String url = "http://img.shellsports.cn/" + key;
		try {

			boolean bRet = QiniuUtils.uploadFile(key, file.getBytes());
			if (!bRet) {
				throw new ServiceException("文件上传错误");
			}

			String key2 = one.getUrl().replace("http://img.shellsports.cn/", "");
			QiniuUtils.deleteFile(key2);

			one.setUrl(url);

			taskAttachmentRepository.saveAndFlush(one);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("文件上传错误");
		}
	}
}
