//package com.example.demo.entity;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import com.example.demo.common.EDeliveryStatus;
//
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "deliveries")
//public class Delivery {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_id")
//	private Order order;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "partner_id")
//	private DeliveryPartner deliveryPartner;
//
//	private String deliveryCode;
//
//	@Enumerated(EnumType.STRING)
//	private EDeliveryStatus deliveryStatus;
//
//	@Temporal(TemporalType.DATE)
//	private Date deliveryDate;
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public Order getOrder() {
//		return order;
//	}
//
//	public void setOrder(Order order) {
//		this.order = order;
//	}
//
//	public DeliveryPartner getDeliveryPartner() {
//		return deliveryPartner;
//	}
//
//	public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
//		this.deliveryPartner = deliveryPartner;
//	}
//
//	public EDeliveryStatus getDeliveryStatus() {
//		return deliveryStatus;
//	}
//
//	public void setDeliveryStatus(EDeliveryStatus deliveryStatus) {
//		this.deliveryStatus = deliveryStatus;
//	}
//
//	public Date getDeliveryDate() {
//		return deliveryDate;
//	}
//
//	public void setDeliveryDate(Date deliveryDate) {
//		this.deliveryDate = deliveryDate;
//	}
//
//	public String getDeliveryCode() {
//		return deliveryCode;
//	}
//
//	public void setDeliveryCode(String deliveryCode) {
//		this.deliveryCode = deliveryCode;
//	}
//
//
//}
