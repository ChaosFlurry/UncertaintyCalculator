package calculations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public String getInput() {
        String input = null;
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
    
    public String getInput(String text) {
        System.out.print(text);
        return getInput();
    }
    
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
