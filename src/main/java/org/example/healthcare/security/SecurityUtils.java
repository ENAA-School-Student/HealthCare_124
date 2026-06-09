package org.example.healthcare.security;

import org.example.healthcare.enums.UserRoles;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static User getCurrentUser(UserRedpository userRedpository) {
        return userRedpository.findByEmail(getCurrentEmail())
                .orElseThrow(() -> new AccessDeniedException("Utilisateur introuvable"));
    }

    public static boolean isAdmin(User user) {
        return user.getRole() == UserRoles.ADMIN;
    }

    public static void requireOwnerOrAdmin(Long resourceOwnerId, User currentUser) {
        if (!isAdmin(currentUser) && !resourceOwnerId.equals(currentUser.getId())) {
            throw new AccessDeniedException("Accès refusé");
        }
    }
}
