class City {

    int size = 3 //  7
    Coordinate cityHall = new Coordinate(x: 2, y: 1)
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
        (0..<floorPlan.size()).each {i ->
            (0..<floorPlan[i].size()).each { j ->
                floorPlan[i][j] = '_'
            }
        }
    }

    boolean full() {
        floorPlan.flatten().contains('_')
    }

    @Override
    String toString() {
        def stringValue = "+" + ("-" * size) + "+\n"
        (1..size).each { y ->
            stringValue += "|"
            (1..size).each { x ->
                if (cityHall.isAt(x, y))
                    stringValue += "o"
                else
                    stringValue += "_"
            }
            stringValue += "|\n"
        }
        stringValue += "+" + ("-" * size) + "+"

    }
}
