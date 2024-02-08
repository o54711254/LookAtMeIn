package com.ssafy.lam.hospital.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDoctorCategory is a Querydsl query type for DoctorCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDoctorCategory extends EntityPathBase<DoctorCategory> {

    private static final long serialVersionUID = 1821021072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDoctorCategory doctorCategory = new QDoctorCategory("doctorCategory");

    public final NumberPath<Long> categorySeq = createNumber("categorySeq", Long.class);

    public final QDoctor doctor;

    public final StringPath part = createString("part");

    public QDoctorCategory(String variable) {
        this(DoctorCategory.class, forVariable(variable), INITS);
    }

    public QDoctorCategory(Path<? extends DoctorCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDoctorCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDoctorCategory(PathMetadata metadata, PathInits inits) {
        this(DoctorCategory.class, metadata, inits);
    }

    public QDoctorCategory(Class<? extends DoctorCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.doctor = inits.isInitialized("doctor") ? new QDoctor(forProperty("doctor"), inits.get("doctor")) : null;
    }

}

