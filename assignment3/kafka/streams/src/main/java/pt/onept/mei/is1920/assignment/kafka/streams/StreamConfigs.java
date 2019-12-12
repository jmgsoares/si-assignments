package pt.onept.mei.is1920.assignment.kafka.streams;

import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class StreamConfigs {
	private StreamConfigs() { }


	public static final Duration WINDOW_SIZE_DURATION = Duration.ofSeconds(30);
	public static final Duration WINDOW_ADVANCE_DURATION = Duration.ofSeconds(30);

	public static final String RESULT_SCHEMA_PREFIX = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":false,\"field\":\"value\"}],\"optional\":false},\"payload\":{\"id\":\"";
	public static final String RESULT_SCHEMA_SEP = "\",\"value\":\"";
	public static final String RESULT_SCHEMA_SUFFIX = "\"}}";

	public static boolean KeepWindow (Windowed<Long> window) {
		long now = System.currentTimeMillis();

		return window.window().end() > now &&
				window.window().end() < now + WINDOW_ADVANCE_DURATION.toMillis();
	}

	public static String WrapKVSchema(Long k, String v) {
		return StreamConfigs.RESULT_SCHEMA_PREFIX + k.toString() + RESULT_SCHEMA_SEP + v + RESULT_SCHEMA_SUFFIX;
	}
}
