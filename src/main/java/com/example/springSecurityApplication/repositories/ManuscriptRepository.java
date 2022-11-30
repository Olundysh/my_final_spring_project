package com.example.springSecurityApplication.repositories;


import com.example.springSecurityApplication.models.Manuscript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManuscriptRepository extends JpaRepository<Manuscript, Integer> {

    // Находим продукты по части наименования без учета регистра
    List<Manuscript> findByTitleContainingIgnoreCase(String name);

    @Query(value = "select * from manuscript where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Manuscript> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float Do);

    // Поиск по наименованию, фильтрация по диапазону цены, сортировка по возрастанию цены
    @Query(value = "select * from manuscript where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price", nativeQuery = true)
    List<Manuscript> findByTitleOrderByPriceAsc(String title, float ot, float Do);

    // Поиск по наименованию, фильтрация по диапазону цены, сортировка по убыванию цены
    @Query(value = "select * from manuscript where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price desc ", nativeQuery = true)
    List<Manuscript> findByTitleOrderByPriceDest(String title, float ot, float Do);

    // Поиск по наименованию,по категории,  фильтрация по диапазону цены, сортировка по возрастанию цены
    @Query(value = "select * from manuscript where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price", nativeQuery = true)
    List<Manuscript> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);

    // Поиск по наименованию,по категории,  фильтрация по диапазону цены, сортировка по убыванию цены
    @Query(value = "select * from manuscript where category_id=?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by  price desc ", nativeQuery = true)
    List<Manuscript> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);
}

