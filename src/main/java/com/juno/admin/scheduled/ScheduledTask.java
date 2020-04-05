package com.juno.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask
{

    @Scheduled(fixedRate = 3000)
    public void test()
    {
        System.out.println("excute it peer 3 second");
    }
}
