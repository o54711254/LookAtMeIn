package com.ssafy.lam.hashtag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewHashtag is a Querydsl query type for ReviewHashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewHashtag extends EntityPathBase<ReviewHashtag> {

    private static final long serialVersionUID = 885930025L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewHashtag reviewHashtag = new QReviewHashtag("reviewHashtag");

    public final QHashtag hashtag;

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final com.ssafy.lam.reviewBoard.domain.QReviewBoard reviewBoard;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QReviewHashtag(String variable) {
        this(ReviewHashtag.class, forVariable(variable), INITS);
    }

    public QReviewHashtag(Path<? extends ReviewHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewHashtag(PathMetadata metadata, PathInits inits) {
        this(ReviewHashtag.class, metadata, inits);
    }

    public QReviewHashtag(Class<? extends ReviewHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
        this.reviewBoard = inits.isInitialized("reviewBoard") ? new com.ssafy.lam.reviewBoard.domain.QReviewBoard(forProperty("reviewBoard"), inits.get("reviewBoard")) : null;
    }

}

