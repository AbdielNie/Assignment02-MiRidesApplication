/* Class:			InvalidDate
 * Description:		represents an issue that occurs when a date problem is found
 * Author:          LiangyuNie - s3716113
 */
public class InvalidDate extends Exception{
	private static final long serialVersionUID = 1L;

	private String invalidDate = "invalid date";

	InvalidDate() {
		System.out.print(invalidDate);
	}

}
