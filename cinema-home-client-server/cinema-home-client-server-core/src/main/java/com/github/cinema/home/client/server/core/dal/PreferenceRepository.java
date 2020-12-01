package com.github.cinema.home.client.server.core.dal;

import com.github.cinema.home.client.server.common.types.tech.ResolutionDefinition;
import com.github.cinema.home.client.server.core.types.ReleasePreference;
import org.springframework.stereotype.Service;

@Service
public class PreferenceRepository {
    public ReleasePreference findPreference() {
        return ReleasePreference.hungarian(ResolutionDefinition.HD);
    }
}
