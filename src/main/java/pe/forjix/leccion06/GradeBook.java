package pe.forjix.leccion06;

import java.util.Objects;

public class GradeBook {
    private final Student[] students;
    private final int MIN_PASSING_SCORE = 11;
    private final String header = String.format("%-15s %6s %10s", "ALUMNO", "NOTA", "ESTADO");

    public GradeBook(String rawData) {
        this.students = parse(rawData);
    }

    private Student[] parse(String rawData) {
        String[] lines = rawData.strip().split("\n");
        Student[] students = new Student[lines.length];
        for (int i = 0; i < lines.length; i++) {
            final String[] cell = lines[i].split(",");
            if (lines[i].isBlank() || cell.length < 2) {
                continue;
            }
            final int score = Integer.parseInt(cell[1].strip());
            students[i] = new Student(cell[0].strip(), score);
        }
        return removeNulls(students);
    }

    public String buildReport() {
        StringBuilder report = new StringBuilder();
        final String separator = "-".repeat(header.length());
        report.append(header).append('\n');
        for (final Student student : students) {
            report.append(String.format("%-15s %6d %10s", student.name(),
                            student.score(),
                            evaluateStatus(student.score())))
                    .append('\n');
        }
        report.append(separator).append('\n');

        report.append(String.format("Promedio: %.2f", average())).append("  |  ")
                .append("Aprobados:")
                .append(countPassing()).append("/").append(size());
        return report.toString();
    }

    private Student[] removeNulls(Student[] students) {
        int validStudents = 0;
        for (final Student student : students) {
            if (Objects.nonNull(student)) {
                validStudents++;
            }
        }
        Student[] result = new Student[validStudents];
        var newIndex = 0;
        for (final Student student : students) {
            if (Objects.nonNull(student)) {
                result[newIndex++] = student;
            }
        }
        return result;
    }

    private double average() {
        int sum = 0;
        for (final Student student : students) {
            sum += student.score();
        }
        return (double) sum / students.length;
    }

    private String evaluateStatus(int score) {
        return score >= MIN_PASSING_SCORE ? "OK" : "FAIL";
    }

    public int max() {
        int maxGrade = students[0].score();
        for (final Student student : students) {
            if (student.score() > maxGrade) {
                maxGrade = student.score();
            }
        }
        return maxGrade;
    }

    public int min() {
        int minGrade = students[0].score();
        for (final Student student : students) {
            if (student.score() < minGrade) {
                minGrade = student.score();
            }
        }
        return minGrade;
    }

    private int size() {
        return students.length;
    }

    private int countPassing() {
        int count = 0;
        for (final Student student : students) {
            if (student.isPassing()) {
                count++;
            }
        }
        return count;
    }

}
