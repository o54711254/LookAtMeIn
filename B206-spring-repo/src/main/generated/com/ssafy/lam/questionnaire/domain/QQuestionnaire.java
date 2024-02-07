package com.ssafy.lam.questionnaire.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionnaire is a Querydsl query type for Questionnaire
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionnaire extends EntityPathBase<Questionnaire> {

    private static final long serialVersionUID = 1332045121L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionnaire questionnaire = new QQuestionnaire("questionnaire");

    public final StringPath blood = createString("blood");

    public final StringPath content = createString("content");

    public final com.ssafy.lam.user.domain.QUser customer;

    public final com.ssafy.lam.user.domain.QUser hospital;

    public final StringPath remark = createString("remark");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath title = createString("title");

    public QQuestionnaire(String variable) {
        this(Questionnaire.class, forVariable(variable), INITS);
    }

    public QQuestionnaire(Path<? extends Questionnaire> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionnaire(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionnaire(PathMetadata metadata, PathInits inits) {
        this(Questionnaire.class, metadata, inits);
    }

    public QQuestionnaire(Class<? extends Questionnaire> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new com.ssafy.lam.user.domain.QUser(forProperty("customer")) : null;
        this.hospital = inits.isInitialized("hospital") ? new com.ssafy.lam.user.domain.QUser(forProperty("hospital")) : null;
    }

}

