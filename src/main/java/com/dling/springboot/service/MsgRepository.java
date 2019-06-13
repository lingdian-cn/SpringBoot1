package com.dling.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.dling.springboot.model.TbMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface MsgRepository extends JpaRepository<TbMsg, Integer> {

    List<TbMsg> findByTitleLike(String title);

    TbMsg findFirstByCreateTimeBetween(Date start, Date end);

    List<TbMsg> findByIdBefore(Integer id);

    // @Modifying     // 删除、修改需增加该注解
    @Transactional(timeout = 10)
    @Query("select id, title, content from TbMsg where title like %?1%")
    List<TbMsg> findByTitle(String title);

    @Transactional(timeout = 10)
    @Modifying
    @Query("update TbMsg set title = ?1 where id = ?2")
    int updateTitleById(String title, Integer id);

    @Transactional(timeout = 10)
    @Query("select title from TbMsg where id = ?1")
    String queryTitleById(Integer id);

    // 1.对象属性，对象名sql语句 key为null
//    @Query("select id, msgId, title, content from TbMsg where id = ?1")
//    List<Map<String, String>> queryById(Integer id);

    // 2.原生sql语句
    @Query(nativeQuery= true, value = "select *, DATE_FORMAT(create_time, '%Y-%m-%d %T') createTime from tb_msg where id = ?1")
    List<Map<String, String>> queryById(Integer id);
//    List<JSONObject> queryById(Integer id);
}
