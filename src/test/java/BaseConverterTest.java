import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class BaseConverterTest {




    @ParameterizedTest(name = "Convert value from binary to Base(8,10,16)")
    @CsvSource({
            "101,2,8,5",
            "111,2,8,7",
            "1010,2,8,12",
            "101,2,10,5",
            "111,2,10,7",
            "1010,2,10,10",
            "101,2,16,5",
            "111,2,16,7",
            "1010,2,16,a",

            "-101,2,8,-5",
            "-111,2,8,-7",
            "-1010,2,8,-12",
            "-101,2,10,-5",
            "-111,2,10,-7",
            "-1010,2,10,-10",
            "-101,2,16,-5",
            "-111,2,16,-7",
            "-1010,2,16,-a"

    })
    void testConvertBinary(String binary, int fromBase, int toBase, String expected) {
        assertEquals(expected, BaseConverter.convert(binary, fromBase, toBase));
    }


    @ParameterizedTest(name = "Convert value from octal to Base(2,10,16)")
    @CsvSource({
            "5,8,2,101",
            "7,8,2,111",
            "12,8,2,1010",
            "5,8,10,5",
            "7,8,10,7",
            "12,8,10,10",
            "5,8,16,5",
            "7,8,16,7",
            "12,8,16,a",

            "-5,8,2,-101",
            "-7,8,2,-111",
            "-12,8,2,-1010",
            "-5,8,10,-5",
            "-7,8,10,-7",
            "-12,8,10,-10",
            "-5,8,16,-5",
            "-7,8,16,-7",
            "-12,8,16,-a"
    })
    void testConverterOctal(String input, int inputBase, int outputBase, String expectedOutput) {
        assertEquals(expectedOutput, BaseConverter.convert(input, inputBase, outputBase));
    }

    @ParameterizedTest(name = "Convert value from decimal to Base(2,8,16)")
    @CsvSource({
            "5,10,2,101",
            "7,10,2,111",
            "10,10,2,1010",
            "5,10,8,5",
            "7,10,8,7",
            "10,10,8,12",
            "5,10,16,5",
            "7,10,16,7",
            "10,10,16,a",

            "-5,10,2,-101",
            "-7,10,2,-111",
            "-10,10,2,-1010",
            "-5,10,8,-5",
            "-7,10,8,-7",
            "-10,10,8,-12",
            "-5,10,16,-5",
            "-7,10,16,-7",
            "-10,10,16,-a"
    })
    void testConverterDecimal(String input, int inputBase, int outputBase, String expected) {
        assertEquals(expected, BaseConverter.convert(input, inputBase, outputBase));
    }

    @ParameterizedTest(name = "Convert value from hexadecimal to Base (2,8,10)")
    @CsvSource({
            "5,16,2,101",
            "7,16,2,111",
            "a,16,2,1010",
            "5,16,8,5",
            "7,16,8,7",
            "a,16,8,12",
            "5,16,10,5",
            "7,16,10,7",
            "a,16,10,10",

            "-5,16,2,-101",
            "-7,16,2,-111",
            "-a,16,2,-1010",
            "-5,16,8,-5",
            "-7,16,8,-7",
            "-a,16,8,-12",
            "-5,16,10,-5",
            "-7,16,10,-7",
            "-a,16,10,-10"
    })
    void testConverterHexadecimal(String input, int inputBase, int outputBase, String expectedOutput) {
        assertEquals(expectedOutput, BaseConverter.convert(input, inputBase, outputBase));
    }


    @ParameterizedTest(name = "Convert exceeding integer values from and to different Bases")
    @CsvSource({
            "2147483647, 10, 2, 1111111111111111111111111111111",
            "2147483647, 10, 3, 12112122212110202101",
            "2147483647, 10, 4, 1333333333333333",
            "2147483647, 10, 5, 13344223434042",
            "2147483647, 10, 6, 553032005531",
            "2147483647, 10, 7, 104134211161",
            "2147483647, 10, 8, 17777777777",
            "2147483647, 10, 10, 2147483647",
            "2147483647, 10, 16, 7fffffff",
            "2147483647, 10, 36, zik0zj",

            "-2147483647, 10, 2, -1111111111111111111111111111111",
            "-2147483647, 10, 3, -12112122212110202101",
            "-2147483647, 10, 4, -1333333333333333",
            "-2147483647, 10, 5, -13344223434042",
            "-2147483647, 10, 6, -553032005531",
            "-2147483647, 10, 7, -104134211161",
            "-2147483647, 10, 8, -17777777777",
            "-2147483647, 10, 10, -2147483647",
            "-2147483647, 10, 16, -7fffffff",
            "-2147483647, 10, 36, -zik0zj",
    })
    void testConvertBase(String number, int fromBase, int toBase, String expected) {
        assertEquals(expected, BaseConverter.convert(number, fromBase, toBase));
    }
    String gigChadInteger = String.valueOf(Integer.MIN_VALUE);

    @Test
    void testConvertValueExceedingIntegerToBinary() {
        //Metoda źle wykonuje podane wartości, dlatego Test jest niepoprawny. Test pokazuje, że opracowana metoda jest nie do końca dorze zoptymalizowana
        assertEquals("-10000000000000000000000000000000", BaseConverter.convert(gigChadInteger, 10, 2));
    }

    @Test
    void testConvertValueExceedingIntegerToOctal(){
        //Metoda źle wykonuje podane wartości, dlatego Test jest niepoprawny. Test pokazuje, że opracowana metoda jest nie do końca dorze zoptymalizowana
        assertEquals("-80000000", BaseConverter.convert(gigChadInteger, 10, 8));
    }

    @Test
    void testValueBiggerThanIntegerToDecimal(){
        //Metoda źle wykonuje podane wartości, dlatego Test jest niepoprawny. Test pokazuje, że opracowana metoda jest nie do końca dorze zoptymalizowana
        assertEquals("-2147483648", BaseConverter.convert(gigChadInteger, 10, 10));
    }

    @Test
    void testValueBiggerThanIntegerToHexadecimal(){
        //Metoda źle wykonuje podane wartości, dlatego Test jest niepoprawny. Test pokazuje, że opracowana metoda jest nie do końca dorze zoptymalizowana
        assertEquals("-80000000", BaseConverter.convert(gigChadInteger, 10, 16));
    }

    @Test
    void testConvertEmptyString() {
       assertThrows(IllegalArgumentException.class, () -> {
            BaseConverter.convert("", 10, 2);
        });
    }

    @Test
    void testConvertWrongBase() {
       assertThrows(IllegalArgumentException.class, () -> {
            BaseConverter.convert("123", 1, 2);
        });
    }

    @Test
    void testConvertWrongNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            BaseConverter.convert("2F", 10, 2);
        });
    }
}
