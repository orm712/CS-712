class StudentBuilder {
    private int id;
    private String name;
    private String grade;
    private String phoneNumber;

    public StudentBuilder id(int id) {
        this.id = id;
        return this;
    }

    public StudentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder grade(String grade) {
        this.grade = grade;
        return this;
    }

    public StudentBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}