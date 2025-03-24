package com.zgzg.hub.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHub is a Querydsl query type for Hub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHub extends EntityPathBase<Hub> {

    private static final long serialVersionUID = -318446668L;

    public static final QHub hub = new QHub("hub");

    public final com.zgzg.common.utils.QBaseEntity _super = new com.zgzg.common.utils.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final StringPath hubAddress = createString("hubAddress");

    public final NumberPath<Long> hubAdminId = createNumber("hubAdminId", Long.class);

    public final ComparablePath<java.util.UUID> hubId = createComparable("hubId", java.util.UUID.class);

    public final StringPath hubLatitude = createString("hubLatitude");

    public final StringPath hubLongitude = createString("hubLongitude");

    public final StringPath hubName = createString("hubName");

    public final BooleanPath isMegaHub = createBoolean("isMegaHub");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final ComparablePath<java.util.UUID> parentHubId = createComparable("parentHubId", java.util.UUID.class);

    public QHub(String variable) {
        super(Hub.class, forVariable(variable));
    }

    public QHub(Path<? extends Hub> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHub(PathMetadata metadata) {
        super(Hub.class, metadata);
    }

}

