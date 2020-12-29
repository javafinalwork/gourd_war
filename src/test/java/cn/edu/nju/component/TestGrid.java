package cn.edu.nju.component;

import cn.edu.nju.constant.Constant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class TestGrid
{
    GridMap gridMap = new GridMap(245,175, 115,85, null);

    @Test
    public void testGetNeighborGrid()
    {
        ArrayList<Integer> neighbors = gridMap.getNeighborGrid(8);
        Collections.sort(neighbors);
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(1);
        ans.add(7);
        ans.add(9);
        ans.add(15);
        for (int i = 0; i < 4; i++)
        {
            assertEquals(neighbors.get(i), ans.get(i));
        }
    }

    @Test
    public void testJudgeDirection()
    {
        Direction left = gridMap.judgeDirection(8, 7);
        assertEquals(left, Direction.LEFT);
        Direction right = gridMap.judgeDirection(8, 9);
        assertEquals(right, Direction.RIGHT);
        Direction up = gridMap.judgeDirection(8, 15);
        assertEquals(up, Direction.UP);
        Direction down = gridMap.judgeDirection(8, 1);
        assertEquals(down, Direction.DOWN);
    }

    @Test
    public void testGridInRange()
    {
        assertTrue(gridMap.isGridInRange(0, 1, 1));
        assertTrue(gridMap.isGridInRange(0, 1, 2));
        assertFalse(gridMap.isGridInRange(0, 2, 1));
    }
}
