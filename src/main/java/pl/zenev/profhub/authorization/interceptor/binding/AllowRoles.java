package pl.zenev.profhub.authorization.interceptor.binding;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

/**
 * Allows any of specified roles.
 */
@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AllowRoles {

    /**
     * @return array of security roles
     */
    @Nonbinding//Required so interceptor does not need to define exact attribute values.
    String[] value() default {};

}
