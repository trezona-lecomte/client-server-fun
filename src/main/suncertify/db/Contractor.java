package main.suncertify.db;

import java.io.Serializable;

public class Contractor implements Serializable {

	private static final long serialVersionUID = 6562144700314939029L;

	private String name = "";
	private String location = "";
	private String[] specialties = null;
	private double size = 0;
	private double rate = 0;
	private int owner = 0;

	static final int NAME_LENGTH = 32;
	static final int LOCATION_LENGTH = 64;
	static final int SPECIALTIES_LENGTH = 64;
	static final int SIZE_LENGTH = 6;
	static final int RATE_LENGTH = 8;
	static final int OWNER_LENGTH = 8;
	static final int RECORD_LENGTH = NAME_LENGTH + LOCATION_LENGTH + SPECIALTIES_LENGTH
								   + SIZE_LENGTH + RATE_LENGTH + OWNER_LENGTH;

	public Contractor() {
	}

	public Contractor (String name, String location, String[] specialties,
					   Double size, Double rate, int owner) {
			this.name = name;
			this.location = location;
			this.specialties = specialties;
			this.size = size;
			this.rate = rate;
			this.owner = owner;
	}

	public boolean equals(Object contractor) {
		if (! (contractor instanceof Contractor)) {
			return false;
		}
		Contractor otherContractor = (Contractor) contractor;

		return (name == null) ? (otherContractor.getName() == null)
							  : name.equals(otherContractor.getName());
	}

	public int hashCode() {
		return name.hashCode();
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public String[] getSpecialties() {
		return specialties;
	}

	public Double getSize() {
		return size;
	}

	public Double getRate() {
		return rate;
	}

	public int getOwner() {
		return owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSpecialties(String[] specialties) {
		this.specialties = specialties;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
}
