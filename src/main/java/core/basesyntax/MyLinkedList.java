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
            size++;
        } else {
            lastNode.next = new Node(value);
            lastNode.next.previous = lastNode;
            lastNode = lastNode.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node currentNode = firstNode;
        Node tmp = new Node(value);

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == size) {
            add(value);
        } else {
            for (int i = 0; i < size; i++) {
                currentNode = currentNode.next;
                if (i == index) {
                    currentNode.next = tmp;
                    tmp.previous = currentNode;
                    size++;
                }
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (index == 0) {
            return firstNode.data;
        }
        int i = 0;
        Node currentNode = null;
        if (firstNode != null) {
            currentNode = firstNode;
        }
        while (i != index && currentNode != null) {
            i++;
            currentNode = currentNode.next;
        }
        return currentNode == null ? null : currentNode.data;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        if (firstNode != null) {
            //int i = 0;
            Node currentNode = firstNode;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
                i++;
            }
            currentNode.data = value;
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
            Node currentNode = firstNode;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                    size--;
                    return currentNode.data;
                }
                currentNode = currentNode.next;
            }
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

    public class Node {
        T data;
        Node previous;
        Node next;

        public Node(T data) {
            this.data = data;
        }
    }
}
