package com.hkblog.domain.vo;

import com.hkblog.domain.entity.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author : HK意境
 * @ClassName : TagVo
 * @date : 2021/11/26 10:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class TagVo {

    private String id ;
    private String name ;
    private String desc ;
    private String avatar ;

    /**
     *
     * 构造方法
     */
    public TagVo(Tag su){

        this.id = su.getTagId() ;
        this.name = su.getName() ;
        this.desc = su.getDescription() ;
        this.avatar = su.getAvatar() ;

    }


}
