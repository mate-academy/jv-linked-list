package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public int size;
    Node<T> first;
    Node<T> last;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;
        int index;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node newNode = new Node(value, null, null);
            newNode.index = size;
            first = newNode;
            last = newNode;
        } else {
            Node newNode = new Node(value, null, null);
            Node currentNode = first;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
            newNode.prev = currentNode;
            newNode.index = size;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkIndex(index);
        }
        if (size == 0 && index == 0) {
            Node newNode = new Node(value, null, null);
            newNode.index = size;
            first = newNode;
            last = newNode;
        } else {
            Node newNode = new Node(value, null, null);
            Node currentNode = first;
            while (currentNode.next != null && currentNode.index != index) {
                currentNode = currentNode.next;
            }
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            newNode.index = index;
            currentNode.index += 1;
            while (currentNode.next != null) {
                currentNode.next.index += 1;
                currentNode = currentNode.next;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node currentNode = first;
        while (currentNode.index != index) {
            currentNode = currentNode.next;
        }
        return (T) currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
            Node currentNode = first;
            while (currentNode.index != index) {
                currentNode = currentNode.next;
            }
            T returnValue = (T) currentNode.item;
            currentNode.item = value;
            return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node currentNode = first;
        while (currentNode.index != index) {
            currentNode = currentNode.next;
        }
        T returnValue = (T) currentNode.item;
        if (index != 0) {
            currentNode.prev.next = currentNode.next;
        }
         if (index != size - 1){
             currentNode.next.prev = currentNode.prev;
        }
         currentNode.index -= 1;
        while (currentNode.next != null) {
            currentNode.next.index -= 1;
            currentNode = currentNode.next;
        }
        size--;
        return returnValue;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
