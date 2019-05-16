import java.util.Date;

/*
 * Class: SilverServiceCar
 * Description: The class represents a Silver Service car.
 * Author: [LiangyuNie] - [s3716113]
 */
public class SilverServiceCar extends Car{
	private double bookingFee;
	private String[] refreshments;
	private static final int MAX_BOOKINGS_NUM = 5;

	/**
	 * Constructs a new SilverServiceCar instance with given parameters
	 * @param regNo
	 * @param make
	 * @param model
	 * @param driverName
	 * @param passengerCapacity
	 * @param bookingFee
	 * @param refreshments
	 * @throws InvalidRefreshments
	 * @throws InvalidId
	 */
	public SilverServiceCar(String regNo, String make, String model, String driverName, int passengerCapacity,
			double bookingFee, String[] refreshments) throws InvalidRefreshments, InvalidId{
		
			super(regNo, make, model, driverName, passengerCapacity);
		
		this.setBookingFee(bookingFee);
		for(int i=0;i<refreshments.length;i++) {
			for (int j=0;j<refreshments.length;j++) {
				if (i!=j&&refreshments[i]==refreshments[j]) {
					// supplying a list of refreshments that contains duplicate items
					throw new InvalidRefreshments();
				}
			}
		}
		// providing less than three items in the refreshment list.
		if (refreshments.length < 3) {
			throw new InvalidRefreshments();
		}
		this.refreshments = refreshments;
		this.setCurrentBookings(new Booking[MAX_BOOKINGS_NUM]);
		this.setPastBookings(new Booking[10]);

		this.setNumCurrentBookings(0);
		this.setNumPastBookings(0);

		this.setAvailable(true);
		
	}

	public double getBookingFee() {
		return bookingFee;
	}

	/**
	 * The booking fee for a SilverService car is a fixed fee but must be greater than or equal to $3.00.
	 * @param bookingFee
	 */
	public void setBookingFee(double bookingFee) {
		this.bookingFee = bookingFee<3.00?3.00:bookingFee;
	}

	public String[] getRefreshments() {
		return refreshments;
	}

	public void setRefreshments(String[] refreshments) {
		this.refreshments = refreshments;
	}
	
	/**
	 * override book method
	 */
	public boolean book(String firstName, String lastName,
            DateTime required, int numPassengers) throws InvalidBooking{
		DateTime now = new DateTime();
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
		if (now.getTime() - required.getTime() > 3*24*60*60) {
			return false;
		}
        Booking newBooking = new Booking(firstName, lastName, required, numPassengers, this);
        Booking[] tmpBookings = this.getCurrentBookings();
        tmpBookings[this.getNumCurrentBookings()] = newBooking;
        this.setCurrentBookings(tmpBookings);
        this.setNumCurrentBookings(this.getNumCurrentBookings()+1);
        

        if (this.getNumCurrentBookings() == MAX_BOOKINGS_NUM)
        {
            this.setAvailable(false);
        }

        return true;
		
	}
	
	/**
	 * override getDetails method
	 */
	public String getDetails() {
		String newLine = "\n";
		String result =  super.getDetails();
		StringBuilder details = new StringBuilder();
		details.append("Standard Fee: $").append(this.bookingFee).append(newLine);
		details.append(newLine);
		details.append("Refreshments Available").append(newLine);
		for(int i=0; i<this.refreshments.length; i++) {
			details.append("Item ").append(i+1).append("        ").append(this.refreshments[i]).append(newLine);
		}
		details.append(newLine);
		details.append("CURRENT BOOKINGS").append(newLine);
		details.append("    ！！！！！！！！！！！！！！！！！！！！！！！！！！！！").append(newLine);
		for(int j=0;j<this.getCurrentBookings().length;j++) {
			if (this.getCurrentBookings()[j]!=null) {
				details.append("    id: ").append(this.getCurrentBookings()[j].getId()).append(newLine);
				details.append("    Booking Fee: ").append(this.getCurrentBookings()[j].getBookingFee()).append(newLine);
				details.append("    Pick Up Date: ").append(this.getCurrentBookings()[j].getPickUpDateTime()).append(newLine);
				details.append("    Name: ").append(this.getCurrentBookings()[j].getFirstName()).append(this.getCurrentBookings()[j].getLastName()).append(newLine);
				details.append("    Passengers: ").append(this.getCurrentBookings()[j].getNumPassengers()).append(newLine);
				details.append("    Travelled: ").append(this.getCurrentBookings()[j].getKilometersTravelled()).append(newLine);
				details.append("    Trip Fee: ").append(this.getCurrentBookings()[j].getTripFee()).append(newLine);
				details.append("    Car Id: ").append(this.getCurrentBookings()[j].getCar().getRegNo()).append(newLine);

				details.append("    ！！！！！！！！！！！！！！！！！！！！！！！！！！！！").append(newLine);
			}
		}
		details.append("PAST BOOKINGS").append(newLine);
		details.append("    ！！！！！！！！！！！！！！！！！！！！！！！！！！！！").append(newLine);
		for(int j=0;j<this.getPastBookings().length;j++) {
			if (this.getPastBookings()[j]!=null) {
				details.append("    id: ").append(this.getPastBookings()[j].getId()).append(newLine);
				details.append("    Booking Fee: ").append(this.getPastBookings()[j].getBookingFee()).append(newLine);
				details.append("    Pick Up Date: ").append(this.getPastBookings()[j].getPickUpDateTime()).append(newLine);
				details.append("    Name: ").append(this.getPastBookings()[j].getFirstName()).append(this.getPastBookings()[j].getLastName()).append(newLine);
				details.append("    Passengers: ").append(this.getPastBookings()[j].getNumPassengers()).append(newLine);
				details.append("    Travelled: ").append(this.getPastBookings()[j].getKilometersTravelled()).append(newLine);
				details.append("    Trip Fee: ").append(this.getPastBookings()[j].getTripFee()).append(newLine);
				details.append("    Car Id: ").append(this.getPastBookings()[j].getCar().getRegNo()).append(newLine);

				details.append("    ！！！！！！！！！！！！！！！！！！！！！！！！！！！！").append(newLine);
			}
		}
		
		return result + details.toString();
		
	}

	/**
	 * override toString method
	 * ALGORITHM
	 * BEGIN
	 *     create empty result
	 *     result = result + bookingFee
	 *     for each booking in currentbooking
	 *         result = result + booking detail
 *         for each booking in pastbooking
	 *         result = result + booking detail
	 *     return result
	 * END
	 */
	public String toString() {
		String result = super.toString();
		result += ":"+this.bookingFee;
		StringBuilder details = new StringBuilder();
		for(Booking b:this.getCurrentBookings()) {
			if (b==null) {continue;}
			details.append("|").append(b.toString());
		}
		for(Booking b:this.getPastBookings()) {
			if (b==null) {continue;}
			details.append("|").append(b.toString());
		}
		return result+details;
	}
}
