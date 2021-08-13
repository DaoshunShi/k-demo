package com.example.kdemo.schedule.integrationQuartz

import com.example.kdemo.schedule.integrationQuartz.job.QuartzDemoJob
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


/**
 * 初始化定时任务
 */
@Component
class QuartzJobInit(
    var quartzUtils: QuartzUtils
) : CommandLineRunner {
    
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        // 每十秒执行一次
        quartzUtils.addSingleJob(QuartzDemoJob::class.java, "demo-job", 10)
        quartzUtils.addCronJob(QuartzDemoJob::class.java, "demo-cron-job", "*/10 * * * * ?")
    }
}