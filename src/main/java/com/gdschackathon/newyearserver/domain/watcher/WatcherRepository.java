package com.gdschackathon.newyearserver.domain.watcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatcherRepository extends JpaRepository<Watcher,Long> {
}
