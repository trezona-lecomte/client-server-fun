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

	public Contractor (String name, String location, String specialties,
					   String size, String rate, String owner) throws RecordLengthExceededException
	{
			this.name = toSizedString(name, NAME_LENGTH);
			this.location = toSizedString(location, LOCATION_LENGTH);
			this.specialties = toSizedArray(specialties, SPECIALTIES_LENGTH);
			this.size = toSizedDouble(size, SIZE_LENGTH);
			this.rate = toSizedDouble(rate, RATE_LENGTH);
			this.owner = toSizedInt(owner, OWNER_LENGTH);
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

	public void setName(String name) throws RecordLengthExceededException {
		this.name = toSizedString(name, NAME_LENGTH);;
	}

	public void setLocation(String location) throws RecordLengthExceededException {
		this.location = toSizedString(location, LOCATION_LENGTH);;
	}

	public void setSpecialties(String specialties) throws RecordLengthExceededException {
		this.specialties = toSizedArray(specialties, SPECIALTIES_LENGTH);
	}

	public void setSize(String size) throws RecordLengthExceededException {
		this.size = toSizedDouble(size, SIZE_LENGTH);;
	}

	public void setRate(String rate) throws RecordLengthExceededException {
		this.rate = toSizedDouble(rate, RATE_LENGTH);;
	}

	public void setOwner(String owner) throws RecordLengthExceededException {
		this.owner = toSizedInt(owner, OWNER_LENGTH);;
	}

	private static String toSizedString(String field, int size)
			throws RecordLengthExceededException {
		if (field.length() <= size) {
			return field;
		}
		else {
			throw new RecordLengthExceededException("Field: " + field + " exceeds the maximum " +
					"length. This field must be no longer than " + size + " characters.");
		}
	}

	private static String[] toSizedArray(String field, int size)
			throws RecordLengthExceededException {
	if (field.length() <= size) {
		return field.split(",");
	}
	else {
		throw new RecordLengthExceededException("Field: " + field + " exceeds the maximum " +
				"length. This field must be no longer than " + size + " characters.");
	}
	}

	private static double toSizedDouble(String field, int size)
			throws RecordLengthExceededException
	{
		if (field.length() <= size) {
			return Double.parseDouble(field);
		}
		else {
			throw new RecordLengthExceededException("Field: " + field + " exceeds the maximum " +
					"length. This field must be no longer than " + size + " characters.");
		}
	}

	private static int toSizedInt(String field, int size)
			throws RecordLengthExceededException
	{
		if (field.length() <= size) {
			return Integer.parseInt(field);
		}
		else {
			throw new RecordLengthExceededException("Field: " + field + " exceeds the maximum " +
					"length. This field must be no longer than " + size + " characters.");
		}
	}
}
