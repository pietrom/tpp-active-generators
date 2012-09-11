package org.amicofragile.test; 

import org.junit.Test;
import static org.junit.Assert.*;

public class MSG01Test {
	@Test
	public void createMSG01() {
		final MSG01 msg = new MSG01();
	}
	
	@Test
	public void setGetValue() {
		final MSG01 msg = new MSG01();
		msg.setXxx("TEST");
		assertEquals("TEST", msg.getXxx());
	}
}
