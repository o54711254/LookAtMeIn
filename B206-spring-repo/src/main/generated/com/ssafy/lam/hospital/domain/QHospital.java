package com.ssafy.lam.hospital.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHospital is a Querydsl query type for Hospital
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHospital extends EntityPathBase<Hospital> {

    private static final long serialVersionUID = -2111838003L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHospital hospital = new QHospital("hospital");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> bookmark = createNumber("bookmark", Integer.class);

    public final ListPath<Category, QCategory> category = this.<Category, QCategory>createList("category", Category.class, QCategory.class, PathInits.DIRECT2);

    public final StringPath closeTime = createString("closeTime");

    public final ListPath<com.ssafy.lam.coordinator.domain.Coordinator, com.ssafy.lam.coordinator.domain.QCoordinator> coordinators = this.<com.ssafy.lam.coordinator.domain.Coordinator, com.ssafy.lam.coordinator.domain.QCoordinator>createList("coordinators", com.ssafy.lam.coordinator.domain.Coordinator.class, com.ssafy.lam.coordinator.domain.QCoordinator.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> hospitalSeq = createNumber("hospitalSeq", Long.class);

    public final StringPath intro = createString("intro");

    public final BooleanPath isApproved = createBoolean("isApproved");

    public final StringPath openTime = createString("openTime");

    public final StringPath tel = createString("tel");

    public final StringPath url = createString("url");

    public final com.ssafy.lam.user.domain.QUser user;

    public QHospital(String variable) {
        this(Hospital.class, forVariable(variable), INITS);
    }

    public QHospital(Path<? extends Hospital> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHospital(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHospital(PathMetadata metadata, PathInits inits) {
        this(Hospital.class, metadata, inits);
    }

    public QHospital(Class<? extends Hospital> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.ssafy.lam.user.domain.QUser(forProperty("user")) : null;
    }

}

