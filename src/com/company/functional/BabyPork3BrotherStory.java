package com.company.functional;

import com.company.functional.House.BuildType;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
class Point {
    final int x;
    final int y;
}

public class BabyPork3BrotherStory implements OldStory {
    private final House house = new House();
    enum Pork {
        FIRST("첫째"), SECOND("둘째"), LAST("막내");
        Pork(String bornOrder) { } // for ease round debug
    }

    Consumer<Point> method = this::build;
    int[] dx = { -1, 1, 0, 0 };
    int[] dy = { 0, 0, 1, -1 };

    @Override
    public BabyPork3BrotherStory startReading() {
        house.initHeritage(); // 아기 돼지 삼형제가 아무것도 지어지지 않은 땅을 유산으로 받았어요.
        method.accept(new Point(0, 0));
        return this;
    }

    public House afterThan() {
        return house;
    }

    public void build(Point startPoint) {
        Deque<Pork> round = new ArrayDeque<>();
        Queue<Point> queue = new LinkedList<>();
        round.offer(Pork.FIRST); round.offer(Pork.SECOND); round.offer(Pork.LAST);

        queue.add(startPoint);

        while(!queue.isEmpty()) {
            Point point;
            point = queue.poll();
            Pork who = round.pollFirst();
            round.offer(who);

            for(int i = 0; i < 3; i++) {
                int x = point.x + dx[i];
                int y = point.y + dy[i];

                if(x >= 0 && y >= 0 && x <= house.N && y <= house.N) {
                    lookForBuildPlace(who, queue, x, y);
                }
            }
        }
    }

    public void lookForBuildPlace(Pork who, Queue<Point> queue, int x, int y) {
        for (BuildType[] row : house.ground) {
            int col = row.length;
            for (int j = 0; j < col; j++) {
                if (row[j] == BuildType.NONE) { // 아직 이 좌표에 집이 지어지지 않았으면 동작합니다.
                    queue.offer(new Point(x, y)); // 현재 돼지가 위치한 좌표를 저장하여 재활용할 수 있게 합니다.
                    switch (who) {
                        case FIRST  :    // 게으른 첫째는 3턴마다 집을 짓습니다.
                            if(j % 3 == 0)
                                row[j] = BuildType.HAY;
                                return;

                        case SECOND :     // 덜 부지런한 둘째는 2턴마다 집을 짓습니다.
                            if(j % 2 == 0)
                                row[j] = BuildType.WOOD;
                                return;

                        case LAST :     // 부지런한 셋째는 매턴마다 집을 짓습니다.
                                row[j] = BuildType.BRICK;
                                return;
                    }
                }
            }
        }
    }
}
// 1, 2, 3
class House {
    final int N = 5;
    final BuildType[][] ground = new BuildType[N][N];
    enum BuildType {
        HAY, WOOD, BRICK, NONE
    }

    void initHeritage() {
        for(BuildType[] row : ground) {
            Arrays.fill(row, BuildType.NONE);
        }
    }

    public void getResult() {
        for(BuildType[] row : ground) {
            for(BuildType col : row) {
                System.out.printf("  %5s ", col);
            }
            System.out.println();
        }
    }
}

class DailyBook {
    static OldStory story;
    public static void main(String[] args) {
        story = new BabyPork3BrotherStory();
        story.startReading()
             .afterThan()
             .getResult();
    }
}

