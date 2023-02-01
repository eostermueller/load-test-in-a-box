package com.github.eostermueller.snail4j.systemproperty.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.systemproperty.BooleanSystemPropertyImpl;
import com.github.eostermueller.snail4j.systemproperty.SystemPropertyTestValueRepoImpl;

public class FluxCapacitorTestBoolean {

	@Test
	public void canFindDefaultFluxCapacitorSetting() throws Snail4jException {
		
		YourClassThatUsesBooleanSystemProperty thingy = new YourClassThatUsesBooleanSystemProperty();
		
		Assertions.assertEquals(false,thingy.isFluxCapacitorActivated());
	}
	
	@Test
	public void canSetFluxCapacitorValueForUnitTest() throws Snail4jException  {

		try {
			//Create this object where system property values for JUnit tests will be kept.
			SystemPropertyTestValueRepoImpl repo = new SystemPropertyTestValueRepoImpl();
			//Make the junit values available to the load-test-in-a-box code base.
			DefaultFactory.getFactory().setSystemPropertyTestValueRepo(repo);
			
			repo.setBoolean(new FluxCapacitorActivator(), true);
			
			//Instantiate the code that uses the system property
			YourClassThatUsesBooleanSystemProperty thingy = new YourClassThatUsesBooleanSystemProperty();
			
			//Confirm that pink.flux.capacitor is found
			Assertions.assertEquals(true,thingy.isFluxCapacitorActivated());
			
		} finally {
			DefaultFactory.getFactory().resetUnitTestSystemProperties(); //required in unit tests, does not harm in production
		}
		
	}
}

class YourClassThatUsesBooleanSystemProperty {
	boolean fluxCapacitorActivated;
	
	public boolean isFluxCapacitorActivated() {
		return fluxCapacitorActivated;
	}

	public void setFluxCapacitorActivated(boolean fluxCapacitorActivated) {
		this.fluxCapacitorActivated = fluxCapacitorActivated;
	}

	public YourClassThatUsesBooleanSystemProperty() throws Snail4jException {
		setFluxCapacitorActivated( 
				//This line shows how to get value of the system property.
				DefaultFactory.getFactory().getSystemPropertyMgr().getBoolean( new FluxCapacitorActivator() ) 
				);
	}
}

/**
 * Need to configure something with a Java System Property "-D" parameter?
 * Create a class like this and place it in the com.github.eostermueller.systemproperty package
 * @author eoste
 *
 */
class FluxCapacitorActivator extends BooleanSystemPropertyImpl {

	@Override
	public boolean getDefaultValue() {
		return false;
	}


	@Override
	/**
	 * You could instead return a simple string, but the approach here enables i18n, where we can support multiple language (Spanish, Ukrainian, French, etc...)
	 */
	public String getHumanReadableDocumentation() throws CannotFindSnail4jFactoryClass {
		return DefaultFactory.getFactory().getMessages().getFluxCapacitorDoc();
	}

	@Override
	public String getDashDProperty() {
		return "com.github.eostermueller.test.snail4j.flux.capacitor.activation";
	}
}
