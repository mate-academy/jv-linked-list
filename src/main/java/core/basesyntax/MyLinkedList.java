package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(last, value, null);
            last.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new MyIndexOutOfBoundsException("Индекс выходит за границы: " + index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> step = findNode(index);

        Node<T> prev = step.getPrev();
        Node<T> newNode = new Node<>(prev, value, step);

        if (prev != null) {
            prev.setNext(newNode);
        } else {
            first = newNode; // Если вставляем в начало
        }

        step.setPrev(newNode);

        size += 1;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException("Индекс выходит за границы: ");
        }
        if (index == size - 1) {
            return last.getItem();
        }

        return findNode(index).getItem();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException("Индекс выходит за границы: " + index);
        }
        Node<T> newNode = findNode(index);
        T oldItem = newNode.getItem();
        newNode.setItem(value);
        return oldItem;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException("Ошибка");
        }
        Node<T> newNode = findNode(index);
        if (newNode == null) {
            throw new MyIndexOutOfBoundsException("Узел не найден!");
        }
        if (index == 0) {
            first = newNode.getNext();
            if (first != null) {
                first.setPrev(null);
            } else {
                last = null;
            }
        } else if (index == size - 1) {
            last = newNode.getPrev();
            if (last != null) {
                last.setNext(null);
            } else {
                first = null;
            }
        } else {
            newNode.getPrev().setNext(newNode.getNext());
            newNode.getNext().setPrev(newNode.getPrev());
        }
        size -= 1;
        return newNode.getItem();

    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = first;
        for (int i = 0; i < size; i++) {
            if ((object == null && newNode.getItem() == null)
                    || (object != null && object.equals(newNode.getItem()))) {
                if (i == 0) {
                    first = newNode.getNext();
                    if (first != null) {
                        first.setPrev(null);
                    } else {
                        last = null;
                    }
                    size -= 1;
                    return true;
                } else if (i == size - 1) {
                    last = newNode.getPrev();
                    if (last != null) {
                        last.setNext(null);
                    } else {
                        first = null;
                    }

                    size -= 1;
                    return true;
                }
                newNode.getPrev().setNext(newNode.getNext());
                newNode.getNext().setPrev(newNode.getPrev());
                size -= 1;
                return true;
            }
            newNode = newNode.getNext();
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

    private Node<T> findNode(int index) {

        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException("Индекс выходит за границы: " + index);
        }

        Node<T> newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.getNext();
        }
        return newNode;
    }
}
