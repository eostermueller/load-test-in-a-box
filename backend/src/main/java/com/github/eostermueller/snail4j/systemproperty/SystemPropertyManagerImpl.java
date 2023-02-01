package com.github.eostermueller.snail4j.systemproperty;

import com.github.eostermueller.snail4j.Snail4jException;

public class SystemPropertyManagerImpl implements SystemPropertyManager {

	private SystemPropertyManager systemPropertyTestRepository;

	public SystemPropertyManager getSystemPropertyTestValueRepository() {
		return this.systemPropertyTestRepository;
	}

	public void setSystemPropertyTestValueRepository(SystemPropertyManager r) {
		this.systemPropertyTestRepository = r;
	}
	
	@Override
	public boolean getBoolean(BooleanSystemProperty systemProperty) throws Snail4jException {
		if (getSystemPropertyTestValueRepository()==null) {
			return systemProperty.getValue();
		} else {
			return this.getSystemPropertyTestValueRepository().getBoolean(systemProperty);
		}
	}

	@Override
	public long getLong(LongSystemProperty systemProperty) throws Snail4jException {
		if (getSystemPropertyTestValueRepository()==null) {
			return systemProperty.getValue();
		} else {
			return this.getSystemPropertyTestValueRepository().getLong(systemProperty);
		}
	}

	@Override
	public String getString(StringSystemProperty systemProperty) {
		if (getSystemPropertyTestValueRepository()==null) {
			return systemProperty.getValue();
		} else {
			return this.getSystemPropertyTestValueRepository().getString(systemProperty);
		}
	}

}
