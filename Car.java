import java.util.*;

/*
 * Class: Car
 * Description: The class represents a single car.
 * Author: LiangyuNie - s3716113
 */
public class Car
{
    private static final int MAX_BOOKINGS_NUM = 5;

    /**
     * the registration number of this car
     */
    private String regNo;
    /**
     * the make of this car
     */
    private String make;
    /**
     * the model of this car
     */
    private String model;
    /**
     * the driver's name of this car
     */
    private String driverName;
    /**
     * the passenger capacity of this car
     */
    private int passengerCapacity;
    /**
     * indicate whether this car is available
     */
    private boolean available;
    /**
     * current bookings of this car
     */
    private Booking[] currentBookings;
    public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Booking[] getCurrentBookings() {
		return currentBookings;
	}

	public void setCurrentBookings(Booking[] currentBookings) {
		this.currentBookings = currentBookings;
	}

	/**
     * past bookings of this car
     */
    private Booking[] pastBookings;
    public Booking[] getPastBookings() {
		return pastBookings;
	}

	public void setPastBookings(Booking[] pastBookings) {
		this.pastBookings = pastBookings;
	}

	/**
     * the number of current bookings of this car
     */
    private int numCurrentBookings;
    public int getNumCurrentBookings() {
		return numCurrentBookings;
	}

	public void setNumCurrentBookings(int numCurrentBookings) {
		this.numCurrentBookings = numCurrentBookings;
	}

	/**
     * the number of past bookings of this car
     */
    private int numPastBookings;

    public int getNumPastBookings() {
		return numPastBookings;
	}

	public void setNumPastBookings(int numPastBookings) {
		this.numPastBookings = numPastBookings;
	}

	/**
     * Constructs a new car instance with given parameters,
     * the car is available by default.
     */
    public Car(String regNo, String make, String model,
               String driverName, int passengerCapacity) throws InvalidId
    {
    	if (regNo==null || regNo=="") {
    		throw new InvalidId();
    	}
        this.regNo = regNo;
        this.make = make;
        this.model = model;
        this.driverName = driverName;
        this.passengerCapacity = passengerCapacity;

        currentBookings = new Booking[MAX_BOOKINGS_NUM];
        pastBookings = new Booking[10];

        numCurrentBookings = 0;
        numPastBookings = 0;

        available = true;
    }

    /**
     * Books a car with the customers's first name and last name,
     * require date and the number of required passengers.
     * @throws InvalidBooking 
     */
    public boolean book(String firstName, String lastName,
                        DateTime required, int numPassengers) throws InvalidBooking
    {
        if (!available)
        {
            return false;
        }
        if (required.getTime()<(new Date()).getTime()) {
        	throw new InvalidBooking();
        }
        int count=0;
        for (Booking b:this.getCurrentBookings()) {
        	if (b!=null && b.getPickUpDateTime().getFormattedDate().equals(required.getFormattedDate())) {
        		throw new InvalidBooking();
        	}
        	if (b!=null) {
        		count ++;
        	}
        }
        if (count==MAX_BOOKINGS_NUM) {
        	throw new InvalidBooking();
        }

        Booking newBooking = new Booking(firstName, lastName,
                required, numPassengers, this);
        currentBookings[numCurrentBookings] = newBooking;
        numCurrentBookings++;

        if (numCurrentBookings == MAX_BOOKINGS_NUM)
        {
            available = false;
        }
        

        return true;
    }

    /**
     * Gets the last booking of this car.
     *
     * @return the last booking of this car.
     */
    public Booking getLastBooking()
    {
        if (numCurrentBookings == 0)
        {
            return null;
        }

        return currentBookings[numCurrentBookings  - 1];
    }

    /**
     * Completes a booking in current bookings of this car.
     *
     * @param id the id the specified booking
     */
    public void completeBooking(String id)
    {
        int index = 0;

        for (int i = 0; i < numCurrentBookings; i++)
        {
            if (currentBookings[i].getId().equals(id))
            {
                index = i;
                break;
            }
        }

        Booking completedBooking = currentBookings[index];
        // remove the completed booking from currentBookings
        for (int i = index; i < numCurrentBookings - 1; i++)
        {
            currentBookings[index] = currentBookings[index + 1];
        }
        currentBookings[numCurrentBookings - 1] = null;
        numCurrentBookings--;
        available = true;

        ensurePastBookingCapacity(numPastBookings + 1);
        pastBookings[numPastBookings] = completedBooking;
        numPastBookings++;
    }

    /**
     * Grow the capacity of the past bookings array if necessary to ensure it's
     * capacity is larger than the given capacity.
     *
     * @param newCapacity the given capacity
     */
    private void ensurePastBookingCapacity(int newCapacity)
    {
        if (newCapacity >= pastBookings.length)
        {
            Booking[] newArray = new Booking[pastBookings.length * 2];
            System.arraycopy(
                    pastBookings, 0, newArray, 0, pastBookings.length);

            pastBookings = newArray;
        }
    }

    /**
     * Gets the details of this car.
     *
     * @return the details of this car
     */
    public String getDetails()
    {
        String newLine = "\n";
        StringBuilder details = new StringBuilder();

        details.append("RegNo:        ").append(regNo).append(newLine);

        details.append("Make & Model: ")
                .append(make).append(" ").append(model).append(newLine);

        details.append("Driver Name:  ").append(driverName).append(newLine);

        details.append("Capacity:     ")
                .append(passengerCapacity).append(newLine);

        details.append("Available:    ").append(available ? "YES" : "NO").append(newLine);

        return details.toString();
    }

    /**
     * Gets the string value of this car.
     *
     * @return the string value of this car.
     */
    public String toString()
    {
        StringJoiner joiner = new StringJoiner(":");

        joiner.add(regNo)
            .add(make)
            .add(model)
            .add(driverName)
            .add(String.valueOf(passengerCapacity))
            .add(available ? "YES" : "NO");

        return joiner.toString();
    }

    /**
     * Gets the registration number of this car
     *
     * @return the registration number of this car
     */
    public String getRegNo()
    {
        return regNo;
    }

    /**
     * Returns true if this car is available on the required date, otherwise false.
     *
     * @param required required date
     * @return true if this car is available on the required date, otherwise false.
     */
    public boolean isAvailable(DateTime required)
    {
        if (!available)
        {
            return false;
        }

        for (int i = 0; i < numCurrentBookings; i++)
        {
            Booking booking = currentBookings[i];

            int daysDiff = DateTime.diffDays(
                    required, booking.getPickUpDateTime());
            // the same day
            if (daysDiff == 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the driver's name
     *
     * @return the driver name
     */
    public String getDriverName()
    {
        return driverName;
    }

    /**
     * Gets the passenger capacity of this car
     *
     * @return the passenger capacity of this car
     */
    public int getPassengerCapacity()
    {
        return passengerCapacity;
    }

}