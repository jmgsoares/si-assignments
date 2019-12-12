package pt.onept.mei.is1920.assignment.kafka.streams.util;

public final class DBSchemaUtility {

	public static final String RESULT_SCHEMA_PREFIX = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"string\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":false,\"field\":\"value\"}],\"optional\":false},\"payload\":{\"id\":\"";
	public static final String RESULT_SCHEMA_SEP = "\",\"value\":\"";
	public static final String RESULT_SCHEMA_SUFFIX = "\"}}";

	private DBSchemaUtility() { }


	public static String WrapKVSchema(Long k, String v) {
		return RESULT_SCHEMA_PREFIX + k.toString() + RESULT_SCHEMA_SEP + v + RESULT_SCHEMA_SUFFIX;
	}
}
