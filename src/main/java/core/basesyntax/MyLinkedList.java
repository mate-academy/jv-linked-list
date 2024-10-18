package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int quantity = 0;
    private Node<T> node;
    private Node<T> temp;

    @Override
    public void add(T value) {
        rewindNodeToHead();
        if (quantity == 0) {
            node = new Node(null, value, null, true, true);
        } else {
            rewindToLastNode();
            node.setTail(false);
            node.setNext(new Node<>(node, value, null, false, true));
        }
        quantity += 1;
    }

    @Override
    public void add(T value, int index) {
        if (index > quantity || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        if (index == quantity) {
            add(value);
        } else {
            rewindToSelectedNode(index);
            insertNodeInCurrentPosition(index, value);
            quantity += 1;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index >= quantity || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index");
        }
        rewindNodeToHead();
        for (int i = 0; i < quantity; i++) {
            if (i != index) {
                node = node.getNext();
                continue;
            }
            temp = node;
        }
        return temp.getElement();
    }

    @Override
    public T set(T value, int index) {
        if (index >= quantity || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        rewindToSelectedNode(index);
        T res = node.getElement();
        node.setElement(value);
        return res;
    }

    @Override
    public T remove(int index) {
        if (index >= quantity || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        rewindNodeToHead();
        rewindToSelectedNode(index);
        T value = node.getElement();
        chooseConditionOfRemoving(index);
        quantity -= 1;
        return value;
    }

    @Override
    public boolean remove(T object) {
        rewindNodeToHead();
        int index = 0;
        while (node != null) {
            if (Objects.equals(node.getElement(), object)) {
                chooseConditionOfRemoving(index);
                quantity -= 1;
                return true;
            }
            node = node.getNext();
            index += 1;
        }
        return false;
    }

    @Override
    public int size() {
        return quantity;
    }

    @Override
    public boolean isEmpty() {
        return quantity == 0;
    }

    private void rewindNodeToHead() {
        if (node != null) {
            while (node.getPrev() != null) {
                node = node.getPrev();
            }
        }
    }

    private void chooseConditionOfRemoving(int index) {
        if (quantity == 1) {
            node = null;
        } else if (index == 0) {
            node = node.getNext();
            node.setPrev(null);
        } else if (index == quantity - 1) {
            node = node.getPrev();
            node.setNext(null);
        } else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
    }

    private void rewindToSelectedNode(int index) {
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
    }

    private void rewindToLastNode() {
        while (node.getNext() != null) {
            node = node.getNext();
        }
    }

    private void insertNodeInCurrentPosition(int index, T value) {
        if (index == 0) {
            node.setHead(false);
            node = new Node<>(null, value, node, true,false);
            node.getNext().setPrev(node);
        } else {
            node = new Node<>(node.getPrev(), value, node, false, false);
            node.getNext().setPrev(node);
            node.getPrev().setNext(node);
        }
    }
}
