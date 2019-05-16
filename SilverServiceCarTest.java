
import org.junit.Test;

/*
 * Class: SilverServiceCarTest
 * Description: The class is used for testing
 * Author: [LiangyuNie] - [s3716113]
 */
public class SilverServiceCarTest {

//	@Test
//	public void testBook() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testGetDetails() {
//		String[] sl = {"Mints", "Orange Juice"};
//		SilverServiceCar s = new SilverServiceCar("1", "make", "model", "driverName", 20, 1.00,sl);
//		s.book("firstname", "lastName", new DateTime(), 12);
//		s.book("firstname", "lastName", new DateTime(), 12);
//		s.book("firstname", "lastName", new DateTime(), 12);
//		System.out.println(s.getDetails());
//	}
	@Test
	public void testToString() {
		try {
			String[] sl = {"Mints", "Orange Juice"};
			SilverServiceCar s = new SilverServiceCar("1", "make", "model", "driverName", 20, 1.00,sl);
			s.book("firstname", "lastName", new DateTime(), 12);
			s.book("firstname", "lastName", new DateTime(), 12);
			s.book("firstname", "lastName", new DateTime(), 12);
			System.out.println(s.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
//
//	@Test
//	public void testSilverServiceCar() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetBookingFee() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetBookingFee() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetRefreshments() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetRefreshments() {
//		fail("Not yet implemented");
//	}

}
