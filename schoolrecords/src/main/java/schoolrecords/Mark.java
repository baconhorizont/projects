package schoolrecords;

public class Mark {

    private MarkType markType;
    private Subject subject;
    private Tutor tutor;
    public Mark(MarkType markType, Subject subject, Tutor tutor) {
      if (markType == null || subject == null || tutor == null){
          throw new IllegalArgumentException("Both subject and tutor must be provided!");
      }
        this.markType = markType;
        this.subject = subject;
        this.tutor = tutor;
    }


    public MarkType getMarkType() {
        return markType;
    }

    public Subject getSubject() {
        return subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public int getMarkValue(){
        return markType.getValue();
    }

    public String getSubjectName(){
        return subject.getName();
    }

    @Override
    public String toString() {
        return getSubjectName() + " - " + markType.getDescription() + "(" +getMarkValue()+ ")";
    }
}
