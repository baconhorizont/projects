package covid;

public class City {

    private Long id;
    private String zipCode;
    private String cityName;
    private String cityPartName;

    public City(String zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }
    public City(Long id, String zipCode, String cityName) {
        this(zipCode, cityName);
        this.id = id;
    }

    public City(Long id, String zipCode, String cityName, String cityPartName) {
        this(id, zipCode, cityName);
        this.cityPartName = cityPartName;
    }

    public Long getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityPartName() {
        return cityPartName;
    }
}
