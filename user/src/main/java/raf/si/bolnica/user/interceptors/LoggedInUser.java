package raf.si.bolnica.user.interceptors;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Set;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoggedInUser {

    private String username;

    private Set<String> roles;

    private Long lbz;

    public LoggedInUser() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLBZ() {
        return lbz;
    }

    public void setLBZ(Long lbz) {
        this.lbz = lbz;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
