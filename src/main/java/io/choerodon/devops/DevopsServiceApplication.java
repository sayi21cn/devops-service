package io.choerodon.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.choerodon.resource.annoation.EnableChoerodonResourceServer;

@EnableFeignClients("io.choerodon")
@EnableEurekaClient
@SpringBootApplication
@EnableChoerodonResourceServer
@EnableAsync
public class DevopsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevopsServiceApplication.class, args);
    }

}
