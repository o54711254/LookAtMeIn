package com.ssafy.lam.hospital.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareer is a Querydsl query type for Career
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareer extends EntityPathBase<Career> {

    private static final long serialVersionUID = 1124367057L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareer career = new QCareer("career");

    public final StringPath careerContent = createString("careerContent");

    public final NumberPath<Integer> careerEnd = createNumber("careerEnd", Integer.class);

    public final NumberPath<Long> careerSeq = createNumber("careerSeq", Long.class);

    public final NumberPath<Integer> careerStart = createNumber("careerStart", Integer.class);

    public final QDoctor doctor;

    public QCareer(String variable) {
        this(Career.class, forVariable(variable), INITS);
    }

    public QCareer(Path<? extends Career> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareer(PathMetadata metadata, PathInits inits) {
        this(Career.class, metadata, inits);
    }

    public QCareer(Class<? extends Career> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.doctor = inits.isInitialized("doctor") ? new QDoctor(forProperty("doctor"), inits.get("doctor")) : null;
    }

}

