package com.dling.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dling.springboot.annotation.Encrypt;
import com.dling.springboot.kit.HttpKit;
import com.dling.springboot.model.TbMsg;
import com.dling.springboot.service.MsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgRepository msgRepository;

    @RequestMapping("/")
    String index() {
        System.out.println("index()");
        return "<h1>MsgController</h1>";
    }

    // http://localhost:8001/boot/msg/list
    @RequestMapping("/list")
    List<TbMsg> list() {
        return msgRepository.findAll();
    }

    // http://localhost:8001/boot/msg/detail/3
    @RequestMapping("/detail/{id}")
    String detail(@PathVariable Integer id) {
        return msgRepository.getOne(id).toString();
    }

    // http://localhost:8001/boot/msg/find/3
    @RequestMapping("/find/{id}")
    Optional<TbMsg> find(@PathVariable Integer id) {
        return msgRepository.findById(id);
    }

    // http://localhost:8001/boot/msg/add?msgId=1&content=2&title=3&creator=4
    @RequestMapping("/add")
    TbMsg add(TbMsg msg) {
        return msgRepository.save(msg);
    }

    // http://localhost:8001/boot/msg/edit?msgId=1&content=222222&title=3&creator=4&id=3
    @RequestMapping("/edit")
    TbMsg edit(TbMsg msg) {
        return msgRepository.save(msg);
    }

    // http://localhost:8001/boot/msg/del/2
    @RequestMapping("/del/{id}")
    String delete(@PathVariable Integer id) {
        msgRepository.deleteById(id);
        return "ok";
    }

    // http://localhost:8001/boot/msg/time?start=2019-06-05&end=2019-06-06&size=10
    @RequestMapping("/time")
    TbMsg time(String start, String end, Integer size) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2019-06-06 16:00:10");
        return msgRepository.findFirstByCreateTimeBetween(startTime, startTime);
    }

    // http://localhost:8001/boot/msg/idbefore/3
    @RequestMapping("/idbefore/{id}")
    List<TbMsg> idBefore(@PathVariable Integer id) {
        return msgRepository.findByIdBefore(id);
    }

    @Transactional
    // http://localhost:8001/boot/msg/titlelike/aaa
    @RequestMapping("/titlelike/{title}")
    List<TbMsg> titlelike(@PathVariable String title) {
//        return msgRepository.findByTitleLike("%"+title+"%");
        return msgRepository.findByTitle(title);
//        return msgRepository.findByTitle("'%"+title+"%'");
    }

    // http://localhost:8001/boot/msg/page
    @RequestMapping("/page")
    Page<TbMsg> page(){
        return msgRepository.findAll(new Pageable() {
            @Override
            public int getPageNumber() {
                return 1;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return Sort.by("id", "createTime");
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        });
    }

    // http://localhost:8001/boot/msg/updatetitle/3-repository
    @RequestMapping("/updatetitle/{id}-{title}")
    int updatetitle(@PathVariable Integer id, @PathVariable String title) {
        return msgRepository.updateTitleById(title, id);
    }

    // http://localhost:8001/boot/msg/querytitle/3
    @RequestMapping("/querytitle/{id}")
    String querytitle(@PathVariable Integer id) {
        return msgRepository.queryTitleById(id);
    }

    // http://localhost:8001/boot/msg/querymap/3
//    @RequestMapping("/querymap/{id}")
//    List<Map<String, String>> querymap(@PathVariable Integer id) {
//        return msgRepository.queryById(id);
//    }

    @Encrypt
    @PostMapping("/querymap/{id}")
    String querymap(@PathVariable Integer id, HttpServletRequest request, @RequestBody String body) {
        // , @RequestParam("id") String id1, String name
        System.out.println("body-->" + body);
        // 此处使用阿里的JSON工具，将json串转换为json对象，然后通过该对象获取数据
        JSONObject data = JSON.parseObject(body);
        String name = data.getString("name");
        Integer age = data.getInteger("age");

        Map<String, String> query = new HashMap<>();
        query.put("page", "1");
        query.put("count", "5");
        System.out.println("Get--->"+HttpKit.get("https://api.apiopen.top/getAllUrl"));
        System.out.println("Post-->"+HttpKit.post("https://api.apiopen.top/getWangYiNews", query, null));

        return JSON.toJSONString(msgRepository.queryById(id));
    }

}
