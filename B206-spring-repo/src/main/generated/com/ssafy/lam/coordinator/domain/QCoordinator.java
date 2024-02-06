package com.ssafy.lam.coordinator.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoordinator is a Querydsl query type for Coordinator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoordinator extends EntityPathBase<Coordinator> {

    private static final long serialVersionUID = 571167457L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoordinator coordinator = new QCoordinator("coordinator");

    public final NumberPath<Long> coordSeq = createNumber("coordSeq", Long.class);

    public final com.ssafy.lam.hospital.domain.QHospital hospital;

    public final com.ssafy.lam.user.domain.QUser user;

    public QCoordinator(String variable) {
        this(Coordinator.class, forVariable(variable), INITS);
    }

    public QCoordinator(Path<? extends Coordinator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoordinator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoordinator(PathMetadata metadata, PathInits inits) {
        this(Coordinator.class, metadata, inits);
    }

    public QCoordinator(Class<? extends Coordinator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospital = inits.isInitialized("hospital") ? new com.ssafy.lam.hospital.domain.QHospital(forProperty("hospital"), inits.get("hospital")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.lam.user.domain.QUser(forProperty("user")) : null;
    }

}

