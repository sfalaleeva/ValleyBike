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
		user = new User(null, null, null, null, null, null, "pwd"); // empty user object
		user.setCreditCard("1234567891234567"); // valid credit card number
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
	
	@Test
	public void testAccountActived() {
		user.updateMembership(Membership.DAY);
		// activates user account
		assertEquals(true, user.getIsActive());
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
		assertNotEquals(user.getMembershipExpirationDate(), null);
	}
	
	@Test
	public void testUserBalance() {
		user.updateMembership(Membership.DAY);
		// user balance updated
		assertEquals(Membership.DAY.getPrice(), user.getBalance(), 0);
	}
	
	@Test
	public void testUpdateStatus() {
		assertEquals(false, user.getIsActive()); // starts false with no membership
		user.updateMembership(Membership.DAY);
		user.updateStatus();
		assertEquals(true, user.getIsActive());
	}
}
