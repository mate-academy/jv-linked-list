package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> headPrevFirst;
    Node<T> tailNextLast;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (headPrevFirst == null) {
            headPrevFirst = tailNextLast = newNode;
        } else {
            this.tailNextLast.next = newNode;
            tailNextLast = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (headPrevFirst == null) {
            headPrevFirst = tailNextLast = newNode;
        } else if (index == 0) {
            newNode.next = headPrevFirst;
            headPrevFirst = newNode;
        } else if (index == size) {
            tailNextLast.next = newNode;
            tailNextLast = newNode;
        } else {
            Node<T> current = findNodeIndex(index - 1);
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            Node<T> newNode = new Node<>(list.get(i));
            tailNextLast.next = newNode;
            tailNextLast = newNode;
        }
        size = size + list.size();
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return findNodeIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> newNode = findNodeIndex(index);
        T element = newNode.value;
        newNode.value = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T deleteElement;
        if (index == 0) {
            deleteElement = headPrevFirst.value;
            headPrevFirst = headPrevFirst.next;
            if (headPrevFirst == null) {
                tailNextLast = null;
            }
        } else {
            Node<T> newNode = findNodeIndex(index - 1);
            deleteElement = newNode.next.value;
            newNode.next = newNode.next.next;
            if (index == size - 1) {
                tailNextLast = newNode;
            }
        }
        size--;
        return deleteElement;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexObject(object);
        if (index == 0) {
            headPrevFirst = headPrevFirst.next;
            if (headPrevFirst == null) {
                tailNextLast = null;
            }
            size--;
            return true;
        } else if (index != -1) {
            Node<T> newNode = findNodeIndex(index - 1);
            newNode.next = newNode.next.next;
            if (index == size - 1) {
                tailNextLast = newNode;
            }
            size--;
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
        return headPrevFirst == null;
    }

    public Node<T> findNodeIndex(int index) {
        Node<T> newNode = headPrevFirst;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode;
    }

    public int findIndexObject(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<T> newNode = headPrevFirst; newNode != null; newNode = newNode.next) {
                if (newNode.value == null)
                    return index;
                index++;
            }
        } else {
            for (Node<T> newNode = headPrevFirst; newNode != null; newNode = newNode.next) {
                if (o.equals(newNode.value))
                    return index;
                index++;
            }
        }
        return -1;
    }

    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
