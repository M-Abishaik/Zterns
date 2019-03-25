package com.zilker.bean;

public class BookRideResponse {
	
	private TravelInvoice travelInvoice;
	private DisplayInvoice displayInvoice;
	private String zipCodeSource;
	private String zipCodeDestination;
	
	public BookRideResponse() {}

	public BookRideResponse(TravelInvoice travelInvoice, DisplayInvoice displayInvoice, String zipCodeSource,
			String zipCodeDestination) {
		super();
		this.travelInvoice = travelInvoice;
		this.displayInvoice = displayInvoice;
		this.zipCodeSource = zipCodeSource;
		this.zipCodeDestination = zipCodeDestination;
	}

	public TravelInvoice getTravelInvoice() {
		return travelInvoice;
	}

	public void setTravelInvoice(TravelInvoice travelInvoice) {
		this.travelInvoice = travelInvoice;
	}

	public DisplayInvoice getDisplayInvoice() {
		return displayInvoice;
	}

	public void setDisplayInvoice(DisplayInvoice displayInvoice) {
		this.displayInvoice = displayInvoice;
	}

	public String getZipCodeSource() {
		return zipCodeSource;
	}

	public void setZipCodeSource(String zipCodeSource) {
		this.zipCodeSource = zipCodeSource;
	}

	public String getZipCodeDestination() {
		return zipCodeDestination;
	}

	public void setZipCodeDestination(String zipCodeDestination) {
		this.zipCodeDestination = zipCodeDestination;
	}
}
