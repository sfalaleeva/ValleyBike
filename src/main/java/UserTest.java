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
	private String creditCard;
	private Membership membership;
	
	
	@Before
	public void setUp() throws Exception {
		user = new User(null, null, null, null, null, null, "pwd"); // empty user object
	}
	
	// Each parameter is an argument in the constructor
	// Every time runner trigger, it will pass arguments from 
	// parameters defined in userTest() method.
	public UserTest(String creditCard, Membership membership, Boolean expectedResult) {
		user = new User(null, null, null, null, null, null, "pwd"); // empty user object
		this.creditCard = creditCard;
		this.membership = membership;
		this.expectedResult = expectedResult;
	}
	
	@Parameterized.Parameters
	public static Collection getTestData() {	
		return Arrays.asList(new Object[][] {
			{ "1212121212123456", Membership.NONE, false },
			{ "invalid", Membership.NONE, false },
			{ "invalid", Membership.YEAR, false },
			{ "1212321212121234", Membership.FOUNDER, true },
		});
	}
	
	// will run four times
	@Test
	public void testAccountActivated() {
		user.setCreditCard(creditCard);
		user.updateMembership(membership);
		assertEquals(expectedResult, user.getIsActive());
	}
	
	// will run four times, an invalid card does not replace a valid card
	@Test
	public void testUpdateInvalidCard() {
		user.setCreditCard(creditCard);
		user.updateMembership(membership);
		user.setCreditCard("invalid");
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

