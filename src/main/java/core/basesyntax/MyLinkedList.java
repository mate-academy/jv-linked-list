package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    //будем сохранять ссылку на первый элемент
    private Node<T> head;
    //будем сохранять ссылку на последний элемент
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        //инициализируем при создании
        tail = new Node<>(null, null, null);
        head = new Node<>(null, null, null);
    }

    @Override
    //при вызове метода add() создаем новый нод
    //c переданным значением и его нужно прицепить к LinkedList
    public void add(T value) {
        //полюбому каждый раз у нас будет новый нод
        Node<T> node = new Node<>(null, value, null);
        //теперь этот новый узел нужно прицепить в конец LinkedList
        //установив в бывший последний элемент
        // ссылку next на наш новый нод -> node

        //если LinkedList пустой то связным ссылкам даем налл
        if (size == 0) {
            tail = head = node;//у нас внутри нода next и prev наллы
        } else {
            //если нод не первый то в бывшем последнем элементе
            //нужно засетить переменную next на новый последний элемент
            tail.next = node;
            //а нашему новому последнему ноду нужно засетить
            //значение prev на бывший последний нод, потому что
            //LinkedList двусвязный
            node.prev = tail;
            //и хвостом у нас становится наш новый элемент
            tail = node;
        }
        //элемент добавили, физический сайз +1
        size++;


    }

    @Override
    //тут также нужно создать новый нод,
    //но цеплять его придется в различные места,
    //а не только в конец(середина, начало, конец)
    //во всех этих ситуациях будет разная логика
    public void add(T value, int index) {
        //прежде всего нужно добавить проверку валидный ли индекс
        checkIndex(index);
        //при любом добавлении в LinkedList это по любому новый нод
        Node<T> node = new Node<>(null, value, null);
        //если LinkedList сейчас пустой, то установить значение
        //мы можем только в первый элемент
        //и тогда снова tail и head будут равны null
        if (head == null) {
            head = tail = node;
        } else if (index == 0) {
            //у нашего добавляемого нода в ссылку next
            //пишем наш предыдущий первый нод
            node.next = head;
            //теперь бывшему первому ноду в ссылку prev добавляем
            //наш новый node
            head.prev = node;
            //затем ссылке head присваиваем наш новый node
            //он стал первым в LinkedList
            tail = node;
            head = node;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            //а теперь логика как добавлять в середину LinkedList
            //нужно дойти до индекса который будет стоять ПЕРЕД местом поэтому index - 1
            //куда нам нужно добавить новый элемент и установить
            //переопределив ссылки, разрываем цепь, вставляем элемент, соединяем.
            Node<T> current = getNodeByIndex(index - 1);
            node.next = current.next;
            node.prev = current;
            current.next = node;
            size++;
        }
        //и поскольку мы добавили элемент то


    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkEqualsIndex(index);
        //у найденного значения по индексу вытягиваем value
        return getNodeByIndex(index).value;
    }

    @Override
    //что бы в LinkedList сменить элемент по индексу
    //нужно только сменить значение, но нам нужно дойти до этого элемента
    public T set(T value, int index) {
        //в сэт проверяем что бы индекс не был больше сайза
        checkEqualsIndex(index);
        //достаем по индексу нужный нам элемент
        Node<T> node = getNodeByIndex(index);
        //просто сетим наше значение что бы его вернуть
        T prevValue = node.value;
        //и меняем его значение
        node.value = value;
        return prevValue;
    }

    @Override
    public T remove(int index) {
        checkEqualsIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;

    }

    @Override
    public boolean remove(T object) {
//
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private void checkEqualsIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index" + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        //создадим референс на первый элемент с какого стартуем
        Node<T> current = head;
        //для того что бы дойти до нужного элемента юзай fori до index
        for (int i = 0; i < index; i++) { //до элемента, который стоит перед местом куда будем вставлять
            //на каждой итерации будем перепрыгивать наши ноды и дойдем до нашего нода
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == head && node == tail) {
            tail = head = null;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;


//        //ecли индексы первого или последнего значения, то нужно обработать
//        T removedValue;
//        if (index == 0) {
//            //сэтим наше первое значение, которую будем удалять
//            removedValue = head.value;
//            //присваиваем хеду новый первый елемент, потому что только что удалил первый
//            head = head.next;
//            //мы удалили элемент и чистим ссылку хвост, что б не указывала на удаленный елемент
//            if (head == null) {
//                tail = null;
//            }
//        } else {
//            //но если у нас был только один елемент в списке
//
//            //prev этот элемент который стоит перед тем, который мы хотим удалить
//            Node<T> prev = getNodeByIndex(index - 1);
//            //который стоит после удаляемого
//            Node<T> next = getNodeByIndex(index + 1);
//            //сохраняем елемент, что бы его вернуть
//            removedValue = prev.value;
//            //в его поле next переменной prev хранится наш нод, который хотим удалить
//            //а у удаляемого сохраняется референс на еще следующий
//            //на то и выходит, что в prev.next сетим значение аж на нод через один удаляемый нод
//            prev.next = prev.next.next;
//            //то же делаем для следующего нода после удаляемого
//            next.prev = next.prev.prev;
//            //если мы удаляем последний, то обращаемся к предпоследнему елементу и ставим его хвостом
//            if (index == size - 1) {
//                tail = prev;
//            }
       // }
//
//        size--;
//        return removedValue;
    }

    private class Node<T> {
        //переменная со ссылкой на след элемент
        private Node<T> next;
        //переменная со ссылкой на посл элемент
        private Node<T> prev;
        //переменная со значением, которое хотим сохранить
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
