package eu.virtusdevelops.ldmontage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    @Bean(name = "MainThread")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("Main-");
        threadPoolTaskExecutor.setCorePoolSize(8);
        //threadPoolTaskExecutor.setMaxPoolSize(12);
        //threadPoolTaskExecutor.setQueueCapacity(500);
        return threadPoolTaskExecutor;
    }

}
