package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (tail == null) { // Список порожній
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index == size) { // Додаємо в кінець
            add(value);
            return;
        }
        if (index == 0) { // Додаємо в початок
            Node<T> newNode = new Node<>(value, null, head);
            if (head != null) {
                head.prev = newNode;
            } else {
                tail = newNode;
            }
            head = newNode;
            size++;
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) { // Знаходимо вузол за індексом
            current = current.next;
        }
        Node<T> newNode = new Node<>(value, current.prev, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        }
        current.prev = newNode;
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
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) { // Проходимо до потрібного вузла
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) { // Проходимо до потрібного вузла
            current = current.next;
        }
        T oldValue = current.value;
        current.value = value; // Змінюємо значення вузла
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        Node<T> current = head;
        if (index == 0) { // Видаляємо перший вузол
            T value = head.value;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            size--;
            return value;
        }
        if (index == size - 1) { // Видаляємо останній вузол
            T value = tail.value;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
            size--;
            return value;
        }
        for (int i = 0; i < index; i++) { // Проходимо до потрібного вузла
            current = current.next;
        }
        T value = current.value;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.value == null) ||
                    (object != null && object.equals(current.value))) {
                if (current == head) { // Видаляємо голову
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    } else {
                        tail = null;
                    }
                } else if (current == tail) { // Видаляємо хвіст
                    tail = tail.prev;
                    if (tail != null) {
                        tail.next = null;
                    } else {
                        head = null;
                    }
                } else { // Видаляємо вузол у середині
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
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
