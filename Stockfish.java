import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stockfish {
    private Process engineProcess;
    private BufferedReader processReader;
    private OutputStreamWriter processWriter;

    public boolean startEngine(String path) {
        try {
            engineProcess = new ProcessBuilder(path).start();
            processReader = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
            processWriter = new OutputStreamWriter(engineProcess.getOutputStream());
            return true;
        } catch (Exception e) {
            // Now it prints the EXACT reason it failed!
            System.out.println("Stockfish Error: " + e.getMessage());
            return false;
        }
    }

    public void sendCommand(String command) {
        if (processWriter == null) return; // CRASH PREVENTION
        try {
            processWriter.write(command + "\n");
            processWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBestMove(String moveHistory, int thinkTimeMs) {
        if (processWriter == null || processReader == null) {
            System.out.println("Cannot get move: Stockfish is not running.");
            return null; // CRASH PREVENTION
        }

        sendCommand("position startpos moves " + moveHistory);
        sendCommand("go movetime " + thinkTimeMs);

        try {
            String line;
            while ((line = processReader.readLine()) != null) {
                if (line.startsWith("bestmove")) {
                    return line.split(" ")[1]; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stopEngine() {
        if (processWriter != null) {
            sendCommand("quit");
        }
    }
}