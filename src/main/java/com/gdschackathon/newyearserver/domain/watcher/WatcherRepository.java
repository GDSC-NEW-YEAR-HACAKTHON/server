package com.gdschackathon.newyearserver.domain.watcher;

import com.gdschackathon.newyearserver.domain.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatcherRepository extends JpaRepository<Watcher,Long> {

    List<Watcher> findAllByChallenge(Challenge challenge);

    void deleteAllByChallenge(Challenge challenge);
}
