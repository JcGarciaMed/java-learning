package pe.forjix.leccion02;

public class Conversor {
    public static void main(String[] args) {
        int seconds = 9345;
        var hours = seconds / 3600;
        var residual  = seconds % 3600;
        var minutes = residual/60;
        var finalSeconds = residual % 60;

        System.out.println(seconds + " seconds = " + hours + "h " + minutes + "m " + finalSeconds + "s");

        final int gradeA = 15;
        final int gradeB = 18;
        final int gradeC = 14;
        final double promedio = (gradeA + gradeB + gradeC) / 3.0;

        System.out.println("average: " + promedio);

    }
}
