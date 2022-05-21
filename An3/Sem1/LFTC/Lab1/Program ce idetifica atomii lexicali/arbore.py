class Nod:
    def __init__(self, value):
        self.__value = value
        self.__left = None
        self.__right = None
        self.__index = None
        self.__i = None

    def set_left(self, left):
        self.__left = left

    def set_right(self, right):
        self.__right = right

    def set_value(self, value):
        self.__value = value

    def set_index(self, index):
        self.__index = index

    def get_left(self):
        return self.__left

    def get_right(self):
        return self.__right

    def get_value(self):
        return self.__value

    def get_index(self):
        return self.__index


class BinarySearchTree:
    def __init__(self):
        self.__head = None

    def set_head(self, head):
        self.__head = head

    def get_head(self):
        return self.__head

    def add_node(self, node):
        if self.__head == None:
            self.__head = node
        else:
            aux = self.__head
            auxx = None
            while aux != None:
                auxx = aux
                if aux.get_value() > node.get_value():
                    aux = aux.get_left()
                elif aux.get_value() < node.get_value():
                    aux = aux.get_right()
                else:
                    break
            if auxx.get_value() > node.get_value():
                auxx.set_left(node)
            elif auxx.get_value() < node.get_value():
                auxx.set_right(node)
            else:
                pass

    def display(self):
        self.__i = 0
        self.__display(self.__head)

    def write_to_file(self):
        self.set_index()
        g = open('TS.txt', 'w')
        self.__write_to_file(self.__head, g)
        g.close()

    def set_index(self):
        self.__i = 0
        self.__set_index(self.__head)

    def __write_to_file(self, head, g):
        if head != None:
            self.__write_to_file(head.get_left(), g)
            g.write(str(head.get_value()) + " " + str(head.get_index()) + '\n')
            self.__write_to_file(head.get_right(), g)

    def __display(self, head):
        if head != None:
            self.__display(head.get_left())
            head.set_index(self.__i)
            print(head.get_value(), " ", head.get_index(), " ")
            self.__i += 1
            self.__display(head.get_right())

    def get_index_by_value(self, value):
        self.set_index()
        self.__get_inedx_by_value(self.__head, value)
        return self.__found_value

    def __get_inedx_by_value(self, head, value):
        if head != None:
            self.__get_inedx_by_value(head.get_left(), value)
            if head.get_value() == value:
                self.__found_value = head.get_index()
            self.__get_inedx_by_value(head.get_right(), value)

    def __set_index(self, head):
        if head != None:
            self.__set_index(head.get_left())
            head.set_index(self.__i)
            self.__i += 1
            self.__set_index(head.get_right())
