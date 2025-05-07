package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> first;
    private MyNode<T> last;

    @Override
    public void add(T value) {
        MyNode<T> newNode = new MyNode<>(null, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkElementIndex(index);
        MyNode<T> current = findNodeByIndex(index);
        MyNode<T> newNode = new MyNode<>(null, value, null);
        if (current.prev == null) {
            first = newNode;
        } else {
            newNode.prev = current.prev;
            current.prev.next = newNode;
        }
        newNode.next = current;
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
        MyNode<T> node = findNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        MyNode<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        MyNode<T> node = findNodeByIndex(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> node;
        if ((node = findNodeByValue(object)) == null) {
            return false;
        }
        unlink(node);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return String.format("Index: %d, Size: %d", index, size);
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    private MyNode<T> findNodeByValue(T value) {
        MyNode<T> current = first;
        while (current != null) {
            if (value == current.item || (value != null && value.equals(current.item))) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private MyNode<T> findNodeByIndex(int index) {
        checkElementIndex(index);
        return (index < size / 2) ? findNodeByIndexFromHead(index) : findNodeByIndexFromTail(index);
    }

    private MyNode<T> findNodeByIndexFromHead(int index) {
        MyNode<T> current = first;
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                break;
            }
            currentIndex++;
            current = current.next;
        }
        return current;
    }

    private MyNode<T> findNodeByIndexFromTail(int index) {
        MyNode<T> current = last;
        int currentIndex = size - 1;
        while (current != null) {
            if (currentIndex == index) {
                break;
            }
            currentIndex--;
            current = current.prev;
        }
        return current;
    }

    private void unlink(MyNode<T> node) {
        if (node == null) {
            return;
        }
        size--;
        if (node.prev == null && node.next == null) {
            first = null;
            last = null;
            return;
        }
        if (node.prev == null) {
            first = node.next;
            node.next.prev = null;
            return;
        }
        if (node.next == null) {
            last = node.prev;
            node.prev.next = null;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private static class MyNode<T> {
        private T item;
        private MyNode<T> next;
        private MyNode<T> prev;

        MyNode(MyNode<T> prev, T element, MyNode<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
