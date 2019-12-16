package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node firstNode = null;
    private Node lastNode = null;
    private int size = 0;

    @Override
    public void add(T value) {
        if (firstNode == null) {
            firstNode = lastNode = new Node(value);
        } else {
            lastNode.next = new Node(value);
            lastNode.next.previous = lastNode;
            lastNode = lastNode.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node currentNode = firstNode;
        Node newNode = new Node(value);

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == size) {
            add(value);
        } else {
            currentNode = getNodeByIndex(index);
            newNode.next = currentNode.next;
            currentNode.next = newNode;
            newNode.previous = currentNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        Node currentNode = getNodeByIndex(index);
        return currentNode == null ? null : currentNode.data;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (firstNode != null) {

            getNodeByIndex(index).data = value;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == 0) {
            if (firstNode != lastNode) {
                size--;
                firstNode.next.previous = null;
                T data = firstNode.data;
                firstNode = firstNode.next;
                return data;
            } else {
                size--;
                T data = firstNode.data;
                firstNode = lastNode = null;
                return data;
            }

        }
        if (index == size - 1 && size > 1) {
            size--;
            T data = lastNode.data;
            lastNode.previous.next = null;
            lastNode = lastNode.previous;
            return data;
        }
        int halfOfSize = size / 2;
        if (index > halfOfSize) {
            Node currentNode = lastNode;
            for (int i = size; i > 0; i--) {
                currentNode = currentNode.previous;
                if (i == index) {
                    currentNode.next.previous = currentNode.previous;
                    currentNode.previous.next = currentNode.next;
                    size--;
                    return currentNode.data;
                }
            }
        } else {
            Node currentNode = getNodeByIndex(index);
            currentNode.previous.next = currentNode.next;
            currentNode.next.previous = currentNode.previous;
            size--;
            return currentNode.data;
        }

        return null;
    }

    @Override
    public T remove(T t) {
        Node currentNode = firstNode;
        int i = 0;
        while (!Objects.equals(currentNode.data, t) && currentNode.next != null) {
            i++;
            currentNode = currentNode.next;
        }
        return remove(i);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node getNodeByIndex(int index) {
        int i = 0;
        Node currentNode = firstNode;
        while (i != index && currentNode != null) {
            i++;
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private class Node {
        T data;
        Node previous;
        Node next;

        private Node(T data) {
            this.data = data;
        }
    }
}
