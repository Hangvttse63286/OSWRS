package com.example.demo.payload;

public class MonthStatistic {
	int numOfOrders;
	int numOfSuccessfulOrder;
	int numOfConfirmedOrder;
	int numOfPendingOrder;
	int numOfUnsuccessfulOrder;

	Double totalSale;

	public MonthStatistic(int numOfOrders, int numOfSuccessfulOrder, int numOfConfirmedOrder, int numOfPendingOrder,
			int numOfUnsuccessfulOrder, Double totalSale) {
		super();
		this.numOfOrders = numOfOrders;
		this.numOfSuccessfulOrder = numOfSuccessfulOrder;
		this.numOfConfirmedOrder = numOfConfirmedOrder;
		this.numOfPendingOrder = numOfPendingOrder;
		this.numOfUnsuccessfulOrder = numOfUnsuccessfulOrder;
		this.totalSale = totalSale;
	}

	public int getNumOfOrders() {
		return numOfOrders;
	}

	public void setNumOfOrders(int numOfOrders) {
		this.numOfOrders = numOfOrders;
	}

	public int getNumOfSuccessfulOrder() {
		return numOfSuccessfulOrder;
	}

	public void setNumOfSuccessfulOrder(int numOfSuccessfulOrder) {
		this.numOfSuccessfulOrder = numOfSuccessfulOrder;
	}

	public int getNumOfConfirmedOrder() {
		return numOfConfirmedOrder;
	}

	public void setNumOfConfirmedOrder(int numOfConfirmedOrder) {
		this.numOfConfirmedOrder = numOfConfirmedOrder;
	}

	public int getNumOfPendingOrder() {
		return numOfPendingOrder;
	}

	public void setNumOfPendingOrder(int numOfPendingOrder) {
		this.numOfPendingOrder = numOfPendingOrder;
	}

	public int getNumOfUnsuccessfulOrder() {
		return numOfUnsuccessfulOrder;
	}

	public void setNumOfUnsuccessfulOrder(int numOfUnsuccessfulOrder) {
		this.numOfUnsuccessfulOrder = numOfUnsuccessfulOrder;
	}

	public Double getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(Double totalSale) {
		this.totalSale = totalSale;
	}


}
