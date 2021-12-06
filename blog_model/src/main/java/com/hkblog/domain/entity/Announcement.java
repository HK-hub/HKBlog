package com.hkblog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HK意境
 * @ClassName : Announcement
 * @date : 2021/12/6 15:43
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@TableName("tb_announcement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Announcement  implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id ;

    private String title ;


    private String content ;
    private Integer level ;
    private Integer userType ;
    private Boolean canceled ;

    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime ;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    @TableLogic
    @TableField(value = "deleted")
    private Boolean deleted;




    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
