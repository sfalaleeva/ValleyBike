import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.*; // instead of just .Test

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class UserTest {
	
	private User user;
	private Boolean expectedResult;
	private String creditCardNumber;
	private LocalDate expirationDate;
	private Membership membership;
	
	
	@Before
	public void setUp() throws Exception {
		user = new User(null, null, null, null, null, null, "pwd"); // empty user object
	}
	
	// Each parameter is an argument in the constructor
	// Every time runner trigger, it will pass arguments from 
	// parameters defined in userTest() method.
	public UserTest(String creditCard, LocalDate expirationDate, Membership membership, Boolean expectedResult) {
		user = new User(null, null, null, null, null, null, "pwd"); // empty user object
		this.creditCardNumber = creditCard;
		this.expirationDate = expirationDate;
		this.membership = membership;
		this.expectedResult = expectedResult;
	}
	
	@Parameterized.Parameters
	public static Collection getTestData() {	
		return Arrays.asList(new Object[][] {
			// valid card number, invalid expiration, no membership -> FALSe
			{ "1212121212123456", LocalDate.now().minusMonths(1), Membership.NONE, false },
			// valid card number, valid expiration, no membership -> FALSE
			{ "1212121212123456", LocalDate.now().plusMonths(1), Membership.NONE, false },
			// invalid card number, invalid expiration, no membership -> FALSE
			{ "invalid", LocalDate.now().minusMonths(1), Membership.NONE, false },
			// invalid card number, invalid expiration, valid membership -> FALSE
			{ "invalid", LocalDate.now().minusMonths(1), Membership.YEAR, false },
			// invalid card number, valid expiration, valid membership -> FALSE
			{ "invalid", LocalDate.now().plusMonths(1), Membership.YEAR, false },
			// valid card number, valid expiration, valid membership -> TRUE
			{ "1212321212121234", LocalDate.now().plusMonths(1), Membership.FOUNDER, true },
		});
	}
	
	// will run for each test case
	@Test
	public void testAccountActivated() {
		user.setCreditCard(creditCardNumber, expirationDate);
		user.updateMembership(membership);
		assertEquals(expectedResult, user.getIsActive());
	}
	
	// will run four times, an invalid card does not replace a valid card
	@Test
	public void testUpdateInvalidCard() {
		user.setCreditCard(creditCardNumber, expirationDate);
		user.updateMembership(membership);
		//user.setCreditCard("invalid", LocalDate.now().plusMonths(1));
		assertEquals(expectedResult, user.getIsActive());
		}	

	@Test
	public void testCancelMembership() {
		user.updateMembership(Membership.NONE);
		assertEquals(false, user.getIsActive());
		assertEquals(Membership.NONE, user.getMembership());
	}
	
	@Test
	// will fail if the creditCard isn't validated.
	public void testMembershipIsSet() {
		user.updateMembership(Membership.DAY);
		// membership set
		assertEquals(Membership.DAY, user.getMembership());
	}
	
	@Test
	public void testExpirationDateChanged() {
		user.updateMembership(Membership.DAY);
		// expiration date updated
		assertEquals(user.getMembershipExpirationDate(), LocalDate.now().plusDays(1));
	}
	
}

