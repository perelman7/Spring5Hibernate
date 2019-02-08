package com.hibernateTest.hibernateTest.configuration.aop;

import com.hibernateTest.hibernateTest.aop.HistoryLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.hibernateTest.hibernateTest.aop"})
public class SpringAOPConfiguration {

    @Bean
    public HistoryLogger historyLogger(){
        return new HistoryLogger();
    }
}
