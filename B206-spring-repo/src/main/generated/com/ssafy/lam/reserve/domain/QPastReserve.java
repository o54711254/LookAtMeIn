package com.ssafy.lam.reserve.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPastReserve is a Querydsl query type for PastReserve
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPastReserve extends EntityPathBase<PastReserve> {

    private static final long serialVersionUID = -1245146769L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPastReserve pastReserve = new QPastReserve("pastReserve");

    public final com.ssafy.lam.user.domain.QUser customer;

    public final NumberPath<Integer> day = createNumber("day", Integer.class);

    public final StringPath dayofweek = createString("dayofweek");

    public final com.ssafy.lam.user.domain.QUser hospital;

    public final NumberPath<Integer> img = createNumber("img", Integer.class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final StringPath pContent = createString("pContent");

    public final NumberPath<Integer> pPrice = createNumber("pPrice", Integer.class);

    public final NumberPath<Long> pReserveSeq = createNumber("pReserveSeq", Long.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QPastReserve(String variable) {
        this(PastReserve.class, forVariable(variable), INITS);
    }

    public QPastReserve(Path<? extends PastReserve> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPastReserve(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPastReserve(PathMetadata metadata, PathInits inits) {
        this(PastReserve.class, metadata, inits);
    }

    public QPastReserve(Class<? extends PastReserve> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.ssafy.lam.user.domain.QUser(forProperty("customer")) : null;
        this.hospital = inits.isInitialized("hospital") ? new com.ssafy.lam.user.domain.QUser(forProperty("hospital")) : null;
    }

}

