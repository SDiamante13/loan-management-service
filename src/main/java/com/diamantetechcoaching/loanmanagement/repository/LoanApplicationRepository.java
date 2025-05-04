package com.diamantetechcoaching.loanmanagement.repository;

import com.diamantetechcoaching.loanmanagement.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanEntity, Long> {
}
