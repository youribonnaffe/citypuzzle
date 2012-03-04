class CityBlock {

    char[][] floorPlan = [
        ['#', '#', '#', '_'],
        ['_', '#', '#', '#'],
        ['_', '_', '#', '_'],
    ];

    CityBlock rotate() {
        def rotated = new CityBlock(floorPlan: new char[floorPlan[0].size()][floorPlan.size()])
        for (int i = 0; i < floorPlan.size(); i++) {
            for (int j = 0; j < floorPlan[i].size(); j++) {
                rotated.floorPlan[j][floorPlan.size() - 1 - i] = floorPlan[i][j] as char
            }
        }
        rotated
    }

    @Override
    String toString() {
        def stringValue = "+" + ("-" * floorPlan[0].size()) + "+\n"
        floorPlan.each() {
            stringValue += "|" + it.toString().replaceAll(",", "").replaceAll(" ", "") + "|\n"
        }
        stringValue += "+" + ("-" * floorPlan[0].size()) + "+"
    }

    @Override
    boolean equals(Object o) {
        o instanceof CityBlock && floorPlan.equals((CityBlock) o)
    }

    @Override
    int hashCode() {
        return floorPlan.hashCode()
    }
}
