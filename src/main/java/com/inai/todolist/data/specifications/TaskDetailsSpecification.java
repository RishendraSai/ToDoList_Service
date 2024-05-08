package com.inai.todolist.data.specifications;

import com.inai.todolist.common.Utils.DateTimeUtil;
import com.inai.todolist.data.entities.TaskDetailsEntity;
import com.inai.todolist.models.TaskSearchDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDetailsSpecification implements Specification<TaskDetailsEntity> {

    private final TaskSearchDto taskSearchDto;

    public TaskDetailsSpecification(final TaskSearchDto taskSearchDto) {
        this.taskSearchDto = taskSearchDto;
    }
    @Override
    public Predicate toPredicate(Root<TaskDetailsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (Optional.ofNullable(taskSearchDto.getPriority()).isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("priority"), taskSearchDto.getPriority()));
        }

        if (Optional.ofNullable(taskSearchDto.getStatus()).isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("status"), taskSearchDto.getStatus()));
        }

        if (Optional.ofNullable(taskSearchDto.getTaskId()).isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("id"), taskSearchDto.getTaskId()));
        }
        if (Optional.ofNullable(taskSearchDto.getUserId()).isPresent()) {
            predicates.add(criteriaBuilder.equal(root.get("userId"), taskSearchDto.getUserId()));
        }
        if (Optional.ofNullable(taskSearchDto.getDueDateCrossed()).isPresent()) {
            predicates.add(criteriaBuilder.greaterThan(root.get("dueDate").as(Date.class
            ), convertToTimeStamp(taskSearchDto.getDueDateCrossed())));
            predicates.add(criteriaBuilder.equal(root.get("status"), "PENDING"));



        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private Timestamp convertToTimeStamp(String time) {
        Timestamp time2 = null;
        try {
            time2 = DateTimeUtil.formatFromString(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return time2;
    }


}
