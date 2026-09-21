package model;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {

    public record Entry(String name, int cards, int score) {}

    private static final Path FILE = Path.of("ranking.txt");

    private final List<Entry> entries = new ArrayList<>();

    public Ranking() {
        if (!Files.exists(FILE)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(FILE, StandardCharsets.UTF_8)) {
                // Name is last so it may contain the separator.
                String[] parts = line.split(";", 3);
                if (parts.length == 3) {
                    try {
                        entries.add(new Entry(parts[2], Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void add(String name, int cards, int score) {
        Entry entry = new Entry(name, cards, score);
        try {
            Files.writeString(FILE, cards + ";" + score + ";" + name + System.lineSeparator(),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        entries.add(entry);
    }

    public List<Entry> getTop(int cards, int limit) {
        return entries.stream()
                .filter(entry -> entry.cards() == cards)
                .sorted(Comparator.comparingInt(Entry::score).reversed())
                .limit(limit)
                .toList();
    }
}
