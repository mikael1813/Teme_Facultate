//import 'dart:ffi';

class Pinguin {
  int id = 0;
  String nume = "";
  double inaltime = 0;
  double greutate = 0;
  Stare stare = Stare.NEADOPTAT;
  double pret = 0;
  String specie = "";
  String dataNasterii = "";

  Pinguin(int id, String nume, double inaltime, double greutate, Stare stare,
      double pret, String specie, String dataNasterii) {
    this.id = id;
    this.nume = nume;
    this.inaltime = inaltime;
    this.greutate = greutate;
    this.stare = stare;
    this.pret = pret;
    this.specie = specie;
    this.dataNasterii = dataNasterii;
  }

  factory Pinguin.fromJson(dynamic json) {
    return Pinguin(
        json['id'] as int,
        json['nume'] as String,
        json['inaltime'] as double,
        json['greutate'] as double,
        //json['stare'] as Stare,
        Stare.values.firstWhere((e) => e.toString() == json['stare']) as Stare,
        json['pret'] as double,
        json['specie'] as String,
        json['dataNasterii'] as String);
  }

  Map toJson() => {
        'id': id,
        'nume': nume,
        'inaltime': inaltime,
        'greutate': greutate,
        'stare': stare.toString(),
        'pret': pret,
        'specie': specie,
        'dataNasterii': dataNasterii,
      };

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'nume': nume,
      'inaltime': inaltime,
      'greutate': greutate,
      'stare': stare.toString(),
      'pret': pret,
      'specie': specie,
      'dataNasterii': dataNasterii,
    };
  }

  @override
  String toString() {
    // TODO: implement toString
    return super.toString();
  }
}

enum Stare { ADOPTAT, NEADOPTAT, DECEDAT }
