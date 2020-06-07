package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.Test;

class TestEnvVarTest {

  @Rule
  public final EnvironmentVariables environmentVariables
    = new EnvironmentVariables();

  @Test
  public void setEnvironmentVariable() {
    environmentVariables.set("name", "value");
    assertEquals("value", System.getenv("name"));
  }
}
