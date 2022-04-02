package ca.csfoy.servicesweb.camarchedoc.domain.user;

public interface UserRepository {
    
    void create(User user);

    void save(User user);

    User get(String id);
}
