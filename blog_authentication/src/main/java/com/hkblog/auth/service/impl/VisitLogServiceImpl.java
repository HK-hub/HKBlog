package com.hkblog.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkblog.auth.mapper.VisitLogMapper;
import com.hkblog.auth.service.VisitLogService;
import com.hkblog.common.utils.IdWorker;
import com.hkblog.common.utils.IpUtils;
import com.hkblog.common.utils.SystemUtils;
import com.hkblog.domain.entity.VisitLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 */
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog>
    implements VisitLogService {

    @Resource
    private IdWorker idWorker ;
    @Resource
    private VisitLogMapper visitLogMapper ;

    /**
     * @methodName : 采集访客，游客访问信息
     * @author : HK意境
     * @date : 2021/11/30 21:19
     * @description :
     * @Todo :
     * @params : 
         * @param : null 
     * @return : null
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override   
    public void saveVisitorInfo(HttpServletRequest request, HttpServletResponse response) {

        VisitLog visitLog = new VisitLog();
        visitLog.setTouristsId(idWorker.nextId() + "");
        visitLog.setIp(IpUtils.getIpAddr(request));
        visitLog.setUri(request.getRequestURI());
        visitLog.setMethod(request.getMethod());
        visitLog.setParam(request.getParameterNames().toString());
        visitLog.setBehavior(request.getRequestURL().toString());

        String agent = request.getHeader("User-Agent");
        visitLog.setUserAgent(agent);
        StringTokenizer st = new StringTokenizer(agent,";");
        //得到访问者的浏览器名
        visitLog.setBrowser(st.nextToken());

        visitLog.setCreateTime(new Date());
        visitLog.setIpSource(request.getHeader("Referer"));
        visitLog.setUpdateTime(visitLog.getCreateTime());
        visitLog.setOs(SystemUtils.getRequestSystemInfo(request));
        visitLog.setRemark((String) request.getAttribute("title"));

        // 保存访问记录
        visitLogMapper.insert(visitLog);

    }
}




