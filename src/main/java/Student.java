public class Student extends Person{
    private Integer mathGrade;
    private Integer englishGrade;
    private Integer historyGrade;

    public Student(String id, String firstName, String lastName, Integer age, Integer mathGrade, Integer englishGrade, Integer historyGrade) {
        super(id, firstName, lastName, age);
        this.mathGrade = mathGrade;
        this.englishGrade = englishGrade;
        this.historyGrade = historyGrade;
    }

    public Integer getMathGrade() {
        return mathGrade;
    }

    public Integer getEnglishGrade() {
        return englishGrade;
    }

    public Integer getHistoryGrade() {
        return historyGrade;
    }
}
