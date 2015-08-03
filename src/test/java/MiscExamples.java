import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

/**
 * Other misc new Java things good to note.
 */
public class MiscExamples {

    @Test
    public void optional() {
        Optional<String> available = Optional.of("foobar");
        Optional<String> empty = Optional.empty();

        Optional<Integer> availableLength = available.map(s -> s.length());
        Optional<Integer> emptyLength = empty.map(s -> s.length());

        assertTrue(availableLength.isPresent());
        assertEquals(6, availableLength.get().intValue());
        assertFalse(emptyLength.isPresent());

        // Note also flatMap for more monadic behaviour
    }

    // TODO - try with resources (Java 7)?
    // TODO - default methods in interfaces?
    // TODO - default generics diamond operator?
}
