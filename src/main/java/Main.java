import com.github.manliogit.javatags.element.Element;
import difflib.Chunk;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.manliogit.javatags.lang.HtmlHelper.*;
import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) throws IOException {

        File original = new File("D:/test/original.txt");
        File revised = new File("D:/test/revised.txt");

        FileComparator comparator = new FileComparator(original, revised);



        List<Chunk> changesFromOriginal = comparator.getChangesFromOriginal();
        for (Chunk chunk : changesFromOriginal) {
            for (int i = 0; i < chunk.size(); i++) {
                System.out.println((chunk.getPosition() + i + 1) + " " + chunk.getLines().get(i));
            }
        }

        System.out.println("=============================");

        List<Chunk> deletesFromOriginal = comparator.getDeletesFromOriginal();
        for (Chunk chunk : deletesFromOriginal) {
            for (int i = 0; i < chunk.size(); i++) {
                System.out.println((chunk.getPosition() + i + 1) + " " + chunk.getLines().get(i));
            }
        }

        System.out.println("=============================");

        List<Chunk> insertsFromOriginal = comparator.getInsertsFromOriginal();
        for (Chunk chunk : insertsFromOriginal) {
            for (int i = 0; i < chunk.size(); i++) {
                System.out.println((chunk.getPosition() + i + 1) + " " + chunk.getLines().get(i));
            }
        }



//        Element g = group();
//        for (String component : comparator.getPatchFile()) {
//            g.add(li(text(component)));
//        }



       List<Element> list = new ArrayList<Element>();
//
//        List<Integer> insertsList = new ArrayList<>();
//        List<Integer> changesList = new ArrayList<>();
//        List<Integer> deletesList = new ArrayList<>();

        int size = comparator.getPatchFile().size();
        int[] insertsArr = new int [size];
        int[] changesArr = new int [size];
        int[] deletesArr = new int [size];

        for (Chunk chunk : insertsFromOriginal) {
            for (int i = 0; i < size; i++) {
                if (i == chunk.getPosition()) {
                    for (int j = 0; j < chunk.size(); j++) {
                        insertsArr[i + j] = i + j;
                    }
                }
            }
        }

        for (Chunk chunk : changesFromOriginal) {
            for (int i = 0; i < size; i++) {
                if (i == chunk.getPosition()) {
                    for (int j = 0; j < chunk.size(); j++) {
                        changesArr[i + j] = i + j;
                    }
                }
            }
        }

        for (Chunk chunk : deletesFromOriginal) {
            for (int i = 0; i < size; i++) {
                if (i == chunk.getPosition()) {
                    for (int j = 0; j < chunk.size(); j++) {
                        deletesArr[i + j] = i + j;
                    }
                }
            }
        }
//
//        for (Chunk chunk : changesFromOriginal) {
//            for (int i = 0; i < chunk.size(); i++) {
//                Integer value = chunk.getPosition() + i;
//                changesList.add(value);
//            }
//        }
//
//        for (Chunk chunk : deletesFromOriginal) {
//            for (int i = 0; i < chunk.size(); i++) {
//                Integer value = chunk.getPosition() + i;
//                deletesList.add(value);
//            }
//        }

        for (int i = 0; i < comparator.getPatchFile().size(); i++) {

            if (i == insertsArr[i]) {
                list.add(li(attr("class -> added"), comparator.getPatchFile().get(i)));
            } else if (i == changesArr[i]) {
                list.add(li(attr("class -> changed"), comparator.getPatchFile().get(i)));
            } else if (i == deletesArr[i]) {
                list.add(li(attr("class -> deleted"), comparator.getPatchFile().get(i)));
            } else {
                list.add(li(comparator.getPatchFile().get(i)));
            }

        }


        Element element =
                ol(
                        group(list)
                );

        Layout layout = new Layout("DiffToHtml", element);

        BufferedWriter outputStream = new BufferedWriter(new FileWriter("D:/test/output.html"));
        outputStream.write(layout.build().render());
        outputStream.close();



//        for (String line : comparator.getPatchFile()) {
//            System.out.println(line);
//        }

//        List<String> lines = new ArrayList<>();
//        BufferedReader in = new BufferedReader(new FileReader(original));
//        String line;
//        while ((line = in.readLine()) != null) {
//            lines.add(line);
//        }
//
//        for (int i = 0; i < lines.size(); i++) {
//            System.out.println((i + 1) + "   " + lines.get(i));
//        }


    }

}
