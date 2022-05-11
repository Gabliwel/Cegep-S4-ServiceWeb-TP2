package ca.csfoy.servicesweb.camarchedoc.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.csfoy.servicesweb.camarchedoc.domain.badge.Badge;

public interface BadgeDao extends JpaRepository<Badge, String> {

}
