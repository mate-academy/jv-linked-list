package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private ListNode<T> head;
    private ListNode<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        ListNode<T> newNode = new ListNode<>(tail, value, null);

        if (tail == null) {
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
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }

        if (index == 0) {
            ListNode<T> newNode = new ListNode<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = head;
            }
        } else if (index == size) {
            ListNode<T> newNode = new ListNode<>(tail, value, null);
            if (tail != null) {
                tail.next = newNode;
            }
            tail = newNode;
        } else {
            ListNode<T> currentNode = (index < size / 2) ? head : tail;
            int i = (index < size / 2) ? 0 : size - 1;

            while (currentNode != null) {
                if (i == index) {
                    ListNode<T> newNode = new ListNode<>(currentNode.prev, value, currentNode);
                    currentNode.prev.next = newNode;
                    currentNode.prev = newNode;
                    break;
                }
                currentNode = (index < size / 2) ? currentNode.next : currentNode.prev;
                i += (index < size / 2) ? 1 : -1;
            }
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }

        ListNode<T> currentNode = (index < size / 2) ? head : tail;
        int i = (index < size / 2) ? 0 : size - 1;

        while (currentNode != null) {
            if (i == index) {
                return currentNode.value;
            }
            currentNode = (index < size / 2) ? currentNode.next : currentNode.prev;
            i += (index < size / 2) ? 1 : -1;
        }

        throw new IllegalStateException("Unexpected error in get method");
    }

    @Override
    public T set(T value, int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }

        ListNode<T> currentNode = (index < size / 2) ? head : tail;
        int i = (index < size / 2) ? 0 : size - 1;

        while (currentNode != null) {
            if (i == index) {
                T oldValue = currentNode.value;
                currentNode.value = value;
                return oldValue;
            }
            currentNode = (index < size / 2) ? currentNode.next : currentNode.prev;
            i += (index < size / 2) ? 1 : -1;
        }

        throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }

        ListNode<T> currentNode;

        if (index == 0) {
            currentNode = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            currentNode = tail;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else { // Видалення з середини
            currentNode = (index < size / 2) ? head : tail;
            int i = (index < size / 2) ? 0 : size - 1;

            while (currentNode != null) {
                if (i == index) {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                    break;
                }
                currentNode = (index < size / 2) ? currentNode.next : currentNode.prev;
                i += (index < size / 2) ? 1 : -1;
            }
        }

        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> currentNode = head;

        while (currentNode != null) {
            if (Objects.equals(object, currentNode.value)) {
                if (currentNode.prev != null) {
                    currentNode.prev.next = currentNode.next;
                } else {
                    head = currentNode.next;
                }
                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    tail = currentNode.prev;
                }
                size--;
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

    static class ListNode<T> {
        private T value;
        private ListNode<T> prev;
        private ListNode<T> next;

        public ListNode(ListNode<T> prev, T value, ListNode<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public ListNode<T> getPrev() {
            return prev;
        }

        public void setPrev(ListNode<T> prev) {
            this.prev = prev;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }
    }
}
