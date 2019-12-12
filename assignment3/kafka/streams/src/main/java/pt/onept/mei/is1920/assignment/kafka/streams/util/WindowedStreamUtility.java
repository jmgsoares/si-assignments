package pt.onept.mei.is1920.assignment.kafka.streams.util;

import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public final class WindowedStreamUtility {

	private final static Logger logger = LoggerFactory.getLogger(WindowedStreamUtility.class);

	public static final Duration WINDOW_SIZE_DURATION = Duration.ofSeconds(30);
	public static final Duration WINDOW_ADVANCE_DURATION = Duration.ofSeconds(29);
	public static final Duration STREAM_JOINING_WINDOW = Duration.ofSeconds(1);
	public static final TimeWindows TIME_WINDOWS = TimeWindows.of(WINDOW_SIZE_DURATION).advanceBy(WINDOW_ADVANCE_DURATION);

	private WindowedStreamUtility() { }

	public static boolean KeepWindow (Windowed<Long> window) {
		long now = System.currentTimeMillis();

		return window.window().end() > now &&
				window.window().end() < now + WINDOW_ADVANCE_DURATION.toMillis();

	}

}
