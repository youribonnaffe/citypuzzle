class CityPuzzle {
    int citySize = 7
    def cityPole = [x: 0, y: 1]
    char[][][] cityBlocks = [
        [
            ['&', '&', '&', '_'],
            ['_', '&', '&', '&'],
            ['_', '&', '&', '_'],
        ],
        [
            ['$', '$', '$', '$'],
            ['_', '$', '$', '_'],
        ],
        [
            ['9', '9'],
            ['9', '9'],
            ['9', '_']
        ],
        [
            ['3', '3', '3', '3', '3'],
        ],
        [
            ['2', '2', '2', '2'],
        ],
        [
            ['7', '_'],
            ['7', '7'],
            ['7', '_']
        ],
        [
            ['1', '_'],
            ['1', '1'],
            ['1', '_']
        ],
        [
            ['8', '_'],
            ['8', '8'],
            ['8', '_']
        ],
        [
            ['6', '6'],
            ['6', '_'],
        ],
        [
            ['4', '4'],
        ],
        [
            ['5', '5'],
        ],
        [
            ['0'],
        ]
    ]

    char[][] cityPlan = new char[citySize][citySize];
    {
        for (i in (0..<citySize)) {
            for (j in (0..<citySize)) {
                cityPlan[i][j] = '_'
            }
        }
        cityPlan[cityPole.y][cityPole.x] = 'o'
    }

    void solve() {
        def tries = []
        int nextBlock = 0
        doSolve(nextBlock, tries)
    }

    void doSolve(int nextBlock, def tries) {
        def block = cityBlocks[nextBlock]

        boolean placed = place(block)
        if (!placed) {
            cityBlocks[nextBlock] = block = rotate(block)
            placed = place(block)
        }
        def original = block
        while (placed && solutionAlreadyTried(tries)) {
            placed = nextPlace(block)
            if (!placed) {
                cityBlocks[nextBlock] = block = rotate(block)
                if (block == original)
                    break
                placed = place(block)
            }
        }
        prettyPrint()

        if (placed) {
            if (solved()) {
                println "DONE !"
                return
            }
            // we found one working, next
            nextBlock++
        } else {
            tries << deepCopy(cityPlan)
            nextBlock--
            def lastBlock = cityBlocks[nextBlock]
            remove(lastBlock)
            // backtrack
        }
        if (nextBlock < 0)
            return
        doSolve(nextBlock, tries)
    }

    boolean solutionAlreadyTried(List list) {
        for (Object item : list) {
            if (Arrays.deepEquals(item, cityPlan)) {
                return true
            }
        }
        false
    }

    boolean onlyOneBlock() {
        def count = 0
        for (i in (0..<cityPlan.length)) {
            for (j in (0..<cityPlan[0].length)) {
                if (cityPlan[i][j] == '_') {
                    if ((j - 1 == 0 || cityPlan[i][j - 1] != '_') &&
                        (j + 1 == cityPlan[0].length || cityPlan[i][j + 1] != '_') &&
                        (i - 1 == 0 || cityPlan[i - 1][j] != '_') &&
                        (i + 1 == cityPlan.length || cityPlan[i + 1][j] != '_')) {
                        count++
                        if (count == 2) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    char[][] deepCopy(char[][] chars) {
        char[][] newChars = new char[citySize][citySize]
        for (i in (0..<citySize)) {
            for (j in (0..<citySize)) {
                newChars[i][j] = chars[i][j]
            }
        }
        newChars
    }

    boolean place(char[][] cityBlock) {
        def candidate = cityBlock
        for (it in (0..4)) {
            for (row in (0..<citySize)) {
                for (col in (0..<citySize)) {
                    if (isFree(row, col)) {
                        if (tryToPlaceCityBlock(candidate, row, col)) {
                            if (onlyOneBlock()) {
                                return true
                            } else {
                                remove(candidate)
                            }
                        }
                    }
                }
            }
            candidate = rotate(cityBlock)
        }
        false
    }

    private char[][] rotate(char[][] floorPlan) {
        def rotated = new char[floorPlan[0].size()][floorPlan.length]
        for (int i = 0; i < floorPlan.length; i++) {
            for (int j = 0; j < floorPlan[i].size(); j++) {
                rotated[j][floorPlan.length - 1 - i] = floorPlan[i][j] as char
            }
        }
        rotated
    }

    private boolean tryToPlaceCityBlock(char[][] cityBlock, int y, int x) {
        if (cityPlan.length < cityBlock.length + y
            || cityPlan[0].length < cityBlock[0].length + x) {
            return false
        }

        boolean placed = true
        for (row in (0..<cityBlock.length)) {
            for (col in (0..<cityBlock[0].length)) {
                if (cityBlock[row][col] != '_' && cityPlan[y + row][x + col] != '_') {
                    placed = false
                }
            }
        }
        if (placed) {
            (0..<cityBlock.length).each {row ->
                (0..<cityBlock[0].length).each { col ->
                    if (cityBlock[row][col] != '_')
                        cityPlan[y + row][x + col] = cityBlock[row][col]
                }
            }
        }
        placed
    }

    private boolean isFree(int row, int col) {
        cityPlan[row][col] == '_'
    }

    void remove(char[][] cityBlock) {
        char block = cityBlock.flatten().join().replaceAll('_', '')[0]
        for (row in (0..<citySize)) {
            for (col in (0..<citySize)) {
                if (cityPlan[row][col] == block)
                    cityPlan[row][col] = '_'
            }
        }
    }

    boolean nextPlace(char[][] cityBlock) {
        def candidate = cityBlock

        def charBlock = cityBlock.flatten().join().replaceAll('_', '')[0]
        def startRow, startCol

        for (row in (0..<citySize)) {
            for (col in (0..<citySize)) {
                if (cityPlan[row][col] == charBlock) {
                    remove(cityBlock)
                    startRow = row
                    startCol = col + 1
                }
            }
        }
        if (startCol >= citySize) {
            startCol = 0
            startRow++
        }
        if (startRow >= citySize)
            return false

        for (row in (startRow..<citySize)) {
            for (col in (startCol..<citySize)) {
                if (isFree(row, col)) {
                    if (tryToPlaceCityBlock(candidate, row, col)) {
                        if (!onlyOneBlock()) {
                            remove(candidate)
                        } else {
                            return true
                        }
                    }
                }
            }
            startCol = 0
        }
        false

    }

    boolean solved() {
        for (i in (0..<citySize)) {
            for (j in (0..<citySize)) {
                if (cityPlan[i][j] == '_') {
                    return false
                }
            }
        }
        true
        //!cityPlan.flatten().join().contains('_')
    }

    String prettyPrint() {
        def stringValue = "+" + ("-" * citySize) + "+\n"
        (0..<citySize).each { y ->
            stringValue += "|"
            (0..<citySize).each { x ->
                stringValue += cityPlan[y][x]
            }
            stringValue += "|\n"
        }
        stringValue += "+" + ("-" * citySize) + "+"

        println stringValue
    }

    static void main(String[] args) {
        def t1 = System.currentTimeMillis()
        def puzzle = new CityPuzzle()
        puzzle.solve()
        println "Took " + ((System.currentTimeMillis() - t1) / 1000) + "s"
    }

}
