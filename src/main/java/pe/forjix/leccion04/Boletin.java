package pe.forjix.leccion04;

public class Boletin {

    public static final int MIN_PASSING_SCORE = 11;

    public static void main(String[] args) {
        final int[] scores = {15, 8, 18, 11, 20, 7, 13};
        printReportResume(scores);
    }

    static double average(int[] scores) {
        int sum = 0;
        for (int item : scores) {
            sum += item;
        }
        return (double) sum / scores.length;
    }

    static int max(int[] scores) {
        int maxGrade = scores[0];
        for (int item : scores) {
            if (item > maxGrade) {
                maxGrade = item;
            }
        }
        return maxGrade;
    }

    static int min(int[] scores) {
        int minGrade = scores[0];
        for (int item : scores) {
            if (item < minGrade) {
                minGrade = item;
            }
        }
        return minGrade;
    }

    public static boolean isPassing(int score) {
        return score >= MIN_PASSING_SCORE;
    }

    public static int countPassing(int[] scores) {
        int count = 0;
        for (int item : scores) {
            if (isPassing(item)) {
                count++;
            }
        }
        return count;
    }

    public static String categoryOf(int score) {
        return switch (score / 5) {
            case 0, 1 -> "deficient";
            case 2 -> "regular";
            case 3 -> "good";
            case 4 -> "excellent";
            default -> "not mapped";
        };
    }

    public static void printReportResume(int[] scores) {
        System.out.println("average: " + average(scores));
        System.out.println("\n" +
                "number of passing grades: " + countPassing(scores));
        System.out.println("\n" +
                "max grade: " + max(scores) + " | min grade: " + min(scores) + "");

        printReportDetail(scores);
    }

    public static void printReportDetail(int[] scores) {
        for (int i = 0; i < scores.length; i++) {
            var detail = isPassing(scores[i]) ? "passed" : "failed";
            System.out.println("Grade " + (i + 1) + " : " + scores[i]
                    + " -> " + categoryOf(scores[i]) + " (" + detail + ")");
        }
    }

}
