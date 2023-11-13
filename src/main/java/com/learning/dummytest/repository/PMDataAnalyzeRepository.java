package com.learning.dummytest.repository;

import com.learning.dummytest.model.PMdata;
import com.learning.dummytest.model.PMdataAnalize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PMDataAnalyzeRepository extends JpaRepository<PMdataAnalize, Long> {
}
