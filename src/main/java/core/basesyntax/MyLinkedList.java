package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private static final String INDEX_IS_OUT_OF_BOUNDS
            = "This index is out of bounds for this list.";
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        addInTheEnd(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addInTheEnd(value);
        } else {
            Node<T> temp = findNodeByIndex(index);
            addBefore(temp, value);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            this.add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> temp = findNodeByIndex(index);
        T returnedElement = temp.element;
        temp.element = value;
        return returnedElement;
    }

    @Override
    public T remove(int index) {
        Node<T> temp = findNodeByIndex(index);
        deleteNode(temp);
        return temp.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> temp = first;
        for (int i = 0; i < size; i++) {
            if (temp.element == t || temp.element.equals(t)) {
                deleteNode(temp);
                return true;
            }
            temp = temp.next;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_IS_OUT_OF_BOUNDS
                    + " Index: " + index + " Size: " + size);
        }
    }

    private void addBefore(Node<T> node, T value) {
        Node<T> prevNode = node.prev;
        Node<T> temp = new Node<>(value, node, prevNode);
        node.prev = temp;
        if (prevNode == null) {
            first = temp;
        } else {
            prevNode.next = temp;
        }
        size++;
    }

    private void addInTheEnd(T value) {
        Node<T> temp = last;
        last = new Node<>(value, null, temp);
        if (temp == null) {
            first = last;
        } else {
            temp.next = last;
        }
        size++;
    }

    private void deleteNode(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> temp = first;
        if (index < size >> 1) {
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp;
    }
}
