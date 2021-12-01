package com.hkblog.auth.service;

import com.hkblog.domain.entity.VisitLog;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public interface VisitLogService extends IService<VisitLog> {

    // 采集访客信息
    void saveVisitorInfo(HttpServletRequest request, HttpServletResponse response);
}
