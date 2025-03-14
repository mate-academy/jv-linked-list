package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    public void checkIndex(int index, boolean isAdd) {
        if (index < 0 || index > size || (!isAdd && index == size)) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
    }

    public Node<T> getNode(int index) {
        checkIndex(index, false);

        Node<T> current;
        if (index == 0) {
            return first;
        }
        if (index == size - 1) {
            return last;
        }
        current = first;
        int i = 0;
        while (i < index) {
            current = current.getNext();
            i++;
        }
        return current;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            first = last = new Node<>(null, value, null);
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        if (index == size) {
            add(value);
            return;
        }

        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.getPrev();
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        if (prevNode != null) {
            prevNode.setNext(newNode);
        } else {
            first = newNode;
        }
        nextNode.setPrev(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> node = getNode(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.getPrev();
        Node<T> nextNode = node.getNext();

        if (prevNode == null) { // Удаляем первый элемент
            first = nextNode;
        } else {
            prevNode.setNext(nextNode);
            node.setPrev(null); // Удаляем ссылку
        }

        if (nextNode == null) { // Удаляем последний элемент
            last = prevNode;
        } else {
            nextNode.setPrev(prevNode);
            node.setNext(null); // Удаляем ссылку
        }

        size--; // Уменьшаем размер списка
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> nodeToRemove = getNode(index);
        T removedValue = nodeToRemove.getValue();

        unlink(nodeToRemove);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> current = first; current != null; current = current.getNext()) {
                if (current.getValue() == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = first; current != null; current = current.getNext()) {
                if (object.equals(current.getValue())) {
                    unlink(current);
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
        return size() == 0;
    }
}
