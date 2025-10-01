package app.security;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password);
    User addUserRole(String username, String role);
    User createUser(String username, String password);
    Role createRole(String role);
}
