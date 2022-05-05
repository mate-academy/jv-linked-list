package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    int size;
    Node<T> head;
    Node<T> tail;
    public static class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, getNode(index));
            head = newNode;
            newNode.next.prev = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(getNode(index).prev, value, getNode(index));
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
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
        checkIndexForGet(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGet(index);
        T oldNodeValue = get(index);
        getNode(index).value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGet(index);
        T oldNodeValue = getNode(index).value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            if (index == 0) {
                head = head.next;
                head.prev = null;
            } else if (index == (size - 1)) {
                tail = tail.prev;
                tail.next = null;
            } else {
                getNode(index).prev.next = getNode(index).next;
                getNode(index).prev = getNode(index).prev.prev;
            }
        }
        size--;
        return oldNodeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> NodeToFind = head;
        int i = 0;
        while (i < size) {
            if (Objects.equals(NodeToFind.value, object)) {
                remove(i);
                return true;
            }
            NodeToFind = NodeToFind.next;
            i++;
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

    private Node<T> getNode(int index) {
        Node<T> answer = head;
        int i = 0;
        while (i != index) {
            answer = answer.next;
            i++;
        }
        return answer;
    }

    private void checkIndexForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can`t reach index " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can`t add element at index " + index
            + ", with list size " + size);
        }
    }

}
