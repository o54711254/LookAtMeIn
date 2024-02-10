package com.ssafy.lam.requestboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSurgery is a Querydsl query type for Surgery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSurgery extends EntityPathBase<Surgery> {

    private static final long serialVersionUID = 219272805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSurgery surgery = new QSurgery("surgery");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath part = createString("part");

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final QRequestboard requestboard;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QSurgery(String variable) {
        this(Surgery.class, forVariable(variable), INITS);
    }

    public QSurgery(Path<? extends Surgery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSurgery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSurgery(PathMetadata metadata, PathInits inits) {
        this(Surgery.class, metadata, inits);
    }

    public QSurgery(Class<? extends Surgery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.requestboard = inits.isInitialized("requestboard") ? new QRequestboard(forProperty("requestboard"), inits.get("requestboard")) : null;
    }

}

