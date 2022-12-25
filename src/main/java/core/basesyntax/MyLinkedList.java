package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    int size;

    private static class Node<T> {
        T value;
        MyLinkedList.Node<T> next;
        MyLinkedList.Node<T> prev;
        public Node(T value){
            this.value = value;
        }
        Node(MyLinkedList.Node<T> prev, T value, MyLinkedList.Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null ) {
            first = this.last = newNode;
        }
        last.next = new Node<>(value);
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (first == null){
            first = last = newNode;
        } else if (index == 0) {
             newNode.next = first;
             first = newNode;
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i <= list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index,size);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> node = getNodeByIndex(index);
        node.value = value;
        return value;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T removedValue;
        if (index == 0) {
            removedValue = first.value;
            first = first.next;
            if (first == null){
                last = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            removedValue = prev.next.value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                last = prev;
            }
        }
         size--;
        return removedValue ;
    }

    @Override
    public boolean remove(T object) {
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
