package com.ssafy.lam.requestboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequestboard is a Querydsl query type for Requestboard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequestboard extends EntityPathBase<Requestboard> {

    private static final long serialVersionUID = 1382817607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequestboard requestboard = new QRequestboard("requestboard");

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final StringPath content = createString("content");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final NumberPath<Integer> requestCnt = createNumber("requestCnt", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final ListPath<Surgery, QSurgery> surgeries = this.<Surgery, QSurgery>createList("surgeries", Surgery.class, QSurgery.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final com.ssafy.lam.user.domain.QUser user;

    public QRequestboard(String variable) {
        this(Requestboard.class, forVariable(variable), INITS);
    }

    public QRequestboard(Path<? extends Requestboard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequestboard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequestboard(PathMetadata metadata, PathInits inits) {
        this(Requestboard.class, metadata, inits);
    }

    public QRequestboard(Class<? extends Requestboard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.ssafy.lam.user.domain.QUser(forProperty("user")) : null;
    }

}

