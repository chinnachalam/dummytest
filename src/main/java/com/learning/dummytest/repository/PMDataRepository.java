package com.learning.dummytest.repository;

import com.learning.dummytest.model.PMdata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PMDataRepository extends JpaRepository<PMdata, Long> {
}
