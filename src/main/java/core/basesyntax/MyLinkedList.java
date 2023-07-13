package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        //якщо це початок LinkedList`a
        if (size == 0) {
            head = tail = newNode;
            // Інакше замінити хвіст новою нодою
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't operate on index: " + index
                    + ", with size: " + size);
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            // якщо голова є - замінити її нової нодою
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            // якщо хвоста немає - призначити його
            if (tail == null) {
                tail = newNode;
            }
            // якщо це додавання в кінець:
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            // якщо це додавання в середину:
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
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
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
    public T remove(int index) {
        checkIndex(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.item;
            // якщо це єдина нода в лісті то видалити ноду повністю
            // інакше зробити наступну ноду головою
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            Node<T> nodeToRemove = prev.next;
            removedElement = nodeToRemove.item;
            prev.next = nodeToRemove.next;
            // якщо це хвіст то призначити хвостом попередню ноду
            // інакше якщо видалення з середини
            // то призначити наступній ноді свого нового сусіда <-prev
            if (nodeToRemove == tail) {
                tail = prev;
            } else {
                nodeToRemove.next.prev = prev;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == null && object == null)
                    || (current.item != null && current.item.equals(object))) {
                if (current == head) {
                    head = head.next;
                    if (head == null) {
                        tail = null;
                    } else {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't operate on index: " + index
                    + ", with size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't operate on index: " + index
                    + ", with size: " + size);
        }
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

}
