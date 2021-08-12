package com.example.kdemo.schedule

import com.example.kdemo.service.UserService
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component


@Configuration
class SchedulingWithQuartz {
    
    @Bean
    fun jobDetail(): JobDetail? {
        return JobBuilder.newJob().ofType(StartOfDayJob::class.java)
            .storeDurably()
            .withIdentity("Qrtz_Job_Detail")
            .withDescription("Invoke Sample Job service...")
            .build()
    }
    
    @Bean
    fun trigger(@Qualifier("jobDetail") job: JobDetail?): Trigger? {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity("Qrtz_Trigger")
            .withDescription("Sample trigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
            .build()
    }
    
}

@Component
class StartOfDayJob : QuartzJobBean() {
    @Autowired
    lateinit var jobService: UserService
    
    @Throws(JobExecutionException::class)
    override fun executeInternal(jobExecutionContext: JobExecutionContext) {
        // 任务的具体逻辑
        println("===============")
        // jobService.test()
        // jobService.deleteByCreatedOnBeforeYesterday()
    }
    
}

