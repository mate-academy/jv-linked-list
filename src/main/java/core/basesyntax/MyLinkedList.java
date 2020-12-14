package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node(value, tail, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
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

        if (index == 0) { //add on head
            Node<T> newNode = new Node(value, null, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }

        if (isIndexInvalid(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> nodeAfter = findNodeByIndex(index);

        Node<T> newNode = new Node(value, nodeAfter.prev, nodeAfter);
        nodeAfter.prev.next = newNode;
        nodeAfter.prev = newNode;
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (isIndexInvalid(index)) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> settedNode = findNodeByIndex(index);

        T oldValue = settedNode.value;
        settedNode.value = value;
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
            return removedNode.value;
        }

        if (index == 0) { //removing head
            size--;
            Node<T> removedNode = head;
            Node<T> nodeAfter = removedNode.next;
            head = nodeAfter;
            return removedNode.value;
        }

        if (index == size - 1) { //removing tail
            size--;
            Node<T> removedNode = tail;
            Node<T> nodeBefore = removedNode.prev;
            tail = nodeBefore;
            return removedNode.value;
        }

        Node<T> removedNode = findNodeByIndex(index); //removing other element

        removedNode.prev.next = removedNode.next;
        removedNode.next.prev = removedNode.prev;
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            T currentValue = current.value;
            if (currentValue == null ? currentValue == object : currentValue.equals(object)) {
                remove(i);
                return true;
            }
            current = current.next;
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
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
    }
}

