package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node prev, E value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node node = new Node(tail, value, null);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add element with value " + value
                    + " on index " + index);
        }
        if (size == 0 && index == 0) {
            Node node = new Node(tail, value, null);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
            size++;
            return;
        }
        if (size != 0 && index == 0) {
            Node node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
            return;
        }
        if (size != 0 && index == size) {
            Node node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
            return;
        }
        Node<T> addedNode = findElementByIndex(index);
        Node<T> node = new Node<>(addedNode.prev, value, addedNode);
        addedNode.prev.next = node;
        addedNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    @Override
    public T get(int index) {
        if (size == 0 || index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Element with index " + index + " does not exist");
        }
        return findElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't set element with value " + value
                    + " on index " + index);
        }
        Node<T> replacedNode = findElementByIndex(index);
        T valueReturned = replacedNode.value;
        replacedNode.value = value;
        return valueReturned;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Element with index " + index + " does not exist");
        }
        Node<T> removedNode = findElementByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        int counter = 0;
        Node<T> elementByValue = head;
        while (!(elementByValue == tail) && !(elementByValue.value == object
                || elementByValue.value != null
                && elementByValue.value.equals(object))) {
            elementByValue = elementByValue.next;
            counter++;
        }
        if (counter < size && (elementByValue.value == object
                || elementByValue.value != null
                && elementByValue.value.equals(object))) {
            unlink(elementByValue);
            return true;
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

    private Node<T> findElementByIndex(int index) {
        Node<T> nodeValue = null;
        if (index < size / 2) {
            nodeValue = head;
            int counter = 0;
            while (counter != index) {
                nodeValue = nodeValue.next;
                counter++;
            }
        } else {
            nodeValue = tail;
            int counter = size - 1;
            while (counter != index) {
                nodeValue = nodeValue.prev;
                counter--;
            }
        }
        return nodeValue;
    }

    private void unlink(Node<T> node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        size--;
    }
}
