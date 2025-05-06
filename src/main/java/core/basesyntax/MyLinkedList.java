package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) { // Якщо список порожній
            head = newNode;
            tail = newNode;
        } else { // Якщо список не порожній
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index); // Метод перевірки допустимого індексу

        Node<T> newNode = new Node<>(value);

        if (index == 0) { // Додаємо на початок списку
            if (head == null) { // Якщо список порожній
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) { // Додаємо в кінець списку
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else { // Додаємо в середину списку
            Node<T> current = getNodeByIndex(index); // Отримуємо вузол на вказаному індексі
            Node<T> previous = current.prev;

            newNode.next = current;
            newNode.prev = previous;

            if (previous != null) {
                previous.next = newNode;
            }
            current.prev = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return; // Якщо список порожній або null, нічого не додаємо
        }

        for (T element : list) {
            add(element); // Додаємо кожен елемент у кінець поточного списку
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index); // Перевірка валідності індексу

        Node<T> current = head; // Починаємо з голови списку
        for (int i = 0; i < index; i++) {
            current = current.next; // Переходимо до наступного вузла
        }

        return current.value; // Повертаємо значення вузла за індексом
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index); // Перевіряємо, чи індекс валідний

        Node<T> current = head; // Починаємо з голови списку
        for (int i = 0; i < index; i++) {
            current = current.next; // Переходимо до потрібного вузла
        }

        T oldValue = current.value; // Зберігаємо попереднє значення
        current.value = value; // Замінюємо значення на нове
        return oldValue; // Повертаємо попереднє значення
    }

    @Override
    public T remove(int index) {
        validateIndex(index);// Перевірка валідності індексу

        // Якщо потрібно видалити перший елемент
        if (index == 0) {
            final T value = head.value;
            head = head.next; // Зміщаємо вказівник на перший елемент на наступний
            if (head == null) { // Якщо список став порожнім після видалення
                tail = null; // Оновлюємо також вказівник на кінець
            } else {
                head.prev = null; // Оновлюємо prev вказівник для нового першого елемента
            }
            size--;
            return value;
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        // Якщо потрібно видалити останній елемент
        if (current.next == null) {
            tail = current.prev; // Оновлюємо вказівник на кінець
        } else {
            current.next.prev = current.prev; // Оновлюємо prev вказівник наступного елемента
        }

        current.prev.next = current.next; // Оновлюємо next вказівник попереднього елемента
        size--;
        return current.value; // Повертаємо видалене значення
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false; // Якщо список порожній, повертаємо false
        }

        // Якщо потрібно видалити перший елемент
        if (head.value == null ? object == null : head.value.equals(object)) { // Обробка null
            head = head.next; // Змінюємо посилання на наступний вузол
            size--; // Зменшуємо розмір списку
            return true; // Повертаємо true, якщо елемент був видалений
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.value == null ? object == null
                    : current.next.value.equals(object)) { // Обробка null
                current.next = current.next.next; // Пропускаємо вузол, який потрібно видалити
                size--; // Зменшуємо розмір списку
                return true; // Повертаємо true, якщо елемент був видалений
            }
            current = current.next; // Переходимо до наступного вузла
        }

        return false; // Якщо елемент не знайдений, повертаємо false
    }

    @Override
    public int size() {
        return size; // повертаємо поточний розмір списку
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Якщо size == 0, список порожній, повертаємо true
    }

    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) { // Якщо індекс ближче до початку списку
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else { // Якщо індекс ближче до кінця списку
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
