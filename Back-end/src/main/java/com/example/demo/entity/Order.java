package com.example.demo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.demo.common.EOrderStatus;
import com.example.demo.common.EPaymentStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@Enumerated(EnumType.STRING)
	private EOrderStatus orderStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	private EPaymentStatus paymentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;

	@OneToMany(mappedBy = "order")
	private Set<OrderItem> orderItems = new HashSet<>();

	private Double subTotal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_id")
	private Voucher voucher;

	private Double deliveryFeeTotal;
	private Double paymentTotal;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(EOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public EPaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(EPaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public Double getDeliveryFeeTotal() {
		return deliveryFeeTotal;
	}

	public void setDeliveryFeeTotal(Double deliveryFeeTotal) {
		this.deliveryFeeTotal = deliveryFeeTotal;
	}

	public Double getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(Double paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


}
