package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first;
            first = newNode;
            if (first.next == null) {
                last = first;
            }
        } else if (index == size) {
            last.next = newNode;
            last = newNode;;
        } else {
            Node<T> node = findNodeByIndex(index - 1);
            newNode.next = node.next;
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        MyLinkedList.Node<T> current = findNodeByIndex(index);
        T oldVal = current.value;
        current.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        return nodeByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null){
            if (currentNode.value == object || currentNode.value != null && currentNode.value.equals(object)){
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
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


    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        if (first == null){
            return null;
        }
        Node<T> currentNode = first;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;

    }

    private T unlink(Node<T> node) {
        T value = node.value;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.next = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.prev = null;
        }
        node.value = null;
        size--;
        return value;
    }

    static class Node<T> {

        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        private Node(T value) {
            this.value = value;
        }
    }
}
