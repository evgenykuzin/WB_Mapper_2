package ru.jekajops.wbtablemapper.util.annotations;

import ru.jekajops.wbtablemapper.models.WB;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
    Class<?> from() default WB.class;
    String value() default "";
}
