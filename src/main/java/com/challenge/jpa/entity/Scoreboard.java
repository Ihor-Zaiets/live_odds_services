package com.challenge.jpa.entity;

import com.challenge.exception.ExceptionMessage;
import com.challenge.exception.ValidateException;
import com.challenge.jpa.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scoreboard {

    private List<Match> scoreboardMatchList = new ArrayList<>();

    public Match startMatch(Country homeTeam, Country awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        scoreboardMatchList.add(match);
        return match;
    }

    public List<Match> getScoreboardMatchList() {
        return scoreboardMatchList.stream()
                .sorted(Comparator.comparing(Match::getScore, Comparator.comparingInt((score) -> score.getAwayTeamScore() + score.getHomeTeamScore())).reversed()
                        .thenComparing(Match::getStartDate))
                .toList();
    }

    public Match updateScore(Match match, Score score) {
        if (!scoreboardMatchList.contains(match)) {
            throw new ValidateException(ExceptionMessage.SCORE_UPDATE_ERROR_NO_SUCH_MATCH);
        }
        match.setScore(score);
        return match;
    }

    public Boolean finishMatch(Match match) {
        return scoreboardMatchList.remove(match);
    }
}
