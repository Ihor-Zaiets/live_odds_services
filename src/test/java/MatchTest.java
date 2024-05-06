import com.challenge.Match;
import com.challenge.enums.Country;
import com.challenge.exception.ExceptionMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest {

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
}
