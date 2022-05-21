package com.example.project_kotlin_ma

import android.os.Handler
import java.io.File
import java.io.FileInputStream

import kotlin.concurrent.fixedRateTimer

class CheckConnection : Thread {
    var fileRepo: FileRepository
    var fileOperationsBackup: File
    var server: ServerRepository
    var dbrepo: DBRepository
    var handler: Handler = Handler()
    var delay: Long = 5000 // 1000 milliseconds == 1 second


    constructor(
        fileRepo: FileRepository,
        fileOpBackup: File,
        server: ServerRepository,
        dbrepo: DBRepository
    ) {
        this.fileRepo = fileRepo
        this.fileOperationsBackup = fileOpBackup
        this.server = server
        this.dbrepo = dbrepo
    }

    fun readOperations(): List<Operation> {
        val inputAsString =
            FileInputStream(fileOperationsBackup).bufferedReader().use { it.readText() }
        val input = inputAsString.split("\n")
        var list = ArrayList<Operation>()
        for (i in input) {
            list.add(Operation.valueOf(i))
        }
        fileOperationsBackup.delete()
        return list

    }


    override fun run() {
        handler.postDelayed(Runnable {
//            server.getAll() {
//                if (it != null) {
//                    var listOp = readOperations()
//                    var listPinguini = fileRepo.getAll()
//                    fileRepo.emptyFile()
//
//                    for (i in 0..listOp.size) {
//                        if (Operation.ADD.equals(listOp[i])) {
//                            server.add(listPinguini!!.get(i)!!) {
//
//                            }
//                        }
//                        if (Operation.UPDATE.equals(listOp[i])) {
//                            var p = listPinguini!!.get(i)!!
//                            server.update(p.id, p) {
//
//                            }
//                        }
//                        if (Operation.DELETE.equals(listOp[i])) {
//                            var p = listPinguini!!.get(i)!!
//                            server.delete(p.id) {
//
//                            }
//                        }
//                    }
//
//                }
//            }

            for (p in dbrepo.getAll()!!) {
                dbrepo.delete(p.id)
            }

            var list: List<Pinguin>? = null
            server.getAll {
                list = it
            }

            if(list!= null)
                for (p in list!!) {
                    dbrepo.add(p)
                }
        }, delay)
        //fixedRateTimer("timer", false, 0, 5000) {

        //}
    }
}