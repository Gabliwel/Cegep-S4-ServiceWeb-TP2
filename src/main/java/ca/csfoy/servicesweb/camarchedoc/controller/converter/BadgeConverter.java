package ca.csfoy.servicesweb.camarchedoc.controller.converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDto;
import ca.csfoy.servicesweb.camarchedoc.api.badge.BadgeDtoWithoutDesc;
import ca.csfoy.servicesweb.camarchedoc.api.badge.SmallBadgeDto;
import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;

@Component
public class BadgeConverter {
    
    public Set<BadgeDto> convertBadgeDtoSetFrom(Set<Badge> badges) {
        if (badges == null || badges.isEmpty()) {
            return null;
        }
        return badges.stream().map(this::badgeToFullDto).collect(Collectors.toSet());
    }
    
    public BadgeDto badgeToFullDto(Badge badge) {
        return new BadgeDto(
                badge.getId(),
                badge.getName(),
                badge.getDescription(),
                badge.getIcone(),
                badge.getCategory());
    }
    
    public BadgeDtoWithoutDesc badgeToDto(Badge badge) {
        return new BadgeDtoWithoutDesc(
                badge.getId(),
                badge.getName(),
                badge.getIcone(),
                badge.getCategory());
    }
    
    public SmallBadgeDto badgeToShortDto(Badge badge) {
        return new SmallBadgeDto(
                badge.getId(),
                badge.getName(),
                badge.getCategory());
    }
    
    public List<SmallBadgeDto> badgeListToDtoList(List<Badge> trails) {
        return trails.stream().map(this::badgeToShortDto).collect(Collectors.toList());
    }

}
