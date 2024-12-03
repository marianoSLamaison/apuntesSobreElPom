package orange_team;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
    @Test
    public void leerUnRecursoDeMemoria()
    {
        InputStream input = getClass().getResourceAsStream("/jerma.png");
        assertTrue(input != null);
    }
}
