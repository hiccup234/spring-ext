package top.hiccup.spring.test.circular;

/**
 * StudentC
 *
 * @author wenhy
 * @date 2020/10/26
 */
public class StudentC {

    private StudentA studentA;

    public void setStudentA(StudentA studentA) {
        this.studentA = studentA;
    }

    public StudentC() {
    }

    public StudentC(StudentA studentA) {
        this.studentA = studentA;
    }
}
