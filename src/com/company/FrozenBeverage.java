package com.company;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@RequiredArgsConstructor
class Point {
    final int x;
    final int y;
}

public class FrozenBeverage {
    private static int[][] tray;
    private int[] dx = { 0, 1, 0, -1 }; // 12시부터 시계방향으로
    private int[] dy = { -1, 0, 1, 0 };
    private boolean[][] visited;
    static int row, col;
    private Queue<Point> queue = new LinkedList<>();
    private ArrayList<Integer> list = new ArrayList<>();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        FrozenBeverage frozenBeverage = new FrozenBeverage();
        frozenBeverage.initArray();
        frozenBeverage.solution();
    }

    public void initArray() throws IOException {
        String[] arraySize = br.readLine().split(" ");
        row = Integer.parseInt(arraySize[0]);
        col = Integer.parseInt(arraySize[1]);
        tray = new int[row][col];
        visited = new boolean[row][col];

        for(int i = 0; i < row; i++) {
            String input = br.readLine();
            for(int j = 0; j < col; j++) {
                tray[i][j] = input.charAt(j) - '0';
            }
        }
        br.close();
    }

    public void solution() {
       int trayCnt = 0;
       for(int i = 0; i < tray.length; i++) {
           for(int j = 0; j < tray[i].length; j++) {
               if(tray[i][j] == 0 && !visited[i][j]) {
                   bfs(i, j);
               }
           }
           trayCnt++;

       }
        System.out.println(trayCnt-1);
    }

    public void bfs(int i, int j) {
        int nx, ny;
        queue.offer(new Point(i, j));
        visited[i][j] = true;

        while(!queue.isEmpty()) {
            Point now;
            now = queue.poll();

            for(int h = 0; h < 4; h++) {
                nx = now.x + dx[h];
                ny = now.y + dy[h];

                if (nx >= 0 && ny >= 0 && nx <= row && ny <= col) {
                    if (tray[i][j] == 0 && !visited[i][j]) {
                        queue.offer(new Point(nx, ny));
                        visited[nx][ny] = true;
                    }
                }
            }
        }
    }

}
/*
4 5
00110
00011
11111
00000

3 4
0010
1011
1001
 */