package pe.forjix.leccion06;

public class Escuela {
    private static final String RAW_DATA = """
              jorge garcia , 15
            maria LOPEZ,18
            
            luis torres , 8
            ANA ruiz,20
              pedro diaz,11
            """;

    public static void main(String[] args) {
        final GradeBook gradeBook = new GradeBook(RAW_DATA);
        System.out.println(gradeBook.buildReport());
    }
}
