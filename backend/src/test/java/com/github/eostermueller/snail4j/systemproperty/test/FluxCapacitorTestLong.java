package com.github.eostermueller.snail4j.systemproperty.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.BootstrapConfig;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.systemproperty.LongSystemPropertyImpl;
import com.github.eostermueller.snail4j.systemproperty.SystemPropertyTestValueRepoImpl;

public class FluxCapacitorTestLong {

	@Test
	public void canFindDefaultFluxCapacitorMaxPowerSetting() throws Snail4jException {
		
		YourClassThatUsesLongSystemProperty thingy = new YourClassThatUsesLongSystemProperty();
		
		Assertions.assertEquals(Long.MAX_VALUE-42,thingy.getFluxCapacitorPowerCap());
	}
	
	@Test
	public void canSetFluxCapacitorValueForUnitTest() throws Snail4jException {
		
		
		try {
			//Create this object where system property values for JUnit tests will be kept.
			SystemPropertyTestValueRepoImpl repo = new SystemPropertyTestValueRepoImpl();
			//Make the junit values available to the load-test-in-a-box code base.
			new BootstrapConfig().setSystemPropertyTestValueRepo(repo);
			
			repo.setLong(new FluxCapacitorPowerCap(), 8675309);//rememberable number
			
			//Instantiate the code that uses the system property
			YourClassThatUsesLongSystemProperty thingy = new YourClassThatUsesLongSystemProperty();
			
			//Confirm that pink.flux.capacitor is found
			Assertions.assertEquals(8675309,thingy.getFluxCapacitorPowerCap());
		} finally {
			new BootstrapConfig().resetUnitTestSystemProperties();
		}
	}
}

class YourClassThatUsesLongSystemProperty {
	public long getFluxCapacitorPowerCap() {
		return fluxCapacitorPowerCap;
	}

	public void setFluxCapacitorPowerCap(long fluxCapacitorPowerCap) {
		this.fluxCapacitorPowerCap = fluxCapacitorPowerCap;
	}



	long fluxCapacitorPowerCap;

	public YourClassThatUsesLongSystemProperty() throws Snail4jException {
		setFluxCapacitorPowerCap( 
				//This line shows how to get value of the system property.
				new BootstrapConfig().getSystemPropertyMgr().getLong( new FluxCapacitorPowerCap() ) 
				);
	}
}

/**
 * Need to configure something with a Java System Property "-D" parameter?
 * Create a class like this and place it in the com.github.eostermueller.systemproperty package
 * @author eoste
 *
 */
class FluxCapacitorPowerCap extends LongSystemPropertyImpl {

	@Override
	public long getDefaultValue() {
		return Long.MAX_VALUE-42;
	}

	@Override
	/**
	 * You could just return a simple string, but the approach here enables i18n, where we can support multiple language (Spanish, Ukrainian, French, etc...)
	 */
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().getFluxCapacitorDoc();
	}

	@Override
	public String getDashDPropertyName() {
		return "com.github.eostermueller.test.snail4j.flux.capacitor.power.cap";
	}

}
