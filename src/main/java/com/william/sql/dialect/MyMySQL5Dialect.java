package com.william.sql.dialect;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;


@SuppressWarnings("serial")
public class MyMySQL5Dialect extends MySQL5Dialect {

	public MyMySQL5Dialect() {
		super();
		this.registerFunction("GetDistance".toLowerCase(),
				new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "GetDistance(?1,?2,?3,?4)"));
	}

}
