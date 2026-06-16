package BoardLayer;

public class Position {
    private int linha;
    private int coluna;

    public Position(int linha,int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha(){
        return linha;
    }

    public void setLinha(int linha){
        this.linha = linha;
    }

    public int getColuna(){
        return coluna;
    }

    public void setColuna(int coluna){
        this.coluna = coluna;
    }

    public void setValue(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public String ToString(){
        return String.format("%, %d",linha,coluna);
    }

}
