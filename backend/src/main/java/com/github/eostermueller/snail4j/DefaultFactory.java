package com.github.eostermueller.snail4j;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.eostermueller.snail4j.config.DefaultGenericConfigFileReaderWriter;
import com.github.eostermueller.snail4j.config.GenericConfigFileReaderWriter;
import com.github.eostermueller.snail4j.install.AvailableMemoryValidator;
import com.github.eostermueller.snail4j.install.AvailableMemoryValidatorImpl;
import com.github.eostermueller.snail4j.install.DefaultSutInstaller;
import com.github.eostermueller.snail4j.install.DiskSpaceValidator;
import com.github.eostermueller.snail4j.install.DiskSpaceValidatorImpl;
import com.github.eostermueller.snail4j.install.Installer;
import com.github.eostermueller.snail4j.install.Snail4jInstaller;
import com.github.eostermueller.snail4j.launcher.BootstrapConfig;
import com.github.eostermueller.snail4j.launcher.CannotFindSnail4jFactoryClass;
import com.github.eostermueller.snail4j.launcher.CommandLine;
import com.github.eostermueller.snail4j.launcher.ConfigLookup;
import com.github.eostermueller.snail4j.launcher.ConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.Configuration;
import com.github.eostermueller.snail4j.launcher.DefaultCommandLine;
import com.github.eostermueller.snail4j.launcher.DefaultConfigLookup;
import com.github.eostermueller.snail4j.launcher.DefaultConfigReaderWriter;
import com.github.eostermueller.snail4j.launcher.DefaultConfiguration;
import com.github.eostermueller.snail4j.launcher.EventHistory;
import com.github.eostermueller.snail4j.launcher.Factory;
import com.github.eostermueller.snail4j.launcher.Messages;

/**
 * Messages_en_US is used as a default if java.util.Locale.getDefault() 
 */
import com.github.eostermueller.snail4j.launcher.Messages_en_US;
import com.github.eostermueller.snail4j.processmodel.DefaultLoadGenerator;
import com.github.eostermueller.snail4j.processmodel.DefaultProcessModelBuilder;
import com.github.eostermueller.snail4j.processmodel.DefaultSystemUnderTest;
import com.github.eostermueller.snail4j.processmodel.LoadGenerator;
import com.github.eostermueller.snail4j.processmodel.ProcessModelBuilder;
import com.github.eostermueller.snail4j.processmodel.SystemUnderTest;
import com.github.eostermueller.snail4j.systemproperty.AvailableDiskSpaceValidation;
import com.github.eostermueller.snail4j.systemproperty.Headless;
import com.github.eostermueller.snail4j.systemproperty.OneTimeConfigChanger;
import com.github.eostermueller.snail4j.systemproperty.SystemPropertyManager;
import com.github.eostermueller.snail4j.systemproperty.SystemPropertyManagerImpl;
import com.google.common.flogger.FluentLogger;

public class DefaultFactory implements Factory {
	
	/**
	 * This private constructor helps consistently use the getFactory() "-D" system property plugin mechanism.
	 * See FACTORY_DASH_D_PARM for the property name.
	 * 
	 * 
	 * Use 
	 * <PRE>
	 * Factory f = DefaultFactory.getFactory();
	 * </PRE>
	 * instead of:
	 * <PRE>
	 * Factory f = new DefaultFactory();
	 * </PRE>
	 * 
	 * 
	 */
	private DefaultFactory() {
		
	}
	
	static EventHistory eventHistory = new EventHistory();
	static Factory FACTORY_INSTANCE = null;
	
	
	/**
	 * @stolenFrom: https://stackoverflow.com/questions/7855700/why-is-volatile-used-in-double-checked-locking
	 */
	private volatile Configuration configuration;
	
	/**
	 * Very low overhead (no more than a dozen a minute, single user changing configuration), so sync won't cause problem slowdowns.
	 */
	@Override
	public void setConfiguration(Configuration cfg) {
		synchronized (this.configuration) {
			this.configuration = cfg;
		}
	}
	@Override
	public OneTimeConfigChanger[] getOneTimeDashDSystemPropertyConfigChanges() {
		List<OneTimeConfigChanger> oneTimeConfigChangers = new ArrayList<OneTimeConfigChanger>();
		oneTimeConfigChangers.add( new Headless() );
		return oneTimeConfigChangers.toArray(new OneTimeConfigChanger[0]);
//		return (OneTimeConfigChanger[]) oneTimeConfigChangers.toArray();
	}
	
	
	
	
	private void oneTimeConfigurationChanges(Configuration cfg) throws Snail4jException {
		for(OneTimeConfigChanger oneTimeConfigChanger : this.getOneTimeDashDSystemPropertyConfigChanges() ) {
			oneTimeConfigChanger.oneTimeChange(cfg);
		}
	}

	@Override
	public GenericConfigFileReaderWriter getGenericConfigReaderWriter() {
		return new DefaultGenericConfigFileReaderWriter();
	}
	@Override
	   public Configuration getConfiguration() throws Snail4jException {
		   return getConfiguration( new BootstrapConfig() );
	}
		
	   @Override
	   public Configuration getConfiguration(BootstrapConfig bootstrapConfig) throws Snail4jException {
	        if (this.configuration == null) {
	            synchronized (Configuration.class) {
	                if (this.configuration == null) {
	                	
	                	
	            		ConfigReaderWriter configReaderWriter = DefaultFactory.getFactory().getConfigReaderWriter();
	            		File loadTestInABoxConfigFile = bootstrapConfig.getFullPathToConfigFile().toFile();
	            		
	            			try {
	            				Class<Configuration> configurationClass = (Class<Configuration>) Class.forName(this.getConfigurationClassName());
	    	            		if (loadTestInABoxConfigFile.exists()) {
	    	            			this.configuration = configReaderWriter.read(loadTestInABoxConfigFile, configurationClass);
	    	            		} else {
		            				this.configuration = (Configuration) configurationClass.getDeclaredConstructor(null).newInstance(null);
		            				bootstrapConfig.createLoadTestInABoxHomeIfNotExist();
	    	            		}
	            				this.oneTimeConfigurationChanges(this.configuration);
		            			configReaderWriter.write(
		            					bootstrapConfig.getFullPathToConfigFile().toFile(),
		            					this.configuration
		            					);

	            				
	            			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
	            					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
	            				CannotFindSnail4jFactoryClass cftf = new CannotFindSnail4jFactoryClass(e,this.getConfigurationClassName());
	            				throw cftf;
	            			} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            			
	                }
	            }
	        }
	        return this.configuration;
	    }	
	
	@Override
	public String getConfigurationClassName() {
		return DefaultConfiguration.class.getCanonicalName();
	}

	public static final String FACTORY_DASH_D_PARM = "com.github.eostermueller.snail4j.FactoryImpl";
    public static final String DEFAULT_FACTORY =     "com.github.eostermueller.snail4j.DefaultFactory";
	private FluentLogger LOG = FluentLogger.forEnclosingClass();
	static AtomicInteger jvmLifetimeUniqueId = new AtomicInteger();
	
	
	//Consider using the following:
	//https://github.com/fluent/fluent-logger-java
	//private static FluentLogger LOG = FluentLogger.getLogger("app", "remotehost", port);
	
	/**
	 * Default to value that JVM initializes, hopefully from operating system's configuration.
	 */
	private  Locale localeForMessages = Locale.getDefault();
	private  Messages messages = null;
	public  Locale getLocaleForMessages() {
		return localeForMessages;
	}
	/* Override this to set a locale other than your JVM's java.util.Locale.getDefault()
	 * (non-Javadoc)
	 * @see com.github.eostermueller.tjp.launcher.agent.MyFactory#setLocaleForMessages(java.lang.String)
	 */
	@Override
	public void setLocaleForMessages(String languageTag) {
		localeForMessages = Locale.forLanguageTag(languageTag);
	}
	/* (non-Javadoc)
	 * @see com.github.eostermueller.tjp.launcher.agent.MyFactory#getMessages()
	 */
	@Override
	public Messages getMessages() {
		if (messages==null)
			messages=createMessages();
		return messages;
	}
	/**
	 * Abides by the following, but this method replaces any
	 * dashes (-) with with underscores, so that the string
	 * can be appended to java class names.
	 * https://en.wikipedia.org/wiki/IETF_language_tag
	 * The following details the Underscore character's special designation as a "JavaLetter", meaning that it is allowed in a Java Class name, and a dash is not.
	 * https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
	 * @return
	 */
	private String getMangledLanguageTag() {
		String languageTag = getLocaleForMessages().toLanguageTag();
		return languageTag.replace('-', '_');
	}
	@Override
	public Messages createMessages() {
		String packageAndClassName = "com.github.eostermueller.snail4j.launcher.Messages_" + getMangledLanguageTag();
		
		Messages messages;
		try {
			Class messagesClass = Class.forName(packageAndClassName);
			messages = (Messages) messagesClass.getDeclaredConstructor(null).newInstance(null);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			LOG.atWarning().withCause(e).log("Could not find class [%s] in the classpath, will use %s instead", packageAndClassName, Messages_en_US.class.getName() );
			messages = new Messages_en_US();
		}
		return messages;
	}
	
	/**
	 *   todo:  figure out why I got this intermittent failure one time:
	 * 
	 * 
[INFO] Results:
[INFO]
[ERROR] Failures:
[ERROR]   UniqueIdTest.test08:60->test:100 expected:<[4, 5, 6, 7, 8, 9, 10, 11]> but was:<[3, 4, 5, 6, 7, 8, 9, 10]>                                 
[INFO]
[ERROR] Tests run: 24, Failures: 1, Errors: 0, Skipped: 1
[INFO]
	 *
	 *
	 */
	@Override
	public long getJvmLifetimeUniqueId() {
		return jvmLifetimeUniqueId.incrementAndGet();
	}
	/**
	 * Build factory from Java -D system parameter: com.github.eostermueller.tjp.launcher.agent.FactoryImpl
	 * @return
	 * @throws CannotFindSnail4jFactoryClass 
	 */
	public static Factory getFactory() throws CannotFindSnail4jFactoryClass {
		if (FACTORY_INSTANCE==null) {
			String myFactoryClassName = System.getProperty(FACTORY_DASH_D_PARM,DEFAULT_FACTORY);
			try {
				Class<Factory> factoryClass = (Class<Factory>) Class.forName(myFactoryClassName); 
				Constructor[] ctors = factoryClass.getDeclaredConstructors();
				Constructor ctor = null;
				for (int i = 0; i < ctors.length; i++) {
				    ctor = ctors[i];
				    if (ctor.getGenericParameterTypes().length == 0)
					break;
				}
				FACTORY_INSTANCE = (Factory)ctor.newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | ClassNotFoundException e) {
				CannotFindSnail4jFactoryClass cftf = new CannotFindSnail4jFactoryClass(e,myFactoryClassName);
				throw cftf;
			}
		}
		
		return FACTORY_INSTANCE;
	}
	@Override
	public EventHistory getEventHistory() {
		return eventHistory;
	}
	@Override
	public ConfigReaderWriter getConfigReaderWriter() {
		ConfigReaderWriter configReaderWriter = new DefaultConfigReaderWriter();
		return configReaderWriter;
	}
	@Override
	public ProcessModelBuilder createProcessModelBuilder() throws Snail4jException {
		return new DefaultProcessModelBuilder( getConfiguration() );
	}
	@Override
	public
	Snail4jInstaller createNewInstaller() throws CannotFindSnail4jFactoryClass {
		return new Snail4jInstaller();
	}
	@Override
	public ConfigLookup createConfigLookup() throws Snail4jException {
		ConfigLookup cfgLookup = new DefaultConfigLookup();
		cfgLookup.setConfiguration(getConfiguration());
		return cfgLookup;
	}
	@Override
	public CommandLine createNewCommandLine(String val) throws Snail4jException  {
		CommandLine cmdLine = new DefaultCommandLine(val);
		cmdLine.setConfigLookup( createConfigLookup() );
		return cmdLine;
	}
	
	@Override
	public SystemUnderTest createSystemUnderTest() throws Snail4jException {
		SystemUnderTest sut = new DefaultSystemUnderTest((Configuration)getConfiguration()); 
		return sut;
	}
	@Override
	public LoadGenerator createLoadGenerator() throws Snail4jException {
		return new DefaultLoadGenerator((Configuration)this.getConfiguration() );
	}
	
	@Override
	public Installer createSutInstaller() throws Snail4jException {
		return new DefaultSutInstaller();
	}
	
	@Override
	public AvailableMemoryValidator getAvailableMemoryValidator() throws Snail4jException {
		AvailableMemoryValidator validator = new AvailableMemoryValidatorImpl();
		validator.setMinMemoryAvailableRequirementInBytes( (getConfiguration().getMinMemoryAvailableRequirementInBytes() ));
		return validator;
	}
	@Override
	public DiskSpaceValidator getDiskSpaceValidator() throws Snail4jException {
		return new DiskSpaceValidatorImpl();
	}
}