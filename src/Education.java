public class Education {
    private double noHighSchool;
    private double onlyHighSchool;
    private double someCollege;
    private double bachelorsOrMore;

    public Education(double onlyHighSchool) {
//        this.noHighSchool = noHighSchool;
        this.onlyHighSchool = onlyHighSchool;
//        this.someCollege = someCollege;
//        this.bachelorsOrMore = bachelorsOrMore;
    }

    public double getNoHighSchool() {
        return noHighSchool;
    }

    public void setNoHighSchool(double noHighSchool) {
        this.noHighSchool = noHighSchool;
    }

    public double getOnlyHighSchool() {
        return onlyHighSchool;
    }

    public void setOnlyHighSchool(double onlyHighSchool) {
        this.onlyHighSchool = onlyHighSchool;
    }

    public double getSomeCollege() {
        return someCollege;
    }

    public void setSomeCollege(double someCollege) {
        this.someCollege = someCollege;
    }

    public double getBachelorsOrMore() {
        return bachelorsOrMore;
    }

    public void setBachelorsOrMore(double bachelorsOrMore) {
        this.bachelorsOrMore = bachelorsOrMore;
    }
}

