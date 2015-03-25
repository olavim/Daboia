package com.github.tilastokeskus.daboia.core;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SnakeTest {
    
    private Snake snake;
    
    public SnakeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.snake = new Snake(5, 5);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constructor_shouldSetInitialPiece() {
        List<Piece> pieces = snake.getPieces();
        assertTrue(pieces.size() == 1);
        Piece p = pieces.get(0);
        assertTrue(p.x == 5 && p.y == 5);
    }

    @Test
    public void constructor_shouldSetInitialLengths() {
        assertTrue(snake.getLength() == 3);
        assertTrue(snake.getTrueLength() == 1);
    }

    @Test
    public void method_move_shouldMoveHead() {
        snake.move(Direction.UP);
        assertEquals(4, snake.getHead().y);
        snake.move(Direction.RIGHT);
        assertEquals(6, snake.getHead().x);
        snake.move(Direction.DOWN);
        assertEquals(5, snake.getHead().y);
        snake.move(Direction.LEFT);
        assertEquals(5, snake.getHead().x);
    }

    @Test
    public void method_move_shouldMoveTail() {
        assertTrue(snake.getTail().x == 5);
        snake.move(Direction.RIGHT);
        assertTrue(snake.getTail().x == 5);
        snake.move(Direction.RIGHT);
        assertTrue(snake.getTail().x == 5);
        snake.move(Direction.RIGHT);
        assertTrue(snake.getTail().x == 6);
        snake.move(Direction.RIGHT);
        assertTrue(snake.getTail().x == 7);
    }

    @Test
    public void method_collidesWith_shouldReturnTrueOnCollision() {
        assertTrue(snake.collidesWith(new Piece(5, 5)));
        snake.move(Direction.RIGHT);
        assertTrue(snake.collidesWith(new Piece(5, 5)));
        assertTrue(snake.collidesWith(new Piece(6, 5)));
        snake.move(Direction.RIGHT);
        assertTrue(snake.collidesWith(new Piece(5, 5)));
        assertTrue(snake.collidesWith(new Piece(6, 5)));
        assertTrue(snake.collidesWith(new Piece(7, 5)));
        snake.move(Direction.RIGHT);
        assertTrue(snake.collidesWith(new Piece(6, 5)));
        assertTrue(snake.collidesWith(new Piece(7, 5)));
        assertTrue(snake.collidesWith(new Piece(8, 5)));
    }

    @Test
    public void method_collidesWithPiece_shouldReturnFalseOnNoCollision() {
        for (int i = 0; i <= 10; i++)
            for (int j = 0; j <= 10; j++)
                if (i != 5 || j != 5)
                    assertFalse(snake.collidesWith(new Piece(j, i)));
    }

    @Test
    public void method_collidesWithSnake_shouldReturnTrueOnCollision() {
        Snake s = new Snake(5, 5);
        assertTrue(snake.collidesWith(s));
        snake.move(Direction.DOWN);
        s.move(Direction.RIGHT);
        assertTrue(snake.collidesWith(s));
        snake.move(Direction.DOWN);
        s.move(Direction.DOWN);
        assertTrue(snake.collidesWith(s));
        snake.move(Direction.DOWN);
        s.move(Direction.LEFT);
        assertTrue(snake.collidesWith(s));
    }

    @Test
    public void method_collidesWithSnake_shouldReturnFalseOnNoCollision() {
        Snake s = new Snake(5, 5);
        snake.move(Direction.DOWN);
        s.move(Direction.RIGHT);
        snake.move(Direction.DOWN);
        s.move(Direction.DOWN);
        snake.move(Direction.DOWN);
        s.move(Direction.DOWN);
        assertFalse(snake.collidesWith(s));
    }

    @Test
    public void method_collidesWithSnake_shouldReturnTrueOnCollisionWithSelf() {
        snake.grow();
        snake.grow();
        snake.move(Direction.RIGHT);
        snake.move(Direction.DOWN);
        snake.move(Direction.LEFT);
        snake.move(Direction.UP);
        assertTrue(snake.collidesWith(snake));
        snake.move(Direction.UP);
        assertFalse(snake.collidesWith(snake));
    }

    @Test
    public void method_copy_shouldReturnCopy() {
        Snake s = snake.copy();
        assertEquals("Pieces should be cloned", s.getHead(), snake.getHead());
        assertEquals("Pieces should be cloned", s.getTail(), snake.getTail());
        assertTrue("List of pieces should be cloned", onlyEquals(s.getPieces(), snake.getPieces()));
        assertEquals("Lengths should be same", s.getLength(), snake.getLength());
    }

    @Test (timeout = 1000)
    public void method_copy_shouldReturnCopyFast() {
        
        /* A maximum-size snake in 40x40 board */
        for (int i = 0; i < 1600; i++) {
            snake.grow();
            snake.move(Direction.RIGHT);
        }
        
        for (int i = 0; i < 10000; i++)
            snake.copy();
    }
    
    private boolean onlyEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2) && o1 != o2;
    }
}
