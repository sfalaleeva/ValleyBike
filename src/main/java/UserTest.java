import static org.junit.Assert.*;
import org.junit.*; // instead of just .Test

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	
	User user;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		user = new User("password"); // empty user object
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCancelMembership() {
		user.updateMembership(Membership.NONE);
		assertEquals(user.getIsActive(), false);
		assertEquals(Membership.NONE, user.getMembership());
	}
	
	// will pass if updateStatus() sets isActive to true
	@Test
	public void setWithValidPaymentOption() {
		user.updateMembership(Membership.DAY);
		// activates user account
		assertEquals(user.getIsActive(), true);
		// membership set
		assertEquals(Membership.DAY, user.getMembership());
		// expiration date updated
		assertNotEquals(user.getMembershipExpirationDate(), null);
		// user balance updated
		assertEquals(Membership.DAY.getPrice(), user.getBalance(), 0);
		//fail("User balance not updated.");
	}

}