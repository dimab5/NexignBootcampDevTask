package com.hrs.repositories.traffics;

import com.hrs.models.traffics.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, String> {
    Traffic findTrafficByClientId(String clientId);
}
