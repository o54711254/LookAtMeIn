package com.ssafy.lam.reserve.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserve is a Querydsl query type for Reserve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserve extends EntityPathBase<Reserve> {

    private static final long serialVersionUID = -1836314527L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserve reserve = new QReserve("reserve");

    public final com.ssafy.lam.user.domain.QUser customer;

    public final NumberPath<Integer> day = createNumber("day", Integer.class);

    public final StringPath dayofweek = createString("dayofweek");

    public final com.ssafy.lam.user.domain.QUser hospital;

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final NumberPath<Long> reserveSeq = createNumber("reserveSeq", Long.class);

    public final NumberPath<Integer> reserveType = createNumber("reserveType", Integer.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QReserve(String variable) {
        this(Reserve.class, forVariable(variable), INITS);
    }

    public QReserve(Path<? extends Reserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserve(PathMetadata metadata, PathInits inits) {
        this(Reserve.class, metadata, inits);
    }

    public QReserve(Class<? extends Reserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.ssafy.lam.user.domain.QUser(forProperty("customer")) : null;
        this.hospital = inits.isInitialized("hospital") ? new com.ssafy.lam.user.domain.QUser(forProperty("hospital")) : null;
    }

}

