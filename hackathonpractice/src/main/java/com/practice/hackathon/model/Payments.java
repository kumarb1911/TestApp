package com.practice.hackathon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payments")
public class Payments {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Long id;
	
	@Column(name="account_number")
	private Long accountNumber;
	
	@Column(name="payment_type")
	private String paymentType;
	
	@Column(name="transaction_date")
	private Date transactionDate;

}
