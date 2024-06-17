package org.winners.core.config.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

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

    public QuerydslBase<T> leftJoin(EntityPath<?> joinTable, Predicate on) {
        this.jpaQuery.leftJoin(joinTable).on(on);
        return this;
    }

    public QuerydslBase<T> where(BooleanExpression booleanExpression) {
        Optional.ofNullable(booleanExpression).ifPresent(this.jpaQuery::where);
        return this;
    }

    public <C> QuerydslBase<T> where(SimpleExpression<C> column, C value) {
        this.jpaQuery.where(column.eq(value));
        return this;
    }

    public <C> QuerydslBase<T> optionalWhere(SimpleExpression<C> column, C value) {
        Optional.ofNullable(value).ifPresent(v -> this.jpaQuery.where(column.eq(v)));
        return this;
    }

    public QuerydslBase<T> orderBy(OrderSpecifier<?>... o) {
        this.jpaQuery.orderBy(o);
        return this;
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
