package com.github.eostermueller.snail4j.systemproperty.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.launcher.BootstrapConfig;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.systemproperty.StringSystemPropertyImpl;
import com.github.eostermueller.snail4j.systemproperty.SystemPropertyTestValueRepoImpl;

public class FluxCapacitorTestString {

	@Test
	public void canFindDefaultFluxCapacitor() throws CannotFindSnail4jFactoryClass {
		
		new BootstrapConfig().setSystemPropertyTestValueRepo(null);//Purge values from previous unit tests
		YourClassThatUsesSystemProperty thingy = new YourClassThatUsesSystemProperty();
		
		Assertions.assertEquals(FluxCapacitorName.WARP,thingy.getFluxCapacitorName());
	}
	
	@Test
	public void canSetFluxCapacitorValueForUnitTest() throws CannotFindSnail4jFactoryClass {
				
		try {
			//Create this object where system property values for JUnit tests will be kept.
			SystemPropertyTestValueRepoImpl repo = new SystemPropertyTestValueRepoImpl();
			//Make the junit values available to the load-test-in-a-box code base.
			new BootstrapConfig().setSystemPropertyTestValueRepo(repo);
			
			repo.setString(new FluxCapacitorName(), "pink.flux.capacitor");
			
			//Instantiate the code that uses the system property
			YourClassThatUsesSystemProperty thingy = new YourClassThatUsesSystemProperty();
			
			//Confirm that pink.flux.capacitor is found
			Assertions.assertEquals(thingy.getFluxCapacitorName(),"pink.flux.capacitor");
		} finally {
			new BootstrapConfig().resetUnitTestSystemProperties(); //required in unit tests, does not harm in production
		}
	}
}

class YourClassThatUsesSystemProperty {
	String fluxCapacitorName;
	
	public YourClassThatUsesSystemProperty() throws CannotFindSnail4jFactoryClass {
		setFluxCapacitorName( 
				//This line shows how to get value of the system property.
				new BootstrapConfig().getSystemPropertyMgr().getString( new FluxCapacitorName() ) 
				);
	}
	
	private void setFluxCapacitorName(String string) {
		this.fluxCapacitorName = string;
	}

	public String getFluxCapacitorName() {
		return fluxCapacitorName;
	}
}

/**
 * Need to configure something with a Java System Property "-D" parameter?
 * Create a class like this and place it in the com.github.eostermueller.systemproperty package
 * @author eoste
 *
 */
class FluxCapacitorName extends StringSystemPropertyImpl {

	public static final String WARP = "warp";
	@Override
	public String getDefaultValue() {
		return WARP;
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
		return "com.github.eostermueller.test.snail4j.flux.capacitor.name";
	}

}
