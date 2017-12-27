package com.fatin.util;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.TimestampType;

public class SqlServerDialectOverrider extends SQLServerDialect {
	public SqlServerDialectOverrider() {
		super();
		registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
		registerHibernateType(Types.LONGVARCHAR, StandardBasicTypes.TEXT.getName());
		registerFunction("conv", new VarArgsSQLFunction(TimestampType.INSTANCE, "convert(varchar(10),", ",", ",102)"));
	}
}
