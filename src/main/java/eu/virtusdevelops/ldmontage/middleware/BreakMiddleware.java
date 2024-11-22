package eu.virtusdevelops.ldmontage.middleware;

import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.domain.work.Break;
import eu.virtusdevelops.ldmontage.repositories.BreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BreakMiddleware {
    private final BreakRepository breakRepository;

    public boolean canDelete(Long breakId) {
        return hasPermission(breakId, "BREAK_DELETE");
    }

    public boolean canUpdate(Long breakId) {
        return hasPermission(breakId, "BREAK_UPDATE");
    }

    private boolean hasPermission(Long breakId, String requiredAuthority) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return breakRepository.findById(breakId)
                .map(breakObj -> isOwner(breakObj, user) || hasAuthority(user, requiredAuthority))
                .orElse(true); // Allow deletion if the break object is not found
    }

    private boolean isOwner(Break breakObj, User user) {
        return breakObj.getWorkTime().getUser().getId().equals(user.getId());
    }

    private boolean hasAuthority(User user, String authority) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority::equals);
    }

}
