package matmic.librarymaneger.model.rolemodel;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class Authority implements GrantedAuthority, Serializable{
    public static final long serialVersionUID = 21343L;

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
