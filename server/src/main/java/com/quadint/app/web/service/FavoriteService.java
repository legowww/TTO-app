package com.quadint.app.web.service;


import com.quadint.app.domain.entity.FavoriteEntity;
import com.quadint.app.domain.entity.LocationCoordinate;
import com.quadint.app.domain.entity.UserEntity;
import com.quadint.app.web.controller.request.LocationCoordinateRequest;
import com.quadint.app.web.repository.FavoriteEntityRepository;
import com.quadint.app.web.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserEntityRepository userEntityRepository;
    private final FavoriteEntityRepository favoriteEntityRepository;

    @Transactional
    public void add(Integer userId, LocationCoordinateRequest request) {
        UserEntity userEntity = userEntityRepository.getOne(userId);
        favoriteEntityRepository.save(FavoriteEntity.of(userEntity, LocationCoordinate.fromRequest(request)));
    }
}
