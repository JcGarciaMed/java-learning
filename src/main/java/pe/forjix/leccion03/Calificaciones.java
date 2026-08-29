package pe.forjix.leccion03;

public class Calificaciones {

    public static void main(String[] args) {

        final int[] scores = {15, 8, 18, 11, 20, 7, 13};

        //PHASE A STATISTICS
        final double itemsQty = scores.length;
        final int minPassingScore = 11;
        int qtyPassing = 0;
        int qtyNotPassing = 0;
        int sum = 0;
        int minGrade = 99;
        int maxGrade = 0;

        for (int item : scores){

            sum+=item;

            if (item < minPassingScore){
                qtyNotPassing+=1;
            }else {
                qtyPassing+=1;
            }

            if (item < minGrade){
                minGrade = item;
            }
            if (item > maxGrade){
                maxGrade = item;
            }
        }

        final double average = sum/itemsQty;

        System.out.println("average: " + average);
        System.out.println("\n" +
                "number of passing grades: " + qtyPassing);
        System.out.println("\n" +
                "number of failed grades: " + qtyNotPassing);

        System.out.println("max grade: " + maxGrade + " | min grade: " + minGrade + "");

        //PHASE B SWITCH DETAIL

        for (int i = 0; i < scores.length; i++) {
            var detail = scores[i] < 11 ? "failed" : "passed";
            var cat = scores[i]/5;
            var category = switch (cat){
                case 1 -> "deficient";
                case 2 -> "regular";
                case 3 -> "good";
                case 4 -> "excelent";
                default -> "not mapped";
            };
            System.out.println("Grade " + (i + 1) + " : " +  scores[i]
                     + " -> " + category + " (" + detail + ")");
        }

    }
}
