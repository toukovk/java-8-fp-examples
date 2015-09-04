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
        // Optional.of will fail with null
        // Optional.ofNullable will create empty with null
        Optional<String> available2 = Optional.ofNullable("foobar");
        Optional<String> empty2 = Optional.ofNullable(null);

        Optional<Integer> availableLength = available.map(s -> s.length());
        Optional<Integer> emptyLength = empty.map(s -> s.length());
        Optional<Integer> available2Length = available2.map(s -> s.length());
        Optional<Integer> empty2Length = empty2.map(s -> s.length());

        assertTrue(availableLength.isPresent());
        assertEquals(6, availableLength.get().intValue());
        assertEquals(6, available2Length.get().intValue());
        assertFalse(emptyLength.isPresent());
        assertFalse(empty2Length.isPresent());

        // Note that Optional:map uses ofNullable for the function's result
        // Note also flatMap for more monadic behaviour
    }

    // Other misc things to note with Java 8:
    // - default methods in interfaces?
    //   -> http://zeroturnaround.com/rebellabs/java-8-explained-default-methods/
    // - Java 8 Date and Time (JSR310)
    //   - Influenced by Joda-time -> http://blog.joda.org/2014/07/threeten-backport-vs-joda-time.html
    //   -> http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html
    //   -> https://jcp.org/en/jsr/detail?id=310

    // Some things introduced already in Java 7:
    // - try with resources (Java 7)?
    //   -> https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
    // - diamond operator (type inference on generic instance creation)
    //   - for ex: List<String> foo = new ArrayList<String>();
    //   -> https://docs.oracle.com/javase/7/docs/technotes/guides/language/type-inference-generic-instance-creation.html
}
