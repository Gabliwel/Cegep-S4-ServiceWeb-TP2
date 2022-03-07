package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.csfoy.servicesweb.camarchedoc.domain.SearchTrailCriteria;
import ca.csfoy.servicesweb.camarchedoc.domain.Trail;

public class TrailDaoMemory implements TrailDao {

    private Map<String, Trail> trails;

    public TrailDaoMemory() {
        this.trails = new HashMap<>();
    }

    @Override
    public boolean doesExist(String id) {
        return trails.containsKey(id);
    }

    @Override
    public boolean doesExist(String name, String city) {
        return trails.values().stream()
                .anyMatch(trail -> trail.getName().equalsIgnoreCase(name) && trail.getCity().equalsIgnoreCase(city));
    }

    @Override
    public Trail selectById(String id) {
        return trails.get(id);
    }

    @Override
    public List<Trail> selectAll() {
        return trails.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Trail> search(SearchTrailCriteria criteria) {
        return trails.values().stream()
                .filter(trail -> trail.getCity().equals(criteria.getCity()) || criteria.shouldIncludeCity())
                .filter(trail -> trail.getDifficulty().equals(criteria.getDifficulty()) || criteria.shouldIncludeDifficulty())
                .collect(Collectors.toList());
    }

    @Override
    public Trail insert(Trail trail) {
        trails.put(trail.getId(), trail);
        return trail;
    }

    @Override
    public void update(String id, Trail trail) {
        trails.put(trail.getId(), trail);
    }

    @Override
    public void delete(String id) {
        trails.remove(id);
    }
}
