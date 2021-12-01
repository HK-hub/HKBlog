package com.hkblog.common.utils;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author : HK意境
 * @ClassName : AuthRequestUtils
 * @date : 2021/12/1 13:56
 * @description : 发送第三方应用授权登录的请求，获取授权链接，
 * @Todo : 生成第三方授权链接，
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
public class AuthRequestUtils {

    // Gitee 登录相关参数
    @Value("${auth.login.gitee.clientId}")
    private String giteeClientId ;
    @Value("${auth.login.gitee.clientSecret}")
    private String giteeClientSecret ;
    @Value("${auth.login.gitee.callback}")
    private String giteeRedirectUri ;

    // Github 登录相关参数
    @Value("${auth.login.github.clientId}")
    private String githubClientId ;
    @Value("${auth.login.github.clientSecret}")
    private String githubClientSecret;
    @Value("${auth.login.github.callback}")
    private String githubRedirectUri ;

    /***
     *  Gitee 登录认证请求构造
     *
     * @return
     */
    public AuthRequest getGiteeAuthRequest() {

        return new AuthGiteeRequest(
                AuthConfig.builder()
                        .clientId(giteeClientId)
                        .clientSecret(giteeClientSecret)
                        .redirectUri(giteeRedirectUri)
                        .build());

    }


    /***
     * Github 登录构造器
     * @return
     */
    public AuthRequest getGithubAuthRequest(){

        return new AuthGithubRequest(
                AuthConfig.builder()
                        .clientId(githubClientId)
                        .clientSecret(githubClientSecret)
                        .redirectUri(githubRedirectUri)
                        .build());

    }



}
