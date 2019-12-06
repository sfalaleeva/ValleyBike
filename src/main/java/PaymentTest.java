import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PaymentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidCardNumber() {
		boolean valid = Payment.validateCardNumber("1234567891234566");
		assertTrue(valid);
	}
	
	@Test
	public void testInvalidCardNumber() {
		boolean valid = Payment.validateCardNumber("1234534566");
		assertFalse(valid);
	}
	
	@Test
	public void testAbstractValidation() {
		boolean valid = Payment.validateCard("12342342322323232", LocalDate.now().plusMonths(1));
		assertTrue(valid);
	}

}
