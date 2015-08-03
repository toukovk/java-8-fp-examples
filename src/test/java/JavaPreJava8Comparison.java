import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * List procession example: iteratively with pre-Java 8 ({@link #processListIteratively()}), with Guava FP idioms (
 * {@link #processListWithGuava()} and with Java 8 streams ({@link #processListWithJava8()})
 */
public class JavaPreJava8Comparison {
    
    /**
     * Pre-Java 8 example of going iteratively through a list of books and
     * selecting names for books with multiple authors
     */
    @Test
    public void processListIteratively() {
        Set<String> names = new HashSet<>();
        for(Book book : TestData.getBooks()) {
            if(book.hasMultipleAuthors()) {
                names.add(book.name);
            }
        }
        
        Set<String> expected = Sets.newHashSet("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Structure and Interpretation of Computer Programs");
        assertEquals(expected, names);
    }





    /**
     * The functionality of {@link #processListIteratively()} with Guava FP idioms.
     * Can't be said to be better than the iterative version. 
     * Read https://code.google.com/p/guava-libraries/wiki/FunctionalExplained (and Caveats)
     */
    @Test
    public void processListWithGuava() {
        // If used more, could be extracted
        final Predicate<Book> hasMultipleAuthors = new Predicate<Book>() {
            public boolean apply(Book book) {
              return book.hasMultipleAuthors();
            }
          };
        Function<Book, String> getBooksName = new Function<Book, String>() {
            public String apply(Book book) {
                return book.getName();
            }
        };
        Iterable<Book> booksWithMultiple = Iterables.filter(TestData.getBooks(), hasMultipleAuthors);
        Set<String> names = Sets.newHashSet(Iterables.transform(booksWithMultiple, getBooksName));
        
        Set<String> expected = Sets.newHashSet("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Structure and Interpretation of Computer Programs");
        assertEquals(expected, names);
    }




    /**
     * Java 8 example with streams & lambda expressions for comparison.
     */
    @Test
    public void processListWithJava8() {
        Set<String> names = TestData.getBooks().stream()
            .filter(book -> book.hasMultipleAuthors())
            .map(book -> book.getName())
            .collect(Collectors.toSet());
        
        Set<String> expected = Sets.newHashSet("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Structure and Interpretation of Computer Programs");
        assertEquals(expected, names);
    }
}
