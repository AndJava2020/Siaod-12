package com.company;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Vocabulary {
    private final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    private final Map<String, String> data = new HashMap<>();
    private final Charset charset = StandardCharsets.UTF_8;

    public void load(String fileName) throws IOException { //чтение
        try (InputStream is = new BufferedInputStream(new FileInputStream(fileName))) {
            int n = readInt(is);
            int s1;
            int s2;
            byte word[];
            byte text[];
            for (int i=0; i<n; i++) {
                word = new byte[readInt(is)];
                text = new byte[readInt(is)];
                s1 = is.read(word);
                s2 = is.read(text);
                if (s1 != word.length || s2 != text.length)
                    throw new IOException("Error read file");
                data.put(new String(word, charset), new String(text, charset));
            }
        }
    }
    public void save(String fileName) throws IOException { //запись и сохранение
        if (data.size() == 0)
            throw new IOException("Nothing to write");
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(fileName, false))) {
            writeInt(os, data.size());
            byte word[];
            byte text[];
            for(Map.Entry<String, String> entry : data.entrySet()) {
                word = entry.getKey().getBytes(charset);
                writeInt(os, word.length);
                text = entry.getValue().getBytes(charset);
                writeInt(os, text.length);
                os.write(word);
                os.write(text);
            }
        }
    }
    public void delete(String word) { //операция удаления
        data.remove(word);
    } //удаление
    public void add(String word, String text) throws Exception { //операция добавления
        if (data.get(word) != null)
            throw new Exception("Word already exist"); //если существует
        data.put(word, text);
    }
    public String find(String word) { //поиск
        String out = data.get(word);
        if (out == null)
            return "Not found";
        else
            return out;
    }
    private int readInt(InputStream in) throws IOException { //чтение
        byte out[] = new byte[4];
        int i = in.read(out);
        if (i != 4)
            throw new IOException("Error read file");
        return ByteBuffer.wrap(out).order(byteOrder).getInt();
    }
    private void writeInt(OutputStream out, int num) throws IOException { //запись
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(byteOrder).putInt(num);
        out.write(buf.array());
    }
}
