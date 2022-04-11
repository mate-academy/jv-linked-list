package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private ListNode<T> first;
    private ListNode<T> last;

    private ListNode<T> findByIndex(int index) {
        if (index < size / 2) {
            ListNode<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            ListNode<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    @Override
    public void add(T value) {
        ListNode adding = new ListNode(value);
        if (size != 0) {
            adding.prev = last;
            last.next = adding;
            last = adding;
            size++;
        } else {
            first = adding;
            last = adding;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<T> valueNode = new ListNode<>(value);
        if (size == 0) {
            first = valueNode;
            last = valueNode;
        } else {
            ListNode<T> indexNode = findByIndex(index);
            if (size == index) {
                last.next = valueNode;
                valueNode.prev = last;
                last = valueNode;
            } else if (index == 0) {
                first.prev = valueNode;
                valueNode.next = first;
                first = valueNode;
            } else {
                indexNode.prev.next = valueNode;
                valueNode.prev = indexNode.prev;
                indexNode.prev = valueNode;
                valueNode.next = indexNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T i : list) {
            add(i);
        }
    }

    @Override
    public T get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ListNode<T> indexNode = findByIndex(index);
        ListNode<T> valueNode = new ListNode<>(value);
        if (indexNode == first) {
            indexNode.next.prev = valueNode;
            valueNode.next = indexNode.next;
            first = valueNode;
        } else if (indexNode == last) {
            indexNode.next.prev = valueNode;
            valueNode.prev = indexNode.prev;
            last = valueNode;
        } else {
            indexNode.next.prev = valueNode;
            indexNode.prev.next = valueNode;
            valueNode.next = indexNode.next;
            valueNode.prev = indexNode.prev;
        }
        return indexNode.value;
    }

    @Override
    public T remove(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return unlink(findByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> x = first;
        for (int i = 0; i < size;i++) {
            if (object != null && object.equals(x.value) || object == x.value) {
                unlink(x);
                return true;
            }
            x = x.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    private T unlink(ListNode<T> node) {
        T returnValue = node.value;
        if (size > 1) {
            if (node.next == null) {
                node.prev.next = null;
                last = node.prev;
            } else if (node.prev == null) {
                node.next.prev = null;
                first = node.next;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
        size--;
        return returnValue;
    }

    private class ListNode<T> {
        private T value;
        private ListNode<T> prev;
        private ListNode<T> next;

        public ListNode(T value) {
            this.value = value;
        }
    }
}
