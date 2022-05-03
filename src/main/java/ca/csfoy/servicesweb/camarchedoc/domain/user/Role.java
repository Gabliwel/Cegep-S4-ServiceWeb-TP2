package ca.csfoy.servicesweb.camarchedoc.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    public String id;
    @Column
    public String roleName;
    
    public Role() {
        
    }
    
    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
    
}
