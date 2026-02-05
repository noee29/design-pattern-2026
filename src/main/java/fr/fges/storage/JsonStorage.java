package fr.fges.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fges.model.BoardGame;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonStorage implements StorageStrategy {

    private final String file;

    public JsonStorage(String file) {
        this.file = file;
    }

    @Override
    public List<BoardGame> load() throws IOException {
        File f = new File(file);
        if (!f.exists()) return List.of();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(f, new TypeReference<>() {});
    }

    @Override
    public void save(List<BoardGame> games) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(file), games);
    }
}