package org.sz.core.annotion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDescription {
	public abstract boolean pk();

	public abstract String columnName();

	public abstract boolean canUpd();
}
