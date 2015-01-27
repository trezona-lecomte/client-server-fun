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
	
	public Contractor() {
	}

	public Contractor(String name, String location, String[] specialties, double size,
					  double rate, int	owner) {
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

	public double getSize() {
		return size;
	}

	public double getRate() {
		return rate;
	}

	public int getOwner() {
		return owner;
	}
	
}
