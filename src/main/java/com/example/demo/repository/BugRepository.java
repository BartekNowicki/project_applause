package com.example.demo.repository;

import com.example.demo.dataModel.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {}

