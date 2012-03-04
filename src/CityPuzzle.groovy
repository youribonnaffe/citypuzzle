class CityPuzzle {

    static void main(String[] args) {
        //blocks()
        println new City().full()
        new City().cityBlocks.each { println it.rotate()}
    }

    private static void solve(City city) {

        List<CityBlock> blocksToPlace = city.cityBlocks
        int nextBlock = 0
        while (!city.full()) {
            final block = blocksToPlace[nextBlock]
            boolean placed = city.place(block)
            CityBlock originalBlock = block
            while (!placed && originalBlock != block) {
                blocksToPlace[nextBlock] = block = block.rotate()
                placed = city.place(block)
            }

            if(!placed){
                // step back ?
            }
        }
        // take next available block
        // place it
        // if no place left, rotate it and try to place it again
        // is solved ?

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
