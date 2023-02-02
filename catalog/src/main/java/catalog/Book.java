package catalog;

import java.util.ArrayList;
import java.util.List;

public class Book implements LibraryItem{

    private String title;
    private int numberOfPages;
    private List<String> contributors;

    public Book(String title, int numberOfPages, List<String> contributors) {
        validateData(title, numberOfPages, contributors);
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.contributors = contributors;
    }

    @Override
    public List<String> getContributors() {
        return new ArrayList<>(contributors);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isPrinted() {
        return true;
    }

    @Override
    public boolean isAudio() {
        return false;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
    private void validateData(String title, int numberOfPages, List<String> contributors) {
        if(Validators.isBlank(title)){
            throw new IllegalArgumentException("Empty title");
        }
        if(numberOfPages <= 0){
            throw new IllegalArgumentException("Illegal number of pages: " + numberOfPages);
        }
        if(Validators.isEmpty(contributors)){
            throw new IllegalArgumentException("No authors");
        }
    }
}
