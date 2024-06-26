package org.winners.core.config.querydsl;

import com.querydsl.core.ResultTransformer;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@Getter
public class QuerydslBase<T> {

    private final QuerydslRepository querydslRepository;
    private final JPAQuery<?> jpaQuery;
    private Expression<T> select;
    private Expression<Long> countSelect;

    public QuerydslBase(QuerydslRepository querydslRepository, JPAQueryFactory queryFactory) {
        this.querydslRepository = querydslRepository;
        this.jpaQuery = queryFactory.query();
    }

    @SneakyThrows
    public <R extends QuerydslSelectDTO> QuerydslBase<T> select(Class<R> selectClass) {
        this.select = (Expression<T>) selectClass.getMethod("constructor").invoke(selectClass.getDeclaredConstructor().newInstance());
        return this;
    }

    public QuerydslBase<T> select(Expression<T> select) {
        this.select = select;
        return this;
    }

    public QuerydslBase<T> countSelect(Expression<Long> countSelect) {
        this.countSelect = countSelect;
        return this;
    }

    public QuerydslBase<T> from(EntityPath<?> from) {
        this.jpaQuery.from(from);
        return this;
    }

    public QuerydslBase<T> innerJoin(EntityPath<?> joinTable, Predicate on) {
        this.jpaQuery.innerJoin(joinTable).on(on);
        return this;
    }

    public <P> QuerydslBase<T> innerJoin(EntityPath<P> joinTable, Path<P> path) {
        this.jpaQuery.innerJoin(joinTable, path);
        return this;
    }


    public QuerydslBase<T> leftJoin(EntityPath<?> joinTable, Predicate on) {
        this.jpaQuery.leftJoin(joinTable).on(on);
        return this;
    }

    public <C> QuerydslBase<T> where(C value, SimpleExpression<C> column) {
        this.jpaQuery.where(column.eq(value));
        return this;
    }

    public QuerydslBase<T> whereOr(String value, StringPath ... columns) {
        this.jpaQuery.where(Expressions.anyOf(Arrays.stream(columns)
            .map(column -> column.eq(value))
            .toArray(BooleanExpression[]::new)));
        return this;
    }

    public <C> QuerydslBase<T> whereIn(Collection<C> value, SimpleExpression<C> column) {
        this.jpaQuery.where(column.in(value));
        return this;
    }

    public <C> QuerydslBase<T> optionalWhere(C value, SimpleExpression<C> column) {
        Optional.ofNullable(value).ifPresent(v -> this.where(v, column));
        return this;
    }

    public QuerydslBase<T> optionalWhereOr(String value, StringPath ... columns) {
        Optional.ofNullable(value).ifPresent(v -> this.whereOr(v, columns));
        return this;
    }

    public <C> QuerydslBase<T> optionalWhereIn(Collection<C> value, SimpleExpression<C> column) {
        Optional.ofNullable(value).ifPresent(v -> this.whereIn(v, column));
        return this;
    }

    public QuerydslBase<T> like(String value, StringPath column) {
        this.jpaQuery.where(column.contains(value));
        return this;
    }

    public QuerydslBase<T> likeOr(String value, StringPath ... columns) {
        this.jpaQuery.where(Expressions.anyOf(Arrays.stream(columns)
            .map(column -> column.contains(value))
            .toArray(BooleanExpression[]::new)));
        return this;
    }

    public QuerydslBase<T> optionalLike(String value, StringPath column) {
        Optional.ofNullable(value).ifPresent(v -> this.like(v, column));
        return this;
    }

    public QuerydslBase<T> optionalLikeOr(String value, StringPath ... columns) {
        Optional.ofNullable(value).ifPresent(v -> this.likeOr(v, columns));
        return this;
    }

    public QuerydslBase<T> orderBy(OrderSpecifier<?>... specifiers) {
        this.jpaQuery.orderBy(specifiers);
        return this;
    }

    public QuerydslBase<T> groupBy(Expression<?>... groupBy) {
        this.jpaQuery.groupBy(groupBy);
        return this;
    }

    public <R> Optional<R> transformRow(ResultTransformer<Map<Long, R>> transformer) {
        return Optional.ofNullable(this.jpaQuery.transform(transformer))
            .flatMap(map -> map.values().stream().findFirst());
    }

    public List<T> transformList(ResultTransformer<List<T>> transformer) {
        return this.jpaQuery.transform(transformer);
    }

    public Optional<T> getRow() {
        return this.querydslRepository.getRow(this);
    }

    public List<T> getList() {
        return this.querydslRepository.getList(this);
    }

    public Page<T> getPage(PageRequest pageRequest) {
        return this.querydslRepository.getPage(this, pageRequest);
    }

}
