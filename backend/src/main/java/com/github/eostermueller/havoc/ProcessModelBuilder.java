package com.github.eostermueller.havoc;

import com.github.eostermueller.havoc.launcher.CannotFindTjpFactoryClass;
import com.github.eostermueller.havoc.launcher.Suite;

public interface ProcessModelBuilder {

	Suite build() throws CannotFindTjpFactoryClass;

}