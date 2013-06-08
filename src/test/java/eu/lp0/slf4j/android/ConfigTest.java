/**
 * Copyright 2013  Simon Arlott
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.lp0.slf4j.android;

import static eu.lp0.slf4j.android.MockUtil.mockConfig;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { ConfigTest.class, LoggingConfig.class }, fullyQualifiedNames = { "android.util.Log", "eu.lp0.slf4j.android.LoggerFactory" })
public class ConfigTest {
	@Mock()
	private Properties ioErrorProperties;

	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	@Test
	public void tag_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("JavaApp", config.get(null).tag);
		Assert.assertEquals("JavaApp", config.get("").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.Socket").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.Void").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.List").tag);
		Assert.assertEquals("JavaApp", config.get("java.oops.Test").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.concurrent.locks.ReentrantReadWriteLock").tag);
		Assert.assertEquals("", config.get("javax.swing.JFrame").tag);
		Assert.assertEquals("JavaApp", config.get("java.sql.Driver").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test1").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test2.Test3").tag);
	}

	@Test
	public void tag_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("JavaApp", config.get(null).tag);
		Assert.assertEquals("JavaApp", config.get("").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.Socket").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.Void").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.List").tag);
		Assert.assertEquals("JavaApp", config.get("java.oops.Test").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.concurrent.locks.ReentrantReadWriteLock").tag);
		Assert.assertEquals("", config.get("javax.swing.JFrame").tag);
		Assert.assertEquals("JavaApp", config.get("java.sql.Driver").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test1").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test2.Test3").tag);
	}

	@Test
	public void level_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals(LogLevel.WARN, config.get(null).level);
		Assert.assertEquals(LogLevel.WARN, config.get("").level);
		Assert.assertEquals(LogLevel.DEBUG, config.get("java.net.Socket").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.Void").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.List").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.oops.Test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").level);
		Assert.assertEquals(LogLevel.WARN, config.get("javax.swing.JFrame").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.sql.Driver").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test1").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test2.Test3").level);
	}

	@Test
	public void level_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals(LogLevel.WARN, config.get(null).level);
		Assert.assertEquals(LogLevel.WARN, config.get("").level);
		Assert.assertEquals(LogLevel.DEBUG, config.get("java.net.Socket").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.Void").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.List").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.oops.Test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").level);
		Assert.assertEquals(LogLevel.WARN, config.get("javax.swing.JFrame").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.sql.Driver").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test1").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test2.Test3").level);
	}

	@Test
	public void showName_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get(null).showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.Socket").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.Void").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.List").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.oops.Test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("javax.swing.JFrame").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.sql.Driver").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test1").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test2.Test3").showName);
	}

	@Test
	public void showName_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get(null).showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.Socket").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.Void").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.List").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.oops.Test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("javax.swing.JFrame").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.sql.Driver").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test1").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test2.Test3").showName);
	}

	@Test
	public void showThread_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals(false, config.get(null).showThread);
		Assert.assertEquals(false, config.get("").showThread);
		Assert.assertEquals(false, config.get("java.net.Socket").showThread);
		Assert.assertEquals(false, config.get("java.lang.Void").showThread);
		Assert.assertEquals(false, config.get("java.util.List").showThread);
		Assert.assertEquals(false, config.get("java.oops.Test").showThread);
		Assert.assertEquals(true, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showThread);
		Assert.assertEquals(false, config.get("javax.swing.JFrame").showThread);
		Assert.assertEquals(true, config.get("java.sql.Driver").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test1").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test2.Test3").showThread);
	}

	@Test
	public void showThread_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals(false, config.get(null).showThread);
		Assert.assertEquals(false, config.get("").showThread);
		Assert.assertEquals(false, config.get("java.net.Socket").showThread);
		Assert.assertEquals(false, config.get("java.lang.Void").showThread);
		Assert.assertEquals(false, config.get("java.util.List").showThread);
		Assert.assertEquals(false, config.get("java.oops.Test").showThread);
		Assert.assertEquals(true, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showThread);
		Assert.assertEquals(false, config.get("javax.swing.JFrame").showThread);
		Assert.assertEquals(true, config.get("java.sql.Driver").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test1").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test2.Test3").showThread);
	}

	@Test
	public void noConfigDefaults_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("noConfig.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void noConfigDefaults_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("noConfig.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void invalidDefaults_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest2.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void invalidDefaults_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest2.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void ioErrorPropertiesFileDefaults_NoLogging() throws Exception {
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(ioErrorProperties);
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(InputStream.class));
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(Reader.class));

		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void ioErrorPropertiesFileDefaults_WithLogging() throws Exception {
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(ioErrorProperties);
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(InputStream.class));
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(Reader.class));

		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}
}
