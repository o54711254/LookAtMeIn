package com.ssafy.lam.hospital.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHospitalCategory is a Querydsl query type for HospitalCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHospitalCategory extends EntityPathBase<HospitalCategory> {

    private static final long serialVersionUID = -1184764693L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHospitalCategory hospitalCategory = new QHospitalCategory("hospitalCategory");

    public final NumberPath<Long> categorySeq = createNumber("categorySeq", Long.class);

    public final QHospital hospital;

    public final StringPath part = createString("part");

    public QHospitalCategory(String variable) {
        this(HospitalCategory.class, forVariable(variable), INITS);
    }

    public QHospitalCategory(Path<? extends HospitalCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHospitalCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHospitalCategory(PathMetadata metadata, PathInits inits) {
        this(HospitalCategory.class, metadata, inits);
    }

    public QHospitalCategory(Class<? extends HospitalCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hospital = inits.isInitialized("hospital") ? new QHospital(forProperty("hospital"), inits.get("hospital")) : null;
    }

}

