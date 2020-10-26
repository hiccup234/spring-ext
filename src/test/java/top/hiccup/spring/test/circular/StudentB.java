package top.hiccup.spring.test.circular;

/**
 * StudentB
 *
 * @author wenhy
 * @date 2020/10/26
 */
public class StudentB {

    private StudentC studentC;

    public void setStudentC(StudentC studentC) {
        this.studentC = studentC;
    }

    public StudentB() {
    }

    public StudentB(StudentC studentC) {
        this.studentC = studentC;
    }
}
