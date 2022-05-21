package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> first;
    private MyNode<T> last;

    @Override
    public void add(T value) {
        MyNode<T> newNode = new MyNode<>(value);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkElementIndex(index);
        MyNode<T> current = findNodeByIndex(index);
        MyNode<T> newNode = new MyNode<>(value);
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public T get(int index) {
        checkElementIndex(index);
        MyNode<T> node = findNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        MyNode<T> node = findNodeByIndex(index);
        @SuppressWarnings("ConstantConditions")
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        T oldValue = get(index);
        remove(oldValue);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> current;
        if ((current = findNodeByValue(object)) == null) {
            return false;
        }
        if (current.prev == null && current.next == null) {
            first = null;
            last = null;
        } else if (current.prev == null) {
            current.next.prev = null;
            first = current.next;
        } else if (current.next == null) {
            current.prev.next = null;
            last = current.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
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

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        MyNode<T> current = first;
        while (current != null) {
            if (current != first) {
                output.append(", ");
            }
            output.append(current.item);
            current = current.next;
        }
        return String.format("[%s] (%d)", output, size());
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

    private MyNode<T> findNodeByValue(T value) {
        MyNode<T> current = first;
        while (current != null) {
            if (Objects.equals(current.item, value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private MyNode<T> findNodeByIndex(int index) {
        MyNode<T> current = first;
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                return current;
            }
            currentIndex++;
            current = current.next;
        }
        return null;
    }
}
