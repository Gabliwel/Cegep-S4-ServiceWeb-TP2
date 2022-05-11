package ca.csfoy.servicesweb.camarchedoc.domain.badge;

import org.springframework.stereotype.Service;

import ca.csfoy.servicesweb.camarchedoc.domain.user.User;
import ca.csfoy.servicesweb.camarchedoc.domain.user.UserRepository;

@Service
public class BadgeServiceImpl implements BadgeService {
    
    private final BadgeRepository badgeRepo;
    private final UserRepository userRepo;
    
    public BadgeServiceImpl(BadgeRepository badgeRepo, UserRepository userRepo) {
        this.badgeRepo = badgeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void giveBadge(String id, String userId) {
        Badge badge = badgeRepo.getById(id);
        User user = userRepo.get(userId);
    }

}
