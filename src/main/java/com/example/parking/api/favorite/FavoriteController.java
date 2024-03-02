package com.example.parking.api.favorite;

import com.example.parking.application.favorite.FavoriteService;
import com.example.parking.application.favorite.dto.FavoriteCreateRequest;
import com.example.parking.application.favorite.dto.FavoriteDeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/favorite")
    public ResponseEntity<Void> create(@RequestBody FavoriteCreateRequest favoriteCreateRequest) {
        favoriteService.createFavorite(favoriteCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/favorite")
    public ResponseEntity<Void> delete(@RequestBody FavoriteDeleteRequest favoriteDeleteRequest) {
        favoriteService.deleteFavorite(favoriteDeleteRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
