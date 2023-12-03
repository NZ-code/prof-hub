package pl.zenev.profhub.authorization.interceptor.binding;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

/**
 * Binding for interceptor allowing only admin or user owning the character.
 */
@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AllowAdminOrCharacterOwner {
}
