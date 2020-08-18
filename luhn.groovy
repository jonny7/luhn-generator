/**
 * Generate NS HCN numbers according to Luhn's algorithm
 */
class LuhnGenerator {
    /**
     * generates a random string of numbers padded with 2 leading zeros
     * @param length total length of the number
     * @return
     */
     static generateRandomStringOfNumbers(int length) {
        def randomStringOfNumbers = ""
         def removePaddingLength = length - 2

        while (randomStringOfNumbers.length() &lt; removePaddingLength) {
            def random = Math.random().toString()
            randomStringOfNumbers += random.substring(2, random.length())

            if (randomStringOfNumbers.length() > length) {
                randomStringOfNumbers = randomStringOfNumbers.substring(0, removePaddingLength)
            }
        }
        return "00" + randomStringOfNumbers
    }

    /**
     * randomly generate a NS HCN
     * @param input
     * @return
     */
    static random(int input) {
        return generate(generateRandomStringOfNumbers(input - 1))
    }

    /**
     * returns random HCN with checksum digit
     * @param randomStringOfNumbers
     * @return String
     */
    static generate(String randomStringOfNumbers) {
        return randomStringOfNumbers + checksum(randomStringOfNumbers)
    }

    static summedNumbers(String randomStringOfNumbers) {
        def specialDigits = [8, 6, 4, 2, 0]
        def summed = []
        for (int i = 0; i &lt; randomStringOfNumbers.length(); i++) {
            def context = randomStringOfNumbers.charAt(i)
            if(specialDigits.contains(i)) {
                def doubled = context.toString().toInteger() * 2
                def digitLen = doubled.toString().length()
                if (digitLen == 1) {
                    summed.add(doubled)
                } else {
                    def numberOne = doubled.toString().charAt(0).toString() as int
                    def numberTwo = doubled.toString().charAt(1).toString() as int
                    summed.add(numberOne + numberTwo)
                }
            } else {
                summed.add(context.toString() as int)
            }
        }
        return summed
    }

    /**
     * provides a checksum for Luhn's algorithm
     * @param randomStringOfNumbers
     * @return String single digit as string
     */
    static String checksum(String randomStringOfNumbers) {
        def digitSum = summedNumbers(randomStringOfNumbers).inject(0, {sum, value -> sum + value})
        return (digitSum * 9) % 10
    }
}
