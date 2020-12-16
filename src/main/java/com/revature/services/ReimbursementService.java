package com.revature.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.exceptions.ReceiptException;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.exceptions.ReimbursementNotPendingException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.repositories.ReimbursementDAO;

@Service
public class ReimbursementService {

	@Autowired
	private ReimbursementDAO reimbursementDAO;

	public List<Reimbursement> findByAuthorId(int id) {
		return reimbursementDAO.findByAuthorId(id);
	}

	public List<Reimbursement> findByResolverId(int id) {
		return reimbursementDAO.findByResolverId(id);
	}

	public List<Reimbursement> findByStatus(ReimbursementStatus status) {
		return reimbursementDAO.findByStatus(status);
	}

	public List<Reimbursement> findByType(ReimbursementType type) {
		return reimbursementDAO.findByType(type);
	}

	public List<Reimbursement> findAll() {
		return reimbursementDAO.findAll();
	}

	public Reimbursement findById(int id) {
		return reimbursementDAO.findById(id)
				.orElseThrow(() -> new ReimbursementNotFoundException("No match found for id"));
	}

	public Reimbursement insert(Reimbursement reimbursement) {
		if (!reimbursement.getStatus().equals(ReimbursementStatus.Pending)) {
			throw new ReimbursementNotPendingException("New Reimbursements must be Pending");
		}
		
		reimbursement.setTimeSubmitted(LocalDateTime.now());

		return reimbursementDAO.save(reimbursement);
	}

	public Reimbursement update(Reimbursement reimbursement) {
		return reimbursementDAO.save(reimbursement);
	}

	public Reimbursement attachReceiptWithId(int id, MultipartFile image) {
		Reimbursement reimbursement = findById(id);

		return attachReceipt(reimbursement, image);
	}

	public Reimbursement attachReceipt(Reimbursement reimbursement, MultipartFile image) {
		if (!reimbursement.getStatus().equals(ReimbursementStatus.Pending)) {
			throw new ReimbursementNotPendingException("receipts can only be attached to Pending Reimbursements");
		}

		// Process and attach receipt
		try {
			reimbursement.setReceipt(image.getBytes());
		} catch (IOException e) {
			throw new ReceiptException("Unable to retrieve image contents", e);
		}

		return reimbursement;
	}
}
