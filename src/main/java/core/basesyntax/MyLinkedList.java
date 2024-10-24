package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private ListNode<T> first;
    private ListNode<T> last;
    private int size = 0;

    private class ListNode<T> {
        private T value;
        private ListNode<T> prev;
        private ListNode<T> next;

        ListNode(T value) {
            this.value = value;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public void add(T value) {
        ListNode<T> newNode = new ListNode<>(value);

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        ListNode<T> newNode = new ListNode<>(value);

        if (index == 0) {
            if (size == 0) {
                first = newNode;
                last = newNode;
            } else {
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            }
        } else if (index == size) {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        } else {
            ListNode<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
        }

        size++;

    }

    @Override
    public void addAll(List<T> list) {

        if (list == null || list.isEmpty()) {
            return;
        }

        for (T elements : list) {
            add(elements);
        }

    }

    @Override
    public T get(int index) {
        checkIndex(index);
        ListNode<T> current = first;

        if (current == null) {
            return null;
        } else {
            for (int i = 0; i < index; i++) {
                if (current != null) {
                    current = current.next;
                }
            }
        }

        if (current == null) {
            return null;
        }

        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        ListNode<T> current = first;

        if (first == null) {
            throw new NullPointerException("The list is empty");
        }

        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        ListNode<T> current = first;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current == first) {
            first = current.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        }

        if (current == last) {
            last = current.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
        }

        if (current.prev != null) {
            current.prev.next = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        }

        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> current = first;
        while (current != null) {
            if (current.value == null && object == null
                    || current.value != null && current.value.equals(object)) {

                if (current == first) {
                    first = current.next;
                    if (first != null) {
                        first.prev = null;
                    }
                }

                if (current == last) {
                    last = current.prev;
                    if (last != null) {
                        last.next = null;
                    }
                }
                if (current.prev != null) {
                    current.prev.next = current.next;
                }
                if (current.next != null) {
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
