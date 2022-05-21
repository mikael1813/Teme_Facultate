// ignore: file_names
import 'package:flutter_application_1/pinguin.dart';
import 'package:flutter_application_1/repository.dart';
import 'package:sqflite/sqflite.dart';

class DataBaseRepo implements Repository {
  late var db;

  DataBaseRepo(var db) {
    this.db = db;
  }
  @override
  List<Pinguin>? read() {
    //final List<Map<String, dynamic>> maps = db.query('Pinguini');
    final List<Map<String, dynamic>> maps =
        db.rawQuery('SELECT * FROM Pinguini');

    // Convert the List<Map<String, dynamic> into a List<Pinguin>.
    return List.generate(maps.length, (i) {
      return Pinguin(
        maps[i]['id'],
        maps[i]['nume'],
        maps[i]['inaltime'],
        maps[i]['greutate'],
        maps[i]['stare'],
        maps[i]['pret'],
        maps[i]['specie'],
        maps[i]['dataNasterii'],
      );
    });
  }

  @override
  void save(Pinguin p) {
    db.insert(
      'Pinguini',
      p.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  @override
  Pinguin? getPoz(int index) {
    return null;
  }

  @override
  bool delete(int id) {
    return db.delete(
      'Pinguini',
      // Use a `where` clause to delete a specific Pinguin.
      where: 'id = ?',
      // Pass the Pinguin's id as a whereArg to prevent SQL injection.
      whereArgs: [id],
    );
  }

  @override
  Pinguin? getPinguinByID(int id) {
    Map result = db.rawQuery('SELECT * FROM Pinguini WHERE id=?', [id]);

    return Pinguin(
        result['id'],
        result['nume'],
        result['inaltime'],
        result['greutate'],
        result['stare'],
        result['pret'],
        result['specie'],
        result['dataNasterii']);
  }

  @override
  bool update(int id, Pinguin p) {
    return db.update(
      'Pinguini',
      p.toMap(),
      // Ensure that the Pinguin has a matching id.
      where: 'id = ?',
      // Pass the Pinguin's id as a whereArg to prevent SQL injection.
      whereArgs: [p.id],
    );
  }

  @override
  void write(List<Pinguin> list) {
    // TODO: implement write
  }
}
