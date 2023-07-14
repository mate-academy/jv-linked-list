package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> next = prev.next;
            Node<T> newNode = new Node<>(prev, value, next);
            prev.next = newNode;
            next.prev = newNode;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == null && object == null)
                    || (current.item != null && current.item.equals(object))) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't operate on index: " + index
                    + ", with size: " + size);
        }
    }

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't operate on index: " + index
                    + ", with size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index <= size / 2) {
            Node<T> current = head;
            int currentIndex = 0;
            while (current != null && currentIndex < index) {
                current = current.next;
                currentIndex++;
            }
            return current;
        } else {
            Node<T> current = tail;
            int currentIndex = size - 1;
            while (current != null && currentIndex > index) {
                current = current.prev;
                currentIndex--;
            }
            return current;
        }
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            // якщо головою стало нічого то хвіст також нічого
            if (head == null) {
                tail = null;
            } else {
                // інакше засетити новій голові нульового сусіда prev
                head.prev = null;
            }
        } else if (node == tail) {
            // зробити сусіда хвоста хвостом
            tail = tail.prev;
            tail.next = null;
        } else {
            // сусіду prev призначити next сусіда від next нашої видаленої ноди
            node.prev.next = node.next;
            // сусіду next призначити prev сусіда від prev нашої видаленої ноди
            node.next.prev = node.prev;
        }
        size--;
    }
}
