import difflib.Chunk;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Comparer {

    private final File original = new File("D:/test/original.txt");

    private final File revised = new File("D:/test/revised.txt");

    public static void main(String[] args) {

        final Comparer comparer = new Comparer();

        comparer.createDiffFile();
    }

    private void createDiffFile() {

        PrintWriter diffFile = null;
        //RandomAccessFile diffFile = null;
        RandomAccessFile oldFile = null;

        try {

            //diffFile = new RandomAccessFile(new File("./diffFile_" + System.currentTimeMillis()), "rw");
            diffFile = new PrintWriter("./diffFile_" + System.currentTimeMillis(), "UTF-8");
            oldFile = new RandomAccessFile(original, "r");

            final FileComparator comparator = new FileComparator(original, revised);

            final List<Chunk> changesFromOriginal = comparator.getChangesFromOriginal();

            final int changeNum = changesFromOriginal.size();
            System.out.println("Tamaño de cambios: " + changeNum);

            final List<Integer> changesIndex = new ArrayList<Integer>();

            for (Chunk change : changesFromOriginal) {

                changesIndex.add(change.getPosition());
            }

            String line = oldFile.readLine();
            int lineIndex = 0;
            while (line != null) {

                if (changesIndex.contains(lineIndex)) {

                    String strikeLine = "From: <strike-through color=yellow>" + line + "</strike-through>";
                    diffFile.print(strikeLine + " To: <strong>");

                    for (Object s : changesFromOriginal.get(changesIndex.indexOf(lineIndex)).getLines()) {
                        diffFile.println(s.toString());
                    }
                    diffFile.print("</strong>");

                } else {

                    diffFile.println(line);
                }

                line = oldFile.readLine();
                lineIndex++;
            }

        } catch (IOException e) {

        } finally {
            try {
                if (diffFile != null) {
                    diffFile.close();
                }

                if (oldFile != null) {
                    oldFile.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}