package com.hkblog.domain.vo;

import com.hkblog.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HK意境
 * @ClassName : UserVo
 * @date : 2021/11/28 14:16
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class UserVo {

    private String nickname;

    private String avatar ;

    private String id ;

    public void copy(User user){
        this.nickname = user.getUsername();
        this.avatar = user.getAvatar() ;
        this.id = user.getUserId() ;
    }

    public UserVo(User user){
        this.nickname = user.getUsername();
        this.avatar = user.getAvatar() ;
        this.id = user.getUserId() ;
    }


}
