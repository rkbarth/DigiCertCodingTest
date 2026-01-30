package com.example.demo.config;

import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class DemoDataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataLoader.class);
    public static final UUID DEMO_ID1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID DEMO_ID2 = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private final CertificateService service;

    public DemoDataLoader(CertificateService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) {
        Certificate demo = new Certificate(DEMO_ID1, "Demo Owner1", "DEMO-SN-0001", Instant.now());
        service.create(demo);
        log.info("Demo certificate created with id={}", DEMO_ID1);
        demo = new Certificate(DEMO_ID2, "Demo Owner2", "DEMO-SN-0002", Instant.now());
        service.create(demo);
        log.info("Demo certificate created with id={}", DEMO_ID2);
    }
}
