import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Printable textOne = new Text("One");
        Printable textTwo = new Text("Two");
        textOne.print(true);
        textTwo.print(false);
        textOne.doOtherThing();
        Printable textThree = isUpperCase -> {
            System.out.println(isUpperCase ? "JAVA IS GOOD" : "java is good");
        };
        textThree.print(true);
        textThree.print(false);
        System.out.println("---------------------------function-----------------------------");
        Function<Integer,Integer> addEleven = inputValue -> inputValue + 11;
        System.out.println(addEleven.apply(10));
        Function<Integer,String> concatText = inputValue -> "The result is " + inputValue;
        System.out.println(concatText.apply(10));
        Function<Integer,String> createSolutionText = addEleven.andThen(concatText);
        System.out.println(createSolutionText.apply(50));
        System.out.println("------------consumer--------------------------------");
        //Consumer<String> print = text -> System.out.println(text);
        Consumer<String> print = System.out::println; //method reference
        print.accept("test string");
        System.out.println("---------------supplier------------------");
        Supplier<Integer> randomNumberSupplier = () -> {
            Random random = new  Random();
            return random.nextInt(100);
        };
        System.out.println(randomNumberSupplier.get());
        System.out.println("--------predicate-----------------");
        Predicate<String> isAtLeastFiveCharacter = text -> text.length() >= 5;
        System.out.println(isAtLeastFiveCharacter.test("long text"));
        System.out.println(isAtLeastFiveCharacter.test("A"));
        Predicate<String> containsA = text -> text.contains("A");
        System.out.println(containsA.test("long text"));
        System.out.println(containsA.test("A"));
        System.out.println("---------------------------------------");
        System.out.println(isAtLeastFiveCharacter.and(containsA).test("long text"));
        System.out.println(isAtLeastFiveCharacter.and(containsA).test("A"));
        System.out.println(isAtLeastFiveCharacter.and(containsA).test("long text with A"));
        System.out.println("--------------Optionals-----------------");
        Optional<String> myOptional = Optional.of("this is my string");
        System.out.println(myOptional.isEmpty());
        System.out.println(myOptional.isPresent());
        //myOptional = Optional.of(null); java.lang.NullPointerException-t add
        myOptional = Optional.ofNullable(null);
        System.out.println(myOptional.isEmpty());
        System.out.println(myOptional.isPresent());
        myOptional = Optional.ofNullable("this is a string");
        System.out.println(myOptional.isEmpty());
        System.out.println(myOptional.isPresent());
        //empty otional:
        Optional<String> empty = Optional.empty();
        System.out.println(empty.isEmpty());
        System.out.println(empty.isPresent());

        //Értek elkérése:
        System.out.println("------------------");
        String value = myOptional.get();
        System.out.println(value);

        //value = empty.get(); java.util.NoSuchElementException-t dob
        value = myOptional.orElse("other string");
        System.out.println(value);

        value = empty.orElse("other string");
        System.out.println(value);


        Optional<Integer> bestNumber = Optional.ofNullable(42);
        //Optional<Integer> bestNumber = Optional.ofNullable(null);
        System.out.println(bestNumber.orElseGet(() -> {
            Random random = new  Random();
            return random.nextInt(100);
        }));

        System.out.println(myOptional.orElseThrow(() -> new MyCustomException()));
        System.out.println(myOptional.orElseThrow(MyCustomException::new));
        //empty.orElseThrow(() -> new MyCustomException());
        bestNumber.orElseThrow(MyCustomException::new);
        System.out.println(bestNumber.map(number -> number + " is the best number").orElse("Best number not provided"));
        System.out.println(empty.map(number -> number + " is the best number").orElse("Best number not provided"));
        System.out.println("-----------------------stream-------------------------");
        List<String> texts = new ArrayList<>(){{
            add("text1");
            add("text2");
            add("text3");
        }};

        Stream<String> myStream = texts.stream();
        myStream.forEach(System.out::println);
        myStream = Stream.of("text1","text2","text3");
        myStream.forEach(text -> System.out.println(text));
        System.out.println("--------------------------------");
        myStream = Stream.of("text1","text222222222","text33333333333");
        myStream.filter(text -> text.length() > 5).forEach(text -> System.out.println(text));

        //Stream<String> myStream1 = myStream.filter(text -> text.length() > 5);
        //myStream1.forEach(text -> System.out.println(text));
        System.out.println("---------------------------------");
        myStream = Stream.of("text1","text222222222","text33333333333");
        List<String> myTexts = myStream.filter(text -> text.length() > 5).collect(Collectors.toList());
        for (String myText : myTexts) {
            System.out.println(myText);
        }

        List<Person> persons = new ArrayList<>(){{
            add(new Person("XYZAB","Harry","Potter",11));
            add(new Person("ABCDE","Bud","Spencer",80));
            add(new Person("ABBDE","Terence","Hill",40));
            add(new Person("XYZBB","Kinsey","Locke",15));
            add(new Person("YYZAB","Bode","Locke",10));
            add(new Person("YKZEB","Max","Mayfield",14));
            add(new Person("YKEEB","Nancy","Wheeler",20));
            //add(new Person("YEEEB","AA","B",20));
        }};
        System.out.println("person names:");
        persons.stream().map(person -> person.getFirstName() + " " + person.getLastName()).forEach(System.out::println);

        System.out.println("filter adults:");
        persons.stream()
                .filter(person -> person.getAge() >= 18)
                .forEach(person -> System.out.println(person.getId()));

        Stream.Builder<String> builder = Stream.builder();
        Stream<String> actorStream = builder.add("Bud").add("Terence").build();
        actorStream.forEach(System.out::println);

        System.out.println("find ABCDE:");
        String result = persons.stream()
                .filter(person -> person.getId().equals("ABCDE"))
                .findFirst()
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .orElse("not found");
        System.out.println(result);

        System.out.println("all name long?");
        boolean isAllNameLong = persons.stream()
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .allMatch(name -> name.length() >= 5);
        System.out.println(isAllNameLong);

        System.out.println("Has older then 60?");
        boolean hasOlderThenSixty = persons.stream()
                .anyMatch(person -> person.getAge() > 60);
        System.out.println(hasOlderThenSixty);

        List<Student> students = new ArrayList<>(){{
            add(new Student("XYZAB","Harry","Potter",11,1,5,3));
            add(new Student("ABCDE","Bud","Spencer",45,4,4,4));
            add(new Student("ABBDE","Terence","Hill",40,5,4,5));
            add(new Student("XYZBB","Kinsey","Locke",15,5,1,3));
            add(new Student("YYZAB","Bode","Locke",10,1,2,1));
            add(new Student("YKZEB","Max","Mayfield",14,4,4,5));
            add(new Student("YKEEB","Nancy","Wheeler",20,1,2,4));
        }};

        boolean hasFailedStudents = students.stream()
                .anyMatch(student -> student.getEnglishGrade() == 1
                        && student.getMathGrade() == 1);
        System.out.println(hasFailedStudents);

        System.out.println("person by age");
        persons.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .forEach(System.out::println);

        persons.stream()
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .map(person -> person.getFirstName() + " " + person.getLastName())
                .forEach(System.out::println);

        System.out.println("oldest person");
        persons.stream()
                .max(Comparator.comparing(Person::getAge))
                .ifPresent(person -> System.out.println(person.getId()));
        System.out.println("youngest person");
        persons.stream()
                .min(Comparator.comparing(Person::getAge))
                .ifPresent(person -> System.out.println(person.getId()));
        System.out.println("math genius with longest name");
        students.stream().filter(student -> student.getMathGrade() == 5)
                .max(Comparator.comparing(student -> student.getLastName().length()))
                .ifPresent(person -> System.out.println(person.getId()));

        System.out.println("avarage age:");
        double avarageAge = persons.stream().mapToDouble(person -> person.getAge()).average().orElse(0);
        System.out.println(avarageAge);
    }
}
