package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexPositionException(index);

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexElementException(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexElementException(index);
        Node<T> oldNode = findNodeByIndex(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexElementException(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (object == null) {
            while (current != null) {
                if (current.value == null) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
        } else {
            while (current != null) {
                if (current.value != null && current.value.equals(object)) {
                    unlink(current);
                    return true;
                }
                current = current.next;
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
        return size == 0;
    }

    private void linkLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(null, value, last);
        tail = newNode;

        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }

        size++;
    }

    private void linkBefore(T value, Node<T> current) {
        Node<T> prev = current.prev;
        Node<T> newNode = new Node<>(current, value, prev);
        current.prev = newNode;

        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }

        size++;
    }
    
    private T unlink(Node<T> current) {
        if (current == null) {
            return null;
        }

        Node<T> next = current.next;
        Node<T> prev = current.prev;

        if (next == null) {
            tail = prev;
        }

        if (prev == null) {
            head = next;
        }
        
        if (next != null && prev != null) {
            next.prev = prev;
            prev.next = next;
        }

        size--;
        return current.value;
    }
    
    private Node<T> findNodeByIndex(int index) {
        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }

    private void checkIndexPositionException(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexElementException(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> prev;

        public Node(Node<T> next, T value, Node<T> prev) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
