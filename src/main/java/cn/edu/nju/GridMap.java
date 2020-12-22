package cn.edu.nju;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GridMap
{
    final double gridWidth = 115;
    final double gridHeight = 85;
    final double originX = 245;
    final double originY = 175;
    final int line = 7;
    final int col = 5;
    final double boundX = originX + line * gridWidth;
    final double boundY = originY + col * gridHeight;

    /**
     * 根据id得到生物在grid上的站位
     * 与gridIdToPos的区别在于会有一个偏移量
     */
    public Point2D gridIdToCreaturePos(int id)
    {
        int lineNo = id % line;
        int colNo = id / line;
        return new Point2D(originX + lineNo * gridWidth, originY + colNo * gridHeight - gridHeight / 2);
    }

    /**
     * 根据id得到对应gird的左上角坐标
     */
    public Point2D gridIdToPos(int id)
    {
        int lineNo = id % line;
        int colNo = id / line;
        return new Point2D(originX + lineNo * gridWidth, originY + colNo * gridHeight);
    }


    /**
     * 得到子弹的初始位置
     */
    public Point2D getBulletPos(int id, int dstId)
    {
        Direction direction = judgeDirection(id, dstId);
        final int bias = 20;
        if (direction == Direction.LEFT)
        {
            Point2D p = gridIdToPos(id);
            return new Point2D(p.getX() - 2 * bias, p.getY() + gridHeight / 2 - bias);
        }
        if (direction == Direction.RIGHT)
        {
            Point2D p = gridIdToPos(id + 1);
            return new Point2D(p.getX() + 2, p.getY() + gridHeight / 2 - bias);
        }
        if (direction == Direction.DOWN)
        {
            Point2D p = gridIdToPos(id);
            return new Point2D(p.getX() + gridWidth / 2 - bias, p.getY() - 2 * bias);
        }
        if (direction == Direction.UP)
        {
            Point2D p = gridIdToPos(id + line);
            return new Point2D(p.getX() + gridWidth / 2 - bias, p.getY() + 2);
        }
        return new Point2D(0, 0);
    }

    /**
     * 得到两个grid间的距离
     */
    public double getBulletRange(Direction direction, int range)
    {
        //TODO: revise
        if (direction == Direction.UP || direction == Direction.DOWN)
        {
            return range * gridHeight - 60;
        }
        else
        {
            return range * gridWidth - 40;
        }
    }

    public int posToGridId(double x, double y)
    {
        if (!posInGrid(x, y))
        {
            return -1;
        }
        int lineNo = (int) ((x - originX) / gridWidth);
        int colNo = (int) ((y - originY) / gridHeight);
        return getGridId(lineNo, colNo);
    }

    public int getGridId(int lineNo, int colNo)
    {
        return colNo * line + lineNo;
    }


    public boolean posInGrid(double x, double y)
    {
        return x > originX && x < boundX && y > originY && y < boundY;
    }

    public int getGridNum()
    {
        return line * col;
    }


    public boolean isGridInRange(int id, int dstId, int range)
    {
        int lineNo = id % line;
        int colNo = id / line;
        for (int i = 1; i <= range; i++)
        {
            if (lineNo - i >= 0 && getGridId(lineNo - i, colNo) == dstId)
            {
                return true;
            }
            if (lineNo + i < line && getGridId(lineNo + i, colNo) == dstId)
            {
                return true;
            }
            if (colNo - i >= 0 && getGridId(lineNo, colNo - i) == dstId)
            {
                return true;
            }
            if (colNo + i < col && getGridId(lineNo, colNo + i) == dstId)
            {
                return true;
            }
        }
        return false;
    }


    public ArrayList<Integer> getNeighborGrid(int id)
    {
        ArrayList<Integer> neighborGrids = new ArrayList<>();
        int lineNo = id % line;
        int colNo = id / line;
        if (lineNo - 1 >= 0)
        {
            neighborGrids.add(id - 1);
        }
        if (lineNo + 1 < line)
        {
            neighborGrids.add(id + 1);
        }
        if (colNo - 1 >= 0)
        {
            neighborGrids.add(id - line);
        }
        if (colNo + 1 < col)
        {
            neighborGrids.add(id + line);
        }
        return neighborGrids;
    }

    Direction judgeDirection(int id, int dstId)
    {
        int dis = dstId - id;
        if (dis > 0 && dis < line)
        {
            return Direction.RIGHT;
        }
        else if (dis >= line)
        {
            return Direction.UP;
        }
        else if (dis < 0 && dis > -line)
        {
            return Direction.LEFT;
        }
        else
        {
            return Direction.DOWN;
        }
    }

}
