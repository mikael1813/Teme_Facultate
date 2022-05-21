import 'package:flutter_application_1/pinguin.dart';
import 'package:flutter_application_1/repository.dart';

class Service {
  late Repository repo;

  Service(Repository repo) {
    this.repo = repo;
  }

  Pinguin add(Pinguin pinguin) {
    int id = 0;
    bool ok = true;
    if (getPinguini() != null) {
      while (ok) {
        ok = false;
        for (var p in getPinguini()!) {
          if (p.id == id) ok = true;
        }
        if (ok) {
          id += 1;
        }
      }
    }
    pinguin.id = id;
    repo.save(pinguin);
    return pinguin;
  }

  Pinguin? getPoz(int index) {
    return repo.getPoz(index);
  }

  bool update(int id, Pinguin pinguin) {
    if (getPinguini() != null) {
      for (var p in getPinguini()!) {
        if (p.id == pinguin.id && p.id != id) {
          return false;
        }
      }
    }
    return repo.update(id, pinguin);
  }

  bool delete(int id) {
    return repo.delete(id);
  }

  Pinguin? getPinguinByID(int id) {
    return repo.getPinguinByID(id);
  }

  List<Pinguin>? getPinguini() {
    return repo.read();
  }
}
