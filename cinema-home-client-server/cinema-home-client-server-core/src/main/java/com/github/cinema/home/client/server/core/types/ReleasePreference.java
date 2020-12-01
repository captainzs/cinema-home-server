package com.github.cinema.home.client.server.core.types;

import com.github.cinema.home.client.server.common.types.tech.ResolutionDefinition;

public class ReleasePreference {
    private final boolean hunDub;
    private final ResolutionDefinition resolutionDefinition;

    public static ReleasePreference hungarian(ResolutionDefinition resolutionDefinition) {
        return new ReleasePreference(true, resolutionDefinition);
    }

    public static ReleasePreference original(ResolutionDefinition resolutionDefinition) {
        return new ReleasePreference(false, resolutionDefinition);
    }

    private ReleasePreference(boolean hunDub, ResolutionDefinition resolutionDefinition) {
        this.hunDub = hunDub;
        this.resolutionDefinition = resolutionDefinition;
    }

    public boolean isHunDub() {
        return this.hunDub;
    }

    public ResolutionDefinition getResolutionDefinition() {
        return this.resolutionDefinition;
    }
}
