public class Text implements Printable{
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void print(boolean isUpperCase) {
        System.out.println(isUpperCase ? text.toUpperCase() : text);
    }
}
