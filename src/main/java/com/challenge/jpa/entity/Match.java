package com.challenge.jpa.entity;

import com.challenge.exception.ExceptionMessage;
import com.challenge.jpa.enums.Country;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Match {

    @NonNull
    private Country homeTeam;
    @NonNull
    private Country awayTeam;
    private Score score = new Score();
    private LocalDateTime startDate = LocalDateTime.now();

    public Match(@NonNull Country homeTeam, @NonNull Country awayTeam) {
        if (homeTeam == awayTeam)
            throw new IllegalArgumentException(ExceptionMessage.MATCH_COUNTRIES_CANNOT_BE_THE_SAME);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void setHomeTeam(Country homeTeam) {
        if (homeTeam == getAwayTeam())
            throw new IllegalArgumentException(ExceptionMessage.MATCH_COUNTRIES_CANNOT_BE_THE_SAME);
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Country awayTeam) {
        if (getHomeTeam() == awayTeam)
            throw new IllegalArgumentException(ExceptionMessage.MATCH_COUNTRIES_CANNOT_BE_THE_SAME);
        this.awayTeam = awayTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match match)) return false;

        if (getHomeTeam() != match.getHomeTeam()) return false;
        return getAwayTeam() == match.getAwayTeam();
    }
}
