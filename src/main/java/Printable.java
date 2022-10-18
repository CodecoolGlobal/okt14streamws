@FunctionalInterface
public interface Printable {
    void print(boolean isUpperCase);
    //void other(); hibát add a @FunctionalInterface annotációval.
    default void doOtherThing(){
        System.out.println("doing other thing");
    }
}
