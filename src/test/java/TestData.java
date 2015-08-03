import java.util.List;

import com.google.common.collect.Lists;

public class TestData {
    public static List<Book> getBooks() {
        return Lists.newArrayList(
            new Book("Structure and Interpretation of Computer Programs", 1984, 
                Lists.newArrayList(new Author("Gerald", "Sussman"), new Author("Hal", "Abelson"))),
            new Book("Design Patterns: Elements of Reusable Object-Oriented Software", 1994, 
                Lists.newArrayList(new Author("Erich", "Gamma"), new Author("John", "Vlissides"), new Author("Raplh", "Johnson"), new Author("Richard", "Helm"))),
            new Book("Refactoring: Improving the Design of Existing Code", 1999, 
                Lists.newArrayList(new Author("Martin", "Fowler"))),
            new Book("Functional Programming in Java", 2014, 
                Lists.newArrayList(new Author("Venkat", "Subramaniam")))
        );
    }
}
