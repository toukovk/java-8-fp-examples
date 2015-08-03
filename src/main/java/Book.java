import java.util.List;

public class Book {
    public final String name;
    public final int publicationyear;
    public final List<Author> authors;

    public Book(String name, int publicationyear, List<Author> authors) {
        this.name = name;
        this.publicationyear = publicationyear;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public int getPublicationyear() {
        return publicationyear;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public boolean hasMultipleAuthors() {
        return authors.size() > 1;
    }
}
