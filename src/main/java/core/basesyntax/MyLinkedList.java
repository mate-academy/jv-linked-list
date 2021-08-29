package core.basesyntax;

import org.w3c.dom.Node;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private boolean listIsEmpty = true;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (listIsEmpty) {
            createFirstNode(value);
        } else {
            Node<T> nextNode = new Node<>(tail, value, null);
            nextNode.prev.next = nextNode;
            tail = nextNode;
        }
    }

    @Override
    public void add(T value, int index) {
        int count = 0;
        Node<T> current = head;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index are not exist");
        } else if (isEmpty()) {
            createFirstNode(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            newNode.next.prev = newNode;
            head = newNode;
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            newNode.prev.next = newNode;
            tail = newNode;
        } else {
            while (count++ != index) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(current.prev, value, current);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        T oldValue = current.element;
        if (index == 0) {
            current.next.prev = null;
            head = current.next;
        } else if (index == (size() - 1)) {
            current.prev.next = null;
            tail = current.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size(); i++) {
            if ((getNode(i).element == null && object == null)
                    || (getNode(i).element != null
                    && getNode(i).element.equals(object))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private boolean indexIsValid(int index) {
        return index >= 0 && index < size;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index is not exist");
        }
        int count = 0;
        Node<T> current = head;
        if (index >= 0 && index <= size) {
            while (count++ != index) {
                current = current.next;
            }
        }
        return current;
    }

    private void createFirstNode(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        head = firstNode;
        tail = firstNode;
        listIsEmpty = false;
    }

    private class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
            size++;
        }
    }
}
