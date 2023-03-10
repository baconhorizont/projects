package covid;

import java.time.LocalDateTime;

public class Vaccination {

    private Long id;
    private Long citizenId;
    private LocalDateTime vaccinationTime;
    private VaccinationStatus status;
    private String note;
    private VaccinationType type;

    public Vaccination(Long id, Long citizenId, LocalDateTime vaccinationTime, VaccinationStatus status, VaccinationType type) {
        this.id = id;
        this.citizenId = citizenId;
        this.vaccinationTime = vaccinationTime;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public LocalDateTime getVaccinationTime() {
        return vaccinationTime;
    }

    public VaccinationStatus getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public VaccinationType getType() {
        return type;
    }

}
