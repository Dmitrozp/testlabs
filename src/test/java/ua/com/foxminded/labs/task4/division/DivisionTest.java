package ua.com.foxminded.labs.task4.division;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DivisionTest {
    private static Division division;

    @BeforeAll
    static void setup(){
        division = new Division();
    }

    @Test
    void process_ShouldThrowException_WhenInputDivisorNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ division.getDivision(1234, 0); });
    }
    @Test
    void process_ShouldThrowException_WhenInputDividendEqualMinInt() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ division.getDivision(-2147483648, 5); });
    }
    @Test
    void process_ShouldThrowException_WhenInputDivisorEqualMinInt() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ division.getDivision(483648, -2147483648); });
    }
    @Test
    void process_ShouldReturnDivision_WhenInputDividendMaxInt(){
        String expected =   "_2147483647│83647\n" +
                            " 167294    │-----\n" +
                            " ------    │25673\n" +
                            " _474543\n" +
                            "  418235\n" +
                            "  ------\n" +
                            "  _563086\n" +
                            "   501882\n" +
                            "   ------\n" +
                            "   _612044\n" +
                            "    585529\n" +
                            "    ------\n" +
                            "    _265157\n" +
                            "     250941\n" +
                            "     ------\n" +
                            "      14216\n";
        Assertions.assertEquals(expected, division.getDivision(2147483647,83647));
    }
    @Test
    void process_ShouldReturnStringResult_WhenInputDivisorMaxInt(){
        String expected =   "2147483646/2147483647=0";
        Assertions.assertEquals(expected, division.getDivision(2147483646,2147483647));
    }
    @Test
    void process_ShouldReturnStringResultNull_WhenInputDividendNull(){
        String expected = "0/1=0";
        Assertions.assertEquals(expected, division.getDivision(0,1));
    }
    @Test
    void process_ShouldReturnDivision_WhenDividendGreaterThanDivisor(){
        String expected =
                "_133│2\n" +
                " 12 │--\n" +
                " -- │66\n" +
                " _13\n" +
                "  12\n" +
                "  --\n" +
                "   1\n";
        Assertions.assertEquals(expected, division.getDivision(133,2));
    }
    @Test
    void process_ShouldReturnStringResult_WhenInputDivisorGreaterThanDividend(){
        String expected =  "4/12323=0";
        Assertions.assertEquals(expected, division.getDivision(4,12323));
    }
    @Test
    void process_ShouldReturnStringResult_WhenInputDividendEqualDivisor(){
        String expected =   "55/55=1";
        Assertions.assertEquals(expected, division.getDivision(55,55));
    }
    @Test
    void process_ShouldReturnDivision_WhenInputPositiveDividendAndNegativeDivisor(){
        String expected =   "_120000│99\n" +
                            "  99   │----\n" +
                            " ---   │1212\n" +
                            " _210\n" +
                            "  198\n" +
                            "  ---\n" +
                            "  _120\n" +
                            "    99\n" +
                            "   ---\n" +
                            "   _210\n" +
                            "    198\n" +
                            "    ---\n" +
                            "     12\n";
        Assertions.assertEquals(expected, division.getDivision(120000, -99));
    }
    @Test
    void process_ShouldReturnDivision_WhenInputNegativeDividendAndPositiveDivisor(){
        String expected =   "_120000│99\n" +
                            "  99   │----\n" +
                            " ---   │1212\n" +
                            " _210\n" +
                            "  198\n" +
                            "  ---\n" +
                            "  _120\n" +
                            "    99\n" +
                            "   ---\n" +
                            "   _210\n" +
                            "    198\n" +
                            "    ---\n" +
                            "     12\n";
        Assertions.assertEquals(expected, division.getDivision(-120000, 99));
    }
    @Test
    void process_ShouldReturnDivision_WhenInputNegativeDividendAndNegativeDivisor(){
        String expected =   "_120000│99\n" +
                            "  99   │----\n" +
                            " ---   │1212\n" +
                            " _210\n" +
                            "  198\n" +
                            "  ---\n" +
                            "  _120\n" +
                            "    99\n" +
                            "   ---\n" +
                            "   _210\n" +
                            "    198\n" +
                            "    ---\n" +
                            "     12\n";
        Assertions.assertEquals(expected, division.getDivision(-120000, -99));
    }
    @Test
    void process_ShouldReturnDivision_WhenInputDividendMaxIntAndDivisorEqual1(){
        String expected =
                "_2147483647│1\n" +
                " 2         │----------\n" +
                " -         │2147483647\n" +
                " _1\n" +
                "  1\n" +
                "  -\n" +
                "  _4\n" +
                "   4\n" +
                "   -\n" +
                "   _7\n" +
                "    7\n" +
                "    -\n" +
                "    _4\n" +
                "     4\n" +
                "     -\n" +
                "     _8\n" +
                "      8\n" +
                "      -\n" +
                "      _3\n" +
                "       3\n" +
                "       -\n" +
                "       _6\n" +
                "        6\n" +
                "        -\n" +
                "        _4\n" +
                "         4\n" +
                "         -\n" +
                "         _7\n" +
                "          7\n" +
                "          -\n" +
                "          0\n";
        Assertions.assertEquals(expected, division.getDivision(2147483647, 1));
    }
}
