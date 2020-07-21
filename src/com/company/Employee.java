package com.company;
import lombok.Setter;
import lombok.Getter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Setter @Getter
public class Employee {
    private double currentSalary; // 현연봉
    private String rating; // 근무 평가등급
    private double raise = 0.0; // 연봉 인상액
    private double newSalary; // 새연봉

    public double increaseSalary(String grade) {
        if(grade.equals(Grade.Good.getGrade())) return currentSalary * Grade.Good.getRaise();
        if(grade.equals(Grade.Normal.getGrade())) return currentSalary * Grade.Normal.getRaise();
        if(grade.equals(Grade.Bad.getGrade())) return currentSalary * Grade.Bad.getRaise();
        return raise;
    }

    public void setCurrentSalary(double currentSalary) {
        this.currentSalary = currentSalary;
    }

    private enum Grade {
        Good("우수", 0.06), Normal("보통", 0.04), Bad("불량", 0.02);
        private final String grade;
        private final double raise;
        private Grade(String grade, double raise) {
            this.grade = grade;
            this.raise = raise;
        }
        public String getGrade() { return grade; }
        public double getRaise() { return raise; }
    }
}

class MainTest {
    public static void main(String[] args) throws IOException {
        Employee employee = new Employee();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("현 연봉을 입력하세요: ");
        double curSal = Double.parseDouble(br.readLine());
        employee.setCurrentSalary(curSal);

        System.out.print("근무 평가등급을 입력하세요: ");
        String rating = br.readLine();
        double raise = employee.increaseSalary(rating);
        System.out.print("연봉 인상액: " + raise + "\n");
        System.out.printf("새 연봉: %.1f", curSal + raise);
    }
}
