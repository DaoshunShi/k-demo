package com.example.kdemo.schedule.integrationQuartz.job

import com.example.kdemo.service.circleDI.ServiceA
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
class QuartzDemoJob(
    private val serviceA: ServiceA
) : QuartzJobBean() {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    override fun executeInternal(context: JobExecutionContext) {
        logger.info("!!!开始执行--测试定时任务")
        serviceA.printA()
    }
    
}