package com.company.javac;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Documented
@Target(ElementType.TYPE)
public @interface Final {

    String implementors();
}
