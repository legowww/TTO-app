package com.quadint.app.domain.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class FavoriteEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id") private UserEntity userEntity;
    @Embedded LocationCoordinate locationCoordinate;

    protected FavoriteEntity() {}

    private FavoriteEntity(UserEntity userEntity, LocationCoordinate locationCoordinate) {
        this.userEntity = userEntity;
        this.locationCoordinate = locationCoordinate;
    }

    public static FavoriteEntity of(UserEntity userEntity, LocationCoordinate locationCoordinate) {
        return new FavoriteEntity(userEntity, locationCoordinate);
    }
}
