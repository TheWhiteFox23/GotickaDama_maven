package cz.whiterabbit.elements.movegenerator;

import java.util.List;

public interface MoveFilter {

    void filter(List<byte[]> moves);
}
