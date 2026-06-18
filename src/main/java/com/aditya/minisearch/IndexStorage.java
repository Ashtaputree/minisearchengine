package com.aditya.minisearch;
import java.io.*;

public class IndexStorage {

    public static void save(SearchIndex index) throws Exception {

        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream("search-index.dat")
                );

        out.writeObject(index);

        out.close();
    }

    public static SearchIndex load() throws Exception {

        ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream("search-index.dat")
                );

        SearchIndex index = (SearchIndex) in.readObject();

        in.close();

        return index;
    }

    public static boolean exists() {

        return new File("search-index.dat").exists();

    }
}   