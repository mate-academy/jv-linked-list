package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    //будем сохранять ссылку на первый элемент
    private Node<T> head;
    //будем сохранять ссылку на последний элемент
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        //инициализируем при создании
        tail = new Node<>(null, null, null);
        head = new Node<>(null, null, null);
    }

    @Override
    //при вызове метода add() создаем новый нод
    // c переданным значением и его нужно прицепить к LinkedList
    public void add(T value) {
        //полюбому каждый раз у нас будет новый нод
        Node<T> node = new Node<>(null, value, null);
        //теперь этот новый узел нужно прицепить в конец LinkedList
        //установив в бывший последний элемент
        // ссылку next на наш новый нод -> node

        //если LinkedList пустой то связным ссылкам даем налл
        if (size == 0) {
            tail = head = node;//у нас внутри нода next и prev наллы
        } else {
            //если нод не первый то в бывшем последнем элементе
            //нужно засетить переменную next на новый последний элемент
            tail.next = node;
            //а нашему новому последнему ноду нужно засетить
            //значение prev на бывший последний нод, потому что
            //LinkedList двусвязный
            node.prev = tail;
            //и хвостом у нас становится наш новый элемент
            tail = node;
        }
        //элемент добавили, физический сайз +1
        size++;


    }

    @Override
    public void add(T value, int index) {

    }

    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private Node<T> findNodeByIndex(int index) {
        return new Node<>(null, null, null);
    }

    private void addFirstNode(Node<T> node) {
        head = node;
        tail = node;
        size++;
    }

    private class Node<T> {
        //переменная со ссылкой на след элемент
        private Node<T> next;
        //переменная со ссылкой на посл элемент
        private Node<T> prev;
        //переменная со значением,которое хотим сохранить
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
