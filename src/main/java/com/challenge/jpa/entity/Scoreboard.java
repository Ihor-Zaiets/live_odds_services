package com.challenge.jpa.entity;

import com.challenge.jpa.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scoreboard {

    private List<Match> scoreboard = new ArrayList<>();

    public Match startMatch(Country homeTeam, Country awayTeam) {
        return null;
    }

    public Match updateScore(Match match, Score score) {
        return null;
    }

    public Boolean finishMatch(Match match) {
        return null;
    }
}
