package com.example.springSecurityApplication.repositories;

import com.example.springSecurityApplication.models.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {

    // Получаем Favourites по id пользователя:
    List<Favourites> findByPersonId(int id);

    void deleteFavouritesByManuscriptId(int id);
}
