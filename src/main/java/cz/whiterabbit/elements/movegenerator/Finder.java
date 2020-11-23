package cz.whiterabbit.elements.movegenerator;

import java.util.List;

public interface Finder {
    List<byte[]> find(int position, byte[] board);
}
