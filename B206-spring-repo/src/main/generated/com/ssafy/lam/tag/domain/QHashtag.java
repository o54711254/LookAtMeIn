package com.ssafy.lam.tag.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashtag is a Querydsl query type for Hashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashtag extends EntityPathBase<Hashtag> {

    private static final long serialVersionUID = 1225227091L;

    public static final QHashtag hashtag = new QHashtag("hashtag");

    public final NumberPath<Long> hashtagSeq = createNumber("hashtagSeq", Long.class);

    public final ListPath<ReviewHashtag, QReviewHashtag> reviewHashtags = this.<ReviewHashtag, QReviewHashtag>createList("reviewHashtags", ReviewHashtag.class, QReviewHashtag.class, PathInits.DIRECT2);

    public final StringPath tagName = createString("tagName");

    public QHashtag(String variable) {
        super(Hashtag.class, forVariable(variable));
    }

    public QHashtag(Path<? extends Hashtag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashtag(PathMetadata metadata) {
        super(Hashtag.class, metadata);
    }

}

