package org.winners.app.config.bean;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.winners.app.application.field.FieldService;
import org.winners.app.application.field.JobService;
import org.winners.app.application.field.impl.FieldServiceV1;
import org.winners.app.application.field.impl.JobServiceV1;
import org.winners.core.domain.field.FieldRepository;

@ComponentScan("org.winners.core.domain")
@Configuration
@RequiredArgsConstructor
public class FieldServiceBean {

    private final JPAQueryFactory queryFactory;
    private final FieldRepository fieldRepository;

    @Bean(name = "FieldServiceV1")
    public FieldService fieldService() {
        return new FieldServiceV1(fieldRepository);
    }

    @Bean(name = "JobServiceV1")
    public JobService jobService() {
        return new JobServiceV1(queryFactory);
    }

}
