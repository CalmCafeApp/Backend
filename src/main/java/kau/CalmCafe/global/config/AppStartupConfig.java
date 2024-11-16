package kau.CalmCafe.global.config;

import kau.CalmCafe.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppStartupConfig {
    private final StoreService storeService;

    @Bean
    public ApplicationRunner scheduleStoreTasksOnStartup() {
        return args -> storeService.scheduleStoreTasks();
    }
}
