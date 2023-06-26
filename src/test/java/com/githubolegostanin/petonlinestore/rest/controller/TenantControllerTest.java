package com.githubolegostanin.petonlinestore.rest.controller;

import com.githubolegostanin.petonlinestore.db.entity.Tenant;
import com.githubolegostanin.petonlinestore.db.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataR2dbcTest
@Testcontainers
public class TenantControllerTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TenantControllerTest.class);
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12")
            .withCopyFileToContainer(MountableFile.forClasspathResource("init.sql"),
                    "/docker-entrypoint-initdb.d/init.sql");

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
                + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @Autowired
    R2dbcEntityTemplate template;

    @Autowired
    TenantRepository tenants;

    @BeforeEach
    public void setup() {
        this.template.delete(Tenant.class).all().block(Duration.ofSeconds(5));
    }

    @Test
    public void testDatabaseClientExisted() {
        assertNotNull(template);
    }

    @Test
    public void testPostRepositoryExisted() {
        assertNotNull(tenants);
    }

    @Test
    public void testInsertAndQuery() {
        var data = Tenant.builder().name("test_name").build();
        this.template.insert(data)
                .thenMany(
                        this.tenants.findByName("test%")
                )
                .log()
                .as(StepVerifier::create)
                .consumeNextWith(p -> {
                            log.info("saved post: {}", p);
                            assertThat(p.getName()).isEqualTo("test_name");
                        }
                )
                .verifyComplete();

    }
}
