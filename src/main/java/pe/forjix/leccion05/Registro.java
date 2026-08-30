package pe.forjix.leccion05;

import pe.forjix.leccion04.Boletin;

import java.util.Objects;

record Student(String names, int score, String status) {
}

record ReportSummary(double average, int approved, int total) {
}

public class Registro {

    private static final String RAW_DATA = """
              jorge garcia , 15
            maria LOPEZ,18
            
            luis torres , 8
            ANA ruiz,20
              pedro diaz,11
            """;

    public static void main(String[] args) {
        System.out.println(buildReport(RAW_DATA));
    }

    static String buildReport(String rawData) {
        StringBuilder report = new StringBuilder();
        final String header = String.format("%-15s %6s %10s", "ALUMNO", "NOTA", "ESTADO");
        final String separator = "-".repeat(header.length());
        report.append(header).append('\n');
        final Student[] students = parse(rawData);

        for (final Student student : students) {
            report.append(String.format("%-15s %6d %10s", student.names(),
                            student.score(),
                            student.status()))
                    .append('\n');
        }
        report.append(separator).append('\n');
        var summary = getReportSummary(students);
        report.append(String.format("Promedio: %.2f", summary.average())).append("  |    ")
                .append(summary.approved()).append("/").append(summary.total());
        return report.toString();
    }

    static String capitalizeNames(String fullName) {
        final String[] words = fullName.strip().toLowerCase().split("\\s+");
        final StringBuilder result = new StringBuilder();
        for (final String word : words) {
            if (!result.isEmpty()) {
                result.append(' ');
            }
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }

    static ReportSummary getReportSummary(Student[] students) {
        int approvedScores = 0;
        int sumScores = 0;
        int scores = 0;
        for (final Student student : students) {
            if (student.score() >= Boletin.MIN_PASSING_SCORE) {
                approvedScores++;
            }
            sumScores += student.score();
            scores++;
        }
        return new ReportSummary((double) sumScores / scores, approvedScores, students.length);
    }

    static Student[] parse(String rawData) {
        String[] lines = rawData.strip().split("\n");
        Student[] students = new Student[lines.length];
        for (int i = 0; i < lines.length; i++) {
            final String[] cell = lines[i].split(",");
            if (lines[i].isBlank() || cell.length < 2) {
                continue;
            }
            final String name = capitalizeNames(cell[0].strip());
            final int score = Integer.parseInt(cell[1].strip());
            final String status = score >= Boletin.MIN_PASSING_SCORE ? "OK" : "FAIL";
            students[i] = new Student(name, score, status);
        }
        return removeNulls(students);
    }

    static Student[] removeNulls(Student[] students) {
        int validStudents = 0;
        for (Student student : students) {
            if (Objects.nonNull(student)) {
                validStudents++;
            }
        }
        Student[] result = new Student[validStudents];
        var newIndex = 0;
        for (Student student : students) {
            if (Objects.nonNull(student)) {
                result[newIndex++] = student;
            }
        }
        return result;
    }
}
