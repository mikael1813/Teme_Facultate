import re


class AutomatFinit:
    def __init__(self):
        self.alfabet = []
        self.stari = []
        self.start_finale = []
        self.tranziti = []

    def read_from_keyboard(self):
        alfabet = input(
            "Introdu toate elementele din Alfabet separate de virgula. Apasa enter cand ai terminat scrierea Alfabetului \nalfabet: ")
        self.alfabet = alfabet.replace(' ', '').replace('\t', '').split(',')
        stari = input(
            "Introdu toate Starile separate prin virgula. Apasa enter cand ai terminat scrierea Starilor\nstari: ")
        self.stari = stari.replace(' ', '').replace('\t', '').split(',')
        stari_finale = input(
            "Introdu toate Starile Finale separate prin virgula. Apasa enter cand ai terminat scrierea Starilor Finale\nstari finale: ")
        self.start_finale = stari_finale.replace(' ', '').replace('\t', '').split(',')
        print(
            "Acum introdu tranzitile astfel: stare1,stare2,litera,litera,...,litera enter. Litera trebuie sa fi fost inclusa in alfabet")
        print("Stop - pentru oprire")
        i = 0
        while True:
            i = i + 1
            line = input("Tranzitia " + str(i) + " : ")
            if line.lower() == 'stop':
                break
            else:
                line = line.replace(' ', '').replace('\t', '').split(',')
                list = [line[0], line[1]]
                list2 = line
                list2.remove(list[0])
                list2.remove(list[1])
                list.append(list2)
                self.tranziti.append(list)
        print("nimic")

    def read_from_file(self, filename):
        f = open(filename, 'r')
        lines = f.readlines()
        self.alfabet = lines[0].replace('//Alfabet', '').replace(' ', '').replace('\n', '').split(',')
        self.stari = lines[1].replace('//Stari', '').replace(' ', '').replace('\n', '').split(',')
        self.start_finale = lines[2].replace('//Stari Finale', '').replace(' ', '').replace('\n', '').split(',')
        for i in range(3, len(lines)):
            list = [lines[i][0], lines[i][2]]
            list2 = lines[i].replace('\n', '').split(',')
            list2.remove(list[0])
            list2.remove(list[1])
            list.append(list2)
            self.tranziti.append(list)

    def check_sequence(self, sequence):
        if not self.check_determinism():
            print("Automatul nu este determinist")
            return False
        ultima_stare = self.stari[0]
        while len(sequence) > 0:
            ok = False
            character = sequence[0]
            for tr in self.tranziti:
                if tr[0] == ultima_stare and character in tr[2]:
                    ok = True
                    ultima_stare = tr[1]
                    sequence = sequence[1:]
                    break
            if not ok:
                return False
        if ultima_stare in self.start_finale:
            return True
        return False

    def check_determinism(self):
        for i in self.tranziti:
            for j in self.tranziti:
                if i != j and j[0] == i[0]:
                    for x in i[2]:
                        if x in j[2]:
                            return False

        return True

    def get_alfabet(self):
        return self.alfabet

    def get_stari(self):
        return self.stari

    def get_stari_finale(self):
        return self.start_finale

    def get_tranziti(self):
        return self.tranziti
