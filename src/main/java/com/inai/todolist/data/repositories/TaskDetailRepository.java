package com.inai.todolist.data.repositories;

import com.inai.todolist.data.entities.TaskDetailsEntity;
import org.hibernate.hql.internal.ast.tree.IdentNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailRepository  extends PagingAndSortingRepository<TaskDetailsEntity, Integer>,
        JpaSpecificationExecutor {
}
