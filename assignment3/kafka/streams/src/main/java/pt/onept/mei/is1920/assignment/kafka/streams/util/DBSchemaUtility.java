package pt.onept.mei.is1920.assignment.kafka.streams.util;

public final class DBSchemaUtility {

	public static final String RESULT_SCHEMA_PREFIX = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32" +
			"\",\"optional\":false,\"field\":\"id\"},{\"type\":\"float\",\"optional\":false,\"field\":\"value\"}]," +
			"\"optional\":false},\"payload\":{\"id\":";
	public static final String RESULT_SCHEMA_SEP = ",\"value\":";
	public static final String RESULT_SCHEMA_SUFFIX = "}}";

	public static final String RESULT_SCHEMA_PREFIX_MPI = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"" +
			"int32\",\"optional\":false,\"field\":\"id\"},{\"type\":\"int32\",\"optional\":false,\"field\":\"value\"}" +
			",{\"type\":\"float\",\"optional\":false,\"field\":\"value2\"}],\"optional\":false},\"payload\":{\"id\":";
	public static final String RESULT_SCHEMA_SEP1_MPI = ",\"value\":";
	public static final String RESULT_SCHEMA_SEP2_MPI = ",\"value2\":";
	public static final String RESULT_SCHEMA_SUFFIX_MPI = "}}";

	private DBSchemaUtility() { }


	public static String WrapKVSchema(Long k, String v) {
		return RESULT_SCHEMA_PREFIX + k.toString() + RESULT_SCHEMA_SEP + v + RESULT_SCHEMA_SUFFIX;
	}

	public static String WrapKVSchema2V(Long k, Long v1, Float v2) {
		return RESULT_SCHEMA_PREFIX_MPI + k.toString() +
				RESULT_SCHEMA_SEP1_MPI + v1.toString() +
				RESULT_SCHEMA_SEP2_MPI + v2.toString() +
				RESULT_SCHEMA_SUFFIX_MPI;
	}
}
