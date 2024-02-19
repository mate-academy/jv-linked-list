package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> head;
    private MyNode<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
            return;
        }
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            head.prev = new MyNode<>(null, value, head);;
            head = head.prev;
        } else {
            MyNode<T> current = getNode(index);
            MyNode<T> newNode = new MyNode<>(current.prev, value, current);
            current.prev.next = current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            addLast(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        MyNode<T> oldNode = getNode(index);
        T oldItem = oldNode.item;
        oldNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        MyNode<T> node = getNode(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (MyLinkedList.MyNode<T> node = head; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.MyNode<T> node = head; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
            }
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

    private void addFirst(T value) {
        MyNode<T> node = new MyNode<>(null, value, null);
        head = node;
        tail = node;
        size++;
    }

    private void addLast(T value) {
        MyNode<T> node = new MyNode<>(tail, value, null);
        if (node.prev == head) {
            head.next = node;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    private MyNode<T> getNode(int index) {
        checkIndex(index);
        MyNode<T> element = head;
        int count = 0;
        while (count != index && element.next != null) {
            element = element.next;
            count++;
        }
        return element;
    }

    private void unlink(MyNode<T> myNode) {
        if (myNode == head) {
            head = myNode.next;
        }
        if (myNode == tail) {
            tail = myNode.prev;
        }
        if (myNode.prev != null) {
            myNode.prev.next = myNode.next;
        }
        if (myNode.next != null) {
            myNode.next.prev = myNode.prev;
        }
        myNode.next = null;
        myNode.prev = null;
        size--;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private static class MyNode<T> {
        private T item;
        private MyNode<T> next;
        private MyNode<T> prev;

        private MyNode(MyLinkedList.MyNode<T> prev, T element, MyLinkedList.MyNode<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
