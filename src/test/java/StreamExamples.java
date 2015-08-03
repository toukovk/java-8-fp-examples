import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Sets;

public class StreamExamples {

    /**
     * Intermediate operations are operations that produce a new stream,
     * e.g. map, filter, distinct and sorted 
     * 
     * Good to note: Intermediate variables here only to clarify the process.
     * Since streams can be consumed only once, these operations are often
     * chained without intermediate variables.
     */
    @Test
    public void intermediateOperations() {
        Stream<Book> books = TestData.getBooks().stream();
        // filter
        Stream<Book> booksWithMultipleAuthors = books.filter(book -> book.hasMultipleAuthors());
        // map
        Stream<String> namesStream = booksWithMultipleAuthors.map(book -> book.getName());
        
        Set<String> expected = Sets.newHashSet("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Structure and Interpretation of Computer Programs");
        assertEquals(expected, namesStream.collect(Collectors.toSet()));
    }

    /**
     * Terminal operations are operations that consume the stream to produce a result or a side-effect.
     *
     * Some examples: reduce, count, collect, foreach, findAny, findFirst 
     * Note that after a stream is consumed (by doing a terminal operation), it can't be used any more.
     */
    @Test
    public void terminalOperations() {
        // count
        assertEquals(4, TestData.getBooks().stream().count());
        // findFirst
        Optional<Book> gangOfFour = TestData.getBooks().stream()
                .filter(book -> book.name.equals("Design Patterns: Elements of Reusable Object-Oriented Software"))
                .findFirst();
        assertTrue(gangOfFour.isPresent());
        assertEquals(4, gangOfFour.get().authors.size());
    }

    @Test
    public void summingWithReduceAndSum() {
        List<Integer> ints = Arrays.asList(1,2,3,4,5);

        // T reduce(T identity, BinaryOperator<T> accumulator)
        assertEquals(15, ints.stream().reduce(0, (Integer a, Integer b) -> a+b).intValue());
        // Note that there are separate Stream interfaces for primitive data types (because of how Java generics work)
        // (thus mapToInt)
        assertEquals(15, ints.stream().mapToInt(i -> i).reduce(0, (a, b) -> a+b));
        // For streams for primitive number types there's also sum
        assertEquals(15, ints.stream().mapToInt(i -> i).sum());
    }

    @Test
    public void collectAndReduce() {
        // Collecting stream elements to can be done manually with general reduce 
        // <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
        List<Book> collectedWithReduce = TestData.getBooks().stream().reduce(new ArrayList<Book>(),
                (list, book) -> {
                    // SEparate list created to avoid mutating elements in the list
                    // -> works also with parallel streams
                    ArrayList<Book> result = new ArrayList<Book>(list);
                    result.add(book);
                    return result;
                }, (list1, list2) -> {
                    ArrayList<Book> result = new ArrayList<Book>(list1);
                    result.addAll(list2);
                    return result;
                });
        assertEquals(4, collectedWithReduce.size());

        // Collect to list "manually" with collect
        // R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)
        List<Book> books = TestData.getBooks().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        assertEquals(4, books.size());

        // And the typical way using utilities from java.util.stream.Collectors
        assertEquals(4, TestData.getBooks().stream().collect(Collectors.toList()).size());
    }
}
