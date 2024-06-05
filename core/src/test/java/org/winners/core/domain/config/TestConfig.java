package org.winners.core.domain.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories("org.winners.core.infra")
@SpringBootConfiguration
public class TestConfig {
}