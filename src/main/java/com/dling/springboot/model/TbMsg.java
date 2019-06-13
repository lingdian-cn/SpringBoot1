package com.dling.springboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
//@Table(name="my_msg") // 指定表名称
public class TbMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String msgId;
    @Column
    private String title;
    private String content;
    private String creator;
    private Date createTime;

    public TbMsg(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TbMsg{" +
                "id=" + id +
                ", msgId='" + msgId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
