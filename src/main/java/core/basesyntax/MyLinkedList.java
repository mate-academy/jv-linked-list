package core.basesyntax;
import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new NullPointerException();
            }
            current = current.next;
        }
        return current;
    }


    private int findIndexByValue(T value) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (Objects.equals(value, current.item)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return index;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
        }
        size--;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value, null, null);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size + "Index Less than 0 or Index more than size");
        }
        if (head == null) {
          head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> previousToNew = findNodeByIndex(index - 1);
            newNode.next = previousToNew.next;
            newNode.prev = previousToNew;
            previousToNew.next.prev = newNode;
            previousToNew.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = findNodeByIndex(index);
        if (current == null) {
            throw new NullPointerException();
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T list = current.item;
        current.item = value;
        return list;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = findNodeByIndex(index);
        T node = currentNode.item;
        unlink(currentNode);
        return node;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (Objects.equals(object, currentNode.item)) {
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
}