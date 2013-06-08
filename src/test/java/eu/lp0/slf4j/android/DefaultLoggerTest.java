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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Marker;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = DefaultLoggerTest.class, fullyQualifiedNames = { "android.util.Log", "eu.lp0.slf4j.android.LoggerFactory" })
public class DefaultLoggerTest {
	@Mock
	private Throwable throwable;

	@Mock
	private Marker marker;

	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	/**
	 * Create a unique tag for the current test
	 */
	private static String createTag(int frames) {
		StackTraceElement ste = new CallerStackTrace(frames + 1).get();
		return ste.getClassName() + "." + ste.getMethodName();
	}

	/**
	 * Mock the log level for the current test
	 */
	private static void mockLogLevel(LogLevel level) {
		when(Log.isLoggable(Matchers.eq(createTag(1)), anyInt())).then(new MockLogLevelAnswer(level));
	}

	/**
	 * Create a mock logger config for the current test
	 */
	private static LoggerConfig mockConfig() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with overridden log level
	 */
	private static LoggerConfig mockConfig(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/* Name, Levels */

	@Test
	public void testName() {
		Assert.assertEquals("Logger Name!", new LogAdapter("Logger Name!", mockConfig()).getName());
	}

	@Test
	public void testLevel_Native_SUPPRESS() {
		mockLogLevel(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfig());

		Assert.assertFalse(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertFalse(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_ERROR() {
		mockLogLevel(LogLevel.ERROR);
		LogAdapter log = new LogAdapter("N/A", mockConfig());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_WARN() {
		mockLogLevel(LogLevel.WARN);
		LogAdapter log = new LogAdapter("N/A", mockConfig());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_INFO() {
		mockLogLevel(LogLevel.INFO);
		LogAdapter log = new LogAdapter("N/A", mockConfig());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_DEBUG() {
		mockLogLevel(LogLevel.DEBUG);
		LogAdapter log = new LogAdapter("N/A", mockConfig());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_VERBOSE() {
		mockLogLevel(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfig());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertTrue(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertTrue(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_SUPPRESS() {
		mockLogLevel(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfig(LogLevel.SUPPRESS));

		Assert.assertFalse(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertFalse(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_ERROR() {
		mockLogLevel(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfig(LogLevel.ERROR));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_WARN() {
		mockLogLevel(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfig(LogLevel.WARN));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_INFO() {
		mockLogLevel(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfig(LogLevel.INFO));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_DEBUG() {
		mockLogLevel(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfig(LogLevel.DEBUG));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_VERBOSE() {
		mockLogLevel(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfig(LogLevel.VERBOSE));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertTrue(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertTrue(log.isTraceEnabled(marker));
	}

	/* Error Enabled */

	@Test
	public void testERROR_errorEnabled() {
		mockLogLevel(LogLevel.ERROR);
		Assert.assertTrue(new LogAdapter("N/A", mockConfig()).isErrorEnabled());
	}

	@Test
	public void testERROR_error_Msg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 1");

		verifyStatic();
		Log.e(createTag(0), "Message 1");
	}

	@Test
	public void testERROR_error_MsgArg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 2 {}", "arg");

		verifyStatic();
		Log.e(createTag(0), "Message 2 arg");
	}

	@Test
	public void testERROR_error_Msg2Args() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 3 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.e(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testERROR_error_MsgManyArgs() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.e(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testERROR_error_MsgExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 5", throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testERROR_error_MsgNullExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 6", (Throwable)null);

		verifyStatic();
		Log.e(createTag(0), "Message 6");
	}

	@Test
	public void testERROR_error_MsgObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 7", (Object)throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testERROR_error_MsgObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 8", (Object)null);

		verifyStatic();
		Log.e(createTag(0), "Message 8");
	}

	@Test
	public void testERROR_error_Msg2ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 9 {}", "arg1", throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testERROR_error_Msg2ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 10 {}", "arg1", null);

		verifyStatic();
		Log.e(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testERROR_error_Msg3ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testERROR_error_Msg3ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.e(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testERROR_error_Marker_Msg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 13");

		verifyStatic();
		Log.e(createTag(0), "Message 13");
	}

	@Test
	public void testERROR_error_Marker_MsgArg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 14 {}", "arg");

		verifyStatic();
		Log.e(createTag(0), "Message 14 arg");
	}

	@Test
	public void testERROR_error_Marker_Msg2Args() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.e(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testERROR_error_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.e(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testERROR_error_Marker_MsgExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 17", throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testERROR_error_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 18", (Throwable)null);

		verifyStatic();
		Log.e(createTag(0), "Message 18");
	}

	@Test
	public void testERROR_error_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 19", (Object)throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testERROR_error_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 20", (Object)null);

		verifyStatic();
		Log.e(createTag(0), "Message 20");
	}

	@Test
	public void testERROR_error_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testERROR_error_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 22 {}", "arg1", null);

		verifyStatic();
		Log.e(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testERROR_error_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.e(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testERROR_error_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.e(createTag(0), "Message 24 arg1 arg2");
	}

	/* Error Disabled */

	@Test
	public void testSUPPRESS_errorEnabled() {
		mockLogLevel(LogLevel.SUPPRESS);
		Assert.assertFalse(new LogAdapter("N/A", mockConfig()).isErrorEnabled());
	}

	@Test
	public void testSUPPRESS_error_Msg() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 1");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 1");
	}

	@Test
	public void testSUPPRESS_error_MsgArg() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 2 {}", "arg");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 2 arg");
	}

	@Test
	public void testSUPPRESS_error_Msg2Args() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testSUPPRESS_error_MsgManyArgs() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testSUPPRESS_error_MsgExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 5", throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testSUPPRESS_error_MsgNullExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 6", (Throwable)null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 6");
	}

	@Test
	public void testSUPPRESS_error_MsgObjExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 7", (Object)throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testSUPPRESS_error_MsgObjNull() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 8", (Object)null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 8");
	}

	@Test
	public void testSUPPRESS_error_Msg2ObjExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 9 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testSUPPRESS_error_Msg2ObjNull() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 10 {}", "arg1", null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testSUPPRESS_error_Msg3ObjExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testSUPPRESS_error_Msg3ObjNull() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 13");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 13");
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgArg() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 14 {}", "arg");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 14 arg");
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg2Args() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 17", throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 18", (Throwable)null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 18");
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 19", (Object)throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 20", (Object)null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 20");
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 22 {}", "arg1", null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfig()).error(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.e(createTag(0), "Message 24 arg1 arg2");
	}

	/* Warn Enabled */

	@Test
	public void testWARN_warnEnabled() {
		mockLogLevel(LogLevel.WARN);
		Assert.assertTrue(new LogAdapter("N/A", mockConfig()).isWarnEnabled());
	}

	@Test
	public void testWARN_warn_Msg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 1");

		verifyStatic();
		Log.w(createTag(0), "Message 1");
	}

	@Test
	public void testWARN_warn_MsgArg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 2 {}", "arg");

		verifyStatic();
		Log.w(createTag(0), "Message 2 arg");
	}

	@Test
	public void testWARN_warn_Msg2Args() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 3 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.w(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testWARN_warn_MsgManyArgs() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.w(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testWARN_warn_MsgExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 5", throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testWARN_warn_MsgNullExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 6", (Throwable)null);

		verifyStatic();
		Log.w(createTag(0), "Message 6");
	}

	@Test
	public void testWARN_warn_MsgObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 7", (Object)throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testWARN_warn_MsgObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 8", (Object)null);

		verifyStatic();
		Log.w(createTag(0), "Message 8");
	}

	@Test
	public void testWARN_warn_Msg2ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 9 {}", "arg1", throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testWARN_warn_Msg2ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 10 {}", "arg1", null);

		verifyStatic();
		Log.w(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testWARN_warn_Msg3ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testWARN_warn_Msg3ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.w(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testWARN_warn_Marker_Msg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 13");

		verifyStatic();
		Log.w(createTag(0), "Message 13");
	}

	@Test
	public void testWARN_warn_Marker_MsgArg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 14 {}", "arg");

		verifyStatic();
		Log.w(createTag(0), "Message 14 arg");
	}

	@Test
	public void testWARN_warn_Marker_Msg2Args() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.w(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testWARN_warn_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.w(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testWARN_warn_Marker_MsgExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 17", throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testWARN_warn_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 18", (Throwable)null);

		verifyStatic();
		Log.w(createTag(0), "Message 18");
	}

	@Test
	public void testWARN_warn_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 19", (Object)throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testWARN_warn_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 20", (Object)null);

		verifyStatic();
		Log.w(createTag(0), "Message 20");
	}

	@Test
	public void testWARN_warn_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testWARN_warn_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 22 {}", "arg1", null);

		verifyStatic();
		Log.w(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testWARN_warn_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.w(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testWARN_warn_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.w(createTag(0), "Message 24 arg1 arg2");
	}

	/* Warn Disabled */

	@Test
	public void testERROR_warnEnabled() {
		mockLogLevel(LogLevel.ERROR);
		Assert.assertFalse(new LogAdapter("N/A", mockConfig()).isWarnEnabled());
	}

	@Test
	public void testERROR_warn_Msg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 1");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 1");
	}

	@Test
	public void testERROR_warn_MsgArg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 2 {}", "arg");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 2 arg");
	}

	@Test
	public void testERROR_warn_Msg2Args() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testERROR_warn_MsgManyArgs() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testERROR_warn_MsgExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 5", throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testERROR_warn_MsgNullExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 6", (Throwable)null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 6");
	}

	@Test
	public void testERROR_warn_MsgObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 7", (Object)throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testERROR_warn_MsgObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 8", (Object)null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 8");
	}

	@Test
	public void testERROR_warn_Msg2ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 9 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testERROR_warn_Msg2ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 10 {}", "arg1", null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testERROR_warn_Msg3ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testERROR_warn_Msg3ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testERROR_warn_Marker_Msg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 13");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 13");
	}

	@Test
	public void testERROR_warn_Marker_MsgArg() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 14 {}", "arg");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 14 arg");
	}

	@Test
	public void testERROR_warn_Marker_Msg2Args() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testERROR_warn_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testERROR_warn_Marker_MsgExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 17", throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testERROR_warn_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 18", (Throwable)null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 18");
	}

	@Test
	public void testERROR_warn_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 19", (Object)throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testERROR_warn_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 20", (Object)null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 20");
	}

	@Test
	public void testERROR_warn_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testERROR_warn_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 22 {}", "arg1", null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testERROR_warn_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testERROR_warn_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfig()).warn(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.w(createTag(0), "Message 24 arg1 arg2");
	}

	/* Info Enabled */

	@Test
	public void testINFO_warnEnabled() {
		mockLogLevel(LogLevel.INFO);
		Assert.assertTrue(new LogAdapter("N/A", mockConfig()).isInfoEnabled());
	}

	@Test
	public void testINFO_info_Msg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 1");

		verifyStatic();
		Log.i(createTag(0), "Message 1");
	}

	@Test
	public void testINFO_info_MsgArg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 2 {}", "arg");

		verifyStatic();
		Log.i(createTag(0), "Message 2 arg");
	}

	@Test
	public void testINFO_info_Msg2Args() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 3 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.i(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testINFO_info_MsgManyArgs() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.i(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testINFO_info_MsgExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 5", throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testINFO_info_MsgNullExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 6", (Throwable)null);

		verifyStatic();
		Log.i(createTag(0), "Message 6");
	}

	@Test
	public void testINFO_info_MsgObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 7", (Object)throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testINFO_info_MsgObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 8", (Object)null);

		verifyStatic();
		Log.i(createTag(0), "Message 8");
	}

	@Test
	public void testINFO_info_Msg2ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 9 {}", "arg1", throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testINFO_info_Msg2ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 10 {}", "arg1", null);

		verifyStatic();
		Log.i(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testINFO_info_Msg3ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testINFO_info_Msg3ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.i(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testINFO_info_Marker_Msg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 13");

		verifyStatic();
		Log.i(createTag(0), "Message 13");
	}

	@Test
	public void testINFO_info_Marker_MsgArg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 14 {}", "arg");

		verifyStatic();
		Log.i(createTag(0), "Message 14 arg");
	}

	@Test
	public void testINFO_info_Marker_Msg2Args() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.i(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testINFO_info_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.i(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testINFO_info_Marker_MsgExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 17", throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testINFO_info_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 18", (Throwable)null);

		verifyStatic();
		Log.i(createTag(0), "Message 18");
	}

	@Test
	public void testINFO_info_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 19", (Object)throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testINFO_info_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 20", (Object)null);

		verifyStatic();
		Log.i(createTag(0), "Message 20");
	}

	@Test
	public void testINFO_info_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testINFO_info_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 22 {}", "arg1", null);

		verifyStatic();
		Log.i(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testINFO_info_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.i(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testINFO_info_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.i(createTag(0), "Message 24 arg1 arg2");
	}

	/* Info Disabled */

	@Test
	public void testWARN_infoEnabled() {
		mockLogLevel(LogLevel.WARN);
		Assert.assertFalse(new LogAdapter("N/A", mockConfig()).isInfoEnabled());
	}

	@Test
	public void testWARN_info_Msg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 1");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 1");
	}

	@Test
	public void testWARN_info_MsgArg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 2 {}", "arg");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 2 arg");
	}

	@Test
	public void testWARN_info_Msg2Args() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testWARN_info_MsgManyArgs() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testWARN_info_MsgExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 5", throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testWARN_info_MsgNullExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 6", (Throwable)null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 6");
	}

	@Test
	public void testWARN_info_MsgObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 7", (Object)throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testWARN_info_MsgObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 8", (Object)null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 8");
	}

	@Test
	public void testWARN_info_Msg2ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 9 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testWARN_info_Msg2ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 10 {}", "arg1", null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testWARN_info_Msg3ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testWARN_info_Msg3ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testWARN_info_Marker_Msg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 13");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 13");
	}

	@Test
	public void testWARN_info_Marker_MsgArg() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 14 {}", "arg");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 14 arg");
	}

	@Test
	public void testWARN_info_Marker_Msg2Args() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testWARN_info_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testWARN_info_Marker_MsgExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 17", throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testWARN_info_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 18", (Throwable)null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 18");
	}

	@Test
	public void testWARN_info_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 19", (Object)throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testWARN_info_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 20", (Object)null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 20");
	}

	@Test
	public void testWARN_info_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testWARN_info_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 22 {}", "arg1", null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testWARN_info_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testWARN_info_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.WARN);
		new LogAdapter("N/A", mockConfig()).info(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.i(createTag(0), "Message 24 arg1 arg2");
	}

	/* Debug Enabled */

	@Test
	public void testDEBUG_warnEnabled() {
		mockLogLevel(LogLevel.DEBUG);
		Assert.assertTrue(new LogAdapter("N/A", mockConfig()).isDebugEnabled());
	}

	@Test
	public void testDEBUG_debug_Msg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 1");

		verifyStatic();
		Log.d(createTag(0), "Message 1");
	}

	@Test
	public void testDEBUG_debug_MsgArg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 2 {}", "arg");

		verifyStatic();
		Log.d(createTag(0), "Message 2 arg");
	}

	@Test
	public void testDEBUG_debug_Msg2Args() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 3 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.d(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testDEBUG_debug_MsgManyArgs() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.d(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testDEBUG_debug_MsgExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 5", throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testDEBUG_debug_MsgNullExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 6", (Throwable)null);

		verifyStatic();
		Log.d(createTag(0), "Message 6");
	}

	@Test
	public void testDEBUG_debug_MsgObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 7", (Object)throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testDEBUG_debug_MsgObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 8", (Object)null);

		verifyStatic();
		Log.d(createTag(0), "Message 8");
	}

	@Test
	public void testDEBUG_debug_Msg2ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 9 {}", "arg1", throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testDEBUG_debug_Msg2ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 10 {}", "arg1", null);

		verifyStatic();
		Log.d(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testDEBUG_debug_Msg3ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testDEBUG_debug_Msg3ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.d(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 13");

		verifyStatic();
		Log.d(createTag(0), "Message 13");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgArg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 14 {}", "arg");

		verifyStatic();
		Log.d(createTag(0), "Message 14 arg");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg2Args() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.d(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.d(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 17", throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 18", (Throwable)null);

		verifyStatic();
		Log.d(createTag(0), "Message 18");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 19", (Object)throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 20", (Object)null);

		verifyStatic();
		Log.d(createTag(0), "Message 20");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 22 {}", "arg1", null);

		verifyStatic();
		Log.d(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.d(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.d(createTag(0), "Message 24 arg1 arg2");
	}

	/* Debug Disabled */

	@Test
	public void testINFO_debugEnabled() {
		mockLogLevel(LogLevel.INFO);
		Assert.assertFalse(new LogAdapter("N/A", mockConfig()).isDebugEnabled());
	}

	@Test
	public void testINFO_debug_Msg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 1");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 1");
	}

	@Test
	public void testINFO_debug_MsgArg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 2 {}", "arg");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 2 arg");
	}

	@Test
	public void testINFO_debug_Msg2Args() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testINFO_debug_MsgManyArgs() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testINFO_debug_MsgExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 5", throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testINFO_debug_MsgNullExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 6", (Throwable)null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 6");
	}

	@Test
	public void testINFO_debug_MsgObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 7", (Object)throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testINFO_debug_MsgObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 8", (Object)null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 8");
	}

	@Test
	public void testINFO_debug_Msg2ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 9 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testINFO_debug_Msg2ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 10 {}", "arg1", null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testINFO_debug_Msg3ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testINFO_debug_Msg3ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testINFO_debug_Marker_Msg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 13");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 13");
	}

	@Test
	public void testINFO_debug_Marker_MsgArg() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 14 {}", "arg");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 14 arg");
	}

	@Test
	public void testINFO_debug_Marker_Msg2Args() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testINFO_debug_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testINFO_debug_Marker_MsgExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 17", throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testINFO_debug_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 18", (Throwable)null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 18");
	}

	@Test
	public void testINFO_debug_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 19", (Object)throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testINFO_debug_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 20", (Object)null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 20");
	}

	@Test
	public void testINFO_debug_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testINFO_debug_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 22 {}", "arg1", null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testINFO_debug_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testINFO_debug_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.INFO);
		new LogAdapter("N/A", mockConfig()).debug(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.d(createTag(0), "Message 24 arg1 arg2");
	}

	/* Trace Enabled */

	@Test
	public void testTRACE_warnEnabled() {
		mockLogLevel(LogLevel.VERBOSE);
		Assert.assertTrue(new LogAdapter("N/A", mockConfig()).isTraceEnabled());
	}

	@Test
	public void testTRACE_trace_Msg() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 1");

		verifyStatic();
		Log.v(createTag(0), "Message 1");
	}

	@Test
	public void testTRACE_trace_MsgArg() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 2 {}", "arg");

		verifyStatic();
		Log.v(createTag(0), "Message 2 arg");
	}

	@Test
	public void testTRACE_trace_Msg2Args() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 3 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.v(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testTRACE_trace_MsgManyArgs() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.v(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testTRACE_trace_MsgExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 5", throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testTRACE_trace_MsgNullExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 6", (Throwable)null);

		verifyStatic();
		Log.v(createTag(0), "Message 6");
	}

	@Test
	public void testTRACE_trace_MsgObjExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 7", (Object)throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testTRACE_trace_MsgObjNull() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 8", (Object)null);

		verifyStatic();
		Log.v(createTag(0), "Message 8");
	}

	@Test
	public void testTRACE_trace_Msg2ObjExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 9 {}", "arg1", throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testTRACE_trace_Msg2ObjNull() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 10 {}", "arg1", null);

		verifyStatic();
		Log.v(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testTRACE_trace_Msg3ObjExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testTRACE_trace_Msg3ObjNull() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.v(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testTRACE_trace_Marker_Msg() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 13");

		verifyStatic();
		Log.v(createTag(0), "Message 13");
	}

	@Test
	public void testTRACE_trace_Marker_MsgArg() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 14 {}", "arg");

		verifyStatic();
		Log.v(createTag(0), "Message 14 arg");
	}

	@Test
	public void testTRACE_trace_Marker_Msg2Args() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic();
		Log.v(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testTRACE_trace_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic();
		Log.v(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testTRACE_trace_Marker_MsgExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 17", throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 18", (Throwable)null);

		verifyStatic();
		Log.v(createTag(0), "Message 18");
	}

	@Test
	public void testTRACE_trace_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 19", (Object)throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 20", (Object)null);

		verifyStatic();
		Log.v(createTag(0), "Message 20");
	}

	@Test
	public void testTRACE_trace_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 22 {}", "arg1", null);

		verifyStatic();
		Log.v(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testTRACE_trace_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic();
		Log.v(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic();
		Log.v(createTag(0), "Message 24 arg1 arg2");
	}

	/* Trace Disabled */

	@Test
	public void testDEBUG_traceEnabled() {
		mockLogLevel(LogLevel.DEBUG);
		Assert.assertFalse(new LogAdapter("N/A", mockConfig()).isTraceEnabled());
	}

	@Test
	public void testDEBUG_trace_Msg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 1");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 1");
	}

	@Test
	public void testDEBUG_trace_MsgArg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 2 {}", "arg");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 2 arg");
	}

	@Test
	public void testDEBUG_trace_Msg2Args() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 3 arg1 arg2");
	}

	@Test
	public void testDEBUG_trace_MsgManyArgs() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testDEBUG_trace_MsgExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 5", throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 5", throwable);
	}

	@Test
	public void testDEBUG_trace_MsgNullExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 6", (Throwable)null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 6");
	}

	@Test
	public void testDEBUG_trace_MsgObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 7", (Object)throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 7", throwable);
	}

	@Test
	public void testDEBUG_trace_MsgObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 8", (Object)null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 8");
	}

	@Test
	public void testDEBUG_trace_Msg2ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 9 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 9 arg1", throwable);
	}

	@Test
	public void testDEBUG_trace_Msg2ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 10 {}", "arg1", null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 10 arg1");
	}

	@Test
	public void testDEBUG_trace_Msg3ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testDEBUG_trace_Msg3ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 12 arg1 arg2");
	}

	@Test
	public void testDEBUG_trace_Marker_Msg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 13");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 13");
	}

	@Test
	public void testDEBUG_trace_Marker_MsgArg() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 14 {}", "arg");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 14 arg");
	}

	@Test
	public void testDEBUG_trace_Marker_Msg2Args() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 15 arg1 arg2");
	}

	@Test
	public void testDEBUG_trace_Marker_MsgManyArgs() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testDEBUG_trace_Marker_MsgExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 17", throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 17", throwable);
	}

	@Test
	public void testDEBUG_trace_Marker_MsgNullExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 18", (Throwable)null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 18");
	}

	@Test
	public void testDEBUG_trace_Marker_MsgObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 19", (Object)throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 19", throwable);
	}

	@Test
	public void testDEBUG_trace_Marker_MsgObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 20", (Object)null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 20");
	}

	@Test
	public void testDEBUG_trace_Marker_Msg2ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 21 arg1", throwable);
	}

	@Test
	public void testDEBUG_trace_Marker_Msg2ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 22 {}", "arg1", null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 22 arg1");
	}

	@Test
	public void testDEBUG_trace_Marker_Msg3ObjExc() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testDEBUG_trace_Marker_Msg3ObjNull() {
		mockLogLevel(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfig()).trace(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(times(0));
		Log.v(createTag(0), "Message 24 arg1 arg2");
	}
}
