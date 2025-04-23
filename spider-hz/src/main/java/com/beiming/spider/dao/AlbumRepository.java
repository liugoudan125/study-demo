package com.beiming.spider.dao;

import com.beiming.spider.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AlbumRepository-2024/3/26-11:32
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
