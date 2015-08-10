import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Examples for lambda expression formats, method references etc.
 */
public class LambdaExpressionExamples {
    /**
     * There are different formats for inline lambda expressions
     */
    @Test
    public void lambdaExpressionVariants() {
        List<String> names = Lists.newArrayList("Teuvo", "Ari", "Kristiina");
        
        System.out.println("Lambda forEach with parameters and types");
        names.forEach((String name) -> System.out.println(name));

        System.out.println("Lambda forEach with type variance");
        names.forEach((name) -> System.out.println(name));        

        System.out.println("Lambda forEach without parentheses (only with single-parameter case)");
        names.forEach(name -> System.out.println(name));    
    }
    
    /**
     * It's also possible to have a multi-line lambda expression.
     *
     * However, consider @venkat_s's advice:
     * "Lambda expressions should be glue code. Two lines may be too many."
     * (https://twitter.com/venkat_s/status/611119147586600960)
     */
    @Test
    public void lambdaExpression_multiLineLambdaExpression() {
        List<String> names = Lists.newArrayList("Teuvo", "Ari", "Kristiina");
        
        // Reference to instance method
        List<Integer> lengths = names.stream()
            .map(name -> {
                int length = name.length();
                return length;
            })
            .collect(Collectors.toList());

        assertEquals(Lists.newArrayList(5, 3, 9), lengths);
    }

    /**
     * It is also possible to refer to instance methods for stream elements. 
     */
    @Test
    public void lambdaExpression_instanceMethodReferences() {
        List<String> names = Lists.newArrayList("Teuvo", "Ari", "Kristiina");
        
        // Reference to instance method
        List<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toList());

        assertEquals(Lists.newArrayList(5, 3, 9), lengths);
    }

    /**
     * For some cases we can refer to instance methods taking another element as a parameter.
     * 
     * For example String's: public int compareToIgnoreCase(String str)
     */
    @Test
    public void lambdaExpression_instanceMethodWithParameter() {
        String[] array = { "Teuvo", "Ari", "Kristiina" };
        Arrays.sort(array, String::compareToIgnoreCase);
        
        assertArrayEquals(new String[] {"Ari", "Kristiina",  "Teuvo"}, array);
    }

    /**
     * It's also possible to refer to static methods (taking right number of parameters) 
     */
    @Test
    public void lambdaExpression_staticMethodReferences() {
        List<String> names = Lists.newArrayList("Teuvo", "Ari", "Kristiina");
        
        List<Integer> lengths = names.stream()
            .map(LambdaExpressionExamples::countLength)
            .collect(Collectors.toList());

        assertEquals(Lists.newArrayList(5, 3, 9), lengths);
    }

    /**
     * Example static method to be referenced.
     */
    private static Integer countLength(String string) {
        return string.length();
    }

    /**
     * Reference to constructor
     */
    @Test
    public void lambdaExpression_constructorReferences() {
        List<String> names = Lists.newArrayList("Teuvo", "Ari", "Kristiina");

        Stream<StringLengthComputer> counters = names.stream()
            .map(StringLengthComputer::new);

        List<Integer> lengths = counters
            .map(StringLengthComputer::computeLength)
            .collect(Collectors.toList());

        assertEquals(Lists.newArrayList(5, 3, 9), lengths);
    }

    /**
     * Example class to be instantiated with constructor reference. 
     */
    private static class StringLengthComputer {
        private String string;

        public StringLengthComputer(String string) {
            this.string = string;
        }

        public Integer computeLength() {
            return string.length();
        }
    }
    
    /**
     * What these lambda expressions end up being? Lambda expressions can be passed whenever a "functional interface" is
     * wanted. Some examples follow.
     * 
     * Some info at https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html &
     * https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
     */
    @Test
    public void functionalInterfaces() {
        List<String> names = Lists.newArrayList("Teuvo", "Ari", "Kristiina");

        // Consumer<T>
        Consumer<String> consumer = name -> System.out.println(name);
        System.out.println("forEach with Consumer");
        names.forEach(consumer);

        // Function<T, R>
        Function<String, Integer> function = String::length;
        List<Integer> lengths = names.stream().map(function).collect(Collectors.toList());
        assertEquals(Lists.newArrayList(5, 3, 9), lengths);

        // Comparator<T>
        Comparator<String> comparator = String::compareToIgnoreCase; 
        String[] array = { "Teuvo", "Ari", "Kristiina" };
        Arrays.sort(array, comparator);
        assertArrayEquals(new String[] {"Ari", "Kristiina",  "Teuvo"}, array);

        // Supplier
        Supplier<Double> supplier = () -> Math.random();
        System.out.println("Supplied: " + supplier.get());
    }
}
