package core.basesyntax;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();
       list.add("sob");
        list.add("Slon");
        list.add("Sobaka");
        list.add("Byriak_Nila",2);
        //list.add("Byriak",1);
       // System.out.println(list.get(0) +" ");
       // System.out.println(list.get(1) +" ");
        System.out.println(list.get(2) +" ");
       // System.out.println(list.get(3) +" ");
       // System.out.println(list.get(4) +" ");
        }
}
