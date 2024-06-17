package org.winners.core.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan("org.winners.core.domain")
@EnableJpaRepositories("org.winners.core.infra")
@SpringBootConfiguration
public class TestConfig {
}