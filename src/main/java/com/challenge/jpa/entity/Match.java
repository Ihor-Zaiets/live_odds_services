package com.challenge.jpa.entity;

import com.challenge.jpa.enums.Country;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Match {

    @NonNull
    private Country homeTeam;
    @NonNull
    private Country awayTeam;
    private Score score = new Score();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match match)) return false;

        if (getHomeTeam() != match.getHomeTeam()) return false;
        return getAwayTeam() == match.getAwayTeam();
    }
}
