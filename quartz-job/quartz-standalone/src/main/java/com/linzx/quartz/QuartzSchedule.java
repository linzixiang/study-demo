package com.linzx.quartz;

import com.linzx.quartz.job.MyJob1;
import com.linzx.quartz.listener.MyJobListener;
import com.linzx.quartz.listener.MySchedulerListener;
import com.linzx.quartz.listener.MyTriggerListener;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * 任务调度器
 */
public class QuartzSchedule {

    /**
     * 基础应用
     */
    @Test
    public void test1() {
        try {
            // JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                    .withIdentity("job1", "group1") // 同一个group可以有多个job，决定了任务的唯一性
                    .usingJobData("gupao","只为更好的你") // 任务携带参数
                    .usingJobData("moon",5.21F)
                    .build();

            // Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    /**
                     * 常见的触发器
                     * SimpleTrigger：固定时刻，固定时间间隔（毫秒）
                     * CalendarIntervalTrigger：
                     * DailyTimeIntervalTrigger：
                     * CronTrigger：定义基于cron表达式的触发器，表达式生成器https://cron.qqe2.com/
                     */
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();

            // SchedulerFactory
            SchedulerFactory  factory = new StdSchedulerFactory();

            // Scheduler
            Scheduler scheduler = factory.getScheduler();

            // 绑定关系是1：N
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 排除某些特殊日期不执行
     */
    @Test
    public void test2() {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            scheduler.start();

            // 定义日历
            AnnualCalendar holidays = new AnnualCalendar();

            // 排除咕泡日
            Calendar gupaoDay = (Calendar) new GregorianCalendar(2019, 8, 8);
            holidays.setDayExcluded(gupaoDay, true);
            // 排除中秋节
            Calendar midAutumn = new GregorianCalendar(2019, 9, 13);
            holidays.setDayExcluded(midAutumn, true);
            // 排除圣诞节
            Calendar christmas = new GregorianCalendar(2019, 12, 25);
            holidays.setDayExcluded(christmas, true);

            // 调度器添加日历
            scheduler.addCalendar("holidays", holidays, false, false);

            JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("gupao","青山 2673")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .modifiedByCalendar("holidays")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();

            Date firstRunTime = scheduler.scheduleJob(jobDetail, trigger);
            System.out.println(jobDetail.getKey() + " 第一次触发： " + firstRunTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加JobListener监听器
     */
    @Test
    public void test3() throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "group1").build();

        // Trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

        // SchedulerFactory
        SchedulerFactory  factory = new StdSchedulerFactory();

        // Scheduler
        Scheduler scheduler = factory.getScheduler();

        scheduler.scheduleJob(jobDetail, trigger);

        // 创建并注册一个全局的Job Listener
        scheduler.getListenerManager().addJobListener(new MyJobListener(), EverythingMatcher.allJobs());

        scheduler.start();
    }

    /**
     * 添加SchedulerListener
     */
    @Test
    public void test4() throws Exception {
        // JobDetail
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "group1").build();

        // Trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

        // SchedulerFactory
        SchedulerFactory  factory = new StdSchedulerFactory();

        // Scheduler
        Scheduler scheduler = factory.getScheduler();

        scheduler.scheduleJob(jobDetail, trigger);

        // 创建Scheduler Listener
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());

        scheduler.start();
    }

    /**
     * 添加TriggerListener
     */
    @Test
    public void test5() throws Exception {
        try {
            // JobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "group1").build();

            // Trigger
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

            // SchedulerFactory
            SchedulerFactory  factory = new StdSchedulerFactory();

            // Scheduler
            Scheduler scheduler = factory.getScheduler();

            scheduler.scheduleJob(jobDetail, trigger);


            // 创建并注册一个全局的Trigger Listener
            scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener1"), EverythingMatcher.allTriggers());

            // 创建并注册一个局部的Trigger Listener
            scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener2"), KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "gourp1")));

            // 创建并注册一个特定组的Trigger Listener
            GroupMatcher<TriggerKey> matcher = GroupMatcher.triggerGroupEquals("gourp1");
            scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("myListener3"), matcher);

            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
