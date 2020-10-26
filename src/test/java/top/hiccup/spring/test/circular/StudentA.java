package top.hiccup.spring.test.circular;

/**
 * StudentA
 *
 * @author wenhy
 * @date 2020/10/26
 */
public class StudentA {

    private StudentB studentB;

    public void setStudentB(StudentB studentB) {
        this.studentB = studentB;
    }

    public StudentA() {
    }

    public StudentA(StudentB studentB) {
        this.studentB = studentB;
    }
}
