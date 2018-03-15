package MobileUtil;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MobileUtilTest {
	MobileUtil mobileUtil=new MobileUtil();
	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("static-access")
	@Test
	public void testValidateMobile() {
		System.out.println(mobileUtil.validateMobile("13091879164"));
	}

}
