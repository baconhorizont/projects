package catalog;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Catalog {

    private static final String DELIMITER = ";";
    private static final String REGISTRATION_FORMAT ="R-";
    private List<CatalogItem> catalogItems = new ArrayList<>();

    public List<CatalogItem> getCatalogItems() {
        return new ArrayList<>(catalogItems);
    }

    public void addItem(CatalogItem catalogItem){
        if(catalogItem == null){
            throw new IllegalArgumentException("Catalog item is empty");
        }
        catalogItems.add(catalogItem);
    }


    public void deleteItemByRegistrationNumber(String registrationNumber) {
        if(registrationNumber == null || registrationNumber.isBlank()){
            throw new IllegalArgumentException("Empty registration number");
        }
        CatalogItem itemToDelete = null;
        for (CatalogItem actual : catalogItems) {
            if(actual.getRegistrationNumber().equals(registrationNumber)){
                itemToDelete = actual;
            }
        }
        catalogItems.remove(itemToDelete);
    }

    public List<CatalogItem> getAudioLibraryItems() {
        List<CatalogItem> audioItems = new ArrayList<>();
        for (CatalogItem actual : catalogItems) {
            if(actual.hasAudioFeature()){
             audioItems.add(actual);
            }
        }
        return audioItems;
    }

    public List<CatalogItem> getPrintedLibraryItems() {
        List<CatalogItem> printItems = new ArrayList<>();
        for (CatalogItem actual : catalogItems) {
            if(actual.hasPrintedFeature()){
                printItems.add(actual);
            }
        }
        return printItems;
    }

    public int getAllPageNumber() {
        int allPage = 0;
        for (CatalogItem actual : catalogItems) {
            if(actual.hasPrintedFeature()){
                allPage += actual.getNumberOfPagesAtOneItem();
            }
        }
        return allPage;
    }

    public double getAveragePageNumberMoreThan(int pageNumber) {
        if(pageNumber < 0){
            throw new IllegalArgumentException("Page number must be positive");
        }
        if(pageNumber > getAllPageNumber()){
            throw new IllegalArgumentException("No such book");
        }
        int pages = 0;
        int numberOfItems = 0;
        for (CatalogItem actual : catalogItems) {
            int actualNumberOfPages = actual.getNumberOfPagesAtOneItem();
            if(actual.hasPrintedFeature() && actualNumberOfPages > pageNumber){
                pages += actualNumberOfPages;
                numberOfItems++;
            }
        }
        if (numberOfItems == 0){
            return 0;
        }
        return (double) pages / numberOfItems;
    }

    public List<CatalogItem> findByCriteria(SearchCriteria searchCriteria) {
        if(searchCriteria == null){
            throw new IllegalArgumentException("Empty criteria");
        }
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem actual: catalogItems) {
            if(searchCriteria.hasTitle() && actual.getTitles().contains(searchCriteria.getTitle()) && !result.contains(actual)){
                result.add(actual);
            }
            if(searchCriteria.hasContributor() && actual.getContributors().contains(searchCriteria.getContributor()) && !result.contains(actual)){
                result.add(actual);
            }
        }
    return result;
    }

    public void readBooksFromFile(Path path) {
        int registrationNumber = getCatalogItems().size()+1;
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                createCatalogItem(line,registrationNumber);
                registrationNumber++;
            }
        }catch (IOException e){
            throw new IllegalStateException("Can not read from file",e);
        }
    }

    private void createCatalogItem(String line,int registrationNumber){
            String[] temp = line.split(DELIMITER);
            List<String> contributors = new ArrayList<>(Arrays.asList(temp).subList(3, temp.length));
        try {
            LibraryItem libraryItem = new Book(temp[1],Integer.parseInt(temp[2]),contributors);
            catalogItems.add(new CatalogItem(REGISTRATION_FORMAT + registrationNumber,Integer.parseInt(temp[0]),libraryItem));
        }catch (NullPointerException | IllegalArgumentException e){
            throw new WrongFormatException("Line format in file is wrong",e);
        }
    }
}
