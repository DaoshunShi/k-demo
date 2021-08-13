package com.example.kdemo.schedule.integrationQuartz

import org.quartz.*
import org.springframework.stereotype.Component
import java.util.*


@Component
class QuartzUtils(private var scheduler: Scheduler) {
    
    fun addSingleJob(jobCLass: Class<out Job>, taskName: String, intervalTime: Int) {
        val jobDetail = JobBuilder.newJob(jobCLass).withIdentity(taskName).build()
        val simpleScheduler = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionNextWithRemainingCount()
        val trigger: Trigger = TriggerBuilder.newTrigger()
            .startAt(Date(System.currentTimeMillis() + 1000 * 10))
            .withIdentity(taskName)
            .withSchedule(
                simpleScheduler.withIntervalInSeconds(intervalTime).repeatForever()
            )
            .build()
        scheduler.scheduleJob(jobDetail, trigger)
    }
    
    
    /**
     * 添加任务--cron
     * @param jobClass jobClass
     * @param taskName jobName、triggerName使用同一个name
     * @param cron cron定时任务规则
     */
    fun addCronJob(jobClass: Class<out Job?>?, taskName: String?, cron: String?) {
        val jobDetail = JobBuilder.newJob(jobClass)
            .withIdentity(taskName).build()
        val cronScheduler = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing()
        val trigger: Trigger = TriggerBuilder.newTrigger().startNow().withIdentity(taskName)
            .withSchedule(cronScheduler)
            .build()
        scheduler.scheduleJob(jobDetail, trigger)
    }
    
    
    /**
     * 修改cron规则
     */
    fun modifyCron(taskName: String?, cron: String?) {
        val triggerKey = TriggerKey.triggerKey(taskName)
        var trigger = scheduler.getTrigger(triggerKey) as CronTrigger
        val oldTime = trigger.cronExpression
        if (!oldTime.equals(cron, ignoreCase = true)) {
            val cronBuilder = CronScheduleBuilder.cronSchedule(cron)
            val triggerBuilder = TriggerBuilder.newTrigger()
            triggerBuilder.withIdentity(taskName).startNow().withSchedule(cronBuilder)
            trigger = triggerBuilder.build() as CronTrigger
            scheduler.rescheduleJob(triggerKey, trigger)
        }
    }
    
    
    /**
     * 删除任务
     */
    fun delJob(taskName: String?) {
        val triggerKey = TriggerKey.triggerKey(taskName)
        val jobKey = JobKey.jobKey(taskName)
        scheduler.pauseTrigger(triggerKey)
        scheduler.unscheduleJob(triggerKey)
        scheduler.deleteJob(jobKey)
    }
}