// 代码生成时间: 2025-10-15 18:22:09
import org.apache.ibatis.session.SqlSession;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 定时任务调度器
public class ScheduledTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskExecutor.class);
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    private Scheduler scheduler;
    private JobDetail jobDetail;

    // 初始化调度器
    public void initScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        scheduler.start();
    }

    // 创建定时任务
    public void createScheduledTask(String jobName, String cronExpression) {
        try {
            jobDetail = JobBuilder.newJob(MyBatisTask.class)
                    .withIdentity(jobName, "mybatisGroup")
                    .build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, "mybatisGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);
            logger.info("Scheduled task {} created with cron expression: {}", jobName, cronExpression);
        } catch (SchedulerException e) {
            logger.error("Error creating scheduled task: {}", e.getMessage(), e);
        }
    }

    // 关闭调度器
    public void shutdownScheduler() {
        if (scheduler != null && scheduler.isStarted()) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                logger.error("Error shutting down scheduler: {}", e.getMessage(), e);
            }
        }
    }

    // MyBatis 任务执行类
    public static class MyBatisTask implements Job {
        private static final Logger logger = LoggerFactory.getLogger(MyBatisTask.class);

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
                // 这里可以添加具体的 MyBatis 操作
                logger.info("Executing MyBatis task...");
            } catch (Exception e) {
                logger.error("Error executing MyBatis task: {}", e.getMessage(), e);
                throw new JobExecutionException(e);
            }
        }
    }

    // 使用示例
    public static void main(String[] args) {
        ScheduledTaskExecutor executor = new ScheduledTaskExecutor();
        try {
            executor.initScheduler();
            executor.createScheduledTask("mybatisJob", "0 0/30 * * * ?");
        } catch (SchedulerException e) {
            logger.error("Error initializing scheduler: {}", e.getMessage(), e);
        }
    }
}
