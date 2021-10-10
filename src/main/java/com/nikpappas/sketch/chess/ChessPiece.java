package com.nikpappas.sketch.chess;

public enum ChessPiece {
    B_BISHOP("b_bishop_png_128px.png"),
    B_KNIGHT("b_knight_png_128px.png"),
    B_KING("b_king_png_128px.png"),
    B_QUEEN("b_queen_png_128px.png"),
    B_ROOK("b_rook_png_128px.png"),
    B_POND("b_pawn_png_128px.png"),
    W_BISHOP("w_bishop_png_128px.png"),
    W_KNIGHT("w_knight_png_128px.png"),
    W_KING("w_king_png_128px.png"),
    W_QUEEN("w_queen_png_128px.png"),
    W_POND("w_pawn_png_128px.png"),
    W_ROOK("w_rook_png_128px.png"),
    ;

    private final String file;

    public String getFile() {
        return file;
    }

    ChessPiece(String file) {
        this.file = file;
    }
}
