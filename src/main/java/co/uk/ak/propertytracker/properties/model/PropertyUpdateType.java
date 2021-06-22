package co.uk.ak.propertytracker.properties.model;


public enum PropertyUpdateType {

	GONE_OFF_MARKET("Sold STC"),BACK_ON_MARKET("");

	private final String code;

	PropertyUpdateType(String code) {
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}

}
