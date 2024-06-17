package org.winners.core.config.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class QuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public <T extends QuerydslSelectDTO> QuerydslBase<T> select(Class<T> selectClass) {
        QuerydslBase<T> querydslBase = new QuerydslBase<>(this, queryFactory);
        return querydslBase.select(selectClass);
    }

    public <T> QuerydslBase<T> select(Expression<T> select) {
        QuerydslBase<T> querydslBase = new QuerydslBase<>(this, queryFactory);
        return querydslBase.select(select);
    }


    public <T> Optional<T> getRow(QuerydslBase<T> querydslBase) {
        return Optional.ofNullable(querydslBase.getJpaQuery().select(querydslBase.getSelect()).fetchOne());
    }

    public <T> List<T> getList(QuerydslBase<T> querydslBase) {
        return querydslBase.getJpaQuery().select(querydslBase.getSelect()).fetch();
    }

    public <T> Page<T> getPage(QuerydslBase<T> querydslBase, PageRequest pageRequest) {
        long count = Optional.ofNullable(querydslBase.getJpaQuery()
            .select(querydslBase.getCountSelect())
            .fetchOne()).orElse(0L);
        List<T> list = count > 0 ? querydslBase.getJpaQuery()
            .select(querydslBase.getSelect())
            .offset(pageRequest.getOffset())
            .limit(pageRequest.getPageSize())
            .fetch() : new ArrayList<>();
        return new PageImpl<>(list, pageRequest, count);
    }

}
