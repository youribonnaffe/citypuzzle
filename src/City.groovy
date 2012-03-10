class City {

    int size = 3 //  7
    Coordinate cityHall = new Coordinate(x: 1, y: 2)
    List<CityBlock> cityBlocks = [
        new CityBlock(floorPlan: [
            ['1', '_'],
            ['1', '1'],
            ['1', '_']
        ]),
        new CityBlock(floorPlan: [
            ['2', '2'],
        ]),
        new CityBlock(floorPlan: [
            ['3', '3'],
        ])
    ]

    char[][] floorPlan = new char[size][size];
    {
        (0..<floorPlan.length).each {i ->
            (0..<floorPlan[i].length).each { j ->
                floorPlan[i][j] = '_'
            }
        }
        floorPlan[cityHall.y - 1][cityHall.x - 1] = 'o'
    }

    boolean full() {
        !floorPlan.flatten().join().contains('_')
    }

    @Override
    String toString() {
        def stringValue = "+" + ("-" * size) + "+\n"
        (1..size).each { y ->
            stringValue += "|"
            (1..size).each { x ->
                stringValue += floorPlan[y - 1][x - 1]
            }
            stringValue += "|\n"
        }
        stringValue += "+" + ("-" * size) + "+"

    }

    boolean place(CityBlock cityBlock) {
        def candidate = cityBlock
        for (it in (0..4)) {
            for (row in (0..<size)) {
                for (col in (0..<size)) {
                    //println "row $row col $col"
                    if (isFree(row, col)) {
                        //println "isFree"
                        if (tryToPlaceCityBlock(candidate, row, col)) {
                            //  println "placed"
                            return true
                        }
                    }
                }
            }
            candidate = cityBlock.rotate()
        }
        false
    }

    private boolean tryToPlaceCityBlock(CityBlock cityBlock, int y, int x) {
        if (floorPlan.length < cityBlock.floorPlan.length + y
            || floorPlan[0].length < cityBlock.floorPlan[0].length + x) {
            return false
        }

        boolean placed = true
        for (row in (0..<cityBlock.floorPlan.length)) {
            for (col in (0..<cityBlock.floorPlan[0].length)) {
                //println "try row $row col $col"
                if (cityBlock.floorPlan[row][col] != '_' && floorPlan[y + row][x + col] != '_') {
                    placed = false
                }
            }
        }
        if (placed) {
            (0..<cityBlock.floorPlan.length).each {row ->
                (0..<cityBlock.floorPlan[0].length).each { col ->
                    if (cityBlock.floorPlan[row][col] != '_')
                        floorPlan[y + row][x + col] = cityBlock.floorPlan[row][col]
                }
            }
        }
        placed
    }

    private boolean isFree(int row, int col) {
        floorPlan[row][col] == '_'
    }

    void remove(CityBlock cityBlock) {
        char block = cityBlock.floorPlan.flatten().join().replaceAll('_','')[0]
        for (row in (0..<size)) {
            for (col in (0..<size)) {
                if (floorPlan[row][col] == block)
                    floorPlan[row][col] = '_'
            }
        }
    }

    @Override
    boolean equals(Object o) {
        o instanceof City && floorPlan.flatten() == (((City) o).floorPlan).flatten()
    }
}
