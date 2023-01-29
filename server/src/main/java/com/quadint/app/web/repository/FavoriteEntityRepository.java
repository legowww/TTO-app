package com.quadint.app.web.repository;

import com.quadint.app.domain.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteEntityRepository extends JpaRepository<FavoriteEntity, Integer> {
}
