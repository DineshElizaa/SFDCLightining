package com.infy.autoqa.karate.helperclass;

import org.openqa.selenium.WebDriver;

/*import com.salesforce.lpop.framework.context.AppContext;
import com.salesforce.lpop.integration.ApplicationUtilities;
import com.salesforce.lpop.integration.LightningPageObjects;*/

public class BaseTest {

	
	private LightningPageObjects lpop;
	protected ApplicationUtilities helpers;
	AppContext type;	
	protected WebDriver driver;
	
	/**
	 * Method to perform integration
	 * @Author: vveeranx
	 * @since 21-Mar-2019
	 * @return
	 */
	protected final LightningPageObjects getIntegration() {
		if (lpop == null) {
			throw new IllegalArgumentException("Integration object is null");
		}
		return lpop;
	}
	
	public final ApplicationUtilities utilities() {
		return getIntegration().getUtilities();
	}
	
}
