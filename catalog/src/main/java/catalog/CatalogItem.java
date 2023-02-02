package catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogItem {

    private String registrationNumber;
    private int pieces;
    private List<LibraryItem> libraryItems;

    public CatalogItem(String registrationNumber, int pieces) {
        validateData(registrationNumber,pieces);
        this.registrationNumber = registrationNumber;
        this.pieces = pieces;
    }
    public CatalogItem(String registrationNumber, int pieces,LibraryItem... libraryItems) {
        this(registrationNumber, pieces);
        this.libraryItems = Arrays.asList(libraryItems);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getPieces() {
        return pieces;
    }

    public List<LibraryItem> getLibraryItems() {
        return List.copyOf(libraryItems);
    }
    private void validateData(String registrationNumber, int pieces) {
        if(Validators.isBlank(registrationNumber)){
            throw new IllegalArgumentException("Empty registration number");
        }
        if(pieces < 0){
            throw new IllegalArgumentException("Pieces must be at least 0");
        }
    }

    public boolean hasAudioFeature() {
        for (LibraryItem actual : libraryItems) {
            if (actual.isAudio()){
                return true;
            }
        }
        return false;
    }

    public boolean hasPrintedFeature() {
        for (LibraryItem actual : libraryItems) {
            if (actual.isPrinted()){
                return true;
            }
        }
        return false;
    }

    public int getNumberOfPagesAtOneItem() {
        int numberOfPages = 0;
        for (LibraryItem actual : libraryItems) {
            if (actual.isPrinted()){
                Book book = (Book) actual;
                numberOfPages += book.getNumberOfPages();
            }
        }
        return numberOfPages;
    }

    public List<String> getContributors() {
        List<String> contributors = new ArrayList<>();
        for (LibraryItem actual : libraryItems) {
            for (String s: actual.getContributors()) {
                if(!contributors.contains(s)){
                contributors.add(s);
                }
            }
        }
        return contributors;
    }

    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        for (LibraryItem actual : libraryItems) {
            titles.add(actual.getTitle());
        }
        return titles;

    }
}
