package com.example.project_kotlin_ma

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class ServerStatusReceiver : BroadcastReceiver() {
    lateinit var serverRepository: ServerRepository
    lateinit var fileRepo: FileRepository
    lateinit var fileOperationsBackup: File
    lateinit var dbrepo: DBRepository
    lateinit var pinguinViewModel: PinguinViewModel

    fun set(
        server: ServerRepository,
        fileRepo: FileRepository,
        fileOperationsBackup: File,
        dbrepo: DBRepository,
        pinguinViewModel: PinguinViewModel
    ) {
        this.serverRepository = server
        this.fileOperationsBackup = fileOperationsBackup
        this.fileRepo = fileRepo
        this.dbrepo = dbrepo
        this.pinguinViewModel = pinguinViewModel
    }

    fun readOperations(): List<Operation> {
        var inputAsString: String = ""
        try {
            inputAsString =
                FileInputStream(fileOperationsBackup).bufferedReader().use { it.readText() }
        } catch (file: FileNotFoundException) {
            var x = 2
        }
        var input = ArrayList<String>()
        for (i in inputAsString.split("\n")) {
            if (i != "") {
                input.add(i)
            }
        }
        var list = ArrayList<Operation>()
        for (i in input) {
            list.add(Operation.valueOf(i))
        }
        fileOperationsBackup.delete()
        return list

    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        var output = serverRepository.getAll {
            if (it == null) {
                Toast.makeText(p0, "Fara conexiune", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(p0, "Cu conexiune", Toast.LENGTH_LONG).show()

                var listOp = readOperations()
                var listPinguini = fileRepo.getAll()
                fileRepo.emptyFile()

                for (i in 0..listOp.size - 1) {
                    if (Operation.ADD.equals(listOp[i])) {
                        serverRepository.add(listPinguini!!.get(i)!!) {

                        }
                        it.add(listPinguini!!.get(i)!!)
                    }
                    if (Operation.UPDATE.equals(listOp[i])) {
                        var p = listPinguini!!.get(i)!!
                        serverRepository.update(p.id, p) {

                        }
                        for (x in it) {
                            if (x.id == p.id) {
                                it.remove(x)
                                it.add(p)
                                break
                            }
                        }
                    }
                    if (Operation.DELETE.equals(listOp[i])) {
                        var p = listPinguini!!.get(i)!!
                        serverRepository.delete(p.id) {

                        }
                        it.remove(p)
                    }
                }

                for (p in dbrepo.getAll()!!) {
                    dbrepo.delete(p.id)
                }


                for (p in it) {
                    dbrepo.add(p)
                }
                pinguinViewModel.pinguin.value = dbrepo.getAll()

//                serverRepository.getAll {
//                    if (it != null)
//                        for (p in it!!) {
//                            dbrepo.add(p)
//
//                            pinguinViewModel.pinguin.value = it as ArrayList<Pinguin>?
//                        }
//
//                }


            }
        }
    }
}