package com.uniquestartup.mohamedaliheeba.sawa7.CityGuide;

public class FoursquareVenue {
	private String name;
	private String city;



	private String distant;
	private String contact_phone;
	private String contact_twitter;
	private String contact_facebookUsername;
	private String formattedAddress;
	private String iconUrl;
	private String siteUrl;

	private String category;

	public FoursquareVenue() {
		this.name = "";
		this.city = "";
		this.distant="";
		this.contact_phone= "";
		this.contact_twitter= "";
		this.contact_facebookUsername= "";
		this.formattedAddress= "";
		this.iconUrl= "";
		this.siteUrl= "";
		this.setCategory("");
	}

	public String getDistant() {
		return distant;
	}

	public void setDistant(String distant) {
		this.distant = distant;
	}
	public String getCity() {
		if (city.length() > 0) {
			return city;
		}
		return city;
	}

	public void setCity(String city) {
		if (city != null) {
			this.city = city.replaceAll("\\(", "").replaceAll("\\)", "");

		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_twitter() {
		return contact_twitter;
	}

	public void setContact_twitter(String contact_twitter) {
		this.contact_twitter = contact_twitter;
	}

	public String getContact_facebookUsername() {
		return contact_facebookUsername;
	}

	public void setContact_facebookUsername(String contact_facebookUsername) {
		this.contact_facebookUsername = contact_facebookUsername;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
}
