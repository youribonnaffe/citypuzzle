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
        def tries = []
        int nextBlock = 0
        doSolve(city, nextBlock, tries)
    }
    static void debug(def log) {
        //println log
    }
    private static void doSolve(City city, int nextBlock, List tries) {
        CityBlock block = city.cityBlocks[nextBlock]

        boolean placed = city.place(block)
        if(!placed){
            city.cityBlocks[nextBlock] = block = block.rotate()
            placed = city.place(block)
        }
        def original = block
        //println tries
        while (placed && tries.contains(city)) {
            placed = city.nextPlace(block)
            if(!placed){
                city.cityBlocks[nextBlock] = block = block.rotate()
                if(block == original)
                    break
                placed = city.place(block)
            }
            //println city
            //println tries
            debug "placed=$placed " + tries.contains(city)
        }
       // println block.code()
       // println city
        if (placed) {
            if (city.full()) {
                println "DONE !"
                return
            }
            // we found one working, next
            debug "next"
            nextBlock++
        } else {
            tries << new City(floorPlan: deepCopy(city.floorPlan))
            nextBlock--
            CityBlock lastBlock = city.cityBlocks[nextBlock]
            city.remove(lastBlock)
            // backtrack
            debug "backtrack"
            //println tries.size()
        }
        if (nextBlock < 0)
            return
        doSolve(city, nextBlock, tries)
    }

    static char[][] deepCopy(char[][] chars) {
        char[][] newChars = new char[chars.length][chars[0].length]
        for (i in (0..<chars.length)) {
            for (j in (0..<chars[0].length)) {
                newChars[i][j] = chars[i][j]
            }
        }
        newChars
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
