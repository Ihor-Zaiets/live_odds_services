import com.challenge.exception.ExceptionMessage;
import com.challenge.exception.ValidateException;
import com.challenge.jpa.entity.Match;
import com.challenge.jpa.entity.Score;
import com.challenge.jpa.entity.Scoreboard;
import com.challenge.jpa.enums.Country;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        assertThrows(ValidateException.class, () -> new Score(-1, 0), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
        assertThrows(ValidateException.class, () -> new Score(0, -1), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
        assertThrows(ValidateException.class, () -> new Score(-1, -1), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
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
        List<Match> scoreboardBefore = new ArrayList<>(scoreboard.getScoreboardMatchList());
        Match match = scoreboard.startMatch(Country.ARGENTINA, Country.BRAZIL);
        List<Match> scoreboardAfter = new ArrayList<>(scoreboard.getScoreboardMatchList());
        assertTrue(scoreboardAfter.contains(match));
        assertEquals(scoreboardBefore.size() + numberOfCreatedMatches, scoreboardAfter.size());
    }

    @Test
    public void shouldDeleteMatchFromScoreboard() {
        int numberOfDeletedMatches = 1;
        Scoreboard scoreboard = new Scoreboard();
        Match match = scoreboard.startMatch(Country.ARGENTINA, Country.BRAZIL);
        List<Match> scoreboardBefore = new ArrayList<>(scoreboard.getScoreboardMatchList());
        assertTrue(scoreboard.finishMatch(match));
        List<Match> scoreboardAfter = new ArrayList<>(scoreboard.getScoreboardMatchList());

        assertTrue(scoreboardBefore.contains(match));
        assertFalse(scoreboardAfter.contains(match));
        assertEquals(scoreboardBefore.size() - numberOfDeletedMatches, scoreboardAfter.size());
    }

    @Test
    public void shouldReturnFalseIfNoMatchWasFinished() {
        Scoreboard scoreboard = new Scoreboard();
        Match match = new Match(Country.ARGENTINA, Country.BRAZIL);
        List<Match> scoreboardBefore = new ArrayList<>(scoreboard.getScoreboardMatchList());
        assertFalse(scoreboard.finishMatch(match));
        List<Match> scoreboardAfter = new ArrayList<>(scoreboard.getScoreboardMatchList());

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
        assertThrows(ValidateException.class, () -> scoreboard.updateScore(match, score), ExceptionMessage.SCORE_UPDATE_ERROR_NO_SUCH_MATCH);
    }

    @Test
    public void shouldReturnMatchesSortedByHighestScoreOrStartDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Scoreboard scoreboard = new Scoreboard();
        Match matchMexicoCanada = scoreboard.startMatch(Country.MEXICO, Country.CANADA);
        matchMexicoCanada.setScore(new Score(0, 5));
        matchMexicoCanada.setStartDate(localDateTime.plusSeconds(1));
        Match matchSpainBrazil = scoreboard.startMatch(Country.SPAIN, Country.BRAZIL);
        matchSpainBrazil.setScore(new Score(10, 2));
        matchSpainBrazil.setStartDate(localDateTime.plusSeconds(2));
        Match matchGermanyFrance = scoreboard.startMatch(Country.GERMANY, Country.FRANCE);
        matchGermanyFrance.setScore(new Score(2, 2));
        matchGermanyFrance.setStartDate(localDateTime.plusSeconds(3));
        Match matchUruguayItaly = scoreboard.startMatch(Country.URUGUAY, Country.ITALY);
        matchUruguayItaly.setScore(new Score(6, 6));
        matchUruguayItaly.setStartDate(localDateTime.plusSeconds(4));
        Match matchArgentinaAustralia = scoreboard.startMatch(Country.ARGENTINA, Country.AUSTRALIA);
        matchArgentinaAustralia.setScore(new Score(3, 1));
        matchArgentinaAustralia.setStartDate(localDateTime.plusSeconds(5));
        List<Match> expectedOrderedList = scoreboard.getScoreboardMatchList().stream()
                .sorted(Comparator.comparing(Match::getScore, Comparator.comparingInt((score) -> score.getAwayTeamScore() + score.getHomeTeamScore())).reversed()
                        .thenComparing(Match::getStartDate))
                .toList();
        assertEquals(expectedOrderedList, scoreboard.getScoreboardMatchList());
    }
}
