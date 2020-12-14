package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node(value, tail, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) { //add on tail
            add(value);
            return;
        }

        Node<T> nodeBefore = null;
        Node<T> nodeAfter = head;

        if (index == 0) { //add on head
            Node<T> newNode = new Node(value, nodeBefore, nodeAfter);
            nodeAfter.setPrev(newNode);
            head = newNode;
            size++;
            return;
        }

        if (isIndexInvalid(index)) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < index; i++) {
            nodeAfter = nodeAfter.getNext();
        }
        nodeBefore = nodeAfter.getPrev();
        Node<T> newNode = new Node(value, nodeBefore, nodeAfter);
        nodeBefore.setNext(newNode);
        nodeAfter.setPrev(newNode);
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (isIndexInvalid(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> result = head;
        for (int i = 0; i < index; i++) {
            result = result.getNext();
        }
        return result.getValue();
    }

    @Override
    public T set(T value, int index) {
        if (isIndexInvalid(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> settedNode = head;
        for (int i = 0; i < index; i++) {
            settedNode = settedNode.getNext();
        }

        T oldValue = settedNode.getValue();
        settedNode.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (isIndexInvalid(index)) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0 && index == size - 1) { //removing last element
            size--;
            Node<T> removedNode = head;
            head = null;
            tail = null;
            return removedNode.getValue();
        }

        if (index == 0) { //removing head
            size--;
            Node<T> removedNode = head;
            Node<T> nodeAfter = removedNode.getNext();
            head = nodeAfter;
            return removedNode.getValue();
        }

        if (index == size - 1) { //removing tail
            size--;
            Node<T> removedNode = tail;
            Node<T> nodeBefore = removedNode.getPrev();
            tail = nodeBefore;
            return removedNode.getValue();
        }

        Node<T> removedNode = head; //removing other element
        for (int i = 0; i < index; i++) {
            removedNode = removedNode.getNext();
        }
        Node<T> nodeBefore = removedNode.getPrev();
        Node<T> nodeAfter = removedNode.getNext();

        nodeBefore.setNext(nodeAfter);
        nodeAfter.setPrev(nodeBefore);
        size--;
        return removedNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            T currentValue = current.getValue();
            if (currentValue == null ? currentValue == object : currentValue.equals(object)) {
                remove(i);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private boolean isIndexInvalid(int index) {
        return index < 0 || index >= size;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}

