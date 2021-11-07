package com.nikpappas.sketch;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.pgn.GameLoader;
import com.nikpappas.sketch.chess.ChessPiece;
import com.nikpappas.utils.collection.Couple;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nikpappas.sketch.chess.ChessPiece.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class ChessVisualiser extends PApplet {

    private static final String PIECES_DIR = "/Users/nikos/Projects/ProcessingSketches/assets/chess/JohnPablokCburnettChessset/PNGs/No shadow/128h/";

    public final Map<Couple<String>, ChessPiece> BOARD = new HashMap<>();
    public final Map<Character, ChessPiece> chessPieceMap = new HashMap<>();

    private final List<String> game = asList("e4", "e5", "Nf3", "d6", "d3", "Be6", "Nbd2", "Nf6", "Nb1", "Be7", "Be3", "O-O", "Nc3", "Nc6", "Nd5", "Bxd5", "exd5", "Nd4", "Nxd4", "exd4", "Bxd4", "Nxd5", "c4", "Nf4", "g3", "c5", "Bxc5", "dxc5", "gxf4", "Qa5+", "Qd2", "Qxd2+", "Kxd2", "Bf6", "Rb1", "Rad8", "Bg2", "b6", "Rhe1", "a5", "a3", "a4", "b4", "axb3", "Rxb3", "Rd4", "Rxb6", "Rxf4", "f3", "Bh4", "Ra1", "Bf6", "Ra2", "Rf5", "Kc2", "h6", "a4", "Bd8", "Rb5", "Re8", "Rb8", "Re2+", "Kb3", "Re8", "a5", "Rf6", "a6", "Rb6+", "Rxb6", "Bxb6", "a7", "Ra8", "Ra6", "Bxa7", "f4", "Rb8+", "Kc3", "Bb6", "Kb3", "Bc7+", "Ka4", "Rb4+", "Ka3", "Bxf4", "Ra4", "Bc1+", "Ka2", "Rxa4+");
    private int index = 0;
    private boolean white = true;
    private final Board board = new Board();
    private final Game cgame = GameLoader.loadNextGame(singletonList("1. e4 e5 2. Nf3 Qf6 3. d3 h6 4. Be3 c6 5. Nc3 a5 6. Be2 Na6 7. d4 Bb4 8. dxe5 Bxc3+ 9. Bd2 Bxd2+ 10. Qxd2 Qe6 11. b3 b5 12. Qxa5 f6 13. Nd4 Qxe5 14. Nf5 Qxa1+ 15. Bd1 Qe5 16. Nd6+ Qxd6 17. O-O Qb4 18. Bh5+ Ke7 19. Qb6 Qc5 20. Qc7 Nxc7 0-1").iterator());


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(900, 900);
        for (int i = 0; i < 8; i++) {
            BOARD.put(coords2Chess(i, 1), B_POND);
            BOARD.put(coords2Chess(i, 6), W_POND);
        }
        BOARD.put(Couple.of("a", "1"), W_ROOK);
        BOARD.put(Couple.of("h", "1"), W_ROOK);
        BOARD.put(Couple.of("a", "8"), B_ROOK);
        BOARD.put(Couple.of("h", "8"), B_ROOK);
        BOARD.put(Couple.of("b", "1"), W_KNIGHT);
        BOARD.put(Couple.of("g", "1"), W_KNIGHT);
        BOARD.put(Couple.of("b", "8"), B_KNIGHT);
        BOARD.put(Couple.of("g", "8"), B_KNIGHT);
        BOARD.put(Couple.of("c", "1"), W_BISHOP);
        BOARD.put(Couple.of("f", "1"), W_BISHOP);
        BOARD.put(Couple.of("c", "8"), B_BISHOP);
        BOARD.put(Couple.of("f", "8"), B_BISHOP);
        BOARD.put(Couple.of("d", "1"), W_QUEEN);
        BOARD.put(Couple.of("e", "1"), W_KING);
        BOARD.put(Couple.of("e", "8"), B_KING);
        BOARD.put(Couple.of("d", "8"), B_QUEEN);

        chessPieceMap.put('p', B_POND);
        chessPieceMap.put('P', W_POND);
        chessPieceMap.put('q', B_QUEEN);
        chessPieceMap.put('Q', W_QUEEN);
        chessPieceMap.put('k', B_KING);
        chessPieceMap.put('K', W_KING);
        chessPieceMap.put('r', B_ROOK);
        chessPieceMap.put('R', W_ROOK);
        chessPieceMap.put('n', B_KNIGHT);
        chessPieceMap.put('N', W_KNIGHT);
        chessPieceMap.put('b', B_BISHOP);
        chessPieceMap.put('B', W_BISHOP);
    }

    @Override
    public void setup() {
        background(40, 60, 50);
        noStroke();
    }

    @Override
    public void draw() {
        drawChessboard();

        delay(20);

    }

    public void drawChessboard() {
        int squareSize = width / 8;
        boolean lastFill = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = i * squareSize;
                int y = j * squareSize;
                boolean sqareFillPredicate = (i % 2) == 0 && +(j % 2) == 0 || ((i % 2) != 0 && +(j % 2) != 0);
                if (sqareFillPredicate) {
                    fill(180);
                } else {
                    fill(50);
                }
                square(x, y, squareSize);
                if (sqareFillPredicate) {
                    fill(50);
                } else {
                    fill(180);
                }
                text(coords2Chess(i, j).toString(), x + squareSize / 2, y + squareSize / 2);
                lastFill = !lastFill;
            }
        }

//        BOARD.forEach((c, p) -> {
//            Couple<Integer> coords = chess2Cords(c);
//            drawPiece(p.getFile(), coords._1, coords._2, squareSize);
//        });
        String[] lines = board.getFen().split(" ")[0].split("/");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < 8; j++) {
                char c = lines[i].charAt(j);
                if (Character.isDigit(c)) {
                    int toJump = parseInt(c) - 1;
                    j += toJump;
                } else {
                    ChessPiece piece = chessPieceMap.get(c);
                    drawPiece(piece.getFile(), j, i, squareSize);
                }
            }
        }

    }

    public void drawPiece(String imgPath, float x, float y, float size) {

        PImage img = loadImage(PIECES_DIR + imgPath);
        image(img, x * size + size / 4, y * size + size / 4, size / 2, size / 2);

    }

    private Couple<String> coords2Chess(int i, int j) {
        return Couple.of((char) ('a' + i) + "", (8 - j) + "");
    }


    private Couple<Integer> chess2Cords(Couple<String> coords) {
        return Couple.of((coords._1.codePointAt(0) - 'a'), (8 - parseInt(coords._2)));
    }


    private void move(String move) {
        String piece = move.substring(0, 1);
        Map.Entry<Couple<String>, ChessPiece> l;
        switch (piece) {
            case "B":
                break;
            case "N":
                break;
            case "R":
                break;
            case "Q":
                ChessPiece queen = white ? W_QUEEN : B_QUEEN;
                l = BOARD.entrySet()
                        .stream()
                        .filter(x -> x.getValue().equals(queen))
                        .findFirst().orElseThrow(RuntimeException::new);
                BOARD.remove(l.getKey());
                BOARD.put(Couple.of(move.substring(1, 2), move.substring(2, 3)), queen);

                break;
            case "K":
                ChessPiece king = white ? W_KING : B_KING;
                l = BOARD.entrySet()
                        .stream()
                        .filter(x -> x.getValue().equals(king))
                        .findFirst().orElseThrow(RuntimeException::new);
                BOARD.remove(l.getKey());
                BOARD.put(Couple.of(move.substring(1, 2), move.substring(2, 3)), king);

                break;
            case "O":
                break;
            default:
                ChessPiece pond = white ? W_POND : B_POND;
                l = BOARD.entrySet()
                        .stream()
                        .filter(x -> x.getValue().equals(pond) &&
                                x.getKey()._1.equals(move.substring(0, 1)))
                        .findFirst().orElseThrow(RuntimeException::new);
                BOARD.remove(l.getKey());
                BOARD.put(Couple.of(move.substring(0, 1), move.substring(1, 2)), pond);

        }
    }


    @Override
    public void keyPressed() {
        if (key == ' ' && index < game.size()) {
            String m = game.get(index++);
            List<Move> validToMoves = board.legalMoves().stream().filter(x ->
                    m.toUpperCase().endsWith(x.getTo().name())).collect(toList());
            if (validToMoves.size() == 1) {
                board.doMove(validToMoves.get(0));
            } else {

            }
//            board.doMove(new Move())
//            move(game.get(index++));
            white = !white;
        }
    }


}