//Based on https://stackoverflow.com/questions/6514876/most-efficient-conversion-of-resultset-to-json
package pt.onept.mei.is1920.assignment.kafka.backend.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class ResultSetToJsonMapper {

	private static Logger logger = LoggerFactory.getLogger(ResultSetToJsonMapper.class);

	private ResultSetToJsonMapper() { }

	public static JsonArray MapResultSet (ResultSet rs) throws SQLException	{

		JsonArray jArray = new JsonArray();
		JsonObject jsonObject;
		ResultSetMetaData rsMd = rs.getMetaData();
		int columnCount = rsMd.getColumnCount();
		while (rs.next()){
			jsonObject = new JsonObject();
			for (int index = 1; index <= columnCount; index++)
			{
				String column = rsMd.getColumnName(index);
				Object value = rs.getObject(column);
				if (value == null) {
					jsonObject.addProperty(column, "");
				}
				else if (value instanceof Integer) {
					jsonObject.addProperty(column, (Integer) value);
				}
				else if (value instanceof String) {
					jsonObject.addProperty(column, (String) value);
				}
				else if (value instanceof Boolean) {
					jsonObject.addProperty(column, (Boolean) value);
				}
				else if (value instanceof Date) {
					jsonObject.addProperty(column, ((Date) value).getTime());
				}
				else if (value instanceof Long) {
					jsonObject.addProperty(column, (Long) value);
				}
				else if (value instanceof Double) {
					jsonObject.addProperty(column, (Double) value);
				}
				else if (value instanceof Float) {
					jsonObject.addProperty(column, (Float) value);
				}
				else if (value instanceof BigDecimal) {
					jsonObject.addProperty(column, (BigDecimal) value);
				}
				else if (value instanceof Byte) {
					jsonObject.addProperty(column, (Byte) value);
				}
				else {
					Exception e = new IllegalArgumentException("Unmappable object type: " + value.getClass());
					logger.error(e.getMessage(), e);
				}
			}
			jArray.add(jsonObject);
		}
		return jArray;
	}
}