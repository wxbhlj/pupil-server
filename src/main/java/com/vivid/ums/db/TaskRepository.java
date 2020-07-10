package com.vivid.ums.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.vivid.ums.db.dao.TaskDO;

public interface TaskRepository  extends JpaSpecificationExecutor<TaskDO>,JpaRepository<TaskDO, Integer>{

	@Query(value = "select avg(score)  from pupil_task where date(created) > DATE_SUB(CURDATE(), INTERVAL 1 MONTH) and score >0 and user_id= ?",nativeQuery=true)
    Integer getLastMonthAvgScore(int userId);
}
