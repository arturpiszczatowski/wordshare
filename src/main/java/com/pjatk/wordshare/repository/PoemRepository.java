package com.pjatk.wordshare.repository;

import com.pjatk.wordshare.entity.Poem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoemRepository extends JpaRepository<Poem, Long> {
}
