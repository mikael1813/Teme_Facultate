package com.example.mdbspringboot.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.Date;

@Document(collection = "User")
public class Penguin {

    @Id
    private Integer id;
    private String nume;
    private Float inaltime;
    private Float greutate;
    private String stare;
    private Float pret;
    private String specie;
    private String dataNasterii;

    public Penguin() {

    }

    public Penguin(int id, String nume, float inaltime, float greutate, String stare, float pret, String specie, String dataNasterii) {
        this.id = id;
        this.nume = nume;
        this.inaltime = inaltime;
        this.greutate = greutate;
        this.stare = stare;
        this.pret = pret;
        this.specie = specie;
        this.dataNasterii = dataNasterii;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getInaltime() {
        return inaltime;
    }

    public void setInaltime(float inaltime) {
        this.inaltime = inaltime;
    }

    public float getGreutate() {
        return greutate;
    }

    public void setGreutate(float greutate) {
        this.greutate = greutate;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    @Override
    public String toString() {
        return "Penguin{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", inaltime=" + inaltime +
                ", greutate=" + greutate +
                ", stare='" + stare + '\'' +
                ", pret=" + pret +
                ", specie='" + specie + '\'' +
                ", dataNasterii='" + dataNasterii + '\'' +
                '}';
    }
}
