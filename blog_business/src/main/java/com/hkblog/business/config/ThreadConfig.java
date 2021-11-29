package com.hkblog.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author : HK意境
 * @ClassName : ThreadConfig
 * @date : 2021/11/28 10:52
 * @description : 线程池配置
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
// 开启线程池
@EnableAsync
public class ThreadConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor(){

        // 线程池任务 executor
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数量
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间
        executor.setKeepAliveSeconds(60);
        // 设置线程默认名称
        executor.setThreadNamePrefix("__HKBlog__");
        //等待所有任务执行完毕后在关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();

        return executor;
    }


}
