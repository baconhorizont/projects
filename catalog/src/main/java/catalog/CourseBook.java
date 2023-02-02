package catalog;

import java.util.List;

public class CourseBook extends Book{

    private String lector;

    public CourseBook(String title, int numberOfPages, List<String> contributors, String lector) {
        super(title, numberOfPages, contributors);
        validateData(lector);
        this.lector = lector;
    }

    public String getLector() {
        return lector;
    }

    @Override
    public List<String> getContributors() {
        List<String> contributors = super.getContributors();
        contributors.add(lector);
        return contributors;
    }
    private void validateData(String lector) {
        if(Validators.isBlank(lector)){
            throw new IllegalArgumentException("No lector");
        }
    }
}
