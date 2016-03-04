package com.globo.globonetwork.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lucas.castro on 3/4/16.
 */
public class TestUtil {

    public static String getSample(String path) {
        try {
            InputStream in = TestUtil.class.getClassLoader().getResourceAsStream(path);

            InputStreamReader is = new InputStreamReader(in);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();

            while (read != null) {
                sb.append(read);
                read = br.readLine();
            }
            return sb.toString();
        }catch (Exception e) {
            throw new RuntimeException("Error try to get file content: " + path, e);
        }
    }
}
