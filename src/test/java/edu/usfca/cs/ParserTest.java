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

        System.out.println((out.getSongs().get(0).getPerformer().getAttributeID()));


        }


    @Test
    void SAtoSQL() {

        Library out = new Library();
        out = test.songsFromSQL();
        Artist alicia = new Artist("Beatles");
        Album blah = new Album("Revolver");
        test.SArtoSQL("Imagine" , alicia, blah);

        Artist s = new Artist("Beatles");
        Album b = new Album("Gossamer");
        test.SArtoSQL("Gossamer" , s, b);
    }
}