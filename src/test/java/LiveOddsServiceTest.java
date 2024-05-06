import com.challenge.exception.ExceptionMessage;
import com.challenge.exception.ValidateException;
import com.challenge.jpa.entity.Match;
import com.challenge.jpa.entity.Score;
import com.challenge.jpa.entity.Scoreboard;
import com.challenge.jpa.enums.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LiveOddsServiceTest {

    private static final Integer DEFAULT_VALUE = 0;
    @Test
    public void shouldCreateMatchWithDefaultValues() {
        Match match = new Match(Country.ARGENTINA, Country.BRAZIL);
        assertEquals(DEFAULT_VALUE, match.getScore().getHomeTeamScore());
        assertEquals(DEFAULT_VALUE, match.getScore().getAwayTeamScore());
    }

    @Test
    public void shouldThrowIfMatchHasSameCountries() {
        Match match = new Match(Country.ARGENTINA, Country.AUSTRALIA);
        assertThrows(IllegalArgumentException.class, () -> new Match(Country.ARGENTINA, Country.ARGENTINA), ExceptionMessage.MATCH_COUNTRIES_CANNOT_BE_THE_SAME);
        assertThrows(IllegalArgumentException.class, () -> {
            match.setAwayTeam(match.getHomeTeam());
            match.setHomeTeam(match.getAwayTeam());
        }, ExceptionMessage.MATCH_COUNTRIES_CANNOT_BE_THE_SAME);
    }

    @Test
    public void shouldThrowIfScoreIsLessThenZero() {
        assertThrows(IllegalArgumentException.class, () -> new Score(-1, 0), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
        assertThrows(IllegalArgumentException.class, () -> new Score(0, -1), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
        assertThrows(IllegalArgumentException.class, () -> new Score(-1, -1), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
    }

    @Test
    public void scoreboardShouldCreateMatchWithDefaultValues() {
        Scoreboard scoreboard = new Scoreboard();
        Match match = scoreboard.startMatch(Country.ARGENTINA, Country.AUSTRALIA);
        assertEquals(DEFAULT_VALUE, match.getScore().getHomeTeamScore());
        assertEquals(DEFAULT_VALUE, match.getScore().getAwayTeamScore());
    }

    @Test
    public void shouldAddMatchToScoreboard() {
        int numberOfCreatedMatches = 1;
        Scoreboard scoreboard = new Scoreboard();
        List<Match> scoreboardBefore = scoreboard.getScoreboard();
        Match match = scoreboard.startMatch(Country.ARGENTINA, Country.BRAZIL);
        List<Match> scoreboardAfter = scoreboard.getScoreboard();
        assertTrue(scoreboardAfter.contains(match));
        assertEquals(scoreboardBefore.size() + numberOfCreatedMatches, scoreboardAfter.size());
    }

    @Test
    public void shouldDeleteMatchFromScoreboard() {
        int numberOfDeletedMatches = 1;
        Scoreboard scoreboard = new Scoreboard();
        Match match = scoreboard.startMatch(Country.ARGENTINA, Country.BRAZIL);
        List<Match> scoreboardBefore = scoreboard.getScoreboard();
        assertTrue(scoreboard.finishMatch(match));
        List<Match> scoreboardAfter = scoreboard.getScoreboard();

        assertTrue(scoreboardBefore.contains(match));
        assertFalse(scoreboardAfter.contains(match));
        assertEquals(scoreboardBefore.size() - numberOfDeletedMatches, scoreboardAfter.size());
    }

    @Test
    public void shouldReturnFalseIfNoMatchWasFinished() {
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(Country.ARGENTINA, Country.BRAZIL);
        List<Match> scoreboardBefore = scoreboard.getScoreboard();
        assertFalse(scoreboard.finishMatch(match));
        List<Match> scoreboardAfter = scoreboard.getScoreboard();

        assertEquals(scoreboardBefore.size(), scoreboardAfter.size());
    }

    @Test
    public void scoreBoardShouldUpdateAbsoluteScore() {
        Scoreboard scoreboard = new Scoreboard();
        Match match = scoreboard.startMatch(Country.ARGENTINA, Country.AUSTRALIA);
        Score score = new Score(1, 2);
        Score newScore = scoreboard.updateScore(match, score).getScore();
        assertEquals(score.getHomeTeamScore(), newScore.getHomeTeamScore());
        assertEquals(score.getAwayTeamScore(), newScore.getAwayTeamScore());
    }

    @Test
    public void shouldThrowIfThereNoSuchMatchDuringScoreUpdate() {
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(Country.ARGENTINA, Country.AUSTRALIA);
        Score score = new Score(1, 2);
        assertThrows(ValidateException.class, () -> scoreboard.updateScore(match, score).getScore(), ExceptionMessage.SCORE_UPDATE_ERROR_NO_SUCH_MATCH);
    }
}
