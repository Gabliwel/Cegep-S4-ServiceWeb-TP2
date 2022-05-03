package ca.csfoy.servicesweb.camarchedoc.domain.user;

public interface UserRepository {
    
    void create(User user);

    void save(String id, User user);

    User get(String id);
    
    User getByEmail(String email);
}
