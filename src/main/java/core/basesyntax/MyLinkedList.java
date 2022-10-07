package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> first;
    private MyNode<T> last;

    private class MyNode<T> {
        private T value;
        private MyNode<T> prev;
        private MyNode<T> next;

        public MyNode(T value, MyNode<T> prev, MyNode<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        MyNode<T> newNode = new MyNode<>(value, last, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        ++size;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexAdd(index);
        MyNode<T> currentNode = getNodeByIndex(index);
        MyNode<T> newNode = new MyNode<>(value, currentNode.prev, currentNode);
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        }
        if (currentNode == first) {
            first = newNode;
        }
        currentNode.prev = newNode;
        ++size;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyNode<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        MyNode<T> currentNode = getNodeByIndex(index);
        removeNode(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> currentNode = getNodeByObject(object);
        if (currentNode != null) {
            removeNode(currentNode);
        }
        return currentNode != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private MyNode<T> getNodeByObject(T object) {
        MyNode<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == null ? currentNode.value == null : object.equals(currentNode.value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private MyNode<T> getNodeByIndex(int index) {
        if (index <= size >> 1) {
            return getNodeMovingForward(index);
        }
        return getNodeMovingBackward(index);
    }

    private MyNode<T> getNodeMovingBackward(int index) {
        MyNode<T> node = last;
        while (index++ < size - 1) {
            node = node.prev;
        }
        return node;
    }

    private MyNode<T> getNodeMovingForward(int index) {
        MyNode<T> node = first;
        while (index-- > 0) {
            node = node.next;
        }
        return node;
    }

    private void removeNode(MyNode<T> currentNode) {
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        if (currentNode == first) {
            first = currentNode.next;
        }
        if (currentNode == last) {
            last = currentNode.prev;
        }
        --size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index);
        }
    }
}
