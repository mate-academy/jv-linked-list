package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node(last, value, null);
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The specified index does not exist");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            currentNode.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        isPresent(index);
        Node<T> currentNode = findNodeByIndex(index);
        return (T) currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        isPresent(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = (T) currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isPresent(index);
        Node currentNode = findNodeByIndex(index);
        T value = (T) currentNode.value;
        removeNode(currentNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == null && node.value == null
                    || object != null && node.value.equals(object)) {
                removeNode(node);
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
        return size == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> currentNode = head;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = last;
            for (int i = size - 2; i >= index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    public boolean isPresent(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new IndexOutOfBoundsException("The specified index does not exist");
    }

    public void removeNode(Node<T> node) {
        if (node.prev == null && node.next == null) {
            head = last = null;
        } else if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
