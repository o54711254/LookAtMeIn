package com.ssafy.lam.requestboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResponse is a Querydsl query type for Response
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResponse extends EntityPathBase<Response> {

    private static final long serialVersionUID = -518242863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResponse response = new QResponse("response");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final QRequestboard requestboard;

    public final com.ssafy.lam.user.domain.QUser user;

    public QResponse(String variable) {
        this(Response.class, forVariable(variable), INITS);
    }

    public QResponse(Path<? extends Response> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResponse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResponse(PathMetadata metadata, PathInits inits) {
        this(Response.class, metadata, inits);
    }

    public QResponse(Class<? extends Response> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.requestboard = inits.isInitialized("requestboard") ? new QRequestboard(forProperty("requestboard"), inits.get("requestboard")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.lam.user.domain.QUser(forProperty("user")) : null;
    }

}

