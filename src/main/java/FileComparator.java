import difflib.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileComparator {

    private final File original;
    private final File revised;

    public FileComparator(File original, File revised){
        this.original = original;
        this.revised = revised;
    }

    public List<Chunk> getChangesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.DELETE);
    }

    private List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        final List<Chunk> listOfChanges = new ArrayList<>();
        final List<Delta> deltas = getDeltas();
        for (Delta delta : deltas) {
            if (delta.getType() == type) {
                if (type == Delta.TYPE.DELETE) {
                   listOfChanges.add(delta.getOriginal());
                } else {
                    listOfChanges.add(delta.getRevised());
                }
            }
        }

        return listOfChanges;
    }

    private List<Delta> getDeltas() throws IOException {
        final List<String> originalFileLines = fileToLines(original);
        final List<String> revisedFileLines = fileToLines(revised);

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        return patch.getDeltas();
    }

    private List<String> fileToLines(File file) throws IOException {
        final List<String> lines = new ArrayList<>();
        String line;
        final BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    public List<String> getPatchFile() throws IOException{
        final List<String> originalFileLines = fileToLines(original);
        final List<String> revisedFileLines = fileToLines(revised);

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        List<String> stringList = null;
        try {
            stringList = (List<String>) DiffUtils.patch(originalFileLines, patch);
        } catch (PatchFailedException e) {
            e.printStackTrace();
        }
        return stringList;
    }

}
