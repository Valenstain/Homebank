package com.solodkov.homebank.repository;

import com.solodkov.homebank.model.History;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

  List<History> findByUserId(int userId);
}
