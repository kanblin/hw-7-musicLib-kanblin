package edu.usfca.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    Parser test = new Parser();

    @BeforeEach
    void setUp() {
    }

    @Test
    void xmlParser() {
    }

    @Test
    void jsonParser() {
    }

    @Test
    void songsFromSQL() {
        Library out = new Library();
        out = test.songsFromSQL();
        for (int i = 0; i < out.getSongs().size(); i++) {
            System.out.println(out.getSongs().get(i));

        }


        }


    @Test
    void SAtoSQL() {

        Library out = new Library();
        out = test.songsFromSQL();

        test.SArtoSQL("Imagine" , "Beatles", "Revolver");


        test.SArtoSQL("Gossamer" , "Passion Pit", "Gossamer");

        out = test.songsFromSQL();
        for (int i = 0; i < out.getSongs().size(); i++) {
            System.out.println(out.getSongs().get(i));

        }
    }
}