package ChessLayer;

import BoardLayer.Position;

public class ChessPosition {
    private int linha;
    private char coluna;

    public ChessPosition(char coluna, int linha){
        if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8){
            throw new ChessExceptions("Erro de instancia em ChessPosition. valores validos vão de a1 á h8.");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna(){
        return coluna;
    }

    public int getLinha(){
        return linha;
    }

    protected Position toPosition(){
        return new Position(8 - linha, 'a' - coluna);
    }

    protected static ChessPosition fromPosition(Position position){
        return new ChessPosition((char)('a' + position.getColuna()), 8 - position.getLinha());
    }

    public String ToString(){
        return ""+coluna+linha;
    }
}
