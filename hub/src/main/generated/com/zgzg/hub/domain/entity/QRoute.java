package com.zgzg.hub.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoute is a Querydsl query type for Route
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoute extends EntityPathBase<Route> {

    private static final long serialVersionUID = -1075491512L;

    public static final QRoute route = new QRoute("route");

    public final com.zgzg.common.utils.QBaseEntity _super = new com.zgzg.common.utils.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final ComparablePath<java.util.UUID> endHubId = createComparable("endHubId", java.util.UUID.class);

    public final StringPath endHubName = createString("endHubName");

    public final NumberPath<Integer> interDistance = createNumber("interDistance", Integer.class);

    public final NumberPath<Integer> interTime = createNumber("interTime", Integer.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final ComparablePath<java.util.UUID> parentId = createComparable("parentId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> routeId = createComparable("routeId", java.util.UUID.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final ComparablePath<java.util.UUID> startHubId = createComparable("startHubId", java.util.UUID.class);

    public final StringPath startHubName = createString("startHubName");

    public QRoute(String variable) {
        super(Route.class, forVariable(variable));
    }

    public QRoute(Path<? extends Route> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoute(PathMetadata metadata) {
        super(Route.class, metadata);
    }

}

