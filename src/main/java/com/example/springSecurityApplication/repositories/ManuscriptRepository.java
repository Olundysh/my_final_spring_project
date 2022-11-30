package com.example.springSecurityApplication.repositories;


import com.example.springSecurityApplication.models.Manuscript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManuscriptRepository extends JpaRepository<Manuscript, Integer> {

    // Находим продукты по части наименования без учета регистра
    List<Manuscript> findByTitleContainingIgnoreCase(String name);

    @Query(value = "select * from manuscript where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (dating >= ?2 and dating <= ?3)", nativeQuery = true)
    List<Manuscript> findByTitleAndDatingGreaterThanEqualAndDatingLessThanEqual(String title, int ot, int Do);

    // Поиск по наименованию, фильтрация по диапазону цены, сортировка по возрастанию цены
    @Query(value = "select * from manuscript where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (dating >= ?2 and dating <= ?3) order by  dating", nativeQuery = true)
    List<Manuscript> findByTitleOrderByDatingAsc(String title, int ot, int Do);

    // Поиск по наименованию, фильтрация по диапазону цены, сортировка по убыванию цены
    @Query(value = "select * from manuscript where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (dating >= ?2 and dating <= ?3) order by  dating desc ", nativeQuery = true)
    List<Manuscript> findByTitleOrderByDatingDest(String title, int ot, int Do);

    // Поиск по наименованию,по категории,  фильтрация по диапазону цены, сортировка по возрастанию цены
    @Query(value = "select * from manuscript where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (dating >= ?2 and dating <= ?3) order by  dating", nativeQuery = true)
    List<Manuscript> findByTitleAndCategoryOrderByDatingAsc(String title, int ot, int Do, int category);

    // Поиск по наименованию,по категории,  фильтрация по диапазону цены, сортировка по убыванию цены
    @Query(value = "select * from manuscript where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (dating >= ?2 and dating <= ?3) order by  dating desc ", nativeQuery = true)
    List<Manuscript> findByTitleAndCategoryOrderByDatingDesc(String title, int ot, int Do, int category);
}

