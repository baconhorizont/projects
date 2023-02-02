package catalog;

import java.util.ArrayList;
import java.util.List;

public class MusicAlbum implements LibraryItem{

    private String title;
    private int length;
    private List<String> composers;
    private List<String> performers;

    public MusicAlbum(String title, int length, List<String> performers) {
        validateData(title,length,performers);
        this.title = title;
        this.length = length;
        this.performers = performers;
    }
    public MusicAlbum(String title, int length, List<String> composers, List<String> performers) {
        this(title, length, performers);
        this.composers = composers;

    }

    public int getLength() {
        return length;
    }

    @Override
    public List<String> getContributors() {
        if(composers == null){
            return performers;
        }
        List<String> contributors = new ArrayList<>(composers);
        contributors.addAll(performers);
        return contributors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isPrinted() {
        return false;
    }

    @Override
    public boolean isAudio() {
        return true;
    }
    private void validateData(String title, int length, List<String> performers) {
        if(Validators.isBlank(title)){
            throw new IllegalArgumentException("Empty title");
        }
        if(length <= 0){
            throw new IllegalArgumentException("Illegal length: " + length);
        }
        if(Validators.isEmpty(performers)){
            throw new IllegalArgumentException("No performer");
        }
    }
}
