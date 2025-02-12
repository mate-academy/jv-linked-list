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
            last.next = newNode;
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

        Node<T> prev = step.prev;
        Node<T> newNode = new Node<>(prev, value, step);

        if (prev != null) {
            prev.next = newNode;
        } else {
            first = newNode; // Если вставляем в начало
        }

        step.prev = newNode;

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
            return last.item;
        }

        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new MyIndexOutOfBoundsException("Индекс выходит за границы: " + index);
        }
        Node<T> newNode = findNode(index);
        T oldItem = newNode.item;
        newNode.item = value;
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
        unlink(newNode, index);
        size -= 1;
        return newNode.item;

    }

    @Override
    public boolean remove(T object) {
        Node<T> newNode = first;
        int index = 0;
        for (int i = 0; i < size; i++) {
            if ((object == null && newNode.item == null)
                    || (object != null && object.equals(newNode.item))) {
                index = i;
                unlink(newNode, index);
                size -= 1;
                return true;
            }
            newNode = newNode.next;
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

        if (size > 1 && index > size / 2) {
            Node<T> newNode = last;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
            return newNode;
        } else {
            Node<T> newNode = first;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
            return newNode;
        }

    }

    public class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

    }

    private void unlink(Node<T> newNode, int index) {
        if (index == 0) {
            first = newNode.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else if (index == size - 1) {
            last = newNode.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
        } else {
            newNode.prev.next = newNode.next;
            newNode.next.prev = newNode.prev;
        }
    }
}
