public class County {
    private String name;
    private int fips;
    private Election vote;
    private Education education;
    private Employment employment;
    private int centers;

    public County(String name, int fips) {
        this.name = name;
        this.fips = fips;
    }

    public int getCenters() {
        return centers;
    }

    public void setCenters(int centers) {
        this.centers = centers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFips() {
        return fips;
    }

    public void setFips(int fips) {
        this.fips = fips;
    }

    public Election getVote() {
        return vote;
    }

    public void setVote(Election vote) {
        this.vote = vote;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }


}
