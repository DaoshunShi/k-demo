package com.example.kdemo.schedule.leaveApplication

import com.example.kdemo.ObservableError
import org.quartz.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId


@Service
class LeaveApplicationService(
    val scheduler: Scheduler
) {
    /**
     * 添加job和trigger到scheduler
     */
    fun addJobAndTrigger(leaveApplication: LeaveApplication) {
        val startJobDetail = jobDetail(leaveApplication)
        val startCronTrigger: CronTrigger = cronTrigger(leaveApplication)
        
        // 将job和trigger添加到scheduler里
        try {
            scheduler.scheduleJob(startJobDetail, startCronTrigger)
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    fun pauseTrigger(leaveApplication: LeaveApplication) {
        val startCronTrigger: CronTrigger = cronTrigger(leaveApplication)
        
        // 将job和trigger添加到scheduler里
        try {
            scheduler.pauseTrigger(startCronTrigger.key)
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    fun resumeTrigger(leaveApplication: LeaveApplication) {
        val startCronTrigger: CronTrigger = cronTrigger(leaveApplication)
        
        // 将job和trigger添加到scheduler里
        try {
            scheduler.resumeTrigger(startCronTrigger.key)
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    fun pauseAllJobs(leaveApplication: LeaveApplication) {
        try {
            scheduler.pauseAll()
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    fun resumeAllJobs(leaveApplication: LeaveApplication) {
        try {
            scheduler.resumeAll()
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    fun pauseJob(leaveApplication: LeaveApplication) {
        val startJobDetail = jobDetail(leaveApplication)
        
        try {
            scheduler.pauseJob(startJobDetail.key)
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    fun resumeJob(leaveApplication: LeaveApplication) {
        val startJobDetail = jobDetail(leaveApplication)
        
        try {
            scheduler.resumeJob(startJobDetail.key)
        } catch (e: SchedulerException) {
            e.printStackTrace()
            throw ObservableError("添加请假任务失败")
        }
    }
    
    
    private fun jobDetail(leaveApplication: LeaveApplication): JobDetail {
        // 创建请假开始Job
        val startTime: LocalDateTime = LocalDateTime.ofInstant(leaveApplication.startTime, ZoneId.systemDefault())
        val className = "com.example.kdemo.schedule.leaveApplication.LeaveStartJob"
        val clazz = Class.forName(className) as Class<out Job?>
        val t = LeaveStartJob::class.java
        return JobBuilder.newJob(clazz) // 指定任务组名和任务名
            .withIdentity(
                leaveApplication.startTime.toString(),
                leaveApplication.proposerUsername.toString() + "_start"
            ) // 添加一些参数，执行的时候用
            .usingJobData("username", leaveApplication.proposerUsername)
            .usingJobData("time", startTime.toString())
            .build()
    }
    
    private fun cronTrigger(leaveApplication: LeaveApplication): CronTrigger {
        // 创建请假开始任务的触发器
        // 创建cron表达式指定任务执行的时间，由于请假时间是确定的，所以年月日时分秒都是确定的，这也符合任务只执行一次的要求。
        val startCron = "*/5 * * * * ?"
        return TriggerBuilder.newTrigger() // 指定触发器组名和触发器名
            .withIdentity(
                leaveApplication.startTime.toString(),
                leaveApplication.proposerUsername.toString() + "_group"
            )
            .withSchedule(CronScheduleBuilder.cronSchedule(startCron))
            .build()
    }
    
}