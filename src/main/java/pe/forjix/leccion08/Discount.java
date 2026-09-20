package pe.forjix.leccion08;

@FunctionalInterface
public interface Discount {
    double apply(double cost);
}
