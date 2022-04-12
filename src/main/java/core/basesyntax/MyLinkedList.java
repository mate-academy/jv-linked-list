package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private ListNode<T> first;
    private ListNode<T> last;

    @Override
    public void add(T value) {
        ListNode nodeToAdd = new ListNode(value);
        if (size != 0) {
            nodeToAdd.prev = last;
            last.next = nodeToAdd;
        } else {
            first = nodeToAdd;
        }
        last = nodeToAdd;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
        ListNode<T> valueNode = new ListNode<>(value);
        if (size == index) {
            add(value);
            return;
        }
        ListNode<T> indexNode = findByIndex(index);
        if (index == 0) {
            first.prev = valueNode;
            valueNode.next = first;
            first = valueNode;
        } else {
            indexNode.prev.next = valueNode;
            valueNode.prev = indexNode.prev;
            indexNode.prev = valueNode;
            valueNode.next = indexNode;
        }
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
        checkIndex(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        ListNode<T> indexNode = findByIndex(index);
        T returnValue = indexNode.value;
        indexNode.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> node = first;
        for (int i = 0; i < size; i++) {
            if (object != null && object.equals(node.value) || object == node.value) {
                unlink(node);
                return true;
            }
            node = node.next;
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
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
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

    private ListNode<T> findByIndex(int index) {
        if (index < size / 2) {
            ListNode<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            ListNode<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
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
