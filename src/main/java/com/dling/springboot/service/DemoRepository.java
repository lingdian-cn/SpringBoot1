package com.dling.springboot.service;

import com.dling.springboot.model.TbMsg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DemoRepository {

    // @Modifying     // 删除、修改需增加该注解
    @Transactional(timeout = 10)
    @Query("select id, title, content from tb_msg a where a.title = ?1")
    List<TbMsg> findByTitle(String title);
}
