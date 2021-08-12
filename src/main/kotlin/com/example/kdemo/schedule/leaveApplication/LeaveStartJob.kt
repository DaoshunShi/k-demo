package com.example.kdemo.schedule.leaveApplication

import org.quartz.JobExecutionContext
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.Trigger
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class LeaveStartJob(
    private val scheduler: Scheduler
) : QuartzJobBean() {
    
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        println("------------------")
        val trigger: Trigger = jobExecutionContext.trigger
        val jobDetail = jobExecutionContext.jobDetail
        val jobDataMap = jobDetail.jobDataMap
        // 将添加任务的时候存进去的数据拿出来
        val username = jobDataMap.getLongValue("username")
        val time = LocalDateTime.parse(jobDataMap.getString("time"))
        
        // 编写任务的逻辑
        
        // 执行之后删除任务
        try {
            // 暂停触发器的计时
            // scheduler.pauseTrigger(trigger.getKey())
            // 移除触发器中的任务
            // scheduler.unscheduleJob(trigger.getKey())
            // 删除任务
            // scheduler.deleteJob(jobDetail.key)
        } catch (e: SchedulerException) {
            e.printStackTrace()
        }
    }
    
    
}
