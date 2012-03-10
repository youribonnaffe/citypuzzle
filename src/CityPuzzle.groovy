class CityPuzzle {

    static def solutions = []

    static void main(String[] args) {
        //blocks()
        final city = new City()
        /*city.place(city.cityBlocks[0])
        println city
        city.place(city.cityBlocks[1])
        println city
        city.place(city.cityBlocks[2].rotate())*/
        println city

        solve(city)
        println city
        //new City().cityBlocks.each { println it.rotate()}
    }

    private static void solve(City city) {

        int nextBlock = 0
        while (true) {
            CityBlock block = city.cityBlocks[nextBlock]
            boolean placed = city.place(block)
            if (!placed) {
                // backtrack
                nextBlock--
                city.remove(city.cityBlocks[nextBlock])
                city.cityBlocks[nextBlock] = city.cityBlocks[nextBlock].rotate()
            } else if (city.full() && notYetFound(city)) {
                println "found $city"
                solutions << city
                nextBlock--
                city.remove(city.cityBlocks[nextBlock])
                city.cityBlocks[nextBlock] = city.cityBlocks[nextBlock].rotate()
            } else {
                nextBlock++
            }

        }
        // take next available block
        // place it
        // if no place left, rotate it and try to place it again
        // is solved ?

    }

    static boolean notYetFound(City city) {
        return !solutions.contains(city)
    }

    private static int notPlaced(boolean placed, int nextBlock, City city, CityBlock block) {
        if (!placed) {
            nextBlock--
            placed = city.placeElseWhere(city.cityBlocks[nextBlock])
            // step back ?
            println "cant place $block"
        }
        nextBlock
    }


    private static void blocks() {
        println new CityBlock(floorPlan: [
            ['_', '_'], ['a', 'b'], ['a', 'c']
        ] as char[][])
        println new CityBlock()
        println "rotate"
        println new CityBlock().rotate()
        println "rotate"
        println new CityBlock().rotate().rotate()
        println "rotate"
        println new CityBlock().rotate().rotate().rotate()
        println "rotate"
        println new CityBlock().rotate().rotate().rotate().rotate()
    }
}
