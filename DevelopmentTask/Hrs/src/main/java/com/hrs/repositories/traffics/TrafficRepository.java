package com.hrs.repositories.traffics;

import com.hrs.models.traffics.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing traffic information.
 */
@Repository
public interface TrafficRepository extends JpaRepository<Traffic, String> {
    /**
     * Retrieves traffic data by client ID.
     * @param clientId The ID of the client to retrieve traffic data for
     * @return The Traffic object associated with the specified client ID
     */
    Traffic findTrafficByClientId(String clientId);
}
