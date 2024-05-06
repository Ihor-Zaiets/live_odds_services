import com.challenge.Score;
import com.challenge.exception.ExceptionMessage;
import com.challenge.exception.ValidateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreTest {

    @Test
    public void shouldThrowIfScoreIsLessThenZero() {
        assertThrows(ValidateException.class, () -> new Score(-1, 0), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
        assertThrows(ValidateException.class, () -> new Score(0, -1), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
        assertThrows(ValidateException.class, () -> new Score(-1, -1), ExceptionMessage.SCORE_CANNOT_BE_LESS_THEN_ZERO);
    }
}
