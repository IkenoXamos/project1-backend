package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

	public List<Reimbursement> findByAuthorId(int id);

	public List<Reimbursement> findByResolverId(int id);

	public List<Reimbursement> findByStatus(ReimbursementStatus status);

	public List<Reimbursement> findByType(ReimbursementType type);
}
