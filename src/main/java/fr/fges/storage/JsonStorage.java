package fr.fges.storage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.fges.model.BoardGame;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonStorage implements StorageStrategy {

    private final String file;
    private final ObjectMapper mapper;

    public JsonStorage(String file) {
        this.file = file;
        this.mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public List<BoardGame> load() throws IOException {
        File f = new File(file);
        if (!f.exists() || f.length() == 0) return List.of();

        return mapper.readValue(f, new TypeReference<>() {});
    }

    @Override
    public void save(List<BoardGame> games) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(file), games);
    }
}