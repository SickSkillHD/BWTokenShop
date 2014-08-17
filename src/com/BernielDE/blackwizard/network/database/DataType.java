package com.BernielDE.blackwizard.network.database;

import java.util.HashMap;
import java.util.Map;

public enum DataType {
	
	TEXT(1), CHAR(2), INT(3), DECIMAL(4), FLOAT(5), DOUBLE(6), BOOLEAN(7), DATE(8), TIME(9);

	private static final Map<Integer, DataType> valueList;
	private final int value;

	static {
		valueList = new HashMap<Integer, DataType>();
		for (DataType result : values()) {
			valueList.put(Integer.valueOf(result.value), result);
		}
	}

	private DataType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static DataType getResult(int value) {
		return (DataType) valueList.get(Integer.valueOf(value));
	}
}
