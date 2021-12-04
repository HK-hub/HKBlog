package com.hkblog.search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : HK意境
 * @ClassName : MultiThreadScheduleTask
 * @date : 2021/12/3 16:38
 * @description :
 * @Todo : 多线程定时任务，
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
// 开启定时任务
@EnableScheduling
// 开启多线程
@EnableAsync
public class MultiThreadScheduleTask {


}
