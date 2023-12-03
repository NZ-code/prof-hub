package pl.zenev.profhub.authorization.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import pl.zenev.profhub.authorization.exception.NoPrincipalException;
import pl.zenev.profhub.authorization.exception.NoRolesException;
import pl.zenev.profhub.authorization.interceptor.binding.AllowAdminOrCharacterOwner;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.security.UserRoles;
import pl.zenev.profhub.services.LectureService;


import java.util.Optional;
import java.util.UUID;

/**
 * Binding for interceptor allowing only admin or user owning the character.
 */
@Interceptor
@AllowAdminOrCharacterOwner
@Priority(10)
public class AllowAdminOrCharacterOwnerInterceptor {

    /**
     * Security context.
     */
    private final SecurityContext securityContext;

    /**
     * Character service.
     */
    private final LectureService lectureService;

    /**
     * @param securityContext  security context
     * @param lectureService character service
     */
    @Inject
    public AllowAdminOrCharacterOwnerInterceptor(SecurityContext securityContext, LectureService lectureService) {
        this.securityContext = securityContext;
        this.lectureService = lectureService;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NoPrincipalException();
        }
        if (authorized(context)) {
            return context.proceed();
        }
        throw new NoRolesException();
    }

    /**
     * @param context invocation context
     * @return true if caller principal is authorized to edit character represented by first method parameter
     */
    private boolean authorized(InvocationContext context) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return true;
        } else if (securityContext.isCallerInRole(UserRoles.USER)) {
            Object provided = context.getParameters()[0];//Simple assumption that first parameter will be character or uuid
            Optional<Lecture> lecture;
            if (provided instanceof Lecture) {
                lecture = lectureService.getById(((Lecture) provided).getUuid());
            } else if (provided instanceof UUID) {
                lecture = lectureService.getById((UUID) provided);
            } else {
                throw new IllegalStateException("No character of UUID as first method parameter.");
            }

            return lecture.isPresent()
                    && lecture.get().getProfessor().getLogin().equals(securityContext.getCallerPrincipal().getName());
        }
        return false;
    }

}
