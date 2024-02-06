package com.ssafy.lam.freeboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreeboard is a Querydsl query type for Freeboard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFreeboard extends EntityPathBase<Freeboard> {

    private static final long serialVersionUID = 1446134689L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreeboard freeboard = new QFreeboard("freeboard");

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final BooleanPath complain = createBoolean("complain");

    public final StringPath content = createString("content");

    public final NumberPath<Long> freeboardSeq = createNumber("freeboardSeq", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final com.ssafy.lam.file.domain.QUploadFile uploadFile;

    public final com.ssafy.lam.user.domain.QUser user;

    public QFreeboard(String variable) {
        this(Freeboard.class, forVariable(variable), INITS);
    }

    public QFreeboard(Path<? extends Freeboard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreeboard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreeboard(PathMetadata metadata, PathInits inits) {
        this(Freeboard.class, metadata, inits);
    }

    public QFreeboard(Class<? extends Freeboard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uploadFile = inits.isInitialized("uploadFile") ? new com.ssafy.lam.file.domain.QUploadFile(forProperty("uploadFile")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.lam.user.domain.QUser(forProperty("user")) : null;
    }

}

